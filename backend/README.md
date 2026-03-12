# Finance Manager Backend

[English](README.md) | [中文](README.zh-CN.md)

A Spring Boot based backend service for the Finance Manager project.

This module provides user authentication, account management, category management, transaction recording, and report statistics APIs. It uses JWT for authentication, MyBatis-Plus for data access, and MySQL as the database.

## Tech Stack

* Java 21
* Spring Boot 3
* Spring Security
* JWT
* MyBatis-Plus
* MySQL
* Maven

## Project Structure

```
backend/                                  # Backend folder
├── src/                                  # Source code
│   └── main/                             # Main code
│       ├── java/com/example/financemanager/ # Java code
│       │   ├── common/                   # Shared tools
│       │   │   └── Result.java           # Shared return result
│       │   ├── config/                   # App settings
│       │   ├── controller/               # Web links (API)
│       │   │   ├── UserController.java       # User API
│       │   │   ├── AccountController.java    # Account API
│       │   │   ├── CategoryController.java   # Category API
│       │   │   ├── TransactionController.java# Money API
│       │   │   └── ReportController.java     # Report API
│       │   ├── dto/                      # Data sent to backend
│       │   │   ├── LoginRequest.java         # Login data
│       │   │   ├── RegisterRequest.java      # Sign up data
│       │   │   ├── AccountCreateRequest.java # New account data
│       │   │   ├── CategoryCreateRequest.java# New category data
│       │   │   └── TransactionCreateRequest.java # New money data
│       │   ├── entity/                   # Database tables
│       │   │   ├── User.java                 # User table
│       │   │   ├── Account.java              # Account table
│       │   │   ├── Category.java             # Category table
│       │   │   └── Transaction.java          # Money table
│       │   ├── exception/                # Error handling
│       │   │   ├── BusinessException.java    # App error
│       │   │   └── GlobalExceptionHandler.java # Error catcher
│       │   ├── mapper/                   # Database tools
│       │   │   ├── UserMapper.java           # User DB tool
│       │   │   ├── AccountMapper.java        # Account DB tool
│       │   │   ├── CategoryMapper.java       # Category DB tool
│       │   │   └── TransactionMapper.java    # Money DB tool
│       │   ├── security/                 # Login and safety
│       │   │   ├── JwtTokenProvider.java     # Make token
│       │   │   ├── JwtAuthenticationFilter.java # Check token
│       │   │   ├── SecurityConfig.java       # Safety rules
│       │   │   ├── SecurityUser.java         # Safe user info
│       │   │   └── SecurityUtils.java        # Safe tools
│       │   ├── service/                  # Business rules
│       │   └── vo/                       # Data sent to frontend
│       │       ├── UserVO.java               # User view data
│       │       └── ReportVO.java             # Report view data
│       └── resources/                    # App files
│           ├── application.yml           # Main settings
│           └── application-dev.yml       # Dev settings
└── pom.xml                               # Tool list
```

## Main Features

* User register and login
* JWT authentication
* BCrypt password encryption
* Current-user based data access
* Account CRUD support for current user
* Category CRUD support for current user
* Income and expense recording
* Automatic balance update
* Financial report statistics
* Unified response format
* Global exception handling
* Parameter validation

## API Modules

### 1. Auth API

* POST /api/auth/login
* POST /api/auth/register

### 2. Account API

* GET /api/accounts
* POST /api/accounts

### 3. Category API

* GET /api/categories
* POST /api/categories

### 4. Transaction API

* GET /api/transactions
* POST /api/transactions

### 5. Report API

* GET /api/reports/stats

## Database Tables

Main tables used by the backend:

* user
* account
* category
* transaction

## Configuration

The backend uses `application.yml` and `application-dev.yml`.

Example database configuration:

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/finance_manager?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
```

Example JWT configuration:

```
app:
  jwt:
    secret: finance-manager-super-secret-key-change-me-please-2026-123456
    expire-millis: 604800000
```

## How to Run

### 1. Create database

Create a MySQL database named `finance_manager`.

### 2. Import tables

Import the project SQL schema before running the service.

### 3. Start backend

```
cd backend
mvn spring-boot:run
```

Or package first:

```
mvn clean package
java -jar target/finance-manager-0.0.1-SNAPSHOT.jar
```

## Authentication Flow

1. User sends username and password to login API
2. Backend validates the credentials
3. Backend creates JWT token
4. Frontend stores token locally
5. Frontend sends token in Authorization header
6. Backend parses token and gets current user information

## Response Format

All APIs return unified JSON data:

```
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

## Notes

* The backend gets current user ID from JWT token
* Protected APIs should not trust userId from frontend parameters
* Passwords should never be stored in plain text
* If old test users used plain-text passwords, register new users again after enabling BCrypt

## Future Improvements

* Update and delete transaction APIs
* Budget module
* More report dimensions
* Export support
* Production profile and deployment scripts

## License

This module is for study and practice use.



