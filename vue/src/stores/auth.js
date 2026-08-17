import { defineStore } from 'pinia'
import axiosInstance from '../api/axiosInstance'

export const useAuthStore = defineStore('auth', {

  state: () => ({
    // 是否登入
    isLoggedIn: false,
    // 會員 ID
    userId: null,
    // 會員帳號
    username: ''
  }),

  actions: {

    // 檢查登入狀態
    async checkLogin() {
      try {
        const response =
          await axiosInstance.get('/member/me')

        this.isLoggedIn =
          response.loggedIn === true

        if (this.isLoggedIn) {
          this.userId =
            response.userId
          this.username =
            response.username

        } else {
          this.userId = null
          this.username = ''
        }

      } catch (error) {

        console.error(
          '檢查登入狀態失敗：',
          error
        )
        this.isLoggedIn = false
        this.userId = null
        this.username = ''
      }
    },

    // 登出
    async logout() {

      try {
        const response =
          await axiosInstance.post('/logout')
        if (response.success) {

          // 清除前端登入狀態
          this.isLoggedIn = false
          this.userId = null
          this.username = ''
          return true
        }

        return false
      } catch (error) {

        console.error(
          '登出失敗：',
          error
        )

        return false
      }
    },


    async login(username, password) {

        try {
            const response =
            await axiosInstance.post('/login', {
            username: username,
            password: password
        })

        if (response.success) {

        // 更新 Pinia 登入狀態
        this.isLoggedIn = true
         // 儲存會員 ID
        this.userId = response.userId
         // 儲存會員帳號
        this.username = response.username

        return true
        }

        return false

    } catch (error) {

        console.error('登入失敗：', error)
        return false
    }

    }
  }
})