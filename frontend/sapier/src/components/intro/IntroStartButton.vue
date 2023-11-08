
<script setup lang="ts">

  const user = useUserStore();
  const route = useRouter();

  function start(){
  
    console.log(user)
        
    if (user.userInfo)
      route.push(`/main`)
    else
      route.push(`/login/oauth`)
  }

</script>

<template>
  <div class="get_start_button_item">
    <button @click="start" class="get_start_button">
        <p>시작하기</p>
    </button>
  </div>
</template>

<style scoped>
.get_start_button{
  width: 20rem;
  height : 6rem;
  border-radius: 30px;
  background: linear-gradient(90deg, #0F4C81 0%, #658DC6 100%);
  color : white;
  font-size: 2.5rem;
  font-weight: 700;
}

.get_start_button_item{
  display: flex;
  flex-direction: row;
  justify-content: center;
  /* background-color: red; */
}

</style>
