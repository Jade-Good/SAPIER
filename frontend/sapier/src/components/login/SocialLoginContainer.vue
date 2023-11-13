<template>
  <div class="social_login_container">
    <div class="blank_space" />
    <SocialLoginLogoContainer />
    <div class="blank_space" />
    <SocialLoginButtonContainer />
  </div>
</template>

<style scoped>
.social_login_container{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 25%;
    margin-left: 40%;
    margin-top: 7%;
    background: white;
    border-radius: 5%;
}

.blank_space{
    margin-bottom: 25%;
}
</style>
