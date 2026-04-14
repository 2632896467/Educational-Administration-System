<<<<<<< HEAD
# 教务管理平台

本项目是一个面向高校师生的教务信息化管理系统，采用前后端分离架构，覆盖选课、成绩管理、排课与用户管理等核心业务场景。

## 项目简介

- 学生端支持课程查询、在线选课/退课、成绩查询
- 教师端支持成绩录入与更新
- 管理员端支持用户管理、课程管理、排课管理
- 针对选课高并发场景，使用乐观锁防止课程超选

## 技术栈

- 后端：Spring Boot、Spring Data JPA、Hibernate Validator
- 前端：Vue 2、Vue Router、Element UI、Axios
- 数据库：MySQL
- 构建工具：Maven、npm

## 核心功能

### 学生模块

- 课程列表查询（可按学期筛选）
- 在线选课与退课
- 个人成绩查询

### 教师模块

- 按课程录入学生成绩
- 支持成绩更新与备注维护

### 管理员模块

- 用户创建与角色管理（学生/教师/管理员）
- 课程创建与容量设置
- 课程排课与排课信息查询

## 高并发选课方案

为避免选课高峰期出现超选，后端采用乐观锁策略：

- `course` 表维护 `selected_count`（已选人数）和 `version`（版本号）
- 选课更新语句带版本条件：`WHERE id = ? AND version = ? AND selected_count < capacity`
- 更新失败触发重试机制
- `course_selection` 表唯一索引 `(student_id, course_id)` 防止重复选课

## API 示例

- `POST /api/auth/login` 用户登录
- `GET /api/courses` 课程列表
- `POST /api/selections` 学生选课
- `DELETE /api/selections/{id}?studentId=3` 学生退课
- `GET /api/scores/me?studentId=3` 学生成绩查询
- `POST /api/scores` 教师录入成绩
- `POST /api/admin/users` 管理员创建用户
- `POST /api/admin/courses` 管理员创建课程
- `POST /api/admin/schedules` 管理员创建排课

## 项目结构

```text
Educational Administration System/
├─ backend
│  ├─ pom.xml
│  └─ src/main
│     ├─ java/com/edu/admin
│     │  ├─ controller
│     │  ├─ service
│     │  ├─ repository
│     │  ├─ entity
│     │  ├─ dto
│     │  └─ common
│     └─ resources/application.yml
├─ frontend
│  ├─ package.json
│  ├─ public/index.html
│  └─ src
│     ├─ api
│     ├─ router
│     └─ views
├─ sql
│  └─ init.sql
└─ README.md
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.9+
- Node.js 18+（npm 9+）
- MySQL 8+

### 2. 初始化数据库

执行 `sql/init.sql`，或在 MySQL 中运行：

```sql
source sql/init.sql;
```

### 3. 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端默认地址：`http://localhost:8080`

### 4. 启动前端

```bash
cd frontend
npm install
npm run serve
```

前端默认地址：`http://localhost:8081`

## 演示账号

- 管理员：`admin / 123456`
- 教师：`teacher01 / 123456`
- 学生：`student01 / 123456`

## 说明

- 当前项目使用简化认证（Mock Token）便于教学与演示。
- 生产环境建议接入 Spring Security + JWT + 密码加密存储。
=======
# -spring-boot-
一款面向高校师生的教务信息化管理平台，涵盖学生选课、成绩查询、教师成绩录入、管理员排课与用户管 理等核心业务模块 。
>>>>>>> 7df32a7755dbccdb5cb1edc4f84c7eef93a839b3
