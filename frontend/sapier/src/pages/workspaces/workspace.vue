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
    <WorkspaceList />
    <!-- <WorkSpaceInfo w-full /> -->
    <CollectionInfo w-60 />
    <RequestInfo w-full />
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
