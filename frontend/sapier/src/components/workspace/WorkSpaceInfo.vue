<script>
import OverviewComponent from './OverviewComponent.vue'
import SettingComponent from './SettingComponent.vue'

export default {
  components: {
    OverviewComponent,
    SettingComponent,
  },
  data() {
    return {
      currentComponent: OverviewComponent,
    }
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
</script>

<template>
  <div class="list">
    <h1 class="overview">
      overview
    </h1>
    <div class="workspaceHead">
      <div class="image-container">
        <img src="/person.png">
      </div>
      <div>my workspace</div>
    </div>
    <div class="workspaceHead">
      <div class="overviewText" @click="showOverviewComponent">
        overview
      </div>
      <div class="settingText" @click="showSettingComponent">
        setting
      </div>
    </div>
    <component :is="currentComponent" />
  </div>
</template>

<style scoped>
.list{

  border: 1px solid #000;
  display: flex; /* 부모 요소를 플렉스 컨테이너로 설정 */
  flex-direction: column; /* 아이템을 수직으로 정렬하기 위해 컬럼 방향 설정 */
  /* align-items: left; 수평 가운데 정렬 */
}
.overview{
  border-bottom: 1px solid #000; /* 밑줄 추가 */
  height: fit-content

}
.workspaceHead {
  padding-bottom: 10px;
  display: flex; /* .workspaceHead를 플렉스 컨테이너로 설정 */
  align-items: center; /* 수평 가운데 정렬 */
  border-bottom: 1px solid #000; /* 밑줄 추가 */
}

.image-container {
  margin-right: 10px; /* 이미지와 텍스트 사이의 간격을 조절 */
}
.overviewText{
  margin-left: 10px; /* 이미지와 텍스트 사이의 간격을 조절 */

}
.settingText{
  margin-left: 10px; /* 이미지와 텍스트 사이의 간격을 조절 */
}
</style>
