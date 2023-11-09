<script setup>

const userInfo = useUserStore();

</script>


<template>
    <div class="user_profile">
        <img class="user_profile_info" :src=userInfo.userInfo.profileImageUrl >
    </div>
</template>



<style scoped>
.user_profile{
    display: flex;
    justify-content: center;

    width: 100%;

    margin-top: 10%;
    margin-bottom: 10%;
    
    
    
    /* background-color: red; */
}

.user_profile_info{
    width: 90%;
    border-radius: 50%;
    border : 0.2rem solid #C9C9C9;
    /* background-color: white; */
}
</style>