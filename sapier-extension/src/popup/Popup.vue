<script setup lang="ts">
import { onMounted, ref } from 'vue'
// import axios from 'axios'
// import { storageDemo } from '~/logic/storage'

function openOptionsPage() {
  browser.runtime.openOptionsPage()
}

// const userData = ref<any>(null)

// async function fetchData() {
//   try {
//     const response = await axios.get('https://sapier.co.kr:8080/api/v1/users')
//     userData.value = response.data
//     // eslint-disable-next-line no-console:
//     console.log(`userData : ${userData.value}`)
//   }
//   catch (error) {
//     console.error('There was an error!', error)
//   }
// }

onMounted(() => {
  // fetchData()
})

const isLoggedIn = ref<any>(null)

browser.storage.sync.get('loggedIn').then((data) => {
  // eslint-disable-next-line no-console
  console.log(`로그인 검사 data.loggedIn ${data.loggedIn}`)
  isLoggedIn.value = data.loggedIn
  console.log(`로그인 검사 isLoggedIn ${isLoggedIn.value}`)
  console.log(`로그인 검사 isLoggedIn.value ${isLoggedIn.value}`)
})
</script>

<template>
  <main class="w-[300px] px-1 py-3 text-center text-gray-700">
    <SocialLoginContainer v-if="!isLoggedIn" />
    <div v-else>
      <div class="social_popup_logo_container_item">
        <span class="social_popup_logo_image">
          <img src="/assets/logo_sapier.png" w-50>
        </span>
      </div>
      <div>
        로그인 성공
      </div>
      <button class="btn mt-2" @click="openOptionsPage">
        Open Options
      </button>
    </div>

    <!-- <div class="mt-2">
      <span class="opacity-50">Storage:</span> {{ storageDemo }}
    </div> -->
  </main>
</template>

<style scoped>
.social_popup_logo_container_item{
  display: flex;
  justify-content: center;
}

.social_popup_logo_image{
  width: 60%;
}
</style>
