<template>
  <div class="add-container">
    <el-card class="add-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span class="title">✨ 新增记账</span>
          <div class="header-ops">
            <el-button type="success" plain icon="PieChart" @click="router.push('/report')">查看统计报表</el-button>
          </div>
        </div>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-position="top">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="交易类型">
              <el-radio-group v-model="form.type" @change="handleTypeChange" class="type-selector">
                <el-radio-button label="EXPENSE">支出</el-radio-button>
                <el-radio-button label="INCOME">收入</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="选择账户" prop="accountId">
          <el-select v-model="form.accountId" placeholder="请选择账户" style="width: 100%" @change="handleAccountChange">
            <el-option v-for="item in accounts" :key="item.id" :label="item.name" :value="item.id">
              <span>{{ item.name }}</span>
              <span style="float: right; color: #999; font-size: 12px">余额: ¥{{ item.balance }}</span>
            </el-option>
            <el-option label="+ 添加其他账户" value="OTHER" style="color: #409eff; font-weight: bold" />
          </el-select>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="金额" prop="amount">
              <el-input-number v-model="form.amount" :precision="2" :min="0.01" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类" prop="categoryId">
              <el-select v-model="form.categoryId" placeholder="选择分类" style="width: 100%" @change="handleCategoryChange">
                <el-option v-for="cat in filteredCategories" :key="cat.id" :label="cat.name" :value="cat.id" />
                <el-option label="+ 添加其他分类" value="OTHER" style="color: #409eff; font-weight: bold" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="记录这笔账单的细节..." />
        </el-form-item>

        <div class="btn-group">
          <el-button type="primary" size="large" :loading="loading" @click="onSubmit" round>保存并上报</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/api/request'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const accounts = ref([])
const categories = ref([]) // 变更为从后端获取

const form = reactive({
  accountId: '',
  amount: 0.01,
  type: 'EXPENSE',
  categoryId: '',
  remark: ''
})

// 计算属性：过滤分类
const filteredCategories = computed(() =>
  categories.value.filter(c => c.type === form.type)
)

// 基础数据获取：获取当前用户信息
const getUserInfo = () => {
  const userInfoStr = localStorage.getItem('user_info')
  if (!userInfoStr) return null
  return JSON.parse(userInfoStr)
}

/**
 * 核心逻辑：处理账户选择变化
 */
const handleAccountChange = async (val) => {
  if (val === 'OTHER') {
    try {
      const { value: newName } = await ElMessageBox.prompt('请输入账户名称（若直接确定则归入“其他”）', '新增账户', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPlaceholder: '例如：微信、支付宝'
      })

      const userInfo = getUserInfo()
      
      // 如果没有输入名字，直接寻找已有的名为“其他”的账户
      if (!newName || newName.trim() === '') {
        const otherAcc = accounts.value.find(a => a.name === '其他')
        form.accountId = otherAcc ? otherAcc.id : ''
        return
      }

      // 如果有输入，调接口新增
      const res = await request.post('/accounts', { 
        name: newName, 
        userId: userInfo.userId || userInfo.id,
        balance: 0 
      })
      if (res.code === 0) {
        await fetchAccounts() // 刷新列表
        form.accountId = res.data.id // 自动选中新生成的 ID
        ElMessage.success('账户添加成功')
      }
    } catch {
      form.accountId = '' // 用户点击取消
    }
  }
}

/**
 * 核心逻辑：处理分类选择变化
 */
const handleCategoryChange = async (val) => {
  if (val === 'OTHER') {
    try {
      const { value: newName } = await ElMessageBox.prompt('请输入分类名称（若直接确定则归入“其他”）', '新增分类', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPlaceholder: '例如：买菜、发奖金'
      })

      const userInfo = getUserInfo()

      if (!newName || newName.trim() === '') {
        const otherCat = filteredCategories.value.find(c => c.name === '其他')
        form.categoryId = otherCat ? otherCat.id : ''
        return
      }

      const res = await request.post('/category', { 
        name: newName, 
        userId: userInfo.userId || userInfo.id,
        type: form.type 
      })
      if (res.code === 0) {
        await fetchCategories()
        form.categoryId = res.data.id
        ElMessage.success('分类添加成功')
      }
    } catch {
      form.categoryId = ''
    }
  }
}

const fetchAccounts = async () => {
  const userInfo = getUserInfo()
  if (!userInfo) return router.push('/login')
  
  try {
    const res = await request.get(`/accounts/user/${userInfo.userId || userInfo.id}`)
    accounts.value = res.data || []
    if (accounts.value.length > 0 && !form.accountId) {
      form.accountId = accounts.value[0].id
    }
  } catch (err) {
    ElMessage.error('获取账户失败')
  }
}

const fetchCategories = async () => {
  const userInfo = getUserInfo()
  if (!userInfo) return
  try {
    const res = await request.get(`/category/user/${userInfo.userId || userInfo.id}`)
    categories.value = res.data || []
  } catch (err) {
    console.error('获取分类失败')
  }
}

const handleTypeChange = () => {
  form.categoryId = ''
}

const rules = {
  accountId: [{ required: true, message: '请选择账户', trigger: 'change' }],
  amount: [{ required: true, message: '请输入金额', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const onSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    loading.value = true
    try {
      const userInfo = getUserInfo()
      const postData = { ...form, userId: userInfo.userId || userInfo.id }
      await request.post('/transactions', postData)
      ElMessage.success('记账成功')
      form.amount = 0.01
      form.remark = ''
      fetchAccounts() 
    } catch (err) {
      console.error('记账失败', err)
    } finally {
      loading.value = false
    }
  })
}

onMounted(() => {
  fetchAccounts()
  fetchCategories()
})
</script>

<style scoped>
.add-container { min-height: 100vh; background-color: #f0f2f5; padding: 40px 20px; }
.add-card { max-width: 600px; margin: 0 auto; border-radius: 12px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.title { font-size: 18px; font-weight: bold; }
.type-selector { width: 100%; display: flex; }
:deep(.el-radio-button) { flex: 1; }
:deep(.el-radio-button__inner) { width: 100%; }
.btn-group { margin-top: 30px; }
.btn-group .el-button { width: 100%; height: 50px; font-size: 16px; }
</style>