<!-- <script>
import OverviewComponent from './OverviewComponent.vue'
import SettingComponent from './SettingComponent.vue'

export default {
  components: {
    OverviewComponent,
    SettingComponent,
  },

  props: {
    workspaceone: Object, // workspaceInfo를 props로 선언
  },

  data() {
    return {
      currentComponent: OverviewComponent,
    }
  },

  mounted() {
    // props로 받은 데이터를 콘솔에 출력
    console.log('Received props data:', this.workspaceone)
  },
  methods: {
    showOverviewComponent() {
      this.currentComponent = OverviewComponent
    },
    showSettingComponent() {
      this.currentComponent = SettingComponent
    },
  },

}
</script> -->
<script setup>
import { ref } from 'vue'
import OverviewComponent from './OverviewComponent.vue'
import SettingComponent from './SettingComponent.vue'

// const { workspaceone } = defineProps(['workspaceone'])

const currentComponent = ref(OverviewComponent)
const WorkspaceOneInfo = useWorkspaceStore()
const workspaceTap = ref('overview')

// console.log(workspaceone)
// console.log('workspaceinfovue data')
</script>

<template>
  <div class="list">
    <!-- <div class="overview">
      overview
    </div> -->
    <div class="workspaceHead">
      <div class="image-container">
        <img src="/person.png">
      </div>
      <div class="workspace-name">
        {{ WorkspaceOneInfo.workspaceInfo.name }}
      </div>
    </div>
    <div class="overviewSettingHead">
      <div class="overviewText" :class="[workspaceTap !== 'overview' ? 'tap' : 'highlight']" @click="currentComponent = OverviewComponent, workspaceTap = 'overview'">
        overview
      </div>
      <div class="settingText" :class="[workspaceTap !== 'setting' ? 'tap' : 'highlight']" @click="currentComponent = SettingComponent, workspaceTap = 'setting'">
        setting
      </div>
    </div>
    <component :is="currentComponent" /><!-- :workspaceone="workspaceone" -->
  </div>
</template>

<!-- <template>
  <div class="list">
    <h1 class="overview">
      overview
    </h1>
    <div class="workspaceHead">
      <div class="image-container">
        <img src="/person.png">
      </div>
      <div>{{ workspaceone.name }}&&{{ WorkspaceOneInfo.WorkspaceOneInfo }}</div>
    </div>
    <div class="workspaceHead">
      <div class="overviewText tap" @click="showOverviewComponent">
        overview
      </div>
      <div class="settingText tap" @click="showSettingComponent">
        setting
      </div>
    </div>
    <component :is="currentComponent" :workspaceone="workspaceone" />
  </div> -->

  <!-- <div flex select-none flex-gap-5 p-3>
        <div :class="[requestTap !== 'Params' ? 'tap' : 'highlight']" @click="requestTap = 'Params'">
          <p>Params</p>
        </div>
        <div :class="[requestTap !== 'Headers' ? 'tap' : 'highlight']" @click="requestTap = 'Headers'">
          <p>Headers</p>
        </div>
        <div :class="[requestTap !== 'Body' ? 'tap' : 'highlight']" @click="requestTap = 'Body'">
          <p>Body</p>
        </div>
        <div :class="[requestTap !== 'Settings' ? 'tap' : 'highlight']" @click="requestTap = 'Settings'">
          <p>Settings</p>
        </div>
      </div>

      <Params v-if="requestTap === 'Params'" />
      <Headers v-if="requestTap === 'Headers'" />
      <Body v-if="requestTap === 'Body'" />
      <Settings v-if="requestTap === 'Settings'" />
</template> -->

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
  border-bottom: 1px solid #000; /* 밑줄 추가 */
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

.tap {
  color: var(--color-gray4);

  &:hover{
    color:#2E2E2E;
  }
}
</style>
