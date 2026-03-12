# Finance Manager
 [English](README.md) | [中文](README.zh-CN.md)

一个基于 Spring Boot 和 Vue 3 的个人财务管理系统。

本项目用于帮助用户管理账户、分类、收支记录，并通过图表查看财务统计信息。项目采用前后端分离架构，使用 JWT 进行登录认证，使用 MyBatis-Plus 操作数据库。

## 功能介绍

* 用户注册与登录
* 基于 JWT 的身份认证
* 使用 BCrypt 进行密码加密
* 账户管理
* 分类管理
* 收入与支出记账
* 自动更新账户余额
* 财务报表统计
* 饼图与柱状图可视化
* 全局异常处理
* 请求参数校验

## 技术栈

### 后端

* Java 21
* Spring Boot 3
* Spring Security
* JWT
* MyBatis-Plus
* MySQL
* Maven

### 前端

* Vue 3
* Vite
* Vue Router
* Pinia
* Axios
* Element Plus
* ECharts

## 项目结构

```
finance-example/                      # 主文件夹
├── backend/                          # 后端文件夹 (Java)
│   ├── src/                          # 源代码
│   │   └── main/                     # 主要代码
│   │       ├── java/com/example/financemanager/ # Java 代码
│   │       │   ├── common/           # 公共工具
│   │       │   ├── config/           # 应用设置
│   │       │   ├── controller/       # 网页接口 (API)
│   │       │   ├── dto/              # 发送的数据
│   │       │   ├── entity/           # 数据库表
│   │       │   ├── exception/        # 错误处理
│   │       │   ├── mapper/           # 数据库工具
│   │       │   ├── security/         # 登录和安全
│   │       │   ├── service/          # 业务规则
│   │       │   └── vo/               # 视图数据
│   │       └── resources/            # 资源文件
│   │           ├── application.yml     # 主要设置
│   │           └── application-dev.yml # 开发设置
│   └── pom.xml                       # 工具列表
│
└── frontend/                         # 前端文件夹 (Vue)
    ├── src/                          # 源代码
    │   ├── api/                      # 连接后端
    │   ├── layout/                   # 页面布局
    │   ├── router/                   # 页面链接
    │   ├── stores/                   # 保存的应用状态
    │   ├── utils/                    # 帮助工具
    │   ├── views/                    # 页面视图
    │   ├── App.vue                   # 主 Vue 页面
    │   ├── main.js                   # 主 JS 文件
    │   └── style.css                 # 页面样式
    └── package.json                  # 工具列表
```

## 主要模块

### 1. 认证模块

该模块负责用户注册和登录。

主要内容：

* 用户可以使用用户名和密码注册
* 密码使用 BCrypt 加密后保存
* 登录成功后后端返回 JWT token
* 前端将用户信息保存在本地
* 受保护接口必须携带有效 token

### 2. 账户模块

该模块负责管理用户账户。

主要内容：

* 查看当前用户账户列表
* 新增账户
* 注册后自动创建默认账户
* 记账时自动更新账户余额

### 3. 分类模块

该模块负责管理交易分类。

主要内容：

* 查看当前用户分类列表
* 新增分类
* 分类分为 INCOME 和 EXPENSE
* 注册后自动创建默认分类

### 4. 交易模块

该模块负责记录收入和支出数据。

主要内容：

* 新增收入记录
* 新增支出记录
* 自动更新账户余额
* 支出时防止余额变成负数
* 保存交易流水，供报表分析使用

### 5. 报表模块

该模块负责提供财务统计和图表数据。

主要内容：

* 总收入
* 总支出
* 结余统计
* 支出分类饼图
* 收支趋势柱状图
* 按时间范围查看交易流水

## 数据库设计

项目主要使用以下数据表：

* user
* account
* category
* transaction

### 表说明

#### user

用于存储用户基础信息。

主要字段：

* id
* username
* password
* create_time

#### account

用于存储用户账户信息。

主要字段：

* id
* user_id
* name
* balance
* create_time

#### category

用于存储用户分类信息。

主要字段：

* id
* user_id
* name
* type
* create_time

#### transaction

用于存储收支流水信息。

主要字段：

* id
* user_id
* account_id
* category_id
* type
* amount
* remark
* create_time

## 接口概览

### 认证接口

* POST /api/auth/login
* POST /api/auth/register

### 账户接口

* GET /api/accounts
* POST /api/accounts

### 分类接口

* GET /api/categories
* POST /api/categories

### 交易接口

* GET /api/transactions
* POST /api/transactions

### 报表接口

* GET /api/reports/stats

## 环境配置

### 后端

1. 创建名为 `finance_manager` 的 MySQL 数据库
2. 导入表结构
3. 在 `application-dev.yml` 或环境变量中配置数据库连接
4. 启动后端项目

示例配置：

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/finance_manager?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
```

启动后端：

```
cd backend
mvn spring-boot:run
```

### 前端

安装依赖：

```
cd frontend
npm install
```

启动开发环境：

```
npm run dev
```

构建项目：

```
npm run build
```

## 登录流程

1. 用户提交用户名和密码
2. 后端校验用户信息
3. 后端生成 JWT token
4. 前端保存用户信息
5. 前端在 Authorization 请求头中携带 token
6. 后端解析 token 并获取当前用户身份

## 安全设计

* 密码使用 BCrypt 加密
* 使用 JWT 实现无状态认证
* 后端从 token 中获取当前用户 ID
* 前端不再直接传 userId 查询受保护业务数据
* 未授权请求返回 401
* 全局异常处理统一返回格式

## 统一返回格式

后端统一使用如下返回结构：

```
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

## 默认数据逻辑

新用户注册成功后：

* 自动创建默认账户
* 自动创建默认收入分类
* 自动创建默认支出分类

## 后续可优化方向

* 支持修改和删除交易记录
* 增加月度预算功能
* 增加更多报表维度
* 支持数据导出
* 优化移动端布局
* 增加生产环境部署配置

## License

本项目仅用于学习与练习。

