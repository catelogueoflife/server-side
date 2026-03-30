# Catalogue of Life - Technical Architecture Document

## 1. 架构概览

`Catalogue of Life` 是一个基于 Java 21 和 Spring Boot 3
构建的安全、高性能并行数据聚合集成调度后端（server-side）。系统旨在全自动或受控地搜集、持久化、索引与检索生命物种分类体系及详尽属性（如纲目科属、维基解释、百度百科、全球/中国名录等）。

本工程利用微服务前奏式的模块化单体架构（Modular Monolith）组织代码结构。

### 核心模块划分

- **manager-web**: Web 控制层。基于 Spring Web 提供 RESTful API，并借助统一拦截器处理异常及规范化响应。
- **spider**: 高性能数据潜行器。集成了定制化的 WebMagic 框架（包含 `PageProcessor`, `Pipeline` 拦截体系）进行海量生物字典源抽取与防反爬会话隔离管理。
- **es-common**: 弹性索引抽象核心。封装了对 Elasticsearch (7.17.x) 的映射检索与海量词条统计功能。
- **dao**: 数据持久层。基于 MyBatis 及 PageHelper 提供物理层的 MySQL 关系映射交互。
- **common**: 全局领域模型。集成基础对象转换（Gson转换、DTO、BO）、常量、全局上下文拦截系统。

## 2. 技术栈标准

- **Runtime环境**: JDK 21 (推荐使用 sdkman 管理，采用兼容的新版模式)
- **核心框架**: Spring Boot 3.5.0
- **持久层中间件**:
    - MySQL 8.x + MyBatis 3.5
    - Alibaba Druid 1.2
- **搜索引擎**: Elasticsearch 7.17.26 (Transport / High Level Rest Client)
- **爬行引擎组件**:
    - WebMagic 0.8.0 (高度解耦，处理高频爬取，防反爬机制)
    - Jsoup 1.17
    - HttpComponents (HttpClient, AsyncClient)
- **JSON解析**: Gson 2.11 + FastJson2 (WebMagic 级联使用)

## 3. Spider 模块深度技术指南 🕸️

`spider` 模块是系统维护生态数据生命力的核心所在。在最新的重构演进中，我们废弃了所有过时的显式 Thread 创建与同步长循环，全面基于
WebMagic 0.8.0 进行流水线设计。

### 3.1 Species 架构图景

- **机制**: 原本采用强耦合循环发包探测物种信息的逻辑被彻底拆解。现在业务发起抽拉指令后，异步激活 Spring 的
  `@Qualifier("spiderTaskExecutor")` 执行器，将 `UrlConstants.SPECIES_FETCH_URL` 及参数压入 Spider 源。
- **清洗与持久化**: `SpeciesProcessor` 承接拉取后进行脏数据清洗并封装到 `ResultItems`。`SpeciesPipeline`
  专注将源数据执行严格校验后分解并插入至 `Species`, `CommonName`, `Synonyms`, `RecordScrutiny` 等相关从表。

### 3.2 Species2000Cn 高危防护

针对名录树严密的 CSRF 防护与 Session 绑定：

1. **处理器状态封存**: `Species2000CnProcessor` 接管核心交互。一旦 `POST` 抓取触发 `Forbidden`，拦截流转并发射 `GET` 至
   Auth 页面。
2. **提取与自修复**: 利用 DOM xpath 获取隐藏表单域的 `_csrf` 令牌，以及结合 `Site` 对象中的无管理拦截策略提取
   `JSESSIONID` Set-Cookie 头。
3. **管线同步回调**: 在 Service 层采用 `AtomicReference` 及内联 `Pipeline`，实现异步 WebMagic 蜘蛛流到内部控制层同步要求的数据封装聚合响应。

## 4. 异常与回执治理协议 (API 契约)

全系统的 Controller 输出统一包裹于 `Result<T>` 工具类中（或旧版本的 `BaseResponse<T>`），由 `@RestControllerAdvice` 注解的
`GlobalExceptionHandler` 进行收口规范。

### 返回规范

```json
{
  "code": 200,
  "msg": "success",
  "data": { ... }
}
```

任何业务与底层组件向外发出的 `Exception` 或特定逻辑熔断，都会在切面被转化为具有合理 HTTP 状态及错误代码的消息结构以满足前端交互。

## 5. 项目拓展指导

若需引入新的生物数据源头：

1. 在 `spider` 模块下的 `com.touchkiss.catelogueoflife.spider` 子包中创建对应生态名称的 Processor。
2. 实现 `PageProcessor` 并在 `process()` 处理解析逻辑。
3. 创建对应的 `Pipeline` 执行特定落表。
4. 将 `Spider.create(xxxProcessor).addPipeline(xxxPipeline)` 编入业务任务池执行列表（或异步触发器）。
