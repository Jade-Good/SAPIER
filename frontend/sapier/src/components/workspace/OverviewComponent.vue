<script setup>
import { ref } from 'vue'

const axios = inject('$axios')

// const props = defineProps({
//   workspaceone: Object,
// })
// const { workspaceone } = defineProps(['workspaceone'])

const WorkspaceOneInfo = useWorkspaceStore()
// const collections = ref(WorkspaceOneInfo.workspaceInfo.collectionList) // Wrap collectionList in a ref
// const selectedDataArray = reactive([])
const memberInfo = useMemberStore()
const isMounted = useMounted()
const showDropdown = ref(false)
const route = useRouter();
// console.log(workspaceone.name)

if (isMounted) {

  if(WorkspaceOneInfo.workspaceInfo == null || WorkspaceOneInfo.workspaceInfo === undefined){
    route.push("/main")
  }
  else{
  // document.addEventListener('click', handleOutsideClick)
  axios
    .get(`/api/v1/workspaces/members/${WorkspaceOneInfo.workspaceInfo.key}`)
    .then((res) => {
      // console.log('memberList 가져오기 마운트시')
      // console.log(res)
      memberInfo.member = res.data
    })
    .catch((error) => {
      console.error('memberList 가져오기 : 마운트시', error)
    },
    )
  // if (WorkspaceOneInfo.workspaceInfo.pinnedList) {
  //   for (let index = 0; index < WorkspaceOneInfo.workspaceInfo.pinnedList.length; index++)
  //     selectedDataArray.push({ id: WorkspaceOneInfo.workspaceInfo.pinnedList[index].collectionKey, name: WorkspaceOneInfo.workspaceInfo.pinnedList[index].collectionName })

  //   for (let index = 0; index < WorkspaceOneInfo.workspaceInfo.pinnedList.length; index++) {
  //     const selectedCollection = collections.value.find(c => c.collectionKey === WorkspaceOneInfo.workspaceInfo.pinnedList[index].collectionKey)
  //     console.log(selectedCollection)
  //     console.log('selectedCollection')
  //     console.log(selectedCollection)

  //     if (selectedCollection && !selectedCollection.disabled)
  //       selectedCollection.disabled = true
  //   }
  // }
  // else {
  //   WorkspaceOneInfo.workspaceInfo.pinnedList = []
  // }

  // 새로운 컬렉션을 추가한 후 collections.value를 업데이트하여 해당 컬렉션을 비활성화
  // collections.value = WorkspaceOneInfo.workspaceInfo.collectionList.map(c => ({
  //   ...c,
  //   disabled: selectedDataArray.some(data => data.id === c.collectionKey),
  // }))
}
}

// onUnmounted(() => {
//   document.removeEventListener('click', handleOutsideClick)
// })

// function handleOutsideClick(event) {
//   // 클릭된 요소가 dropdown 내부에 속하지 않으면 dropdown을 닫음
//   if (showDropdown.value === true && !event.target.closest('.dropdown'))
//     showDropdown.value = false
// }

// workspaceone 변경 감시
watch(() => WorkspaceOneInfo.workspaceInfo, async (newWorkspaceOne) => {
  showDropdown.value = false
  // selectedDataArray.length = 0
  // if (newWorkspaceOne) {
  // selectedCollection.length = 0
  // if (newWorkspaceOne.pinnedList) {
  //   for (let index = 0; index < newWorkspaceOne.pinnedList.length; index++)
  //     selectedDataArray.push({ id: newWorkspaceOne.pinnedList[index].collectionKey, name: newWorkspaceOne.pinnedList[index].collectionName })
  // }
  // else {
  //   newWorkspaceOne.pinnedList = []
  //   console.log('빈리스트생서')
  // }

  // const selectedCollection = newWorkspaceOne.collectionList.value.find(c => c.collectionKey === newWorkspaceOne.pinnedList[index].collectionKey)

  // console.log(selectedCollection)
  // console.log('sdssgwrgwrgwgwegew')

  // for (let index = 0; index < newWorkspaceOne.pinnedList.length; index++) {
  //   if (selectedCollection && !selectedCollection.disabled)
  //     selectedCollection.disabled = true
  // }

  // if (WorkspaceOneInfo.workspaceInfo.pinnedList) {
  //   for (let index = 0; index < WorkspaceOneInfo.workspaceInfo.pinnedList.length; index++) {
  //     selectedDataArray.push({ id: WorkspaceOneInfo.workspaceInfo.pinnedList[index].collectionKey, name: WorkspaceOneInfo.workspaceInfo.pinnedList[index].collectionName })
  //     console.log(WorkspaceOneInfo.workspaceInfo.pinnedList[index])
  //     console.log('(WorkspaceOneInfo.workspaceInfo.pinnedList[index]')

  //     const selectedCollection = WorkspaceOneInfo.workspaceInfo.collectionList.value.find(c => c.collectionKey === WorkspaceOneInfo.workspaceInfo.pinnedList[index].collectionKey)
  //     console.log(selectedCollection)
  //     console.log(selectedCollection)

  //     if (selectedCollection && !selectedCollection.disabled)
  //       selectedCollection.disabled = true
  //   }
  // }
  // else {
  //   newWorkspaceOne.pinnedList = []
  // }

  try {
    const res = await axios.get(`/api/v1/workspaces/members/${WorkspaceOneInfo.workspaceInfo.key}`)
    // console.log('memberList 가져오기watch 안에서')
    // console.log(res)
    memberInfo.member = res.data
  }
  catch (error) {
    console.error('memberList 가져오기watch 안에서: ', error)
  }
  // }
})

