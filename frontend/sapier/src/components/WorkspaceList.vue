<script setup lang="ts">
import axios from 'axios'

const userInfo = useUserStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces/1`)
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
  <div w-18 border-r-2 class="list">
    워크스페이스 리스트
  </div>
</template>

<style scoped>
.list{
  background: #C9C9C9;
  border-color: #B6B6B6;
}
</style>
