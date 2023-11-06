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
      WorkspaceListInfo.workSpaceInfo = res.data
      // dataElement.textContent = WorkspaceListInfo.workSpaceInfo.
    })
    .catch((error) => {
      console.log(error)
    },
    )
}
</script>

<template>
  <div w-18 border-r-2 class="list">
    <div class="box">
      <div id="workSpaceListData" class="workspaceId">
        B101
      </div>
    </div>
  </div>
</template>

<style scoped>
.list{
  background: #C9C9C9;
  border-color: #B6B6B6;
  display: flex; /* 부모 요소를 플렉스 컨테이너로 설정 */
    justify-content: center; /* 수평 가운데 정렬 */

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
