<script setup lang="ts">
const user = useUserStore()
const name = ref(user.savedName)
function enter() {
}

const { t } = useI18n()
</script>

<template>
  <nav flex flex-justify-between border-b-2 class="stroke" p1rem py-3>
    <div>
      <RouterLink to="/">
        <img width="120" src="/logo.svg" mt-1>
      </RouterLink>
    </div>
    <div>
      <TheInput
        v-model="name"
        :placeholder="t('main.search-now')"
        autocomplete="true"
        @keydown.enter="enter"
      />
      <label class="hidden" for="input">{{ t('main.search-now') }}</label>
    </div>
    <div mt-3 text-lg>
      <button mx-2>
        <div i-carbon-settings />
      </button>
      <button mx-2>
        <div i-carbon-recently-viewed />
      </button>
      <button mx-2>
        <div i-carbon-notification />
      </button>
      <button mx-2>
        <div i-carbon-user-avatar-filled-alt />
      </button>
    </div>
  </nav>
</template>

<style scoped>
.stroke{
    border-color: #B6B6B6;
}
</style>
