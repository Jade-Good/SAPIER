<script setup lang="ts">
const workspaceStore = useWorkspaceStore()
const selectedWorkspaceName = ref('-')

watchEffect(() => {
  const selectedWorkspaceIndex = workspaceStore.selectedWorkspaceIndex !== null ? workspaceStore.selectedWorkspaceIndex : 0

  if (workspaceStore.workspaceInfo) {
    const workspace = workspaceStore.workspaceInfo[selectedWorkspaceIndex]
    const workspaceName = workspace ? workspace.name : '기본값'
    console.log('선택한 워크스페이스 이름: ', workspaceName)
    selectedWorkspaceName.value = workspaceName
  }
})
</script>

<template>
  <div>
    <li>name : {{ selectedWorkspaceName }}</li>

    <p>검색바 자리</p>
  </div>
</template>

<style></style>
