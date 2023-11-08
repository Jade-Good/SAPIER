<template>
    
    <button class="social_login_button">
        <span class="social_login_button_img">
            <img :src=img @click="$emit('onclick')" />
        </span>
    </button>
    
</template>

<script>
export default {
    props : {
        img : String,
        onclick : Function
    }
}
</script>

<style>
.social_login_button{
  display: flex;
  justify-content: center;
}

.social_login_button_img{
  width: 70%;
  margin-top: 3%;

}

</style>