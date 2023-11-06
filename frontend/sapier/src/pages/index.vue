<script setup lang="ts">
defineOptions({
  name: 'IndexPage',
})
const user = useUserStore()

const router = useRouter()
function start() {
  if (user.userInfo)
    router.push(`/main`)
  else
    router.push(`/login/oauth`)
    // router.push(`/main`)
}

// const { t } = useI18n()
</script>

<template>
  <div py>
    <img mx-5 width="200" src="/logo.svg">
  </div>
  <div bg-gray200 py-4>
    <div px-40 py-40>
      <div text-4xl>
        SSAFY + API = SAPIer!
      </div>
      <p>
        <a rel="noreferrer" href="https://github.com/antfu/vitesse" target="_blank">
          SAPIER
        </a>
      </p>
      <p>
        <em text-sm opacity-75>asd</em>
      </p>

      <div py-4 />

      <div>
        <button
          m-3 bg-bluegray-400 text-sm btn
          @click="start"
        >
          asd
        </button>
      </div>
    </div>
  </div>
  <!-- <RouterView /> -->
</template>

<route lang="yaml">
meta:
  requiresAuth: false
</route>
