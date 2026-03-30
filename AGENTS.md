# AGENTS.md

本文件为 AI 编码助手提供项目上下文、架构约定与编码规范。所有 AI Agent 在修改本项目代码前，**必须**遵循以下规则。

## 项目概述

**Catalogue of Life (Server-Side)** — 一个基于 Java 21 / Spring Boot 3.5.0
的生物名录数据聚合后端系统。核心能力是通过多源爬虫采集全球生物分类数据（物种、生命树、百科等），经清洗后持久化至 MySQL，并通过
Elasticsearch 提供检索能力。

## 技术栈

| 层级      | 技术选型                                | 版本                                                     |
|---------|-------------------------------------|--------------------------------------------------------|
| 运行时     | JDK                                 | 21 (sdkman 管理，切换 JDK8: `sdk use java 8.0.482.fx-zulu`) |
| 框架      | Spring Boot                         | 3.5.0                                                  |
| ORM     | MyBatis                             | 3.5.x + mybatis-spring-boot 3.0.4                      |
| 数据库     | MySQL                               | 8.x (Druid 1.2 连接池)                                    |
| 搜索      | Elasticsearch                       | 7.17.26 (High Level REST Client)                       |
| 爬虫引擎    | WebMagic                            | 0.8.0                                                  |
| HTML 解析 | Jsoup                               | 1.17.2                                                 |
| JSON    | Gson 2.11 / FastJson2 (WebMagic 内部) |
| 构建      | Maven                               | 3.x (Wrapper included)                                 |

## 模块架构

```
server-side/                        # Maven 多模块父工程 (pom packaging)
├── common/                         # 基础设施层
│   ├── controller/BaseController   # Controller 接口 (参数解析, DataTable 排序)
│   ├── exception/GlobalExceptionHandler  # 全局异常拦截 (@RestControllerAdvice)
│   ├── response/Result<T>          # 统一 API 响应包装
│   ├── response/BaseResponse<T>    # 旧版响应包装 (兼容)
│   ├── utils/                      # GsonUtil, HttpUtil 等
│   └── aop/                        # 切面
│
├── dao/                            # 数据访问层
│   ├── {domain}/bean/              # JPA/MyBatis 实体 (Lombok @Data)
│   ├── {domain}/mapper/            # MyBatis Mapper 接口
│   ├── {domain}/services/          # DAO Service 接口 + impl/
│   └── resources/mapper/**/*.xml   # MyBatis XML 映射文件
│
├── manager-web/                    # Web 控制层
│   ├── {domain}/controller/        # REST Controller
│   └── config/                     # Web 相关配置
│
├── spider/                         # 爬虫引擎层
│   ├── {source}/processor/         # WebMagic PageProcessor (网络解析)
│   ├── {source}/pipeline/          # WebMagic Pipeline (数据入库)
│   ├── {source}/services/          # 爬虫业务调度 Service
│   ├── {source}/controller/        # 爬虫触发 API
│   ├── {source}/bean/bo/           # 爬虫业务对象
│   ├── {source}/bean/convertor/    # BO → Entity 转换器
│   ├── config/AsyncConfig          # Spring 异步线程池 (spiderTaskExecutor)
│   └── constants/UrlConstants      # 目标站点 URL 常量
│
└── es-common/                      # Elasticsearch 封装层
    ├── repositories/               # ES 仓储抽象
    ├── services/                   # ES 服务封装
    └── utils/                      # ES 工具类
```

## 核心编码规范

### 1. API 响应规范

所有 `manager-web` 下的 Controller **必须**使用 `Result<T>` 包装返回值：

```java
// ✅ 正确
@GetMapping("list")
public Result<List<Species>> list() {
    return Result.success(speciesDaoService.queryList());
}

// ❌ 禁止直接返回裸对象
@GetMapping("list")
public List<Species> list() { ...}
```

**唯一例外**：兼容 jQuery DataTables 的分页接口可直接返回 `PageResult`。

### 2. Spider 模块 — Processor/Pipeline 分离原则

这是本项目最核心的架构约束：

```
┌─────────────┐     ┌──────────────┐     ┌────────────────┐
│   Service    │────▶│  Processor   │────▶│    Pipeline     │
│  (调度编排)   │     │  (网络解析)   │     │   (数据入库)    │
└─────────────┘     └──────────────┘     └────────────────┘
```

- **Processor** (`implements PageProcessor`): 只做网络响应解析。JSON/HTML → `page.putField()`。**绝不操作数据库**。
- **Pipeline** (`implements Pipeline`): 只做数据持久化。从 `resultItems.get()` 取数据 → 调用 DaoService 入库。**绝不发起网络请求
  **。
