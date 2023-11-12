<script setup lang="ts">
const workspaceListStore = useWorkspaceListStore()
const workspaceStore = useWorkspaceStore()
const selectedWorkspaceIndex = ref<number>(-1)
const selectedWorkspaceName = ref('-')

watchEffect(() => {
  selectedWorkspaceIndex.value = workspaceStore.selectedWorkspaceIndex !== null ? workspaceStore.selectedWorkspaceIndex : 0

  // console.log('인덱스 출력: ', selectedWorkspaceIndex.value)
  // console.log('워크스페이스 인덱스: ', workspaceStore.selectedWorkspaceIndex)
  if (workspaceStore.workspaceInfo) {
    const workspace = workspaceListStore.WorkspaceList[selectedWorkspaceIndex.value]
    // console.log('워크스페이스 출력: ', workspace)
    const workspaceName = workspace ? workspace.name : '기본값'
    // console.log('선택한 워크스페이스 이름: ', workspaceName)
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
