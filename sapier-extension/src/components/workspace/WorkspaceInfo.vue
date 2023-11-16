<script setup lang="ts">
import { shallowRef } from 'vue'
import OverviewComponent from './OverviewComponent.vue'

const currentComponent = shallowRef(OverviewComponent)
const WorkspaceOne = ref<any>(null)
const workspaceTap = ref('overview')

browser.storage.local.get(['workspace']).then((data) => {
  WorkspaceOne.value = data.workspace
})
</script>

<template>
  <div class="list">
    <div class="workspaceHead">
      <div class="image-container">
        <img src="/assets/workspace/group_copy.svg">
      </div>
      <div class="workspace-name">
        {{ WorkspaceOne ? WorkspaceOne.name : '' }}
      </div>
    </div>
    <div class="overviewSettingHead">
      <div class="overviewText" :class="[workspaceTap !== 'overview' ? 'tap' : 'highlight']" @click="currentComponent = OverviewComponent, workspaceTap = 'overview'">
        overview
      </div>
    </div>
    <component :is="currentComponent" />
    <!-- :workspaceone="workspaceone" -->
  </div>
</template>

<style scoped>
/* 가로 스크롤 바 숨기기 */
::-webkit-scrollbar {
  width: 0; /* 가로 스크롤 바의 너비를 0으로 설정하여 숨깁니다. */
}

/* 세로 스크롤 바 숨기기 (선택 사항) */
::-webkit-scrollbar-thumb {
  display: none; /* 스크롤 바의 스크롤 박스(Thumb)를 숨깁니다. */
}
.list{
  overflow-x: hidden;
  border: 1px solid #000;
  display: flex; /* 부모 요소를 플렉스 컨테이너로 설정 */
  flex-direction: column; /* 아이템을 수직으로 정렬하기 위해 컬럼 방향 설정 */
}
.highlight {
  border-bottom: 3px;
  border-bottom-style: solid;
  border-block-color: var(--color-blue1);
  /* border: solid 1px blue; */
}
.overview{
  border-bottom: 1px solid #000; /* 밑줄 추가 */
  /* height: fit-content; */
}
.workspace-name{
  font-size: var(--font-H1-size);
  font-weight: var(--font-H1-weight);
}
.workspaceHead {
  border-bottom: 1px solid #C9C9C9; /* 밑줄 추가 */
  padding-bottom: 10px;
  display: flex; /* .workspaceHead를 플렉스 컨테이너로 설정 */
  align-items: center; /* 수평 가운데 정렬 */
  font-size: var(--font-H6-size);
  width: 100%;
  /* font-weight: var(--font-H1-weight); */
}

.overviewSettingHead {

  padding-bottom: 10px;
  display: flex; /* .workspaceHead를 플렉스 컨테이너로 설정 */
  align-items: center; /* 수평 가운데 정렬 */
  font-size: var(--font-H6-size);
  width: 100%;
  margin-left: 30px;
  /* font-weight: var(--font-H1-weight); */
}
.workspaceHead:hover {
  cursor: pointer; /* 호버 시 커서를 손가락 아이콘으로 변경 */
}
.image-container {
  margin-right: 25px; /* 이미지와 텍스트 사이의 간격을 조절 */
  margin-left: 25px; /* 이미지와 텍스트 사이의 간격을 조절 */

}
.overviewText{
  margin-left: 10px; /* 이미지와 텍스트 사이의 간격을 조절 */

}
.settingText{
  margin-left: 10px; /* 이미지와 텍스트 사이의 간격을 조절 */
}

/* .tap {
  color: var(--color-gray4);

  &:hover{
    color:#2E2E2E;
  }
} */
</style>
