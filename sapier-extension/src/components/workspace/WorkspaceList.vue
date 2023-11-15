<script setup lang="ts">
import type { Component } from 'vue'
import { ref } from 'vue'
import WorkspaceInfo from './WorkspaceInfo.vue'

const currentComponent = ref<Component | null>(WorkspaceInfo)// 초기값은 MainInfo 컴포넌트로 설정
const WorkspaceOneInfo = ref<any>(null) // workspaceInfoOne 데이터를 저장할 ref

function truncateText(text: string, maxLength: number) {
  if (text.length > maxLength)
    return `${text.slice(0, maxLength)}`

  else
    return text
}

function showInfoComponent(workspaceInfoOne: any, index: any) {
  currentComponent.value = WorkspaceInfo // WorkspaceInfo 컴포넌트로 변경
  WorkspaceOneInfo.value = workspaceInfoOne
  // WorkspaceOneInfo.value.selectedWorkspaceIndex = index
}

const workspaceListInfo = ref<any>(null)

async function getWorkspaceList() {
  try {
    await browser.storage.local.get(['token']).then(async (value) => {
      await fetch('https://sapier.co.kr/api/v1/workspaces', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': `Bearer ${value.token}`,
        },
      })
        .then((response) => {
          console.log(response)
          if (!response.ok)
            throw new Error('네트워크 응답이 정상적이지 않습니다.')
          return response.text()
        })
        .then((text) => {
          try {
            return JSON.parse(text)
          }
          catch (error) {
            console.error('JSON 파싱 오류:', error)
            // 파싱 오류 처리
          }
        })
        .then((data) => {
          // 데이터 할당
          workspaceListInfo.value = data
        })
        .catch(error => console.error('Error:', error))
    })
  }
  catch (error) {
    console.error('Error getWorkspaceList:', error)
  }
}

onMounted(getWorkspaceList)
</script>

<template>
  <div w-18 border-r-2 class="list">
    <!-- <div>
      <ul>
        <li v-for="workspace in workspaceListInfo" :key="workspace.id">
          {{ workspace.name }}
        </li>
      </ul>
    </div> -->
    <div v-for="(workspace, index) in workspaceListInfo" :key="workspace.id" class="box" :style="{ backgroundColor: workspace.color }">
      <div id="workSpaceListData" class="workspaceId" @click="showInfoComponent(workspace, index)">
        {{ truncateText(workspace.name, 4) }}
      </div>
    </div>
    <div class="plus-box cross" />
  </div>
</template>

<style scoped>
.mid {
  height: calc(100% - 70px);
}

.list{

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
