
<script setup lang="ts">

function googleLogin(){
  console.log("구글로그인 시도");
  const googleLoginUrl = `${import.meta.env.VITE_SERVER_URL}/api/oauth2/authorization/google?redirect_uri=${import.meta.env.VITE_FRONT_URL}/login/redirect`;
  window.location.href = googleLoginUrl;
  
}

function githubLogin(){
  const githubLoginUrl = `${import.meta.env.VITE_SERVER_URL}/api/oauth2/authorization/github?redirect_uri=${import.meta.env.VITE_FRONT_URL}/login/redirect`;
  window.location.href = githubLoginUrl;
}
      
</script>



<template>
  
  <button m-3 bg-bluegray-400 text-sm btn @click="googleLogin">
    구글로그인
  </button>
         
        
  <button m-3 bg-bluegray-400 text-sm btn @click="githubLogin">
    깃헙로그인
  </button>

</template>





<style>
</style>

