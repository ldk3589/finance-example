<template>
  <div class="common-layout">
    <el-container>
      <el-header class="nav-header">
        <div class="logo">💰 极简记账</div>
        
        <div class="nav-menu">
          <el-button 
            :type="route.path === '/add' ? 'primary' : ''" 
            text 
            @click="router.push('/add')"
          >新增记账</el-button>
          <el-button 
            :type="route.path === '/report' ? 'primary' : ''" 
            text 
            @click="router.push('/report')"
          >统计报表</el-button>
        </div>

        <div class="user-ops">
          <el-tag class="username-tag" effect="plain">{{ username }}</el-tag>
          <el-button type="danger" link icon="SwitchButton" @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>

      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const route = useRoute()

// 从会话存储获取用户名
const username = computed(() => {
  const info = localStorage.getItem('user_info')
  return info ? JSON.parse(info).username : '未登录'
})

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出当前登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.clear() // 清空所有会话存储
    ElMessage.success('已退出登录')
    router.push('/login')
  })
}
</script>

<style scoped>
.nav-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  padding: 0 40px;
  height: 60px;
  position: sticky;
  top: 0;
  z-index: 100;
}
.logo { font-size: 20px; font-weight: bold; color: #409eff; }
.nav-menu { flex: 1; margin-left: 50px; }
.user-ops { display: flex; align-items: center; gap: 15px; }
.username-tag { font-weight: bold; }
.main-content { background-color: #f0f2f5; min-height: calc(100vh - 60px); }
</style>