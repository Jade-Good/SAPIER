
<script setup>

const userInfo = useUserStore();

</script>


<template>
    <div class="user_info_detail_container">
          <UserInfoDetail src="/src/components/main/nickname_icon.png"  :data=userInfo.userInfo.nickname />
          <UserInfoDetail src="/src/components/main/email_icon.png"     :data=userInfo.userInfo.email />
    </div>
  </template>



<style>
.user_info_detail_container{
    display: flex;
    flex-direction: column;
}
</style>