# finance-example
 [English](README.md) | [中文](README.zh-CN.md)

💰 Finance Example

一个 前后端分离的个人理财管理系统，支持用户登录、自动创建账户、记账、账户管理及财务数据展示。

本项目为学习 / 实践型项目，适合 Spring Boot + Vue 3 全栈练手。

---
## 📁 项目结构

    finance-example
    ├── backend/          # 后端：Spring Boot 项目（finance-manager）
    │   ├── src/
    │   ├── pom.xml
    │   └── README.md     # （可选）后端子项目说明
    │
    ├── frontend/         # 前端：Vue 3 + Vite 项目（finance-frontend）
    │   ├── src/
    │   ├── package.json
    │   └── README.md     # （可选）前端子项目说明
    │
    └── README.md         # 项目总说明（当前文件）
---
## 🚀 功能介绍

✅ 已实现功能

- 👤 用户登录（新用户自动注册 / 初始化账户）
    
- 🧾 记账功能（选择账户、分类、金额）
    
- 🏦 多账户管理（每个用户独立账户）
    
- 📊 财务数据统计（支持图表展示）
    
- 🔐 未登录强制跳转登录页
---

## 🧱 技术栈

后端（Backend）

- Java 21
        
- Spring Boot
        
- MyBatis-Plus
        
- MySQL
        
- Maven

前端（Frontend）

- Vue 3
        
- Vite
        
- Element Plus
        
- Axios
        
- Vue Router

## ⚙️ 环境准备

必须环境

- JDK ≥ 17（推荐 21）
        
- Node.js ≥ 18
        
- MySQL ≥ 8.0
        
- Maven ≥ 3.8
---
## 🛠️ 快速启动

### 1️⃣ 后端启动（Spring Boot）
① 创建数据库

        CREATE DATABASE finance_manager DEFAULT CHARACTER SET utf8mb4;

② 修改数据库配置

编辑文件：

    backend/src/main/resources/application-dev.yml


示例：

    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/finance_manager?useSSL=false&serverTimezone=Asia/Shanghai
        username: root
        password: 123456

③ 启动后端

    cd backend 
    mvn spring-boot:run
            

启动成功后：

    http://localhost:8080

### 2️⃣ 前端启动（Vue 3）

    cd frontend
    npm install
    npm run dev


浏览器访问：

    http://localhost:5173

🔗 前后端接口说明（示例）

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
    "token": "xxxx-xxxx"
    }
    }

🔐 登录逻辑说明

- 用户首次登录：
    
- 自动创建用户
    
- 自动创建默认账户
    
- 登录成功：
    
- 用户信息保存至 localStorage
    
- 未登录访问页面会被强制跳转登录页
