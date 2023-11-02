<script setup lang="ts">
import axios from 'axios'

const user = useUserStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/users`)
    .then((res) => {
      console.log(res)
      user.userInfo = res.data
      useStorage('sapier-user', user.userInfo)
    })
    .catch((error) => {
      console.log(error)
    },
    )
}
</script>

<template>
  <p>대충 로그인 중인 화면</p>
</template>

<style>
</style>
