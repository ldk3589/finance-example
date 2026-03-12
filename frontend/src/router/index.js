import { createRouter, createWebHashHistory } from 'vue-router'
import { getToken } from '../utils/auth'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/',
    component: () => import('../layout/Layout.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: 'add',
        name: 'Add',
        component: () => import('../views/Add.vue')
      },
      {
        path: 'report',
        name: 'Report',
        component: () => import('../views/Report.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = getToken()

  if ((to.path === '/login' || to.path === '/register') && token) {
    next('/add')
    return
  }

  if (to.meta.requiresAuth && !token) {
    next('/login')
    return
  }

  next()
})

export default router