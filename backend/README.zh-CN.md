# Finance Manager Backend

[English](README.md) | [中文](README.zh-CN.md)


一个基于 Spring Boot 的 Finance Manager 后端服务。

该模块提供用户认证、账户管理、分类管理、收支记账和报表统计等接口。项目使用 JWT 进行认证，使用 MyBatis-Plus 操作数据库，使用 MySQL 作为数据存储。

## 技术栈

* Java 21
* Spring Boot 3
* Spring Security
* JWT
* MyBatis-Plus
* MySQL
* Maven

## 项目结构

```
backend/                                  # 后端文件夹
├── src/                                  # 源代码
│   └── main/                             # 主要代码
│       ├── java/com/example/financemanager/ # Java 代码
│       │   ├── common/                   # 公共工具
│       │   │   └── Result.java           # 公共返回结果
│       │   ├── config/                   # 应用设置
│       │   ├── controller/               # 网页接口 (API)
│       │   │   ├── UserController.java       # 用户接口
│       │   │   ├── AccountController.java    # 账户接口
│       │   │   ├── CategoryController.java   # 分类接口
│       │   │   ├── TransactionController.java# 交易接口
│       │   │   └── ReportController.java     # 报表接口
│       │   ├── dto/                      # 接收的数据
│       │   │   ├── LoginRequest.java         # 登录数据
│       │   │   ├── RegisterRequest.java      # 注册数据
│       │   │   ├── AccountCreateRequest.java # 建账户数据
│       │   │   ├── CategoryCreateRequest.java# 建分类数据
│       │   │   └── TransactionCreateRequest.java # 建交易数据
│       │   ├── entity/                   # 数据库表
│       │   │   ├── User.java                 # 用户表
│       │   │   ├── Account.java              # 账户表
│       │   │   ├── Category.java             # 分类表
│       │   │   └── Transaction.java          # 交易表
│       │   ├── exception/                # 错误处理
│       │   │   ├── BusinessException.java    # 业务错误
│       │   │   └── GlobalExceptionHandler.java # 错误捕获
│       │   ├── mapper/                   # 数据库工具
│       │   │   ├── UserMapper.java           # 用户库工具
│       │   │   ├── AccountMapper.java        # 账户库工具
│       │   │   ├── CategoryMapper.java       # 分类库工具
│       │   │   └── TransactionMapper.java    # 交易库工具
│       │   ├── security/                 # 登录和安全
│       │   │   ├── JwtTokenProvider.java     # 生成令牌
│       │   │   ├── JwtAuthenticationFilter.java # 检查令牌
│       │   │   ├── SecurityConfig.java       # 安全规则
│       │   │   ├── SecurityUser.java         # 安全用户信息
│       │   │   └── SecurityUtils.java        # 安全工具
│       │   ├── service/                  # 业务规则
│       │   └── vo/                       # 发送给前端的数据
│       │       ├── UserVO.java               # 用户视图数据
│       │       └── ReportVO.java             # 报表视图数据
│       └── resources/                    # 资源文件
│           ├── application.yml           # 主要设置
│           └── application-dev.yml       # 开发设置
└── pom.xml                               # 工具列表
```

## 主要功能

* 用户注册与登录
* JWT 身份认证
* BCrypt 密码加密
* 基于当前登录用户的数据访问
* 当前用户账户管理
* 当前用户分类管理
* 收入与支出记账
* 自动更新账户余额
* 财务报表统计
* 统一返回格式
* 全局异常处理
* 请求参数校验

## 接口模块

### 1. 认证接口

* POST /api/auth/login
* POST /api/auth/register

### 2. 账户接口

* GET /api/accounts
* POST /api/accounts

### 3. 分类接口

* GET /api/categories
* POST /api/categories

### 4. 交易接口

* GET /api/transactions
* POST /api/transactions

### 5. 报表接口

* GET /api/reports/stats

## 数据库表

后端主要使用以下数据表：

* user
* account
* category
* transaction

## 配置说明

后端使用 `application.yml` 和 `application-dev.yml` 进行配置。

数据库配置示例：

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/finance_manager?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
```

JWT 配置示例：

```
app:
  jwt:
    secret: finance-manager-super-secret-key-change-me-please-2026-123456
    expire-millis: 604800000
```

## 运行方式

### 1. 创建数据库

创建名为 `finance_manager` 的 MySQL 数据库。

### 2. 导入表结构

在启动服务前，先导入项目所需的数据库表结构。

### 3. 启动后端

```
cd backend
mvn spring-boot:run
```

或者先打包再运行：

```
mvn clean package
java -jar target/finance-manager-0.0.1-SNAPSHOT.jar
```

## 认证流程

1. 用户向登录接口提交用户名和密码
2. 后端校验用户信息
3. 后端生成 JWT token
4. 前端在本地保存 token
5. 前端在 Authorization 请求头中携带 token
6. 后端解析 token 并获取当前登录用户信息

## 返回格式

所有接口统一返回如下 JSON 数据：

```
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

## 注意事项

* 后端从 JWT token 中获取当前用户 ID
* 受保护接口不应信任前端传入的 userId
* 密码不能以明文形式存储
* 如果旧测试用户使用的是明文密码，在启用 BCrypt 后请重新注册测试用户

## 后续可优化方向

* 增加修改和删除交易接口
* 增加预算模块
* 增加更多报表维度
* 增加导出功能
* 增加生产环境配置与部署脚本

## License

该模块仅用于学习和练习。

