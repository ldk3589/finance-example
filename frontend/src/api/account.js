import request from './request'

export function getAccounts() {
  return request.get('/accounts')
}

export function addAccount(data) {
  return request.post('/accounts', data)
}