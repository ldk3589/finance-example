💰 Finance Example (English Version)

[English](README.md) | [中文](README.zh-CN.md)

A full-stack Personal Finance Management System featuring user authentication, automatic account initialization, bookkeeping, and financial data visualization.

This is a learning/practice-oriented project designed for mastering the Spring Boot + Vue 3 stack.

📁 Project Structure
    
    finance-example
    ├── backend/          # Backend: Spring Boot project (finance-manager)
    │   ├── src/
    │   ├── pom.xml
    │   └── README.md     # Backend-specific documentation
    │
    ├── frontend/         # Frontend: Vue 3 + Vite project (finance-frontend)
    │   ├── src/
    │   ├── package.json
    │   └── README.md     # Frontend-specific documentation
    │
    └── README.md         # Main project documentation (Current file)
🚀 Key Features
✅ Implemented Features
    
    👤 Authentication: Seamless login (Auto-registers new users and initializes default accounts).
    
    🧾 Bookkeeping: Record transactions with specific accounts, categories, and amounts.
    
    🏦 Multi-Account Management: Independent account tracking for each user.
    
    📊 Data Visualization: Statistical financial reports supported by charts.
    
    🔐 Security: Auth-guards to redirect unauthenticated users to the login page.

🧱 Tech Stack

    Backend
        Java 21
        
        Spring Boot
        
        MyBatis-Plus
        
        MySQL
        
        Maven

    Frontend
        Vue 3 (Composition API)
        
        Vite
        
        Element Plus
        
        Axios
        
        Vue Router

⚙️ Prerequisites

    JDK ≥ 17 (21 recommended)
    
    Node.js ≥ 18
    
    MySQL ≥ 8.0
    
    Maven ≥ 3.8

🛠️ Quick Start

    1️⃣ Backend Setup (Spring Boot)
        Initialize Database:
        SQL
        CREATE DATABASE finance_manager DEFAULT CHARACTER SET utf8mb4;

    Configure Database: Edit backend/src/main/resources/application-dev.yml with your credentials:
        spring:
          datasource:
            url: jdbc:mysql://localhost:3306/finance_manager?useSSL=false&serverTimezone=Asia/Shanghai
            username: root
            password: your_password

    Run Application:
        cd backend
        mvn spring-boot:run
  The API will be available at: http://localhost:8080

    2️⃣ Frontend Setup (Vue 3)
    Install & Run:
        cd frontend
        npm install
        npm run dev
  Access the web UI at: http://localhost:5173

🔗 API Overview (Example)

  User Login POST /api/user/login

    Request Body:

        JSON
        {
        "username": "test",
        "password": "your_password"
        }

    Response Body:

        JSON
        {
        "code": 0,
        "message": "success",
        "data": {
        "id": 1,
        "username": "test",
        "token": "xxxx-xxxx"
        }
        }
🔐 Authentication Logic
    
    New Users: Upon the first login, the system automatically creates the User entity and initializes default accounts (e.g., "Cash").
    
    Session Persistence: User info is stored in sessionStorage (or localStorage) to maintain the login state.
    
    Navigation Guards: Front-end routes are protected; unauthorized access triggers a redirect to the /login page.
