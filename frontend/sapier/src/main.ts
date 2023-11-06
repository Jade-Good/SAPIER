import { ViteSSG } from 'vite-ssg'
import { setupLayouts } from 'virtual:generated-layouts'

// import Previewer from 'virtual:vue-component-preview'
import { createRouter, createWebHistory } from 'vue-router'
import App from './App.vue'
import type { UserModule } from './types'
import generatedRoutes from '~pages'

import '@unocss/reset/tailwind.css'
import './styles/main.css'
import 'uno.css'

const routes = setupLayouts(generatedRoutes)

export const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// https://github.com/antfu/vite-ssg
export const createApp = ViteSSG(
  App,
  { routes, base: import.meta.env.BASE_URL },
  (ctx) => {
    // install all modules under `modules/`
    Object.values(import.meta.glob<{ install: UserModule }>('./modules/*.ts', { eager: true }))
      .forEach(i => i.install?.(ctx))
    // ctx.app.use(Previewer)

    // 로그인 상태 복원
    const userStore = useUserStore()
    const storedUserInfo = localStorage.getItem('sapier-user')
    if (storedUserInfo) {
      const userInfo = JSON.parse(storedUserInfo)
      userStore.userInfo = userInfo
      console.log(`userStore: ${userStore.userInfo}`)
    }

    ctx.router.beforeEach((to) => {
      const userStore = useUserStore()
      if (to.meta.requiresAuth === true && !userStore.userInfo) {
        alert('로그인 해주세요.')
        return '/login/oauth'
      }
    })
  },
)
