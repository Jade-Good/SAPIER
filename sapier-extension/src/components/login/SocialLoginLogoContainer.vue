<script setup lang="ts">
</script>

<template>
  <div class="social_login_logo_container">
    <div class="social_login_logo_container_item">
      <span class="social_login_logo_image">
        <img src="/assets/logo_sapier.png" w-50>
      </span>
    </div>
    <div class="social_login_logo_container_item">
      <span class="social_login_logo_sub">Free API Tester</span>
    </div>
    <div class="social_login_logo_container_item">
      <span class="social_login_logo_text">Please Join or Login</span>
    </div>
  </div>
</template>

<style scoped>
.social_login_logo_container{
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
}

.social_login_logo_container_item{
  display: flex;
  justify-content: center;
}

.social_login_logo_image{
  width: 60%;
}

.social_login_logo_sub{
  color: #0F4C81;
  font-size: 7vw;
  font-weight: 700;
}

.social_login_logo_text{
  color: #0F4C81;
  font-size: 5vw;
  font-weight: 500;
}
</style>
