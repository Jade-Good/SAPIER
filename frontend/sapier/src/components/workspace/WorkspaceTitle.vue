<script setup lang="ts">
import { inject } from 'vue'

const axios = inject('$axios')

const workspaceListStore = useWorkspaceListStore()
const workspaceStore = useWorkspaceStore()
const selectedWorkspaceIndex = ref<number>(-1)
const selectedWorkspaceName = ref('-')
let memberListSize = 1

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

  memberListSize = workspaceStore.workspaceInfo?.memberList.length
  // console.log('길이', memberListSize)
  // console.log('스토어 확인', workspaceStore.workspaceInfo?.memberList)
})

async function addCollectionDocument() {
  let newDocumentId = ''
  try {
    const response = await axios.post(`/api/v1/collection`)
    console.log('axios.post  성공', response)

    try {
      const resLast = await axios.get(`/api/v1/collection/last`)
      console.log('axios get 성공', resLast)
      newDocumentId = resLast.data
    }
    catch (error) {
      console.error(error)
    }

    try {
      const newCollectionInfo = {
        collectionKey: newDocumentId,
        collectionName: 'New Document',
      }
      // console.log('컬렉션 인포 : ', JSON.stringify(newCollectionInfo))
      const workspaceId = workspaceStore.workspaceInfo?.key
      const res = await axios.post(`/api/v1/workspaces/collection/${workspaceId}`, newCollectionInfo)
      // console.log('워크스페이스에 추가 axios 성공', res)
    }
    catch (error) {
      console.error(error)
    }
  }
  catch (error) {
    console.error(error)
  }
}
</script>

<template>
  <div class="titleBox">
    <!-- <img :src="imageSource" class="people"> -->
    <img v-if="memberListSize >= 2" src="./group.svg" class="people">
    <img v-else src="./person.svg" class="people">
    <div class="titleName">
      {{ selectedWorkspaceName }}
    </div>
    <img src="./Group 70.png" class="plus" @click="addCollectionDocument()">
  </div>
  <!-- <p>검색바 자리</p> -->

  <input type="text" placeholder="search" class="searchBox"><div class="searching" />
</template>

<style scoped>
  .titleBox {
    display: flex;
    align-items: center; /* 수직 가운데 정렬 */
    justify-content: space-between; /* 수평으로 간격을 벌림 */
    margin-top: 15%;
    margin-bottom: 10%;
    height: 30px;
max-width: 100%;
  }

  .titleName {
font-size: var(--font-H2-size);
font-weight: var(--font-H5-weight);
white-space: nowrap;       /* 텍스트가 줄 바꿈되지 않도록 설정 */
  overflow: hidden;          /* 넘치는 텍스트를 숨김 */
  text-overflow: clip;   /* 넘치는 텍스트에 '...'을 표시하여 잘림을 나타냄 */
  max-width: 75%;
  }

  .people {
    margin-left: 10%;
    margin-right: 5%;
  }
  .plus {
    margin-right: 10%;
    margin-left: 5%;
  }

  .searchBox{
    background-color: var(--color-gray2);
    margin-left: 5%;
    border-radius: 10px;
    height: 30px;
    width: 90%;
    background-repeat: no-repeat;
    background-image: url('./search.png');
    background-position: 10px 6px;
    margin-bottom: 25px;

  }
  input:focus { outline:none; width: 75%;}

  .searchBox::placeholder{
    font-size: var(--font-H6-size);
    font-weight: var(--font-H6-weight);
    color: var(--color-gray4);
    text-align: center;

  }
</style>
