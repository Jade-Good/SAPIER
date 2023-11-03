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

const homeRoute = routes.find(route => route.path === '/')
if (homeRoute) {
  console.log('i found homeRoute')
  homeRoute.meta = { requiresAuth: false }
}

const oauthRoute = routes.find(route => route.path === '/login/oauth')
if (oauthRoute) {
  console.log('i found oauthRoute')
  oauthRoute.meta = { requiresAuth: false }
}

const redirectRoute = routes.find(route => route.path === '/login/redirect')
if (redirectRoute) {
  console.log('i found redirectRoute')
  redirectRoute.meta = { requiresAuth: false }
}

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

    ctx.router.beforeEach((to) => {
      const userStore = useUserStore()
      if (!(to.meta.requiresAuth === false) && !userStore.userInfo) {
        alert('로그인 해주세요.')
        return '/login/oauth'
      }
    })
  },
)
