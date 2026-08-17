<script setup>
import { ref,computed,onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import { getProductById } from '../../api/productApi'
import axiosInstance from '../../api/axiosInstance'
import { useCartStore } from '../../stores/cart'


const route = useRoute() // 取得目前網址的路由資訊
const product = ref(null) // 儲存商品詳細資料
const isLoading = ref(true) // 商品資料是否正在載入
const errorMessage = ref('') // 儲存商品資料載入時的錯誤訊息
const showCartDialog = ref(false) // 控制「加入購物車」視窗是否顯示
const quantity = ref(1) // 加入購物車的商品數量，預設為 1
const showLoginDialog = ref(false) // 控制「尚未登入」提示視窗是否顯示
const authStore = useAuthStore() // 取得會員登入狀態的 Pinia Store
const cartStore = useCartStore() // 取得購物車的 Pinia Store

// 取得商品資料
async function fetchProduct() {
  try {
    isLoading.value = true
    errorMessage.value = ''
    const response =
      await getProductById(route.params.id)
    product.value = response

  } catch (error) {
    console.error('取得商品失敗：', error)
    errorMessage.value =
      '商品資料取得失敗'

  } finally {
    isLoading.value = false

  }
}

onMounted(() => {
  fetchProduct()
  authStore.checkLogin()

})

// 開啟加入購物車視窗
function openCartDialog() {
  // 每次打開視窗都從 1 開始
  quantity.value = 1
  showCartDialog.value = true

}

// 關閉未登入視窗
function closeLoginDialog() {
  showLoginDialog.value = false
}

// 關閉加入購物車視窗
function closeCartDialog() {
  showCartDialog.value = false
}

// 減少數量
function decreaseQuantity() {
  if (quantity.value > 1) {
    quantity.value--
  }
}

// 增加數量
function increaseQuantity() {
  if (
    product.value &&
    quantity.value < product.value.productStock
  ) {
    quantity.value++
  }
}

//「加入購物車」按鈕
function handleAddToCart() {
  // 如果目前沒有登入
  if (!authStore.isLoggedIn) {
    showLoginDialog.value = true
    return
  }

  openCartDialog()
}

// 加入購物車
function addToCart() {

  // 將目前商品與加入數量加入購物車
  cartStore.addToCart(
    product.value,
    quantity.value
  )

  // 加入成功後關閉視窗
  showCartDialog.value = false

}

// 計算總價格
const totalPrice = computed(() => {

  if (!product.value) {
    return 0
  }

  const price = Number(product.value.productPrice)

  return price * quantity.value

})

onMounted(() => {
  fetchProduct()
})
</script>


<template>

  <div class="product-detail">

    <!-- 載入中 -->
    <div v-if="isLoading" class="status">
      商品資料載入中...
    </div>

    <!-- 發生錯誤 -->
    <div v-else-if="errorMessage" class="status error">
      {{ errorMessage }}
    </div>

    <!-- 商品內容 -->
    <div v-else-if="product" class="product-container">

      <!-- 商品圖片 -->
      <div class="image-box">
        <img
          :src="`/images/products/${product.productImage}`"
          :alt="product.productName"
        />
      </div>

      <!-- 商品資訊 -->
      <div class="product-info">

        <h1>{{ product.productName }}</h1>

        <p class="category">
          商品分類：{{ product.productCategory }}
        </p>

        <p class="price">
          ${{ product.productPrice }}
        </p>

        <p class="description">
          {{ product.productDescription }}
        </p>

        <p class="stock">
          庫存：{{ product.productStock }}
        </p>

      <!-- 加入購物車 -->
      <button
        class="cart-button"
        @click="handleAddToCart"
        :disabled="product.productStock <= 0"
      >
        加入購物車
      </button>

      </div>

    </div>


<!-- 未登入視窗 -->
<div
  v-if="showLoginDialog"
  class="cart-overlay"
>

  <div class="cart-dialog">

    <h2>
      未登入
    </h2>

    <p>
      請先登入會員才能加入購物車
    </p>

    <div class="dialog-actions">

      <!-- 前往登入 -->
      <router-link
        to="/login"
        class="login-link"
      >
        立即登入
      </router-link>

      <!-- 取消 -->
      <button
        class="cancel-button"
        @click="closeLoginDialog"
      >
        取消
      </button>

    </div>

  </div>

</div>


  <!-- 加入購物車視窗 -->
  <div
    v-if="showCartDialog"
    class="cart-overlay"
  >
    <div class="cart-dialog">
      <h2>
        加入購物車
      </h2>

      <!-- 商品名稱 -->
      <p>
        商品名稱：
        {{ product.productName }}
      </p>

      <!-- 商品價格 -->
      <p>
        單價：
        ${{ product.productPrice }}
      </p>

      <!-- 庫存 -->
      <p>
        庫存：
        {{ product.productStock }}
      </p>

      <!-- 加入數量 -->
      <div class="quantity-area">
        <span>
          加入數量：
        </span>

        <button
          @click="decreaseQuantity"
          :disabled="quantity <= 1"
        >
          -
        </button>

        <span class="quantity">
          {{ quantity }}
        </span>

        <button
          @click="increaseQuantity"
          :disabled="quantity >= product.productStock"
        >
          +
        </button>

      </div>

      <!-- 總共價格 -->
      <p class="total-price">

        總共價格：

        ${{ totalPrice }}

      </p>

      <!-- 按鈕 -->
      <div class="dialog-actions">

        <button
          class="cancel-button"
          @click="closeCartDialog"
        >
          取消
        </button>

        <button
          class="confirm-cart-button"
          @click="addToCart"
        >
          加入購物車
        </button>

      </div>

    </div>

  </div>

</div>
</template>


<style scoped>

.product-detail {
  width: 100%;
  padding: 40px;
  box-sizing: border-box;
}

/* 商品主要區域 */
.product-container {
  max-width: 1000px;
  margin: 0 auto;
  display: flex;
  gap: 60px;
  padding: 30px;
  border: 1px solid #ddd;
  border-radius: 10px;
}

/* 商品圖片 */
.image-box {
  width: 450px;
  height: 450px;
  display: flex;
  justify-content: center;
  align-items: center;
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

/* 商品名稱 */
.product-info h1 {
  margin-top: 0;
  margin-bottom: 20px;
}

/* 商品分類 */
.category {
  color: #666;
  margin-bottom: 20px;
}

/* 商品價格 */
.price {
  font-size: 30px;
  font-weight: bold;
  margin-bottom: 25px;
}

/* 商品描述 */
.description {
  line-height: 1.8;
  margin-bottom: 25px;
}

/* 庫存 */
.stock {
  margin-bottom: 25px;
}

/* 加入購物車 */
.cart-button {
  width: 200px;
  padding: 12px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
}

/* 載入與錯誤 */
.status {
  text-align: center;
  padding: 50px;
}

.error {
  color: red;
}

/* 加入購物車視窗的背景遮罩 */
.cart-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

/* 加入購物車視窗 */
.cart-dialog {
  width: 400px;
  padding: 30px;
  background-color: rgb(20, 21, 34);
  border-radius: 10px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.3);
}

/* 視窗標題 */
.cart-dialog h2 {
  text-align: center;
  margin-bottom: 25px;
}

/* 數量區域 */
.quantity-area {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  margin: 25px 0;
}

/* 數量按鈕 */
.quantity-area button {
  width: 35px;
  height: 35px;
  font-size: 20px;
  cursor: pointer;
}

/* 數量 */
.quantity {
  min-width: 30px;
  text-align: center;
  font-size: 18px;
}

/* 總價格 */
.total-price {
  font-size: 20px;
  font-weight: bold;
  text-align: right;
  margin-top: 20px;
}

/* 視窗底部按鈕 */
.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 25px;
}

/* 取消按鈕 */
.cancel-button {
  padding: 10px 20px;
  border: 1px solid #333;
  border-radius: 5px;
  background-color: #333;
  cursor: pointer;
}


/* 加入購物車確認按鈕 */
.confirm-cart-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #333;
  color: white;
  cursor: pointer;
}

/* 商品庫存為 0 時，加入購物車按鈕不能使用 */
.cart-button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

</style>