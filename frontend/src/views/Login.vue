<template>
  <div class="auth-page">
    <div class="auth-left">
      <div class="auth-brand">
        <div class="auth-badge">财务可视化 · 前后端分离项目</div>
        <h1 class="auth-heading">
          让每一笔收入和支出，
          <br />
          都看得更清楚
        </h1>
        <p class="auth-desc">
          记录日常消费、管理账户余额、查看统计图表，
          用更简单的方式了解自己的财务情况。
        </p>

        <div class="auth-points">
          <div class="auth-point"><span class="auth-dot"></span>账户与分类分开管理</div>
          <div class="auth-point"><span class="auth-dot"></span>收入支出自动汇总统计</div>
          <div class="auth-point"><span class="auth-dot"></span>图表展示更加直观</div>
        </div>
      </div>
    </div>

    <div class="auth-right">
      <el-card class="auth-card">
        <div class="auth-card-header">
          <div class="auth-card-title">欢迎登录</div>
          <div class="auth-card-tip">请输入你的账号信息</div>
        </div>

        <el-form :model="form" :rules="rules" ref="formRef" label-position="top">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名" size="large" />
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input
              v-model="form.password"
              type="password"
              show-password
              placeholder="请输入密码"
              size="large"
            />
          </el-form-item>

          <el-form-item style="margin-top: 8px">
            <el-button type="primary" size="large" :loading="loading" style="width: 100%" @click="handleLogin">
              登录
            </el-button>
          </el-form-item>

          <div class="bottom-tip">
            还没有账号？
            <span class="link" @click="$router.push('/register')">立即注册</span>
          </div>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { login } from '../api/auth'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()

  try {
    loading.value = true
    const res = await login(form)
    authStore.loginSuccess(res.data)
    ElMessage.success('登录成功')
    router.push('/add')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.bottom-tip {
  margin-top: 8px;
  text-align: center;
  font-size: 14px;
  color: #6b7280;
}

.link {
  color: #4f46e5;
  font-weight: 700;
  cursor: pointer;
}
</style>