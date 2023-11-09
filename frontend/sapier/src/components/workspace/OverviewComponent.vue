<script setup>
import axios from 'axios'
import { ref } from 'vue'

// const props = defineProps({
//   workspaceone: Object,
// })
// const { workspaceone } = defineProps(['workspaceone'])

const WorkspaceOneInfo = useWorkspaceStore()

const memberInfo = useMemberStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true
// console.log(workspaceone.name)
console.log('sdfsdfsdfsdfjawrhboiaernbineqriobnoierbnoqwirn')

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces/members/${WorkspaceOneInfo.workspaceInfo.key}`)
    .then((res) => {
      console.log('memberList 가져오기')
      console.log(res)
      memberInfo.member = res.data
    })
    .catch((error) => {
      console.log(error)
    },
    )
}

// workspaceone 변경 감시
watch(() => WorkspaceOneInfo.workspaceInfo, async (newWorkspaceOne) => {
  if (newWorkspaceOne) {
    try {
      console.log('newWorkspaceOne')
      console.log(newWorkspaceOne)

      const res = await axios.get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces/members/${newWorkspaceOne.key}`)
      console.log('memberList 가져오기ww')
      console.log(res)
      memberInfo.member = res.data
    }
    catch (error) {
      console.log(error)
    }
  }
})

function showUserInfo(user) {
  console.log(user)
  localStorage.setItem('MemberData', JSON.stringify(user))
}

const collections = ref(WorkspaceOneInfo.workspaceInfo.collectionList) // Wrap collectionList in a ref
const showDropdown = ref(false)
const selectedDataArray = ref([])
function showCollectionsDropdown() {
  showDropdown.value = !showDropdown.value
}

function addCollectionToPinned(collection) {
  const selectedCollection = collections.value.find(c => c.id === collection.id)
  if (selectedCollection && !selectedCollection.disabled) {
    selectedDataArray.value.push({ id: collection.id, name: collection.collectionName })
    selectedCollection.disabled = true
    console.log(`Adding "${collection.collectionName}" to pinned collections`)
  }
}

function dropdownClick(event) {
  // 드롭다운 메뉴를 클릭해도 이벤트 버블링을 중지시키지 않음
  event.stopPropagation()
}

function removeCollection(collectionId) {
  // 선택한 컬렉션을 삭제
  selectedDataArray.value = selectedDataArray.value.filter(data => data.id !== collectionId)
  const selectedCollection = collections.value.find(c => c.id === collectionId)
  if (selectedCollection)
    selectedCollection.disabled = false
}
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

      <div class="maindiv">
        <div class="OC_maindiv_add">
          <h5 class="maindivHeader">
            Pinned Collections
          </h5>
          <div class="collection-container">
            <!-- <img src="/add.png" class="pin_add_image"> -->
            <img src="/add.png" alt="Add Collection" @click="showCollectionsDropdown">
            <div class="pin_add_image">
              <div v-if="showDropdown" class="dropdown">
                <div v-for="collection in WorkspaceOneInfo.workspaceInfo.collectionList" :key="collection.id">
                  <div :class="{ disabled: collection.disabled }" @click="addCollectionToPinned(collection)">
                    {{ collection.collectionName }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="maindivText">
          <!-- {{ workspaceone }} -->
          <div v-for="data in selectedDataArray" :key="data.id" class="collection-border">
            {{ data.name }}
            <span class="remove-button" @click="removeCollection(data.id)">X</span>
          </div>
        </div>
      </div>
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

      <div class="maindiv">
        <div v-if="memberInfo.member.length >= 2">
          <h5 class="maindivHeader">
            Contributors
          </h5>
          <div class="maindivText">
            <div w-18 border-r-2 class="list">
              <div v-for="m in memberInfo.member" :key="m.uuid" class="box">
                <div id="workSpaceListData" class="workspaceId">
                  <div class="boxs" style="background: #BDBDBD;">
                    <img :src="m.profileImageUrl" class="profile" alt="User Profile Image">s
                  </div>
                  <div class="name" @click="showUserInfo(m)">
                    {{ m.nickname }}
                  </div>
                  <div>
                    {{ m.email }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.mainInfo {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  /* width: 1220px; */
}
.dropdown {
  position: absolute;
  bottom: 80%; /* 드롭다운 메뉴가 화면 상단에 붙도록 설정 */
  z-index: 1000; /* 드롭다운 메뉴의 z-인덱스를 설정 */
  /* 나머지 스타일 속성들 */
}
.overview-group {
  display: flex;
  margin-bottom: 2em; /*그룹 간의 간격 조절*/
  width: max-content;
  gap: 10em;
  margin-top:2em;
  margin-left: 13em;
}

.document-group {
  display: flex;
  gap: 10em;
  margin-left: 13em;

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
  max-height: 1.2em; /* 이미지의 최대 높이 설정 */
}
.collection-border{
  display: inline-block;
  width: 300px;
  border: 2px solid #2c3e50; /* 테두리 설정 */
  border-radius: 10px; /* 주위 라운드 설정 */
}
.remove-button {
  cursor: pointer;
  margin-left: 4px;
}

.disabled {
  color: gray; /* 회색 텍스트 색상 */
  pointer-events: none; /* 클릭 이벤트 비활성화 */
}

.maindivHeader {
  display: inline-block;
  font-size: 1.5em;
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
  font-size: 1em;
  font-weight: 400;
  margin-left: 0.5em;
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
