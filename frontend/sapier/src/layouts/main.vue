<script setup lang="ts">
import axios from 'axios'
import { ref } from 'vue'

import MainInfo from '../components/main/MainInfo.vue'
import WorkspaceInfo from '../components/workspace/WorkSpaceInfo.vue'

// import WorkspaceListInf from '../stores/workspace'

// const WorkspacList :Array<WorkspaceListInf> = useWorkspaceStore()
const WorkspaceListInfo = useWorkspaceStore()
const isMounted = useMounted()
const currentComponent = ref<Component | null>(MainInfo)// 초기값은 MainInfo 컴포넌트로 설정
const workspaceinfo = ref<any>(null) // workspaceInfoOne 데이터를 저장할 ref

axios.defaults.withCredentials = true

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces`)
    .then((res) => {
      console.log(res)
      // WorkspacList = res.data
      WorkspaceListInfo.workSpaceInfo = res.data
    })
    .catch((error) => {
      console.error(error)
    },
    )
}

function showInfoComponent(workspaceInfoOne: any) {
  currentComponent.value = WorkspaceInfo // WorkspaceInfo 컴포넌트로 변경
  workspaceinfo.value = workspaceInfoOne
  WorkspaceListInfo = workspaceInfoOne
  console.log('----------main------------')
  console.log(workspaceinfo)
}
</script>

<template>
  <TheNav />
  <div flex class="mid">
    <div w-18 border-r-2 class="list">
      <div v-for="workspace in WorkspaceListInfo.workSpaceInfo" :key="workspace.name" class="box">
        <div id="workSpaceListData" class="workspaceId" @click="showInfoComponent(workspace)">
          {{ workspace.name }}
        </div>
      </div>
    </div>

    <UserInfo w-60 />
    <!-- <MainInfo w-full /> -->
    <component :is="currentComponent" w-full :workspaceone="workspaceinfo" />
  </div>
  <RouterView />

  <!-- <TheFooter />
    <div mx-auto mt-5 text-center text-sm opacity-50>
      [Default Layout]
    </div> -->
</template>

<style scoped>
.mid {
  height: calc(100% - 70px);
}

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
.active {
  border-color: #000;
}
</style>
