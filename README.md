<div align="center">

# Catalogue of Life (Server-Side)

**🌐 生命大百科 (Java 生态核心后端引擎)**

[![JDK](https://img.shields.io/badge/JDK-21-4ea94b.svg?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-6db33f.svg?logo=springboot)](https://spring.io/projects/spring-boot)
[![MyBatis](https://img.shields.io/badge/MyBatis-3.5.x-dc382d.svg?logo=mybatis)](https://mybatis.org/)
[![Elasticsearch](https://img.shields.io/badge/Elasticsearch-7.17.x-005571.svg?logo=elasticsearch)](https://www.elastic.co/)
[![WebMagic](https://img.shields.io/badge/WebMagic-0.8.0-1d70b8.svg)](http://webmagic.io/)

提供从宏观生态树抓取分发，到微观物种实体溯源存储的一体化微服务基础体系。

[架构文档 (TECHNICAL.md)](TECHNICAL.md) · [系统运行反馈 (Issue)]()

</div>

---

## 📖 项目简介

本工程是 `Catalogue of Life` 应用体系的服务器端底座 (server-side)。依托于现代化 JDK 21 和 Spring Boot 3，构建了一个专注于*
*生物名录系统收集**、**清洗规范化**、**物理全景映射聚合**的数据与服务后台。

系统重点关注两大能力：

1. **Spider 数据探险矩阵**: 内嵌基于 WebMagic 0.8.0 的高级多队列爬虫，能够突破物种库 (Species2000)、生命图谱 (
   LifeCategory) 等复杂防御型站点（包含抗级连分析、防重定向、会话 CSRF 回溯维持与隔离），实现异构生态源在后端同源降维并高度分发至库底。
2. **高速映射与数据网管**: 后端依靠 MyBatis 接入关系聚合数据库 MySQL，同时依赖 `es-common` 中的 Transport 与
   RestHighLevel 建立全网字典集的弹性缓冲域与高并发搜索承载层。

## 🚀 核心特性

- 🕸️ **次世代 Spider 并发架构**: 告别古早线程池手写模型，业务调度通过 Pipeline 与 Processor 分层解耦。不仅有效处理阻塞与线程抢驻，而且原生支撑
  CSRF 回声令牌等高难度表单的反爬认证。
- ⚡ **全局规范统一反馈**: Controller 全切面包裹 `@RestControllerAdvice` 隔离栈污染，标准化 `Result<T>` 数据体规范前端交互口径。
- 🔍 **泛化检索生态**: `es-common` 和 `MyBatis` 双轨制承载：精确映射关系数据到生命层级，非结构数据和重度模糊分类通过
  Elasticsearch 集群支持。
- 📦 **模块化单体 (Modularized Monolith)**: 代码分为 `manager-web`、`spider`、`dao`、`es-common`，清晰隔离持久化和执行组件，支持横向和微服务化解体。

## 🛠️ 技术生态

项目技术选型充分考虑了长期的高维护性及前瞻性技术标准：

- **基座**: Java 21 / Spring Boot 3.5.0
- **持久化方案**: MySQL 8.x / MyBatis 3.5.x / Druid 连接池
- **搜索引擎**: Elasticsearch 7.17.26
- **网络采集引擎**: WebMagic 0.8.0 / Jsoup 1.17
- **序列化与工具**: Gson / FastJson2 / Apache Commons Lang3 & IO

## 📦 目录结构

```text
catalogueoflife/server-side/
├── common/         # 基础设施层：通用工具类、全局异常拦截、规范实体 Response
├── dao/            # 数据访问层：MySQL 的持久化映射、MyBatis Mapper及数据实体
├── es-common/      # 数据增强层：基于 ES 7.x 的复合搜索模块封装及底层驱动
├── manager-web/    # 网络控制层：面向下游 Web 的各类 Controller 及 API 网关暴露
└── spider/         # 网际突击层：针对生命百科、维基、Species 等深度采集的隔离化执行管道
```

## ⚙️ 快速拉起指南

1. **前提依赖准备**:
   确保您的机器运行 **JDK 21**、**Maven 3+** 以及包含 **MySQL/Elasticsearch** 环境配置。

2. **检出项目 & 编译**:
   ```bash
   git clone <repo-url> /catalogueoflife
   cd catalogueoflife/server-side
   mvn clean compile
   ```
   > 核心构建测试正常通过后，控制台会输出 `BUILD SUCCESS` 的所有 Reactor Summary。

3. **配置文件指派**:
   请在相应的 resource 根目录下准备 `application.yml`，映射正确的 MySQL 和 Elasticsearch 地址及相关 Druid 和连接池参量。

4. **服务器启动**:
   进入主模块直接部署运行：
   ```bash
   # Spring Boot 原生启动命令类似：
   mvn spring-boot:run -pl manager-web
   ```

---

*Catalogue of Life - 构建所有生命形态的可追溯引擎网络。*
