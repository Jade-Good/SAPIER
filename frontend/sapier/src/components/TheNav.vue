<script setup lang="ts">
// const user = useUserStore()
// const name = ref(user.savedName)
function enter() {
}
</script>

<template>
  <nav class="nav" flex flex-justify-between border-b-2 p1rem py-3>
    <div>
      <RouterLink to="/">
        <img width="120" src="/logo.svg" mt-1>
      </RouterLink>
    </div>
    <div>
      <TheInput
        v-model="name"
        autocomplete="true"
        @keydown.enter="enter"
      />
      <label class="hidden" for="input">Search with SAPIER now!</label>
    </div>
    <div mt-3 text-lg>
      <button mx-2>
        <div i-carbon-settings />
      </button>
      <button mx-2 onclick="location.href='/histories/history';">
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
.nav{
  height: 70px;
  border-color: #B6B6B6;
}
</style>
