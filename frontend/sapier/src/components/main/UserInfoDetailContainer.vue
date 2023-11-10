
<script setup>

const userInfo = useUserStore();

let socialProviderIcon = "";
if(userInfo.userInfo.socialProvider === "GOOGLE")
    socialProviderIcon = "/src/components/main/google.png";
else if(userInfo.userInfo.socialProvider === "GITHUB")
    socialProviderIcon = "/src/components/main/github.png";

const mailLink = "mailto:" + `${userInfo.userInfo.email}`;
</script>


<template>
    <div class="user_info_detail_container">
        <UserInfoDetail 
            :src=socialProviderIcon
            :data=userInfo.userInfo.nickname />
        
        <UserInfoDetail 
            src="/src/components/main/email_icon.png"     
            :data=userInfo.userInfo.email 
            :link=mailLink />
    </div>
  </template>



<style>
.user_info_detail_container{
    display: flex;
    flex-direction: column;
}
</style>