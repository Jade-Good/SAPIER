<script setup lang="ts">
import api from "../../modules/api";

const user = useUserStore()
const isMounted = useMounted()

const route = useRouter();

if (isMounted) {
  api
    .get(`/api/v1/users`)
    .then((res) => {
      console.log(res)
      user.userInfo = res.data
      useStorage('sapier-user', user.userInfo)
      route.push("/main");
    })
    .catch((error) => {
      console.log(error)
      alert("로그인 에러")
      route.push("/login/oauth");
    },
    )
}
</script>

<template>
  
</template>

<style>
</style>
