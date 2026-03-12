#  Finance Manager Frontend

[English](README.md) | [中文](README.zh-CN.md)

一个基于 Vue 3 的 Finance Manager 前端应用。

该模块提供登录、注册、账户查看、分类管理、收支记账和财务报表可视化等页面，并通过 REST API 与后端进行通信。

## 技术栈

* Vue 3
* Vite
* Vue Router
* Pinia
* Axios
* Element Plus
* ECharts

## 项目结构

```
frontend/                             # 前端文件夹
├── src/                              # 源代码
│   ├── api/                          # 连接后端
│   │   ├── request.js                # 发送数据工具
│   │   ├── auth.js                   # 登录连接
│   │   ├── account.js                # 账户连接
│   │   ├── category.js               # 分类连接
│   │   └── transaction.js            # 交易连接
│   ├── layout/                       # 页面布局
│   │   └── Layout.vue                # 页面主框
│   ├── router/                       # 页面链接
│   │   └── index.js                  # 链接规则
│   ├── stores/                       # 保存数据
│   │   └── auth.js                   # 保存登录信息
│   ├── utils/                        # 帮助工具
│   │   └── auth.js                   # 登录工具
│   ├── views/                        # 页面视图
│   │   ├── Login.vue                 # 登录页面
│   │   ├── Register.vue              # 注册页面
│   │   ├── Add.vue                   # 添加数据页面
│   │   └── Report.vue                # 报表页面
│   ├── App.vue                       # 主 Vue 页面
│   ├── main.js                       # 主 JS 文件
│   └── style.css                     # 页面样式
├── package.json                      # 工具列表
└── vite.config.js                    # Vite 工具设置
```

## 主要页面

### 1. 登录页

* 用户登录表单
* 用户名和密码校验
* 登录成功后保存登录状态

### 2. 注册页

* 用户注册表单
* 确认密码校验
* 注册成功后跳转到登录页

### 3. 记账页

* 录入收入和支出数据
* 选择账户和分类
* 新增账户
* 新增分类
* 查看当前账户余额

### 4. 统计页

* 查看总收入
* 查看总支出
* 查看当前结余
* 支出饼图
* 收支柱状图
* 交易流水列表

## 接口通信

前端使用 Axios，通过 `/api` 代理或基础路径向后端发送请求。

主要接口文件：

* `src/api/request.js`
* `src/api/auth.js`
* `src/api/account.js`
* `src/api/category.js`
* `src/api/transaction.js`

## 登录状态管理

前端使用：

* Pinia 管理认证状态
* localStorage 持久化用户信息
* Vue Router 路由守卫保护页面

本地保存的用户信息通常包含：

```
{
  "userId": 1,
  "username": "test",
  "token": "jwt-token"
}
```

## 路由设计

主要路由包括：

* /login
* /register
* /add
* /report

受保护页面必须在持有有效 token 的情况下才能访问。

## 运行方式

### 1. 安装依赖

```
cd frontend
npm install
```

### 2. 启动开发环境

```
npm run dev
```

### 3. 构建生产版本

```
npm run build
```

## 界面设计

前端主要使用 Element Plus 作为 UI 组件库。

主要设计思路：

* 简洁的后台式布局
* 基于卡片的内容展示
* 响应式页面结构
* 统一的颜色、间距和圆角风格
* 更清晰的报表图表展示

## 注意事项

* 使用前请先确保后端已经启动
* 如果登录失效或请求失败，请先检查 `request.js`
* Authorization 请求头应使用 Bearer token 格式
* 如果统计图表为空，先检查后端是否正确返回了报表数据

## 后续可优化方向

* 增加交易编辑和删除页面
* 优化移动端适配
* 增加主题切换
* 增加更细的报表筛选条件
* 增加导出和打印功能

## License

该模块仅用于学习和练习。