- **Service**: 只做任务编排。通过 `Spider.create(processor).addPipeline(pipeline).run()` 驱动。**绝不包含解析或入库逻辑**。

```java
// ✅ 正确的 Service 写法
Spider.create(speciesProcessor)
    .

addUrl(UrlConstants.SPECIES_FETCH_URL +id)
    .

addPipeline(speciesPipeline)
    .

thread(1)
    .

run();

// ❌ 禁止在 Service 中直接调用 HttpUtil + 手动解析
String response = HttpUtil.get(url);
SpeciesResponse resp = GsonUtil.fromJson(response, ...);
        speciesDaoService.

insert(...);
```

### 3. 异步执行规范

- **必须使用** Spring 管理的 `spiderTaskExecutor`（定义于 `AsyncConfig`），通过 `@Qualifier("spiderTaskExecutor")` 注入。
- **禁止**创建 `new Thread()`、`static ThreadPoolExecutor` 或任何手动线程管理。
- 异步任务使用 `CompletableFuture.runAsync(() -> { ... }, spiderTaskExecutor)`。

### 4. 实体与数据层约定

- **实体类** (`dao/{domain}/bean/`): 使用 Lombok `@Data`，字段名为 camelCase。
- **MyBatis Mapper XML** (`dao/resources/mapper/`): 表名以 `t_` 前缀，列名为 snake_case。
- **BO 对象** (`spider/{source}/bean/bo/`): 爬虫原始数据模型，字段名保持与外部 API 一致（通常为 snake_case）。
- **Convertor** (`spider/{source}/bean/convertor/`): BO → Entity 的转换器，使用 `@Component` 注册。
- 主键策略：`t_species` 使用外部 32 位 hex ID（VARCHAR）；其余表使用 `INT AUTO_INCREMENT`。
- 时间字段统一用 `BIGINT` 存储毫秒时间戳 (`create_time`, `last_modify_time`)。

### 5. 数据库操作约定

- 所有 Mapper XML 的动态查询使用统一的 `whereSql` 模式，通过 `HashMap<String, String>` 传参：
    - `key` = 字段名 → 等值匹配
    - `key_eq` / `key_ne` / `key_lt` / `key_gt` / `key_like` → 条件后缀
    - `key_isn` / `key_isnn` → IS NULL / IS NOT NULL
    - `limit` → SQL LIMIT 子句
    - `datatableorderby` → ORDER BY 子句

### 6. Spring Bean 注册

- Processor、Pipeline、Convertor 均使用 `@Component` 注册。
- Service 实现类使用 `@Service`。
- 配置类使用 `@Configuration`。

## 新增数据源检查清单

当需要接入新的生物数据源时，按以下步骤操作：

1. 在 `spider/constants/UrlConstants.java` 添加目标 URL 常量。
2. 创建 `spider/{source}/bean/bo/` 定义原始数据模型。
3. 创建 `spider/{source}/bean/convertor/` 实现 BO → Entity 转换。
4. 创建 `spider/{source}/processor/XxxProcessor.java` 实现 `PageProcessor`。
5. 创建 `spider/{source}/pipeline/XxxPipeline.java` 实现 `Pipeline`。
6. 创建 `spider/{source}/services/XxxSpiderService.java` 接口 + `impl/` 实现。
7. （可选）创建 `spider/{source}/controller/` 暴露触发 API。
8. 对应的 `dao/{domain}/` 下创建实体、Mapper、DaoService。
9. 在 `dao/resources/mapper/{domain}/` 创建 MyBatis XML。

## 构建与验证

```bash
# 编译检查（每次修改后必须通过）
mvn clean compile

# 运行 spider 模块
mvn spring-boot:run -pl spider

# 运行 manager-web 模块
mvn spring-boot:run -pl manager-web
```

## 禁止事项

- ❌ 不允许在 Service 层直接使用 `HttpUtil`、`Jsoup` 进行网络请求
- ❌ 不允许创建 `new Thread()` 或静态 `ThreadPoolExecutor`
- ❌ 不允许 Controller 返回裸对象（必须包装为 `Result<T>`）
- ❌ 不允许在 Processor 中调用 DaoService
- ❌ 不允许在 Pipeline 中发起网络请求
- ❌ 不允许使用 Windows 绝对路径（如 `D:\xxx`），文件路径一律使用相对路径
- ❌ 不允许硬编码数据库连接信息，必须通过 `application.yml` 配置

## 文件参考

- 技术架构详情 → [TECHNICAL.md](TECHNICAL.md)
- 数据库建表脚本 → [schema.sql](schema.sql)
- 项目介绍 → [README.md](README.md)
