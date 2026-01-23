import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/login' // 根路径重定向到记账页
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue'),
  },
  {
    // 布局父路由：包含导航栏和退出逻辑
    path: '/',
    component: () => import('../layout/Layout.vue'), 
    meta: { requiresAuth: true }, // 父路由开启校验，子路由自动继承
    children: [
      {
        path: 'add',
        name: 'Add',
        component: () => import('../views/Add.vue'),
      },
      {
        path: 'report',
        name: 'Report',
        component: () => import('../views/Report.vue'),
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

/**
 * 全局导航守卫
 */
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('user_info')
  
  if (to.path === '/login' && token) {
    next('/add')
  } else if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router

