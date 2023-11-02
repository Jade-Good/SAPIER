<template>
  <TheNav />
  <div flex class="mid">
    <WorkspaceList />
    <CollectionInfo w-60 />
    <!-- <WorkSpaceInfo w-full /> -->
    <RequestInfo w-full />
  </div>
  <RouterView />

  <!-- <TheFooter />
    <div mx-auto mt-5 text-center text-sm opacity-50>
      [Default Layout]
    </div> -->
</template>

<style>
.mid {
  height: calc(100% - 70px);
}
</style>
