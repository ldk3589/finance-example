#  Finance Manager Frontend

[English](README.md) | [中文](README.zh-CN.md)

 Finance Manager Frontend

基于 **Vue 3 + Vite** 的个人理财管理系统前端，实现登录、记账、账户管理及数据展示。

---

## 🧱 技术栈

- Vue 3
- Vite
- Composition API
- Vue Router
- Element Plus
- Axios

---

## 📁 项目结构

    frontend
    ├── src
    │   ├── api           # 接口请求封装
    │   ├── views         # 页面组件（Login / Add / Home 等）
    │   ├── router        # 路由配置
    │   ├── utils         # 工具类
    │   ├── App.vue
    │   └── main.js
    │
    ├── public
    ├── package.json
    └── vite.config.js
---
## ⚙️ 环境要求

- Node.js ≥ 18

- npm ≥ 9

## 🛠️ 本地启动
### 1️⃣ 安装依赖
    npm install

### 2️⃣ 启动项目
    npm run dev

访问地址：

    http://localhost:5173

🔗 接口代理配置

- vite.config.js

      server: {
      proxy: {
      '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
      }
      }
      }

#### 🔐 登录逻辑说明

登录成功后：

- 用户信息存储在 localStorage

路由守卫：

- 未登录访问页面 → 自动跳转 /login

#### 📌 页面说明

- /login：用户登录页

- /add：新增记账页面

- /home：账户及数据展示页面

### ✨ 项目特点

- Vue 3 Composition API

- Axios 请求统一封装

- 路由守卫控制登录态

- Element Plus UI 组件

- 前后端完全解耦
