<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <h2 class="login-title">极简记账</h2>
      </template>
      
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="0">
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username" 
            placeholder="用户名" 
            :prefix-icon="User"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password" 
            type="password" 
            placeholder="密码" 
            :prefix-icon="Lock"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" :loading="loading" class="login-button" @click="handleLogin">
            立即登录
          </el-button>
        </el-form-item>

        <div class="register-link">
          <span>还没有账号？</span>
          <el-link type="primary" @click="goToRegister">立即注册</el-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import request from '@/api/request' 
import { ElMessage } from 'element-plus'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const goToRegister = () => {
  router.push('/register')
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        // 执行登录请求
        const res = await request.post('/user/login', loginForm)
        
        // 关键逻辑：图片显示成功状态码 code 为 0
        if (res.code === 0) {
          ElMessage.success('登录成功')
          
          // 1. 保存用户信息（存储 res.data 这个 Object）
          localStorage.setItem('user_info', JSON.stringify(res.data))
          
          // 2. 保存 Token（从 res.data 中提取）
          if (res.data && res.data.token) {
            localStorage.setItem('token', res.data.token)
          }
          
          // 3. 只有成功后才执行跳转
          router.push('/add')
        } else {
          // 如果 code 不为 0，则显示后端返回的错误信息
          ElMessage.error(res.message || '登录失败')
        }
      } catch (error) {
        console.error('登录跳转异常:', error)
        // 注意：如果 request.js 拦截器里没处理 4xx/5xx 错误，这里需要手动提示
        ElMessage.error('服务器响应异常')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f7fa;
}

.login-card {
  width: 400px;
}

.login-title {
  text-align: center;
  margin: 0;
  color: #409eff;
}

.login-button {
  width: 100%;
}

.register-link {
  text-align: center;
  margin-top: 15px;
  font-size: 14px;
  color: #606266;
}
</style>