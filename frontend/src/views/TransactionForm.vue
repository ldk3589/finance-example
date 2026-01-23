<template>
  <div class="form-container">
    <h2>记账录入</h2>

    <form @submit.prevent="submitTransaction">
      <div>
        <label>账户ID：</label>
        <input v-model="form.accountId" type="number" required />
      </div>

      <div>
        <label>金额：</label>
        <input v-model="form.amount" type="number" step="0.01" required />
      </div>

      <div>
        <label>类型：</label>
        <select v-model="form.type">
          <option value="INCOME">收入</option>
          <option value="EXPENSE">支出</option>
        </select>
      </div>

      <div>
        <label>分类ID：</label>
        <input v-model="form.categoryId" type="number" />
      </div>

      <button type="submit">提交记账</button>
    </form>

    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import axios from 'axios'

const message = ref('')

const form = reactive({
  accountId: 1,
  amount: '',
  type: 'INCOME',
  categoryId: 1
})

const submitTransaction = async () => {
  try {
    await axios.post('http://localhost:8080/transactions', form)
    message.value = '记账成功'
  } catch (error) {
    message.value = '提交失败'
    console.error(error)
  }
}
</script>

<style scoped>
.form-container {
  width: 300px;
  margin: 40px auto;
}
.form-container div {
  margin-bottom: 10px;
}
</style>
