<template>
  <div class="social_login_container">
    <div class="social_login_logo_container_item">
      <span class="social_login_logo_sub">Free API Tester</span>
    </div>
    <div class="social_login_logo_container_item">
      <span class="social_login_logo_text">Please Join or Login</span>
    </div>
    <span class="social_login_button_img">
      <img src="./assets/btn_google_siginIn.png">
    </span>
    <span class="social_login_button_img">
      <img src="./assets/btn_github_siginIn.png">
    </span>
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
.social_login_logo_text{
  color: #0F4C81;
  font-size: 5vw;
  font-weight: 500;
}
</style>
