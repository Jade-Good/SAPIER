<script setup lang="ts">
defineOptions({
  name: 'IndexPage',
})
const user = useUserStore()
const name = ref(user.savedName)

const router = useRouter()
function start() {
  if (name.value)
    router.push(`/main`)
  else
    router.push(`/login`)
}

const { t } = useI18n()
</script>

<template>
  <div py>
    <img mx-5 width="200" src="../../public/logo.svg">
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
        <em text-sm opacity-75>{{ t('intro.desc') }}</em>
      </p>

      <div py-4 />

      <!-- <TheInput
      v-model="name"
      :placeholder="t('intro.whats-your-name')"
      autocomplete="false"
      @keydown.enter="go"
    />
    <label class="hidden" for="input">{{ t('intro.whats-your-name') }}</label> -->

      <div>
        <button
          m-3 text-sm btn
          @click="start"
        >
          {{ t('button.start') }}
        </button>
      </div>
    </div>
  </div>
</template>

<route lang="yaml">
meta:
  layout: home
</route>
