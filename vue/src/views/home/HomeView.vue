<template>
  <div class="home">
    <h1>(ﾟ∀。) Shop</h1>

    <br>
    <p>
      歡迎來到"沒什麼貨的"電商網站
    </p>
    

    <router-link
      to="/products"
      class="btn"
    >
      查看商品
    </router-link>
  </div>
</template>

<style scoped>
.home {
  min-height: 100vh;

  display: flex;
  flex-direction: column;

  justify-content: center;
  align-items: center;

  background: #3d6995;

  text-align: center;
}

.home h1 {
  color: #000000;
  font-size: 40px;
  margin-bottom: 10px;
  font-weight: bold;
}

.home p {
  color: #000000;
  margin-bottom: 32px;
}

.btn {
  padding: 12px 24px;

  background: #379c6e;
  color: white;

  border-radius: 6px;

  text-decoration: none;

  font-weight: bold;
}

.btn:hover {
  background: #369b6e;
}
</style>