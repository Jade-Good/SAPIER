<script setup lang="ts">
const extensionUrl = browser.runtime.getURL('dist/options/index.html')

function googleLogin() {
  // const googleLoginUrl = `${import.meta.env.VITE_SERVER_URL}/api/oauth2/authorization/google?redirect_uri=${import.meta.env.VITE_FRONT_URL}/login/redirect`
  const googleLoginUrl = `http://sapier.co.kr:8080/api/oauth2/authorization/google?redirect_uri=${extensionUrl}`
  browser.tabs.create({ url: googleLoginUrl })
  // window.location.href = googleLoginUrl
}

function githubLogin() {
  // const githubLoginUrl = `${import.meta.env.VITE_SERVER_URL}/api/oauth2/authorization/github?redirect_uri=${import.meta.env.VITE_FRONT_URL}/login/redirect`
  const githubLoginUrl = `http://sapier.co.kr:8080/api/oauth2/authorization/github?redirect_uri=${extensionUrl}`
  browser.tabs.create({ url: githubLoginUrl })
  // window.location.href = githubLoginUrl
}
</script>

<template>
  <div class="social_login_button_container">
    <button class="social_login_button">
      <span class="social_login_button_img">
        <img src="/assets/btn_google_siginIn.png" @click="googleLogin">
      </span>
    </button>
    <button class="social_login_button">
      <span class="social_login_button_img">
        <img src="/assets/btn_github_siginIn.png" @click="githubLogin">
      </span>
    </button>
    <div class="blacnk_space" />
  </div>
</template>

<style scoped>
.social_login_button_container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
}
.social_login_button{
  display: flex;
  justify-content: center;
}

.social_login_button_img{
  width: 70%;
  margin-top: 3%;
}

.blacnk_space{
  margin-top: 10%;
}
</style>
