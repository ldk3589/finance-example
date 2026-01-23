import request from './request'

/**
 * 获取指定用户的所有账户列表
 * @param {Number} userId 
 */
export function getUserAccounts(userId) {
  return request({
    url: `/accounts/user/${userId}`,
    method: 'get'
  })
}

/**
 * 获取单个账户详情
 * @param {Number} id 
 */
export function getAccountDetail(id) {
  return request({
    url: `/accounts/${id}`,
    method: 'get'
  })
}