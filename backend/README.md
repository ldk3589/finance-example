# Finance Manager Backend

[English](README.md) | [中文](README.zh-CN.md)

Finance Manager Backend

A Spring Boot-powered backend service for personal finance management, providing RESTful APIs for user authentication, account management, and transaction tracking.

## 🧱 Tech Stack
- Java: 21

- Framework: Spring Boot 3.x

- ORM: MyBatis-Plus

- Database: MySQL 8.0

- Build Tool: Maven

- Utilities: Lombok

## 📁 Project Structure
    
    backend
    ├── src/main/java/com/example/financemanager
    │   ├── controller    # REST Controllers (API Endpoints)
    │   ├── service       # Business Logic Layer
    │   ├── mapper        # MyBatis Mapper Interfaces
    │   ├── entity        # Domain Entities
    │   ├── dto           # Data Transfer Objects (Request Payloads)
    │   ├── vo            # View Objects (Response Payloads)
    │   ├── common        # Unified Results, Exceptions, Constants
    │   └── FinanceManagerApplication.java
    │
    ├── src/main/resources
    │   ├── application.yml
    │   ├── application-dev.yml
    │   └── mapper        # MyBatis XML Mapping files
    │
    └── pom.xml
---
## ⚙️ Prerequisites
- JDK ≥ 17 (21 recommended)

- Maven ≥ 3.8

- MySQL ≥ 8.0
---
## 🛠️ Local Development
### 1️⃣ Initialize Database

    CREATE DATABASE finance_manager DEFAULT CHARACTER SET utf8mb4;
### 2️⃣ Configure Data Source
Update your credentials in src/main/resources/application-dev.yml:

    spring:
    datasource:
    url: jdbc:mysql://localhost:3306/finance_manager?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
### 3️⃣ Run Application
Using Maven:

    mvn spring-boot:run

Alternatively, run the FinanceManagerApplication class directly within your IDE (IntelliJ IDEA or VS Code).

    Service URL: http://localhost:8080

🔗 API Examples
User Login

    Endpoint: POST /api/user/login

Request Body:
    
    {
    "username": "test",
    "password": "your_password"
    }
Response Example:
    
    {
    "code": 0,
    "message": "success",
    "data": {
    "id": 1,
    "username": "test",
    "token": "uuid-token"
    }
    }