function showUserInfo(user) {
  // console.log(user)
  localStorage.setItem('MemberData', JSON.stringify(user))
}

function showCollectionsDropdown() {
  showDropdown.value = !showDropdown.value
}

function addCollectionToPinned(collection) {
  WorkspaceOneInfo.workspaceInfo.pinnedList.push(collection)

  // console.log(WorkspaceOneInfo.workspaceInfo.pinnedList)
  // console.log(WorkspaceOneInfo.workspaceInfo)

  axios
    .patch(`/api/v1/workspaces/${WorkspaceOneInfo.workspaceInfo.key}`, WorkspaceOneInfo.workspaceInfo)
    .then((res) => {
      // console.log('pinnedlist 수정')
      // console.log(res)
    })
    .catch((error) => {
      console.error('pinnedlist 수정오류 : ', error)
    },
    )
}

function removeCollection(collectionId) {
  // console.log('removeCollection-------------------------before')
  // console.log(collectionId)

  // 선택한 컬렉션을 삭제
  WorkspaceOneInfo.workspaceInfo.pinnedList = WorkspaceOneInfo.workspaceInfo.pinnedList.filter(data => data.collectionKey !== collectionId)
  // selectedDataArray.splice(selectedDataArray.findIndex(data => data.id === collectionId), 1)
  // const selectedCollection = collections.value.find(c => c.collectionKey === collectionId)
  // selectedCollection.disabled = false

  // console.log('선택한 컬렉션 삭제-------------------------')
  // console.log(WorkspaceOneInfo.workspaceInfo.pinnedList)

  axios
    .patch(`/api/v1/workspaces/${WorkspaceOneInfo.workspaceInfo.key}`, WorkspaceOneInfo.workspaceInfo)
    .then((res) => {
      // console.log('pinnedlist 삭제')
      // console.log(res)
    })
    .catch((error) => {
      console.error('pinnedlist 삭제오류 : ', error)
    },
    )
}

function isCollectionDisabled(collection) {

  if (WorkspaceOneInfo.workspaceInfo.pinnedList) {
    return WorkspaceOneInfo.workspaceInfo.pinnedList.some(
      pinnedCollection => pinnedCollection.collectionName === collection.collectionName,
    )
  }
  else {
    WorkspaceOneInfo.workspaceInfo.pinnedList = []
    return true
  }
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
            <div class="imgBorder">
              <img src="/add.png" alt="Add Collection" @click="showCollectionsDropdown">
            </div>
            <div class="pin_add_image">
              <div v-if="showDropdown" class="dropdown">
                <div v-for="collection in WorkspaceOneInfo.workspaceInfo.collectionList" :key="collection.collectionKey">
                  <div class="collectionName" :class="{ disabled: isCollectionDisabled(collection) }" @click="addCollectionToPinned(collection)">
                    <div class="collectionNameDetail">
                      {{ collection.collectionName }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="pinDivText">
          <!-- {{ workspaceone }} -->
          <div v-for="data in WorkspaceOneInfo.workspaceInfo.pinnedList" :key="data.collectionKey" class="collection-border">
            <div>
              <img class="folder" src="/folder.svg">
              <div class="pinDataName">
                {{ data.collectionName }}
              </div>
            </div>
            <img class="remove-button" src="/xbox.png" @click="removeCollection(data.collectionKey)">
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
        <div v-if="memberInfo.member && memberInfo.member.length >= 2">
          <h5 class="maindivHeader">
            Contributors
          </h5>
          <div class="maindivText">
            <div w-18 border-r-2 class="list">
              <div v-for="m in memberInfo.member" :key="m?.uuid" class="box">
                <div id="workSpaceListData" class="workspaceId">
                  <div class="boxs" style="background: #BDBDBD;">
                    <img :src="m?.profileImageUrl" class="profile" alt="User Profile Image">s
                  </div>
                  <div class="name" @click="showUserInfo(m)">
                    {{ m?.nickname }}
                  </div>
                  <div>
                    {{ m?.email }}
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
  bottom: 0%; /* 드롭다운 메뉴가 화면 상단에 붙도록 설정 */
  z-index: 9999; /*드롭다운 메뉴의 z-인덱스를 설정*/
  width:11rem;
  padding :0.5rem;
  border-radius: 3px;
  border: 1px solid #C9C9C9; /* 테두리 설정 */

}
.overview-group {
  display: flex;
  margin-bottom: 2em; /*그룹 간의 간격 조절*/
  width: max-content;
  gap: 2em;
  margin-top:2em;
  margin-left: 13em;
}

.document-group {
  display: flex;
  gap: 2em;
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
.imgBorder{
  text-align: center; /* 가로 중앙 정렬 */
  display: flex;
  align-items: center; /* 세로 중앙 정렬 */
  justify-content: center; /* 세로 중앙 정렬 (다른 요소와 함께 사용할 때 유용) */
  width: 25px;
  height: 25px;
  border-radius: 3px;

  &:hover {
    background-color: #C9C9C9;
    /* 다른 스타일들을 추가할 수 있습니다. */
  }

}

.pin_add_image{
  width: auto; /* 이미지의 원본 크기를 유지 */
}
.collectionName{
  width:10rem;
  text-align: left; /* 가로 중앙 정렬 */
  display: flex;
  align-items: center; /* 세로 중앙 정렬 */
  border-radius: 3px;

  &:hover {
    background-color: #C9C9C9;
    /* 다른 스타일들을 추가할 수 있습니다. */
  }
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
