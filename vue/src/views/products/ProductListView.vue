<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getProducts } from '../../api/productApi'
import { useAuthStore } from '../../stores/auth'


const router = useRouter() // 取得目前網址的路由資訊
const products = ref([]) // 儲存商品列表資料
const isLoading = ref(true) // 商品資料是否正在載入
const errorMessage = ref('') // 儲存商品資料載入時的錯誤訊息
const showLogoutConfirm = ref(false) // 控制「確定登出嗎？」確認視窗是否顯示
const authStore = useAuthStore() // 取得會員登入狀態的 Pinia Store


// 取得商品
async function fetchProducts() {
  try {
    isLoading.value = true
    errorMessage.value = ''

    const response = await getProducts()

    products.value = response
  } catch (error) {
    console.error(error)
    errorMessage.value = '商品取得失敗'
  } finally {
    isLoading.value = false
  }
}

// 執行會員登出
async function logout() {
  const success =
    await authStore.logout()
  if (success) {
    showLogoutConfirm.value = false
    router.push('/products')
  }
}

// 前往商品詳細頁
function goToDetail(productId) {
  router.push(`/products/${productId}`)
}

// 頁面載入時取得商品
// 頁面載入時檢查登入狀態
onMounted(() => {
  fetchProducts()
  authStore.checkLogin()
})

</script>

<template>

  <!-- 右上角會員與購物車 -->
<div class="top-bar">

  <!-- 會員區域 -->
  <div class="member-area">

    <!-- 尚未登入 -->
    <template v-if="!authStore.isLoggedIn">

      <span>
        未登入
      </span>

      <router-link to="/login">
        立即登入
      </router-link>

    </template>

    <!-- 已登入 -->
    <template v-else>

      <span>
        Hi~{{ authStore.username }}
      </span>

      <button
        class="logout-button"
        @click="showLogoutConfirm = true"
      >
        登出
      </button>

    </template>

  </div>

  <!-- 我的購物車 -->
  <router-link
    to="/cart"
    class="cart-link"
  >
    我的購物車
  </router-link>

</div>

<!-- 登出確認視窗 -->
<div
  v-if="showLogoutConfirm"
  class="logout-overlay"
>

  <div class="logout-dialog">

    <h3>
      確定登出嗎？
    </h3>

    <div class="logout-actions">

      <button @click="logout">
        是
      </button>

      <button @click="showLogoutConfirm = false">
        否
      </button>

    </div>

  </div>

</div>

  <h1>商品列表</h1>

  <!-- 載入中 -->
  <div v-if="isLoading" class="status">
    商品載入中...
  </div>

  <!-- 發生錯誤 -->
  <div v-else-if="errorMessage" class="status error">
    <p>{{ errorMessage }}</p>

    <button @click="fetchProducts">
      重新載入
    </button>
  </div>

  <!-- 商品列表 -->
  <div v-else class="product-grid">

    <div
      v-for="product in products"
      :key="product.productId"
      class="product-card"
    >

      <div class="image-box">

      <img
        :src="`/images/products/${product.productImage}`"
        :alt="product.productName"
      />

      </div>

      <div class="product-info">

        <h3>
          {{ product.productName }}
        </h3>

        <p class="price">
          ${{ product.productPrice }}
        </p>

        <button @click="goToDetail(product.productId)">
          查看商品
        </button>

      </div>

    </div>

  </div>

</template>

<style scoped>

  /* 右上角會員與購物車區域 */
.top-bar {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 20px;
  margin-bottom: 20px;
}


/* 會員登入區域
   包含「未登入」與「立即登入」*/
.member-area {
  display: flex;
  align-items: center;
  gap: 10px;
}


/*「立即登入」超連結 */
.member-area a {
  text-decoration: none;
}

/* 登出按鈕 */
.logout-button {
  border: none;
  background: none;
  cursor: pointer;
  font-size: 14px;
}

/* 登出確認視窗背景 */
.logout-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.4);
}


/* 登出確認視窗 */
.logout-dialog {
  width: 300px;
  padding: 30px;
  background-color: white;
  border-radius: 10px;
  text-align: center;
}


/* 登出標題 */
.logout-dialog h3 {
  margin-bottom: 25px;
}


/* 是、否按鈕區域 */
.logout-actions {
  display: flex;
  justify-content: center;
  gap: 15px;
}


/* 是、否按鈕 */
.logout-actions button {
  padding: 8px 25px;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
}

/* 我的購物車 */
.cart-link {
  text-decoration: none;
  padding: 8px 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

/* 商品整體排列 */
.product-grid {
  display: grid;
  grid-template-columns: repeat(4, 250px);
  gap: 30px;
  justify-content: center;
}

/* 商品卡片 */
.product-card {
  width: 250px;
  min-height: 400px;

  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 15px;

  display: flex;
  flex-direction: column;

  box-sizing: border-box;
}

/* 圖片區域固定大小 */
.image-box {
  width: 100%;
  height: 250px;

  display: flex;
  justify-content: center;
  align-items: center;

  overflow: hidden;
}

/* 商品圖片 */
.image-box img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

/* 商品資訊 */
.product-info {
  flex: 1;

  display: flex;
  flex-direction: column;
}

/* 商品名稱 */
.product-info h3 {
  margin: 15px 0 8px;

  min-height: 48px;

  font-size: 18px;
}

/* 價格 */
.price {
  margin: 5px 0;

  font-size: 20px;
  font-weight: bold;
}

/* 查看商品按鈕 */
.product-info button {
  margin-top: auto;

  width: 100%;
  padding: 10px;

  border: none;
  border-radius: 6px;

  cursor: pointer;
}
</style>