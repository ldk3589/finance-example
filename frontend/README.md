#  Finance Manager Frontend

[English](README.md) | [中文](README.zh-CN.md)

Finance Manager Frontend

A modern, responsive personal finance management frontend built with Vue 3 + Vite, featuring transaction tracking, account management, and data visualization.

---
## 🧱 Tech Stack
- Framework: Vue 3 (Composition API)

- Build Tool: Vite

- Routing: Vue Router

- UI Component Library: Element Plus

- HTTP Client: Axios
---
## 📁 Project Structure

    frontend
    ├── src
    │   ├── api           # API request wrappers
    │   ├── views         # Page components (Login / Add / Home, etc.)
    │   ├── router        # Router configuration
    │   ├── utils         # Utility functions
    │   ├── App.vue
    │   └── main.js
    │
    ├── public
    ├── package.json
    └── vite.config.js

---

## ⚙️ Prerequisites
- Node.js ≥ 18

- npm ≥ 9
---
## 🛠️ Getting Started
### 1️⃣ Install Dependencies
    npm install

### 2️⃣ Run the Development Server
    npm run dev

Access the application at:

    http://localhost:5173

🔗 API Proxy Configuration

- The frontend communicates with the backend through a proxy configured in vite.config.js:

        server: {
        proxy: {
        '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
        }
        }
        }

#### 🔐 Authentication Logic

After a successful login:

- User information is stored in localStorage

Route guards:

- Unauthenticated access → automatically redirected to /login

### 📌 Pages Overview

- /login – User login page

- /add – Add new income or expense records

- /home – Account overview and data visualization

### ✨ Features

- Built with Vue 3 Composition API

- Centralized Axios request handling

- Route guards for authentication control

- Element Plus UI components

- Fully decoupled frontend and backend architecture
