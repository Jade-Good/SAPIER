<script setup lang="ts">
import axios from 'axios'

const WorkspaceListInfo = useWorkspaceStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true
// const dataElement = document.getElementById('workSpaceListData')

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces`)
    .then((res) => {
      console.log(res)
      WorkspaceListInfo.workspaceInfo = res.data
      // dataElement.textContent = WorkspaceListInfo.workSpaceInfo.
    })
    .catch((error) => {
      console.log(error)
    },
    )
}

function workspaceInfo(workspaceName) {
  const workspaceInfoUrl = `${import.meta.env.VITE_FRONT_URL}/workspaces/workspace?name=${workspaceName}`
  window.location.href = workspaceInfoUrl
}

function truncateText(text: string, maxLength: number) {
  if (text.length > maxLength)
    return `${text.slice(0, maxLength)}...`

  else
    return text
}
// const dataToSend = {
//   param1: ,
// };

// const hashString = JSON.stringify(dataToSend);
</script>

<template>
  <div w-18 border-r-2 class="list">
    <div v-for="workspace in WorkspaceListInfo.workspaceInfo" :key="workspace.name" class="box">
      <div id="workSpaceListData" class="workspaceId" @click="workspaceInfo(workspace.name)">
        {{ truncateText(workspace.name, 4) }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.list{
  background: #C9C9C9;
  border-color: #B6B6B6;
  display: flex; /* 부모 요소를 플렉스 컨테이너로 설정 */
  align-items: center;
  flex-direction: column;
}
.box{
  margin-top: 5px ;
  border-radius: 10px;
  width: 50px;
  height: 50px;
  border: 2px solid #000; /* 테두리 스타일 및 색상 설정 */
  background-color: yellow; /* 배경색 설정 */
}
.workspaceId{

  text-align: center; /* 텍스트 가운데 정렬 */
  line-height: 50px; /* 텍스트를 수직 중앙으로 정렬 */

}

.workspaceId:hover {
  cursor: pointer; /* 호버 시 커서를 손가락 아이콘으로 변경 */
}
</style>
