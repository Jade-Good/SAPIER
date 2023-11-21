<script setup lang="ts">
import { ref } from 'vue'
// import WorkspaceInfo from '../components/workspace/WorkSpaceInfo.vue'
// import MainInfo from '../components/main/MainInfo.vue'

// const currentComponent = ref<Component | null>(MainInfo)// 초기값은 MainInfo 컴포넌트로 설정
// const WorkspaceOneInfo = ref<any>(null) // workspaceInfoOne 데이터를 저장할 ref

function truncateText(text: string, maxLength: number) {
  if (text.length > maxLength)
    return `${text.slice(0, maxLength)}`

  else
    return text
}

function showInfoComponent(workspaceInfoOne: any, workspaceInfoOneIdx: any) {
  browser.storage.local.set({ workspace: workspaceInfoOne })
  browser.storage.local.set({ workspaceIndex: workspaceInfoOneIdx })
  browser.storage.local.get(['workspace']).then(data => console.log(`워크스페이스 : ${data.workspace.name}`))
  browser.storage.local.get(['workspaceIndex']).then(data => console.log(`워크스페이스 인덱스 : ${data.workspaceIndex}`))
}

const workspaceListInfo = ref<any>(null)
browser.storage.local.get(['workspaceList']).then((data) => {
  workspaceListInfo.value = data.workspaceList
  console.log(`workspaceList : ${data.workspaceList}`)
})
</script>

<template>
  <div w-18 border-r-2 class="list">
    <div v-for="(workspace, index) in workspaceListInfo" :key="workspace.id" class="box" :style="{ backgroundColor: workspace.color }">
      <div id="workSpaceListData" class="workspaceId" @click="showInfoComponent(workspace, index)">
        {{ truncateText(workspace.name, 4) }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.mid {
  height: calc(100% - 70px);
}

.list{
  padding-top: 1rem;
  display: flex; /* 부모 요소를 플렉스 컨테이너로 설정 */
  align-items: center;
  flex-direction: column;
}
.box{
  margin-top: 5px ;
  border-radius: 10px;
  width: 50px;
  height: 50px;
  background-color:#658DC6; /* 배경색 설정 */
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
  color: #F0F0F0;
  font-size: var(--font-H4-size);
  font-weight: var(--font-H5-weight);
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
