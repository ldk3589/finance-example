import axios from 'axios'
import { ElMessage } from 'element-plus'

function getUserInfo() {
  try {
    return JSON.parse(localStorage.getItem('user_info') || '{}')
  } catch (e) {
    return {}
  }
}

function clearUserInfo() {
  localStorage.removeItem('user_info')
}

const request = axios.create({
  baseURL: '/api',
  timeout: 8000
})

request.interceptors.request.use(
  config => {
    const userInfo = getUserInfo()
    const token = userInfo.token
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => Promise.reject(error)
)

request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 0) {
      ElMessage.error(res.message || '系统异常')
      return Promise.reject(new Error(res.message || 'Error'))
    }
    return res
  },
  error => {
    const status = error?.response?.status

    if (status === 401) {
      clearUserInfo()
      ElMessage.error('登录已失效，请重新登录')
      if (window.location.hash !== '#/login') {
        window.location.hash = '#/login'
      }
      return Promise.reject(error)
    }

    ElMessage.error(error?.response?.data?.message || error.message || '网络请求失败')
    return Promise.reject(error)
  }
)

export default request