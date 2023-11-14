<script setup lang="ts">
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { storageDemo } from '~/logic/storage'

function openOptionsPage() {
  browser.runtime.openOptionsPage()
}

const userData = ref<any>(null)

async function fetchData() {
  try {
    const response = await axios.get('https://sapier.co.kr:8080/api/v1/users')
    userData.value = response.data
    // eslint-disable-next-line no-console:
    console.log(`userData : ${userData.value}`)
  }
  catch (error) {
    console.error('There was an error!', error)
  }
}

onMounted(() => {
  fetchData()
})

// const isLoggedIn = ref(false)
// browser.storage.local.get('loggedIn').then((data) => {
//   // eslint-disable-next-line no-console
//   console.log(`로그인 검사 ${data.loggedIn}`)
//   isLoggedIn.value = data.loggedIn
// })
</script>

<template>
  <main class="w-[300px] px-1 py-3 text-center text-gray-700">
    <Logo />
    <div>Popup</div>
    <SharedSubtitle />
    <!-- <SocialLoginContainer v-if="!isLoggedIn" />
    <UserInfo v-else /> -->
    <button class="btn mt-2" @click="openOptionsPage">
      Open Options
    </button>
    <div class="mt-2">
      <span class="opacity-50">Storage:</span> {{ storageDemo }}
    </div>
  </main>
</template>
