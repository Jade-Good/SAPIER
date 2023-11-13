// import Previewer from 'virtual:vue-component-preview'
import { createRouter, createWebHistory } from 'vue-router'
import { createPinia } from 'pinia'
import NProgress from 'nprogress'
import axios from './plugins/axios'

import App from './App.vue'
import generatedRoutes from '~pages'

import '@unocss/reset/tailwind.css'
import './styles/main.css'
import 'uno.css'

const app = createApp(App)
const pinia = createPinia()
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: generatedRoutes,
})

// 앱과 라우터를 사용합니다.
app.use(router)
app.use(pinia)

app.config.globalProperties.axios = axios

app.provide('$axios', axios)

router.beforeEach((to, from) => {
  if (to.path !== from.path)
    NProgress.start()
})
router.afterEach(() => {
  NProgress.done()
})

const userStore = useUserStore()
const storedUserInfo = localStorage.getItem('sapier-user')
if (storedUserInfo) {
  const userInfo = JSON.parse(storedUserInfo)
  userStore.userInfo = userInfo
  console.log(`userStore: ${userStore.userInfo}`)
}
router.beforeEach((to) => {
  const userStore = useUserStore()
  if (to.meta.requiresAuth === true && !userStore.userInfo) {
    alert('로그인 해주세요.')
    return '/login/oauth'
  }
})

// 앱을 마운트합니다.
app.mount('#app')
