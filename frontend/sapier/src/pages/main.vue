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
import type { Component } from 'vue'
import { ref } from 'vue'

import MainInfo from '../components/main/MainInfo.vue'
import WorkspaceInfo from '../components/workspace/WorkSpaceInfo.vue'
import UserInfo from '../components/main/UserInfo.vue'

// import CategoryInfo from '../components/workspace/Category.vue'

const axios = inject('$axios')

const WorkspaceListInfo = useWorkspaceListStore()
const WorkspaceOneInfo = useWorkspaceStore()
const User = useUserStore()

const isMounted = useMounted()
const currentComponent = ref<Component | null>(MainInfo)// 초기값은 MainInfo 컴포넌트로 설정
// const currentUserComponent = ref<Component | null>(UserInfo)
const workspaceinfo = ref<any>(null) // workspaceInfoOne 데이터를 저장할 ref

axios.defaults.withCredentials = true

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

function showInfoComponent(workspaceInfoOne: any) {
  currentComponent.value = WorkspaceInfo // WorkspaceInfo 컴포넌트로 변경
  // workspaceinfo.value = workspaceInfoOne
  console.log('----------main------------')
  console.log(workspaceinfo)
  WorkspaceOneInfo.workspaceInfo = workspaceInfoOne
  // WorkspaceOneInfo.$patch(workspaceInfoOne)

  console.log(WorkspaceOneInfo)
  console.log('asdfasfasdfadsfasdf')
}

function truncateText(text: string, maxLength: number) {
  if (text.length > maxLength)
    return `${text.slice(0, maxLength)}`

  else
    return text
}

async function addWorkSpace() {
  try {
    const Userdata = {

      memberList: [
        {
          uuId: User.userInfo?.uuid,
          userPermission: 'admin',
        },

      ],
      name: 'My  workspace',
    }

    // axios를 사용하여 서버에 POST 요청을 보냅니다.
    const response = await axios.post(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces`, Userdata)

    // 요청이 성공하면 WorkspaceList에 새로운 데이터를 추가합니다.
    this.WorkspaceListInfo.WorkspaceList.push(Userdata)
  }
  catch (error) {
    console.error('Error adding workspace:', error)
  }
}
</script>

<template>
  <TheNav />
  <div flex class="mid">
    <div w-18 border-r-2 class="list">
      <div v-for="workspace in WorkspaceListInfo.WorkspaceList" :key="workspace.name" class="box" :style="{ backgroundColor: workspace.color }">
        <div id="workSpaceListData" class="workspaceId" @click="showInfoComponent(workspace)">
          {{ truncateText(workspace.name, 4) }}
        </div>
      </div>
      <div class="plus-box cross" @click="addWorkSpace()" />
    </div>

    <UserInfo w-60 />
    <!-- <component :is="currentUserComponent" w-60 /> -->
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
  color:#F0F0F0;
  cursor: pointer;

}
.plus-box{
  margin-top: 5px ;
  border-radius: 10px;
  width: 50px;
  height: 50px;
  border: 2px solid #000; /* 테두리 스타일 및 색상 설정 */
  background-color :#658DC6; /* 배경색 설정 */
  color:#F0F0F0;
  cursor: pointer;

}

.workspaceId{
  text-align: center; /* 텍스트 가운데 정렬 */
    line-height: 50px; /* 텍스트를 수직 중앙으로 정렬 */
}

.cross {
  position: relative;
}

.cross::before,
.cross::after {
  content: '';
  position: absolute;
  background-color: #F0F0F0; /* 바의 색상을 설정하세요. */
}

.cross::before {
  width: 2px;
  height: 50%;
  top: 25%;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 50px
}

.cross::after {
  width: 50%;
  height: 2px;
  top: 50%;
  left: 25%;
  transform: translateY(-50%);
  border-radius: 50px

}

.workspaceId:hover {
  cursor: pointer; /* 호버 시 커서를 손가락 아이콘으로 변경 */
}
.active {
  border-color: #000;
}
</style>
