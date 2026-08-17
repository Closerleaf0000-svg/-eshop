import { defineStore } from 'pinia'

export const useCartStore = defineStore('cart', {

  // 購物車資料
  state: () => ({

    // 頁面重新整理時，從 localStorage 讀取購物車
    items: JSON.parse(
      localStorage.getItem('cartItems') || '[]'
    )
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
          total +
          item.productPrice * item.quantity,
        0
      )
    }
  },

  actions: {

        // 儲存購物車資料到 localStorage
    saveCart() {

      localStorage.setItem(
        'cartItems',
        JSON.stringify(this.items)
      )
    },

    // 加入購物車
    addToCart(product, quantity) {

      // 尋找購物車中是否已經有這個商品
      const existingItem =
        this.items.find(
          item =>
            item.productId === product.productId
        )

      // 如果已經存在
      if (existingItem) {

        // 增加商品數量
        existingItem.quantity += quantity

        // 不可以超過庫存
        if (
          existingItem.quantity >
          product.productStock
        ) {

          existingItem.quantity =
            product.productStock
        }
      } else {

        // 第一次加入購物車
        this.items.push({
          productId:
            product.productId,
          productName:
            product.productName,
          productPrice:
            product.productPrice,
          productImage:
            product.productImage,
          productStock:
            product.productStock,
          quantity:
            quantity

        })
      }
      // 更新 localStorage
      this.saveCart()
    },


    // 增加商品數量
    increaseQuantity(productId) {
      const item =
        this.items.find(
          item =>
            item.productId === productId
        )

      if (!item) {
        return
      }

      // 不可以超過商品庫存
      if (
        item.quantity <
        item.productStock
      ) {
        item.quantity++

      // 更新 localStorage
        this.saveCart()

      }
    },


    // 減少商品數量
    decreaseQuantity(productId) {
      const item =
        this.items.find(
          item =>
            item.productId === productId
        )

      if (!item) {
        return
      }

      // 最少只能有 1 個
      if (item.quantity > 1) {
        item.quantity--

        // 更新 localStorage
        this.saveCart()
      }
    },

    // 移除商品
    removeItem(productId) {
      this.items =
        this.items.filter(
          item =>
            item.productId !== productId
        )
        // 更新 localStorage
        this.saveCart()
    },

    // 清空購物車
    clearCart() {
      this.items = []
      
      // 清除 localStorage
      localStorage.removeItem('cartItems')
    }
  }
})