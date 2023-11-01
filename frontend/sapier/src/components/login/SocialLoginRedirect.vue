<script setup lang="ts">
import axios from 'axios'

const userInfo = useUserStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/users`)
    .then((res) => {
      console.log(res)
      userInfo.user = res.data
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
