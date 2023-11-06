<template>
  <TheNav />
  <div flex class="mid">
    <WorkspaceList />
    <UserInfo w-60 />
    <MainInfo w-full />
  </div>
  <!-- <RouterView /> -->
</template>

<style>
.mid {
  height: calc(100% - 70px);
}
</style>

<route lang="yaml">
  meta:
    requiresAuth: true
</route>
