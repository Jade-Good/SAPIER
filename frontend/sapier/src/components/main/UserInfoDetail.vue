
<script>
export default{
    props :{
        src : String,
        data : String,
    }
}
</script>

<template>
  <div class="user_info_detail">
        <img class="user_info_detail_icon" :src=src>
        <p class="user_info_detail_data">{{ data }}</p>
  </div>
</template>



<style>
.user_info_detail{
    display: flex;
    margin-left: 10%;
    margin-bottom: 10%;
}

.user_info_detail_icon{
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-right: 5%;
}
.user_info_detail_data{
    display: flex;
    flex-direction: column;
    justify-content: center;

    font-weight: 500;
}
</style>