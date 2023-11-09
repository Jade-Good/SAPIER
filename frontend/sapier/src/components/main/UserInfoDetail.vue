
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
        <span class="user_info_detail_data">{{ data }}</span>
  </div>
</template>



<style>
.user_info_detail{
    display: flex;
    justify-content: left;
    margin-left: 10%;
    margin-bottom: 10%;
}

.user_info_detail_icon{
    width: 20%;
    height: auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-right: 5%;
}
.user_info_detail_data{
    display: flex;
    flex-direction: column;
    justify-content: center;
    font-size : 1.2vw;
    font-weight: 500;
}
</style>