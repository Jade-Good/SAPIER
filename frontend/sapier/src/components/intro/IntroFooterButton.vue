<template>
  <div class="intro_footer_button">
    
    <div class="intro_footer_button_img">
        <img :src=img>
    </div>

    <div class="intro_footer_button_text">
        <p>{{text}}</p>
    </div>

  </div>
</template>

<script>
export default{
    props : {
        img : String,
        text : String
    }
}
</script>

<style>
.intro_footer_button_img{
    display: flex;
    flex-direction: row;
    justify-content: center;
    
    margin-top: 5%;
    width: 100%;
    
}

.intro_footer_button_text{
    display: flex;
    flex-direction: row;
    justify-content: center;
    font-size: 2.5vw;
    font-weight: 500;
}

.intro_footer_button{
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 5%;
    margin-top: 1%;
    
}
</style>