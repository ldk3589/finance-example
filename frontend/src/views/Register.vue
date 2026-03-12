<template>
  <div class="auth-page">
    <div class="auth-left">
      <div class="auth-brand">
        <div class="auth-badge">快速开始 · 创建你的个人账户</div>
        <h1 class="auth-heading">
          准备好开始管理
          <br />
          你的每一笔资金了吗？
        </h1>
        <p class="auth-desc">
          注册后可以直接体验账户管理、分类管理、记账和报表功能。
        </p>

        <div class="auth-points">
          <div class="auth-point"><span class="auth-dot"></span>自动创建默认账户</div>
          <div class="auth-point"><span class="auth-dot"></span>自动初始化常用分类</div>
          <div class="auth-point"><span class="auth-dot"></span>注册后即可马上使用</div>
        </div>
      </div>
    </div>

    <div class="auth-right">
      <el-card class="auth-card">
        <div class="auth-card-header">
          <div class="auth-card-title">创建账号</div>
          <div class="auth-card-tip">填写下面信息完成注册</div>
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

          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
              v-model="form.confirmPassword"
              type="password"
              show-password
              placeholder="请再次输入密码"
              size="large"
            />
          </el-form-item>

          <el-form-item style="margin-top: 8px">
            <el-button type="primary" size="large" :loading="loading" style="width: 100%" @click="handleRegister">
              注册
            </el-button>
          </el-form-item>

          <div class="bottom-tip">
            已有账号？
            <span class="link" @click="$router.push('/login')">返回登录</span>
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
import { register } from '../api/auth'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
  confirmPassword: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度需在 3 到 20 位之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 32, message: '密码长度需在 6 到 32 位之间', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (_, value, callback) => {
        if (value !== form.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const handleRegister = async () => {
  await formRef.value.validate()

  try {
    loading.value = true
    await register({
      username: form.username,
      password: form.password
    })
    ElMessage.success('注册成功，请登录')
    router.push('/login')
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