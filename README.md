# 高校知识点共享平台 - 后端

本项目是一个基于 SpringBoot 构建的高校知识点共享平台后端代码。

## 🛠 技术栈
- **核心语言**：Java 21
- **核心框架**：SpringBoot 4.x (Controller / Service / Mapper 三层架构)
- **持久层框架**：MyBatis
- **数据库**：MySQL 8.0
- **缓存中间件**：Redis (用于存储验证码、缓存热点数据)
- **安全认证**：JWT (JSON Web Token) + Spring 拦截器
- **构建工具**：Maven
- **接口测试**：Apifox

## ✨ 核心功能
1. **统一响应体**：自定义 `Result<T>` 泛型类，统一管理 `code`、`msg`、`data`，规范 RESTful 接口返回格式。
2. **无状态鉴权**：基于 JWT + 自定义 `LoginInterceptor` 拦截器，实现用户登录与全局接口鉴权，支持 `@CrossOrigin` 跨域及 OPTIONS 预检放行。
3. **RBAC 权限模型**：数据库设计 `role` 字段，区分管理员 (`admin`) 与普通学生 (`user`)，控制不同接口的访问权限。
4. **多表关联查询**：使用 MyBatis 注解实现 `Comment` 与 `User` 表的 JOIN 查询，查评论时自动带出用户名。
5. **Redis 缓存应用**：结合 Redis 的 TTL（过期时间）特性，实现“发送验证码”及“忘记密码（5分钟过期重置）”业务逻辑。

## 🔗 关联仓库
- **前端代码**：[knowledge-frontend](https://github.com/mengyaoen/knowledge-frontend)

## 🚀 本地运行
1. 执行 `resources` 目录下的 SQL 建库建表脚本。
2. 启动本地 MySQL 和 Redis 服务。
3. 修改 `application.properties` 配置数据库和 Redis 连接密码。
4. 使用 IDEA 运行 `KnowledgePlatformApplication.java`。
5. 后端服务启动在 `http://localhost:8080`。
