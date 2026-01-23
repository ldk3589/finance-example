<template>
  <div class="report-container">
    <el-page-header @back="goBack" title="返回记账" content="财务报表统计" style="margin-bottom: 20px" />

    <el-tabs v-model="activeRange" @tab-change="handleRangeChange" type="border-card">
      <el-tab-pane label="本周" name="week" />
      <el-tab-pane label="本月" name="month" />
      <el-tab-pane label="全部历史" name="all" />

      <el-row :gutter="20" style="margin-top: 20px">
        <el-col :span="12">
          <el-card shadow="never" header="分类支出占比">
            <div ref="pieChartRef" style="height: 350px"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="never" header="收支趋势对比">
            <div ref="barChartRef" style="height: 350px"></div>
          </el-card>
        </el-col>
      </el-row>

      <el-card shadow="never" header="明细列表" style="margin-top: 20px">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column prop="createTime" label="时间" />
          <el-table-column prop="categoryName" label="分类" />
          <el-table-column prop="type" label="类型">
            <template #default="scope">
              <el-tag :type="scope.row.type === 'INCOME' ? 'success' : 'danger'">
                {{ scope.row.type === 'INCOME' ? '收入' : '支出' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="amount" label="金额">
            <template #default="scope">
              <span :style="{ color: scope.row.type === 'INCOME' ? '#67C23A' : '#F56C6C' }">
                {{ scope.row.type === 'INCOME' ? '+' : '-' }}¥{{ scope.row.amount }}
              </span>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import * as echarts from 'echarts'
import { getReportData } from '@/api/report'
import { ElMessage } from 'element-plus'

const router = useRouter()
const activeRange = ref('month')
const pieChartRef = ref(null)
const barChartRef = ref(null)
const tableData = ref([])

let pieChart = null
let barChart = null

const goBack = () => router.back()

const initCharts = () => {
  if (pieChartRef.value) pieChart = echarts.init(pieChartRef.value)
  if (barChartRef.value) barChart = echarts.init(barChartRef.value)
}

const fetchData = async () => {
  try {
    const userInfoStr = localStorage.getItem('user_info')
    if (!userInfoStr) return
    
    const userInfoWrap = JSON.parse(userInfoStr)
    const userId = userInfoWrap.id || (userInfoWrap.data && userInfoWrap.data.id)
    
    // 发起请求
    const axiosResponse = await getReportData(userId, activeRange.value)
    
    // 解析 Axios 响应
    const result = axiosResponse
    const reportData = result.data

    // 💡 关键修改：判断 code 是否为 0 (根据你的截图，成功是 0)
    if (result.code === 0 && reportData) {
      
      // 1. 更新饼图 (pieData)
      pieChart.setOption({
        tooltip: { trigger: 'item', formatter: '{b}: ¥{c} ({d}%)' },
        series: [{ 
          type: 'pie', 
          data: reportData.pieData || [], 
          radius: '50%' 
        }]
      })
      
      // 2. 更新柱状图 (使用后端返回的 barData 数组)
      // 根据截图，barData 格式为 [{name: "总收入", value: 0}, {name: "总支出", value: 75}]
      const xAxisData = reportData.barData.map(item => item.name)
      const seriesData = reportData.barData.map(item => {
        return {
          value: item.value,
          itemStyle: { color: item.name === '总收入' ? '#67C23A' : '#F56C6C' }
        }
      })

      barChart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: xAxisData },
        yAxis: { type: 'value' },
        series: [{ 
          data: seriesData, 
          type: 'bar',
          barWidth: '40%'
        }]
      })

      // 3. 更新明细列表
      tableData.value = reportData.list || []
      
    } else {
      ElMessage.warning(result.message || '获取数据失败')
    }
  } catch (error) {
    console.error('报表数据渲染失败:', error)
  }
}

const handleRangeChange = () => fetchData()

onMounted(() => {
  initCharts()
  fetchData()
  window.addEventListener('resize', () => {
    pieChart?.resize()
    barChart?.resize()
  })
})
</script>

<style scoped>
.report-container { padding: 30px; background-color: #f5f7fa; min-height: 100vh; }
</style>