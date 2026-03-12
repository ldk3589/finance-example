import request from './request'

export function addTransaction(data) {
  return request.post('/transactions', data)
}

export function getTransactions(params) {
  return request.get('/transactions', { params })
}