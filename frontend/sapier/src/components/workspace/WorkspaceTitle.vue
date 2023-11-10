<script setup lang="ts">
const workspaceListStore = useWorkspaceListStore()
const workspaceStore = useWorkspaceStore()
const selectedWorkspaceName = ref('-')

watchEffect(() => {
  const selectedWorkspaceIndex = workspaceStore.selectedWorkspaceIndex !== null ? workspaceStore.selectedWorkspaceIndex : 0

  if (workspaceStore.workspaceInfo) {
    const workspace = workspaceListStore.WorkspaceList[selectedWorkspaceIndex]
    const workspaceName = workspace ? workspace.name : '기본값'
    // console.log('선택한 워크스페이스 이름: ', workspaceName)
    selectedWorkspaceName.value = workspaceName
  }
})
</script>

<template>
  <p>워크스페이스 제목란</p>
  <p>검색바 자리</p>
</template>

<style></style>
