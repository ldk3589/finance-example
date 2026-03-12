<template>
  <div>
    <div class="page-title">记账中心</div>
    <div class="page-subtitle">快速记录收入和支出，并随时查看账户变化</div>

    <div class="dashboard-grid">
      <div>
        <el-card>
          <template #header>
            <div class="toolbar-row">
              <span class="section-title">新增记账</span>
              <el-tag type="primary" round>实时更新余额</el-tag>
            </div>
          </template>

          <el-form :model="form" :rules="rules" ref="formRef" label-position="top">
            <el-row :gutter="16">
              <el-col :span="12">
                <el-form-item label="账户" prop="accountId">
                  <div style="display: flex; gap: 10px; width: 100%">
                    <el-select v-model="form.accountId" placeholder="请选择账户" style="flex: 1">
                      <el-option
                        v-for="item in accounts"
                        :key="item.id"
                        :label="`${item.name}（余额：${item.balance}）`"
                        :value="item.id"
                      />
                    </el-select>
                    <el-button @click="showAccountDialog = true">新增</el-button>
                  </div>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="类型" prop="type">
                  <el-radio-group v-model="form.type" @change="handleTypeChange">
                    <el-radio-button label="EXPENSE">支出</el-radio-button>
                    <el-radio-button label="INCOME">收入</el-radio-button>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="16">
              <el-col :span="12">
                <el-form-item label="分类" prop="categoryId">
                  <div style="display: flex; gap: 10px; width: 100%">
                    <el-select v-model="form.categoryId" placeholder="请选择分类" style="flex: 1">
                      <el-option
                        v-for="item in filteredCategories"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                      />
                    </el-select>
                    <el-button @click="openCategoryDialog">新增</el-button>
                  </div>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="金额" prop="amount">
                  <el-input-number
                    v-model="form.amount"
                    :min="0.01"
                    :precision="2"
                    style="width: 100%"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="备注">
              <el-input
                v-model="form.remark"
                type="textarea"
                :rows="4"
                placeholder="比如：午饭、打车、工资到账"
              />
            </el-form-item>

            <el-form-item style="margin-bottom: 0">
              <el-button type="primary" :loading="loading" @click="handleSubmit">提交记账</el-button>
              <el-button @click="resetForm">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>

      <div>
        <el-card>
          <template #header>
            <div class="toolbar-row">
              <span class="section-title">我的账户</span>
              <el-tag round>共 {{ accounts.length }} 个</el-tag>
            </div>
          </template>

          <div v-if="accounts.length === 0">
            <el-empty description="暂无账户" />
          </div>

          <div v-else class="side-list">
            <div class="side-item" v-for="item in accounts" :key="item.id">
              <div class="side-item-name">{{ item.name }}</div>
              <div class="side-item-value">¥ {{ Number(item.balance || 0).toFixed(2) }}</div>
            </div>
          </div>
        </el-card>

        <div style="height: 18px"></div>

        <el-card>
          <template #header>
            <span class="section-title">使用小提示</span>
          </template>

          <div class="side-list">
            <div class="side-item">
              <div class="side-item-name">收入类型</div>
              <div class="side-item-value">会增加余额</div>
            </div>
            <div class="side-item">
              <div class="side-item-name">支出类型</div>
              <div class="side-item-value">会扣减余额</div>
            </div>
            <div class="side-item">
              <div class="side-item-name">分类切换</div>
              <div class="side-item-value">会自动刷新列表</div>
            </div>
          </div>
        </el-card>
      </div>
    </div>

    <el-dialog v-model="showAccountDialog" title="新增账户" width="420px">
      <el-form :model="accountForm" label-position="top">
        <el-form-item label="账户名称">
          <el-input v-model="accountForm.name" placeholder="请输入账户名称" />
        </el-form-item>
        <el-form-item label="初始余额">
          <el-input-number v-model="accountForm.balance" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="showAccountDialog = false">取消</el-button>
        <el-button type="primary" :loading="accountLoading" @click="handleAddAccount">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showCategoryDialog" title="新增分类" width="420px">
      <el-form :model="categoryForm" label-position="top">
        <el-form-item label="分类名称">
          <el-input v-model="categoryForm.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="分类类型">
          <el-input v-model="categoryForm.type" disabled />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="showCategoryDialog = false">取消</el-button>
        <el-button type="primary" :loading="categoryLoading" @click="handleAddCategory">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../api/request'
import { getAccounts, addAccount } from '../api/account'
import { getCategories, addCategory } from '../api/category'

const formRef = ref()
const loading = ref(false)

const accounts = ref([])
const categories = ref([])

const showAccountDialog = ref(false)
const showCategoryDialog = ref(false)
const accountLoading = ref(false)
const categoryLoading = ref(false)

const form = reactive({
  accountId: null,
  categoryId: null,
  type: 'EXPENSE',
  amount: 0.01,
  remark: ''
})

const accountForm = reactive({
  name: '',
  balance: 0
})

const categoryForm = reactive({
  name: '',
  type: 'EXPENSE'
})

const rules = {
  accountId: [{ required: true, message: '请选择账户', trigger: 'change' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  amount: [{ required: true, message: '请输入金额', trigger: 'blur' }]
}

const filteredCategories = computed(() => {
  return categories.value.filter(item => item.type === form.type)
})

const loadAccounts = async () => {
  const res = await getAccounts()
  accounts.value = res.data || []
  if (!form.accountId && accounts.value.length > 0) {
    form.accountId = accounts.value[0].id
  }
}

const loadCategories = async () => {
  const res = await getCategories()
  categories.value = res.data || []
  if (!form.categoryId && filteredCategories.value.length > 0) {
    form.categoryId = filteredCategories.value[0].id
  }
}

const handleTypeChange = () => {
  form.categoryId = filteredCategories.value[0]?.id ?? null
}

const handleSubmit = async () => {
  await formRef.value.validate()

  try {
    loading.value = true
    await request.post('/transactions', form)
    ElMessage.success('记账成功')
    resetForm()
    await loadAccounts()
  } finally {
    loading.value = false
  }
}

const handleAddAccount = async () => {
  if (!accountForm.name) {
    ElMessage.error('请输入账户名称')
    return
  }

  try {
    accountLoading.value = true
    const res = await addAccount(accountForm)
    ElMessage.success('新增账户成功')
    showAccountDialog.value = false
    accountForm.name = ''
    accountForm.balance = 0
    await loadAccounts()
    form.accountId = res.data.id
  } finally {
    accountLoading.value = false
  }
}

const openCategoryDialog = () => {
  categoryForm.type = form.type
  categoryForm.name = ''
  showCategoryDialog.value = true
}

const handleAddCategory = async () => {
  if (!categoryForm.name) {
    ElMessage.error('请输入分类名称')
    return
  }

  try {
    categoryLoading.value = true
    const res = await addCategory(categoryForm)
    ElMessage.success('新增分类成功')
    showCategoryDialog.value = false
    await loadCategories()
    form.categoryId = res.data.id
  } finally {
    categoryLoading.value = false
  }
}

const resetForm = () => {
  form.type = 'EXPENSE'
  form.amount = 0.01
  form.remark = ''
  form.categoryId = categories.value.find(item => item.type === 'EXPENSE')?.id ?? null
}

onMounted(async () => {
  await loadAccounts()
  await loadCategories()
})
</script>