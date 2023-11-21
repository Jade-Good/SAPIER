<script setup lang="ts">
import { ref } from 'vue'

const WorkspaceOneInfo = ref<any>(null)

// WorkspaceOneInfo 얻기
browser.storage.local.get(['workspace']).then((data) => {
  // eslint-disable-next-line no-console
  console.log(`WorkspaceOneInfo 얻기 ${data.workspace}`)
  WorkspaceOneInfo.value = data.workspace
})
</script>

<template>
  <div class="mainInfo">
    <div class="overview-group">
      <!-- overview  document  pinned  collection -->
      <div class="maindiv">
        <h5 class="maindivHeader">
          Summmary
        </h5>
        <p class="maindivText">
          개인 워크스페이스 Overview 입니다.
        </p>
        <p class="maindivText">
          워크스페이스 요약, 설명이나 컬렉션을 고정할 수 있습니다.
        </p>
        <p class="maindivText">
          요약, 설명은 클릭하면 수정이 되고, 다른곳을 누르면 저장됩니다.
          글자수(높이)가 제한되어 있습니다.
        </p>
        <p class="maindivText">
          setting에서는 기타 워크스페이스 설정이나 삭제 및 탈퇴 할 수 있습니다.
        </p>
      </div>

      <div class="maindiv" />
    </div>

    <div class="document-group">
      <div class="maindiv">
        <h5 class="maindivHeader">
          document
        </h5>
        <p class="maindivText">
          개인 워크스페이스 Overview 입니다.
        </p>
        <p class="maindivText">
          워크스페이스 요약, 설명이나 컬렉션을 고정할 수 있습니다.
        </p>
        <p class="maindivText">
          요약, 설명은 클릭하면 수정이 되고, 다른곳을 누르면 저장됩니다.
          글자수(높이)가 제한되어 있습니다.
        </p>
        <p class="maindivText">
          setting에서는 기타 워크스페이스 설정이나 삭제 및 탈퇴 할 수 있습니다.
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.mainInfo {
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  /* width: 1220px; */
}
.dropdown {
  position: absolute;
  bottom: 0%; /* 드롭다운 메뉴가 화면 상단에 붙도록 설정 */
  z-index: 9999; /*드롭다운 메뉴의 z-인덱스를 설정*/
  width:11rem;
  padding :0.5rem;
  border-radius: 3px;
  border: 1px solid #C9C9C9; /* 테두리 설정 */

}
.overview-group {
  margin-left: 4rem;
}

.document-group {
  margin-left: 4rem;

}

.maindiv {
  display: block;
  width: 30em;
  padding-top: 1.5em;

}
.workspaceId{
  display: flex;
  flex-direction: row;
  gap: 2%;
  margin-top: 2%;
}
.OC_maindiv_add{
  display: flex;
  gap:1em;
    text-align: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
}
.collection-container{
  position: relative; /* 부모 요소에 상대 위치 설정 */
  text-align: center; /* 가로 중앙 정렬 */
  display: flex;
  align-items: center; /* 세로 중앙 정렬 */
  justify-content: center; /* 세로 중앙 정렬 (다른 요소와 함께 사용할 때 유용) */

}

.pin_add_image{
  width: auto; /* 이미지의 원본 크기를 유지 */
}

.collectionNameDetail{
  margin-left: 0.2rem;

}
.collection-border{
  width: 200px;
  height: 50px;
  border: 1px solid #C9C9C9; /* 테두리 설정 */
  border-radius:5px; /* 주위 라운드 설정 */
  margin-top: 0.5rem;

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

}
.remove-button {
  cursor: pointer;
  margin-left: 4px;
  margin-right: 4px;
  width :1rem;
}
.pinDataName{
  display: inline;
  font-size: var(--font-H5-size);
  margin-left: 0.5rem;

}
.folder{
  display: inline;
  width: 1.5rem;
  margin-left: 0.5rem;
}

.disabled {
  color: gray; /* 회색 텍스트 색상 */
  pointer-events: none; /* 클릭 이벤트 비활성화 */
}

.maindivHeader {
  display: inline-block;
  font-size: var(--font-H2-size);
  font-weight: 700;
}

/* .ContributorsHeader {
  display: inline-block;
  font-size: 24px;
  font-weight: 700;
} */
.list{
  width: 100%;

}
.maindivText {
  margin-left: 1em;
  font-size: var(--font-H6-size);
}
.pinDivText{
  margin-left: 1.3em;
  font-size: var(--font-H6-size);
  display: flex;
    flex-wrap: wrap;
    gap:1.5%;
}
.name{
  text-align: center; /* 가로 중앙 정렬 */
  display: flex;
  align-items: center; /* 세로 중앙 정렬 */
  justify-content: center; /* 세로 중앙 정렬 (다른 요소와 함께 사용할 때 유용) */

}

.boxs {
    width: 30px;
    height: 30px;
    border-radius: 70%;
    overflow: hidden;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>
