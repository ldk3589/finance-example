import request from './request'

export function getCategories() {
  return request.get('/categories')
}

export function addCategory(data) {
  return request.post('/categories', data)
}