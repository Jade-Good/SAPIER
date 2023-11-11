<script>
export default {
  data() {
    return {
      // page: 'Workspace',
      // page: 'Collection',
      // page: 'Request',
    }
  },
}
</script>

<template>
  <TheNav />
  <div flex class="mid">
    <WorkspaceList w-20 />
    <Category w-60 />
    <!-- <CollectionInfo w-60 /> -->
    <WorkSpaceInfo h-full w-full />
    <!-- <RequestInfo h-full w-full /> -->
  </div>
  <!-- <RouterView /> -->

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
