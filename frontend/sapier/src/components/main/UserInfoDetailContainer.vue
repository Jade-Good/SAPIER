
<script setup>

const userInfo = useUserStore();

const mailLink = "mailto:" + `${userInfo.userInfo.email}`;
</script>


<template>
    <div class="user_info_detail_container">
        <UserInfoDetail 
            src="/src/components/main/nickname_icon.png"
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