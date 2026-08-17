<script setup>

import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../../stores/cart'
import { useAuthStore } from '../../stores/auth'

const router = useRouter() // 取得目前網址的路由資訊
const cartStore = useCartStore() // 取得購物車 Store
const authStore = useAuthStore() // 取得會員登入 Store
const paymentMethod = ref('COD') // 付款方式

// 購物車商品
const cartItems = computed(() => {
  return cartStore.items
})

// 商品總數量
const totalQuantity = computed(() => {
  return cartStore.totalQuantity
})

// 訂單總金額
const totalPrice = computed(() => {
  return cartStore.totalPrice
})

// 回到購物車
function goToCart() {
  router.push('/cart')
}

// 確認訂單
function confirmOrder() {
  console.log('訂單成立')
  console.log('會員：', authStore.username)
  console.log('付款方式：', paymentMethod.value)
  console.log('商品：', cartItems.value)
  console.log('總數量：', totalQuantity.value)
  console.log('總金額：', totalPrice.value)
}

</script>


<template>

  <div class="order-page">

    <!-- 頁面標題 -->
    <h1>
      訂單確認
    </h1>

    <!-- 會員資訊 -->
    <section class="order-section">

      <h2>
        會員資訊
      </h2>

      <p>
        會員名稱：
        {{ authStore.username }}
      </p>

    </section>

    <!-- 商品資訊 -->
    <section class="order-section">

      <h2>
        商品資訊
      </h2>

      <div
        v-for="item in cartItems"
        :key="item.productId"
        class="order-item"
      >

        <!-- 商品圖片 -->
        <div class="image-box">

          <img
            :src="`/images/products/${item.productImage}`"
            :alt="item.productName"
          />

        </div>

        <!-- 商品資訊 -->
        <div class="product-info">

          <h3>
            {{ item.productName }}
          </h3>

          <p>
            單價：
            ${{ item.productPrice }}
          </p>

          <p>
            購買數量：
            {{ item.quantity }}
          </p>

          <p class="subtotal">

            小計：

            ${{ item.productPrice * item.quantity }}

          </p>
        </div>
      </div>
    </section>


    <!-- 商品總數 -->
    <section class="order-section">

      <h2>
        訂單資訊
      </h2>

      <p>
        商品總數：
        {{ totalQuantity }}
        件
      </p>

      <p class="total-price">

        訂單總金額：

        ${{ totalPrice }}

      </p>
    </section>


    <!-- 付款方式 -->
    <section class="order-section">

      <h2>
        付款方式
      </h2>

      <div class="payment-option">

        <label>

          <input
            type="radio"
            value="COD"
            v-model="paymentMethod"
          />

          貨到付款
        </label>

        <label>

          <input
            type="radio"
            value="CARD"
            v-model="paymentMethod"
          />

          線上刷卡

        </label>

      </div>

    </section>

    <!-- 訂單 ID -->
    <section class="order-section">

      <h2>
        訂單 ID
      </h2>

      <p>
        尚未建立
      </p>

    </section>

    <!-- 按鈕 -->
    <div class="order-actions">

      <button
        class="back-button"
        @click="goToCart"
      >
        回購物車
      </button>

      <button
        class="confirm-button"
        @click="confirmOrder"
      >
        確認訂單
      </button>
    </div>
  </div>
</template>


<style scoped>

.order-page {
  max-width: 1000px;
  margin: 40px auto;
  padding: 20px;
}

.order-page h1 {
  margin-bottom: 30px;
  font-size: 32px;
  font-weight: bold;
  text-align: center;
}

/* 訂單區塊 */
.order-section {
  margin-bottom: 25px;
  padding: 25px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: rgb(40, 37, 37);
}

.order-section h2 {
  margin-bottom: 20px;
  font-size: 22px;
}

/* 商品 */
.order-item {
  display: flex;
  gap: 25px;
  padding: 20px 0;
  border-bottom: 1px solid #eee;
}

/* 商品圖片 */
.image-box {
  width: 150px;
  height: 150px;
  flex-shrink: 0;
}

.image-box img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

/* 商品資訊 */
.product-info {
  flex: 1;
}

.product-info h3 {
  margin-top: 0;
}

/* 小計 */
.subtotal {
  font-weight: bold;
}

/* 總金額 */
.total-price {
  margin-bottom: 10px;
  font-size: 24px;
  font-weight: bold;
}

/* 付款方式 */
.payment-option {
  display: flex;
  justify-content: center;
  gap: 30px;
}

.payment-option label {
  cursor: pointer;
}

/* 按鈕 */
.order-actions {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}

.order-actions button {
  padding: 12px 25px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

</style>