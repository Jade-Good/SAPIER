<script>
export default {
  methods: {
    googleLogin() {
      const googleLoginUrl = `${import.meta.env.VITE_SERVER_URL}/api/oauth2/authorization/google?redirect_uri=${import.meta.env.VITE_FRONT_URL}/login/redirect`
      window.location.href = googleLoginUrl
    },
    githubLogin() {
      const githubLoginUrl = `${import.meta.env.VITE_SERVER_URL}/api/oauth2/authorization/github?redirect_uri=${import.meta.env.VITE_FRONT_URL}/login/redirect`
      window.location.href = githubLoginUrl
    },
  },
}
</script>

<template>
  <div class="social_login_button_container">
    <SocialLoginButton
      img="/src/components/login/btn_google_siginIn.png"
      @onclick="googleLogin"
    />

    <SocialLoginButton
      img="/src/components/login/btn_github_siginIn.png"
      @onclick="githubLogin"
    />

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

.blacnk_space{
  margin-top: 20%;
}
</style>
