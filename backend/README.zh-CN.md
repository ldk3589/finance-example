# Finance Manager Backend

基于 **Spring Boot** 的个人理财管理系统后端服务，提供用户登录、账户管理、记账等 RESTful API。

---

## 🧱 技术栈

- Java 21
- Spring Boot 3.x
- MyBatis-Plus
- MySQL
- Maven
- Lombok

---

## 📁 项目结构
    
    backend
    ├── src/main/java/com/example/financemanager
    │   ├── controller    # 控制层（REST API）
    │   ├── service       # 业务逻辑层
    │   ├── mapper        # MyBatis Mapper
    │   ├── entity        # 实体类
    │   ├── dto           # 数据传输对象
    │   ├── vo            # 前端返回对象
    │   ├── common        # 通用返回结果、异常等
    │   └── FinanceManagerApplication.java
    │
    ├── src/main/resources
    │   ├── application.yml
    │   ├── application-dev.yml
    │   └── mapper
    │
    └── pom.xml
---
## ⚙️ 环境要求

-JDK ≥ 17（推荐 21）

-Maven ≥ 3.8

-MySQL ≥ 8.0

🛠️ 本地启动

### 1️⃣ 创建数据库
    CREATE DATABASE finance_manager DEFAULT CHARACTER SET utf8mb4;

### 2️⃣ 修改数据库配置

    src/main/resources/application-dev.yml
    
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/finance_manager?useSSL=false&serverTimezone=Asia/Shanghai
        username: root
        password: 123456

### 3️⃣ 启动项目

    mvn spring-boot:run

或在 IDEA 中运行：

    FinanceManagerApplication


启动成功后：

http://localhost:8080

🔗 接口示例

用户登录

    POST /api/user/login

请求参数：

    {
      "username": "test",
      "password": "123456"
    }


返回示例：

    {
      "code": 0,
      "message": "success",
      "data": {
        "id": 1,
        "username": "test",
        "token": "uuid-token"
      }
    }
