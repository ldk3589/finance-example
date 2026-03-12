export function getUserInfo() {
  try {
    return JSON.parse(localStorage.getItem('user_info') || '{}')
  } catch (e) {
    return {}
  }
}

export function setUserInfo(userInfo) {
  localStorage.setItem('user_info', JSON.stringify(userInfo))
}

export function clearUserInfo() {
  localStorage.removeItem('user_info')
}

export function getToken() {
  return getUserInfo().token || ''
}