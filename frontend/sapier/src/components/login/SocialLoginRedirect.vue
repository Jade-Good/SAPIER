<script setup lang="ts">
import axios from 'axios'

const userInfo = useUserStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true

const router = useRouter()

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/users`)
    .then((res) => {
      console.log(res)
      userInfo.user = res.data
      router.push(`/main`)
    })
    .catch((error) => {
      console.log(error)
      alert("로그인 오류");
      router.push(`/login/oauth`)
    },
    )
}
</script>

<template>
</template>

<style>
</style>
