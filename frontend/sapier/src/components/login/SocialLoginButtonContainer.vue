
<script setup lang="ts">

function googleLogin(){
  console.log("구글로그인 시도");
  const googleLoginUrl = `${import.meta.env.VITE_SERVER_URL}:8080/api/oauth2/authorization/google?redirect_uri=${import.meta.env.VITE_FRONT_URL}/login/redirect`;
  window.location.href = googleLoginUrl;
  
}

function githubLogin(){
  const githubLoginUrl = `${import.meta.env.VITE_SERVER_URL}:8080/api/oauth2/authorization/github?redirect_uri=${import.meta.env.VITE_FRONT_URL}/login/redirect`;
  window.location.href = githubLoginUrl;
}
      
</script>



<template>
  
  <div class="social_login_button_container">
    
    
      <button class="social_login_button_container_item">
        <span class="social_login_button">
          <img src="./btn_google_siginIn.png" @click="googleLogin"/>
        </span>
      </button>
  
          
  
      <button class="social_login_button_container_item">
        <span class="social_login_button">
          <img src="./btn_github_siginIn.png" @click="githubLogin"/>
        </span>
      </button>

      <div class="social_login_button_container_blacnk_space"></div>
  </div>

</template>




<style scoped>
.social_login_button_container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  
  /* background-color: yellow; */
}

.social_login_button_container_item{
  display: flex;
  justify-content: center;
}

.social_login_button{
  width: 70%;
  margin-top: 3%;

}

.social_login_button_container_blacnk_space{
  margin-top: 20%;
}

</style>

