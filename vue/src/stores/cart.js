import { defineStore } from 'pinia'
import axiosInstance from '../api/axiosInstance'
import { computed, onMounted } from 'vue'

export const useCartStore = defineStore('cart', {

  // 購物車資料
  state: () => ({
  items: [
    {
      productId: 999,
      productName: '測試商品',
      productPrice: 100,
      productStock: 10,
      quantity: 1
    }
  ]
}),

  getters: {

    // 購物車商品總數量
    totalQuantity: (state) => {

      return state.items.reduce(
        (total, item) =>
          total + item.quantity,
        0
      )
    },

    // 購物車總金額
    totalPrice: (state) => {

      return state.items.reduce(
        (total, item) =>
          total + item.productPrice * item.quantity,
        0
      )
    }
  },

  actions: {

    // 查詢目前登入會員的購物車
  async fetchCart() {

  try {

    const response =
      await axiosInstance.get('/cart')

    this.items = response

  } catch (error) {

    console.error(
      '取得購物車失敗：',
      error
    )

    this.items = []
  }
},

    // 加入購物車
    async addToCart(product, quantity) {

      try {

        await axiosInstance.post('/cart', {

          productId:
            product.productId,

          quantity:
            quantity

        })

        // 加入成功後重新取得購物車
        await this.fetchCart()

      } catch (error) {

        console.error(
          '加入購物車失敗：',
          error
        )
      }
    },

    // 修改商品數量
    async updateQuantity(
      productId,
      quantity
    ) {

      try {

        await axiosInstance.put(
          '/cart',
          null,
          {
            params: {
              productId:
                productId,

              quantity:
                quantity
            }
          }
        )

        // 更新成功後重新取得購物車
        await this.fetchCart()

      } catch (error) {

        console.error(
          '修改購物車數量失敗：',
          error
        )
      }
    },

    // 增加商品數量
    async increaseQuantity(productId) {

      const item =
        this.items.find(
          item =>
            item.productId === productId
        )

      if (!item) {
        return
      }
      await this.updateQuantity(
        productId,
        item.quantity + 1
      )
    },

    // 減少商品數量
    async decreaseQuantity(productId) {

      const item =
        this.items.find(
          item =>
            item.productId === productId
        )

      if (!item) {
        return
      }

      // 最少只能有 1 個
      if (item.quantity <= 1) {
        return
      }

      await this.updateQuantity(
        productId,
        item.quantity - 1
      )
    },

    // 移除購物車商品
    async removeItem(productId) {

      try {

        await axiosInstance.delete(
          `/cart/${productId}`
        )

        // 移除成功後重新取得購物車
        await this.fetchCart()

      } catch (error) {

        console.error(
          '移除購物車商品失敗：',
          error
        )
      }
    },

    // 清空購物車
    clearCart() {
      this.items = []
    }
  }
})