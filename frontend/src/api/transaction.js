import request from './request'

/**
 * 提交记账上报
 * @param {Object} data - 包含 accountId, amount, type, categoryId, description
 */
export function createTransaction(data) {
  return request({
    url: '/transactions',
    method: 'post',
    data
  })
}

/**
 * 获取账单历史列表
 * @param {Object} params - 分页参数等
 */
export function getTransactionList(params) {
  return request({
    url: '/transactions',
    method: 'get',
    params
  })
}