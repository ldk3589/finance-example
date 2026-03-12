<template>
  <div>
    <div class="page-title">统计</div>
    <div class="page-subtitle">用图表和列表查看你的收入、支出和结余变化</div>

    <div class="info-cards">
      <div class="info-card info-card-income">
        <div class="info-card-label">总收入</div>
        <div class="info-card-value">¥ {{ Number(report.totalIncome || 0).toFixed(2) }}</div>
      </div>

      <div class="info-card info-card-expense">
        <div class="info-card-label">总支出</div>
        <div class="info-card-value">¥ {{ Number(report.totalExpense || 0).toFixed(2) }}</div>
      </div>

      <div class="info-card info-card-balance">
        <div class="info-card-label">当前结余</div>
        <div class="info-card-value">¥ {{ balance.toFixed(2) }}</div>
      </div>
    </div>

    <el-card>
      <template #header>
        <div class="toolbar-row">
          <span class="section-title">统计概览</span>
          <el-select v-model="range" style="width: 180px" @change="loadReport">
            <el-option label="近 7 天" value="week" />
            <el-option label="本月" value="month" />
            <el-option label="本年" value="year" />
          </el-select>
        </div>
      </template>

      <div v-loading="loading">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card shadow="never">
              <template #header>支出分类占比</template>
              <div ref="pieRef" class="chart-box"></div>
            </el-card>
          </el-col>

          <el-col :span="12">
            <el-card shadow="never">
              <template #header>收支趋势</template>
              <div ref="barRef" class="chart-box"></div>
            </el-card>
          </el-col>
        </el-row>

        <div style="height: 20px"></div>

        <el-card shadow="never">
          <template #header>交易流水</template>

          <el-empty
            v-if="!report.list || report.list.length === 0"
            description="当前时间范围内暂无数据"
          />

          <el-table v-else :data="report.list" style="width: 100%">
            <el-table-column prop="createTime" label="时间" width="180" />
            <el-table-column prop="categoryName" label="分类" width="140" />
            <el-table-column prop="type" label="类型" width="100">
              <template #default="{ row }">
                <el-tag :type="row.type === 'INCOME' ? 'success' : 'danger'" round>
                  {{ row.type === 'INCOME' ? '收入' : '支出' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="amount" label="金额" width="120">
              <template #default="{ row }">
                <span :style="{ color: row.type === 'INCOME' ? '#059669' : '#dc2626', fontWeight: 700 }">
                  {{ row.type === 'INCOME' ? '+' : '-' }}{{ Number(row.amount || 0).toFixed(2) }}
                </span>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注" />
          </el-table>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { computed, nextTick, onBeforeUnmount, onMounted, reactive, ref, watch } from 'vue'
import * as echarts from 'echarts'
import request from '../api/request'

const loading = ref(false)
const range = ref('month')
const pieRef = ref(null)
const barRef = ref(null)

let pieChart = null
let barChart = null
let resizeTimer = null

const report = reactive({
  totalIncome: 0,
  totalExpense: 0,
  pieData: [],
  barData: [],
  list: []
})

const balance = computed(() => {
  return Number(report.totalIncome || 0) - Number(report.totalExpense || 0)
})

const loadReport = async () => {
  try {
    loading.value = true
    const res = await request.get('/reports/stats', {
      params: { range: range.value }
    })

    Object.assign(report, {
      totalIncome: res.data?.totalIncome || 0,
      totalExpense: res.data?.totalExpense || 0,
      pieData: res.data?.pieData || [],
      barData: res.data?.barData || [],
      list: res.data?.list || []
    })

    await nextTick()
    initCharts()
    renderCharts()
  } finally {
    loading.value = false
  }
}

const initCharts = () => {
  if (pieRef.value && !pieChart) {
    pieChart = echarts.init(pieRef.value)
  }
  if (barRef.value && !barChart) {
    barChart = echarts.init(barRef.value)
  }
}

const renderCharts = () => {
  if (pieChart) {
    pieChart.setOption({
      title: report.pieData.length === 0
        ? {
            text: '暂无数据',
            left: 'center',
            top: 'center',
            textStyle: { fontSize: 16, fontWeight: 500, color: '#9ca3af' }
          }
        : undefined,
      tooltip: { trigger: 'item' },
      legend: { bottom: 0 },
      series: [
        {
          type: 'pie',
          radius: ['42%', '72%'],
          avoidLabelOverlap: true,
          data: report.pieData.length === 0 ? [] : report.pieData
        }
      ]
    })
    pieChart.resize()
  }

  if (barChart) {
    barChart.setOption({
      title: report.barData.length === 0
        ? {
            text: '暂无数据',
            left: 'center',
            top: 'center',
            textStyle: { fontSize: 16, fontWeight: 500, color: '#9ca3af' }
          }
        : undefined,
      tooltip: { trigger: 'axis' },
      legend: { data: ['收入', '支出'] },
      grid: { left: 36, right: 20, top: 46, bottom: 36 },
      xAxis: {
        type: 'category',
        data: (report.barData || []).map(item => item.name)
      },
      yAxis: { type: 'value' },
      series: [
        {
          name: '收入',
          type: 'bar',
          barMaxWidth: 26,
          data: (report.barData || []).map(item => Number(item.income || 0))
        },
        {
          name: '支出',
          type: 'bar',
          barMaxWidth: 26,
          data: (report.barData || []).map(item => Number(item.expense || 0))
        }
      ]
    })
    barChart.resize()
  }
}

const handleResize = () => {
  clearTimeout(resizeTimer)
  resizeTimer = setTimeout(() => {
    pieChart?.resize()
    barChart?.resize()
  }, 120)
}

watch(
  () => [report.pieData, report.barData],
  async () => {
    await nextTick()
    initCharts()
    renderCharts()
  },
  { deep: true }
)

onMounted(async () => {
  await nextTick()
  initCharts()
  await loadReport()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  clearTimeout(resizeTimer)
  pieChart?.dispose()
  barChart?.dispose()
  pieChart = null
  barChart = null
})
</script>

<style scoped>
.chart-box {
  height: 320px;
  width: 100%;
}
</style>