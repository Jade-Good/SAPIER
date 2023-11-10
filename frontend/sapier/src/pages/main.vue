<!-- <template>
  <TheNav />
  <div flex class="mid">
    <WorkspaceList />
    <UserInfo w-60 />
    <MainInfo w-full />
  </div> -->
  <!-- <RouterView /> -->
<!-- </template>

<style>
.mid {
  height: calc(100% - 70px);
}
</style>

<route lang="yaml">
  meta:
    requiresAuth: true
</route> -->

<script setup lang="ts">
import { ref } from 'vue'

import MainInfo from '../components/main/MainInfo.vue'
import WorkspaceInfo from '../components/workspace/WorkSpaceInfo.vue'

const axios = inject('$axios')

const WorkspaceListInfo = useWorkspaceListStore()
const WorkspaceOneInfo = useWorkspaceStore()

const isMounted = useMounted()
const currentComponent = ref<Component | null>(MainInfo)// 초기값은 MainInfo 컴포넌트로 설정
const workspaceinfo = ref<any>(null) // workspaceInfoOne 데이터를 저장할 ref

if (isMounted) {
  axios
    .get(`/api/v1/workspaces`)
    .then((res) => {
      // console.log(res)
      WorkspaceListInfo.WorkspaceList = res.data
    })
    .catch((error) => {
      console.error(error)
    },
    )
}

// function showInfoComponent(workspaceInfoOne: any) {
//   currentComponent.value = WorkspaceInfo // WorkspaceInfo 컴포넌트로 변경
//   // workspaceinfo.value = workspaceInfoOne
//   console.log('----------main------------')
//   console.log(workspaceinfo)
//   WorkspaceOneInfo.workspaceInfo = workspaceInfoOne
//   // WorkspaceOneInfo.$patch(workspaceInfoOne)

//   console.log(WorkspaceOneInfo)
//   console.log('asdfasfasdfadsfasdf')
// }

function handleClick(workspaceInfoOne: any, index) {
  // 첫 번째 이벤트 동작
  firstAction(workspaceInfoOne)

  // 두 번째 이벤트 동작
  secondAction(index)
}

function firstAction(workspaceInfoOne: any) {
  // 첫 번째 이벤트에 대한 동작 수행
  currentComponent.value = WorkspaceInfo // WorkspaceInfo 컴포넌트로 변경
  // workspaceinfo.value = workspaceInfoOne
  console.log('----------main------------')
  console.log(workspaceinfo)
  WorkspaceOneInfo.workspaceInfo = workspaceInfoOne
  // WorkspaceOneInfo.$patch(workspaceInfoOne)

  console.log(WorkspaceOneInfo)
  console.log('asdfasfasdfadsfasdf')
}

function secondAction(index) {
  // 두 번째 이벤트에 대한 동작 수행
  WorkspaceOneInfo.selectedWorkspaceIndex = index
  console.log('index: ', index)
}

function truncateText(text: string, maxLength: number) {
  if (text.length > maxLength)
    return `${text.slice(0, maxLength)}`

  else
    return text
}
</script>

<template>
  <TheNav />
  <div flex class="mid">
    <div w-18 border-r-2 class="list">
      <div v-for="(workspace, index) in WorkspaceListInfo.WorkspaceList" :key="workspace.name" class="box" :style="{ backgroundColor: workspace.color }">
        <div id="workSpaceListData" class="workspaceId" @click="handleClick(workspace, index)">
          <!-- </div>showInfoComponent(workspace)"> -->
          {{ truncateText(workspace.name, 4) }}
        </div>
      </div>
    </div>

    <UserInfo w-60 />
    <Category w-60 />
    <!-- <MainInfo w-full /> -->
    <component :is="currentComponent" w-full /><!--    <component :is="currentComponent" w-full :workspaceone="workspaceinfo" />
-->
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
  background-color:#0F4C81; /* 배경색 설정 */
  color:#F0F0F0
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
