import { defineStore } from 'pinia'
import { clearUserInfo, getUserInfo, setUserInfo } from '../utils/auth'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    userInfo: getUserInfo()
  }),

  getters: {
    isLogin: state => !!state.userInfo?.token
  },

  actions: {
    loginSuccess(userInfo) {
      this.userInfo = userInfo
      setUserInfo(userInfo)
    },

    logout() {
      this.userInfo = {}
      clearUserInfo()
    }
  }
})