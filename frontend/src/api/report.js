import request from '@/api/request'

export const getReportData = (userId, range) => {
  return request({
 
    url: '/reports/stats', 
    method: 'get',
    // 必须用 params 传递 GET 参数
    params: {
      userId: userId,
      range: range
    }
  })
}