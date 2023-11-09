
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
        <div class="user_info_detail_icon"><img :src=src></div>
        <div class="user_info_detail_data">
            <p class="user_info_detail_text">{{ data }}</p>
        </div>
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
    
    width: 65%;
    
    display: flex;
    flex-direction: column;
    justify-content: center;

}

.user_info_detail_text{
    display: block;

    font-size : 1.2vw;
    font-weight: 500;

    white-space:nowrap;
    overflow:hidden;
    text-overflow: ellipsis;
}
</style>