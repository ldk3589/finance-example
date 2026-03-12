<template>
  <div class="page-shell">
    <el-container>
      <el-header class="layout-header glass-panel">
        <div class="header-left">
          <div class="brand">
            <div class="brand-logo">¥</div>
            <div>
              <div class="brand-title">财务管理</div>
              <div class="brand-subtitle">个人记账管理</div>
            </div>
          </div>

          <div class="nav-tabs">
            <div
              class="nav-tab"
              :class="{ active: activePath === '/add' }"
              @click="router.push('/add')"
            >
              记账中心
            </div>
            <div
              class="nav-tab"
              :class="{ active: activePath === '/report' }"
              @click="router.push('/report')"
            >
              统计
            </div>
          </div>
        </div>

        <div class="header-right">
          <div class="user-box">
            <div class="user-avatar">
              {{ (authStore.userInfo?.username || 'U').slice(0, 1).toUpperCase() }}
            </div>
            <div>
              <div class="user-name">{{ authStore.userInfo?.username || '未登录' }}</div>
              <div class="user-tip">欢迎回来</div>
            </div>
          </div>

          <el-button type="danger" plain @click="handleLogout">退出</el-button>
        </div>
      </el-header>

      <el-main style="padding: 22px 0 0">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const activePath = computed(() => route.path)

const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', { type: 'warning' })
    authStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  } catch (_) {
    //
  }
}
</script>

<style scoped>
.layout-header {
  min-height: 84px;
  padding: 14px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 18px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 28px;
  flex-wrap: wrap;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand-logo {
  width: 46px;
  height: 46px;
  border-radius: 16px;
  display: grid;
  place-items: center;
  color: #fff;
  font-size: 22px;
  font-weight: 800;
  background: linear-gradient(135deg, #4f46e5, #10b981);
  box-shadow: 0 14px 28px rgba(79, 70, 229, 0.24);
}

.brand-title {
  font-size: 18px;
  font-weight: 800;
  color: #111827;
}

.brand-subtitle {
  font-size: 12px;
  color: #6b7280;
}

.nav-tabs {
  display: flex;
  align-items: center;
  gap: 10px;
}

.nav-tab {
  padding: 10px 18px;
  border-radius: 12px;
  font-size: 15px;
  font-weight: 600;
  color: #4b5563;
  cursor: pointer;
  transition: all 0.2s ease;
}

.nav-tab:hover {
  background: #eef2ff;
  color: #4f46e5;
}

.nav-tab.active {
  background: linear-gradient(135deg, #4f46e5, #6366f1);
  color: #fff;
  box-shadow: 0 10px 24px rgba(79, 70, 229, 0.22);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-box {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 12px;
  border-radius: 16px;
  background: rgba(248, 250, 252, 0.9);
}

.user-avatar {
  width: 38px;
  height: 38px;
  border-radius: 50%;
  display: grid;
  place-items: center;
  color: #fff;
  font-weight: 800;
  background: linear-gradient(135deg, #6366f1, #8b5cf6);
}

.user-name {
  font-size: 14px;
  font-weight: 700;
  color: #111827;
}

.user-tip {
  font-size: 12px;
  color: #6b7280;
}

@media (max-width: 900px) {
  .layout-header {
    align-items: flex-start;
    flex-direction: column;
  }

  .header-right {
    width: 100%;
    justify-content: space-between;
  }
}
</style>