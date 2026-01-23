<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <h2 class="register-title">新用户注册</h2>
      </template>

      <el-form :model="registerForm" :rules="rules" ref="registerFormRef" label-width="0">
        <el-form-item prop="username">
          <el-input 
            v-model="registerForm.username" 
            placeholder="设置用户名" 
            :prefix-icon="User"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input 
            v-model="registerForm.password" 
            type="password" 
            placeholder="设置密码" 
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>

        <el-form-item prop="confirmPassword">
          <el-input 
            v-model="registerForm.confirmPassword" 
            type="password" 
            placeholder="确认密码" 
            :prefix-icon="Check"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button type="success" :loading="loading" class="register-button" @click="handleRegister">
            立即注册
          </el-button>
        </el-form-item>

        <div class="login-link">
          <span>已有账号？</span>
          <el-link type="primary" @click="router.push('/login')">返回登录</el-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock, Check } from '@element-plus/icons-vue'
import request from '@/api/request'
import { ElMessage } from 'element-plus'

const router = useRouter()
const registerFormRef = ref(null)
const loading = ref(false)

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: ''
})

// 自定义校验：验证两次密码是否一致
const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致!'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少为 6 位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validatePass2, trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  if (!registerFormRef.value) return
  
  await registerFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        // 注意：这里传给后端的通常不需要 confirmPassword
        const { username, password } = registerForm
        const res = await request.post('/user/register', { username, password })
        
        if (res) {
          ElMessage.success('注册成功，请登录')
          router.push('/login')
        }
      } catch (error) {
        console.error('注册失败:', error)
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.register-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f7fa;
}

.register-card {
  width: 400px;
}

.register-title {
  text-align: center;
  margin: 0;
  color: #67c23a; /* 使用绿色代表注册/成功 */
}

.register-button {
  width: 100%;
}

.login-link {
  text-align: center;
  margin-top: 15px;
  font-size: 14px;
  color: #606266;
}
</style>