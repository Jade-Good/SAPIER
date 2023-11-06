<script setup lang="ts">
import axios from 'axios'

const user = useUserStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true

const route = useRouter();

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/users`)
    .then((res) => {
      console.log(res)
      user.userInfo = res.data
      useStorage('sapier-user', user.userInfo)
      route.push("/main");
    })
    .catch((error) => {
      console.log(error)
      alert("로그인 에러")
      route.push("/main");
    },
    )
}
</script>

<template>
  
</template>

<style>
</style>
