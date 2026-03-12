# Finance Manager

[English](README.md) | [中文](README.zh-CN.md)

A simple personal finance management system built with Spring Boot and Vue 3.

This project helps users manage accounts, categories, income and expense records, and view financial reports with charts. It uses a front-end and back-end separated architecture, JWT-based login authentication, and MyBatis-Plus for database operations.

## Features

* User registration and login
* JWT-based authentication
* Password encryption with BCrypt
* Account management
* Category management
* Income and expense recording
* Automatic balance update
* Financial report statistics
* Pie chart and bar chart visualization
* Global exception handling
* Request parameter validation

## Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* JWT
* MyBatis-Plus
* MySQL
* Maven

### Frontend

* Vue 3
* Vite
* Vue Router
* Pinia
* Axios
* Element Plus
* ECharts

## Project Structure

```
finance-example/                      # Main folder
├── backend/                          # Backend folder (Java)
│   ├── src/                          # Source code
│   │   └── main/                     # Main code
│   │       ├── java/com/example/financemanager/ # Java code
│   │       │   ├── common/           # Shared tools
│   │       │   ├── config/           # App settings
│   │       │   ├── controller/       # Web links (API)
│   │       │   ├── dto/              # Data to send
│   │       │   ├── entity/           # Database tables
│   │       │   ├── exception/        # Error handling
│   │       │   ├── mapper/           # Database tools
│   │       │   ├── security/         # Login and safety
│   │       │   ├── service/          # Business rules
│   │       │   └── vo/               # View data
│   │       └── resources/            # App files
│   │           ├── application.yml     # Main settings
│   │           └── application-dev.yml # Dev settings
│   └── pom.xml                       # Tool list
│
└── frontend/                         # Frontend folder (Vue)
    ├── src/                          # Source code
    │   ├── api/                      # Link to backend
    │   ├── layout/                   # Page layout
    │   ├── router/                   # Page links
    │   ├── stores/                   # Saved app data
    │   ├── utils/                    # Helpful tools
    │   ├── views/                    # Page views
    │   ├── App.vue                   # Main Vue page
    │   ├── main.js                   # Main JS file
    │   └── style.css                 # Page styles
    └── package.json                  # Tool list
```

## Main Modules

### 1. Authentication Module

This module supports user registration and login.

Main points:

* Users can register with username and password
* Passwords are stored after BCrypt encryption
* After login, the backend returns a JWT token
* The frontend stores user information locally
* Protected APIs require a valid token

### 2. Account Module

This module manages user accounts.

Main points:

* View current user accounts
* Add new accounts
* Default accounts are created automatically after registration
* Account balance is updated when a transaction is recorded

### 3. Category Module

This module manages transaction categories.

Main points:

* View current user categories
* Add new categories
* Categories are divided into INCOME and EXPENSE
* Default categories are created automatically after registration

### 4. Transaction Module

This module records income and expense data.

Main points:

* Create income records
* Create expense records
* Update account balance automatically
* Prevent negative balance in expense operations
* Save transaction history for report analysis

### 5. Report Module

This module provides financial statistics and chart data.

Main points:

* Total income
* Total expense
* Balance summary
* Expense category pie chart
* Income and expense trend bar chart
* Transaction list by time range

## Database Design

The project mainly uses these tables:

* user
* account
* category
* transaction

### Table Description

#### user

Stores user basic information.

Main fields:

* id
* username
* password
* create_time

#### account

Stores user accounts.

Main fields:

* id
* user_id
* name
* balance
* create_time

#### category

Stores user categories.

Main fields:

* id
* user_id
* name
* type
* create_time

#### transaction

Stores income and expense records.

Main fields:

* id
* user_id
* account_id
* category_id
* type
* amount
* remark
* create_time

## API Overview

### Auth

* POST /api/auth/login
* POST /api/auth/register

### Accounts

* GET /api/accounts
* POST /api/accounts

### Categories

* GET /api/categories
* POST /api/categories

### Transactions

* GET /api/transactions
* POST /api/transactions

### Reports

* GET /api/reports/stats

## Environment Setup

### Backend

1. Create a MySQL database named `finance_manager`
2. Import the table structure
3. Configure database connection in `application-dev.yml` or environment variables
4. Start the backend project

Example configuration:

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/finance_manager?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
```

Run backend:

```
cd backend
mvn spring-boot:run
```

### Frontend

Install dependencies:

```
cd frontend
npm install
```

Start development server:

```
npm run dev
```

Build project:

```
npm run build
```

## Login Flow

1. User submits username and password
2. Backend verifies the user
3. Backend generates JWT token
4. Frontend stores user information
5. Frontend sends token in the Authorization header
6. Backend parses token and gets current user identity

## Security Design

* Passwords are encrypted with BCrypt
* JWT is used for stateless authentication
* The backend gets current user ID from token
* The frontend no longer passes userId directly for protected business queries
* Unauthorized requests return 401
* Global exception handler returns unified response format

## Response Format

The backend uses a unified response structure:

```
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

## Default Data Logic

After a new user registers:

* Default accounts are created automatically
* Default income categories are created automatically
* Default expense categories are created automatically

## Future Improvements

* Edit and delete transactions
* Monthly budget management
* More report dimensions
* Export records
* Better mobile layout
* Deployment configuration for production

## License

This project is for study and practice use.
