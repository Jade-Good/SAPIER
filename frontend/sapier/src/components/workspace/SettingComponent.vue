<script setup lang="ts">
import { defineStore } from 'pinia'

const axios = inject('$axios')

// const props = defineProps({
//   workspaceone: Object,
// })
const WorkspaceOneInfo = useWorkspaceStore()

// const { workspaceone } = defineProps(['workspaceone'])

const memberInfo = useUserStore()
const userInfo = useUserStore()

const isMounted = useMounted()

// const workspaceName = ref(workspaceone.name)
const shouldRender = ref(true) // 초기값 설정
const searchInput = ref('')

const boxColor = ref('yellow') // 초기 색상 설정
const colors = ['red', 'blue', 'green', 'purple', 'orange'] // 사용할 색상 목록
const alphabet = ['A', 'B', 'C', 'D', 'E'] // 사용할 색상 목록

function firstChangeBoxColor(color) {
  boxColor.value = color
  WorkspaceOneInfo.workspaceInfo.color = color
}

function changeBoxColor(color) {
  boxColor.value = color
  WorkspaceOneInfo.workspaceInfo.color = color

  axios
    .patch(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces/${WorkspaceOneInfo.workspaceInfo.key}`, WorkspaceOneInfo.workspaceInfo)
    .then((res) => {
      console.log('changeBoxColor (setting component)patch-----------------------')
      console.log(res)
    })
    .catch((error) => {
      console.log(error)
    },
    )
}

if (isMounted) {
  axios
    .get(`/api/v1/workspaces/members/${WorkspaceOneInfo.workspaceInfo.key}`)
    .then((res) => {
      // console.log('memberList (setting component)가져오기')
      // console.log(res)
      memberInfo.member = res.data
    })
    .catch((error) => {
      console.error('memberList (setting component)가져오기 실패 : ', error)
    },
    )
  firstChangeBoxColor(WorkspaceOneInfo.workspaceInfo?.color)
}

// workspaceone 변경 감시
watch(() => WorkspaceOneInfo.workspaceInfo, async (newWorkspaceOne) => {
  if (newWorkspaceOne) {
    try {
      const res = await axios.get(`/api/v1/workspaces/members/${newWorkspaceOne.key}`)
      // console.log(res)
      memberInfo.member = res.data
    }
    catch (error) {
      console.error('memberList 가져오기 실패 : ', error)
    }
  }
  console.log('iwejfopjwefopjweopopwnerw[voerokopr]')

  console.log(WorkspaceOneInfo.workspaceInfo)
  changeBoxColor(WorkspaceOneInfo.workspaceInfo?.color)
})

computed(() => {

})

const dropdownData = ref({
  isOpen: false, // 드롭다운 메뉴 표시 여부
  selectedPermission: null, // 선택한 권한
})

// 드롭다운을 토글하는 메서드
function toggleDropdown() {
  dropdownData.value.isOpen = !dropdownData.value.isOpen
}

// 권한 선택 메서드
function selectPermission(user, userpermission) {
  user.userPermission = userpermission
  dropdownData.value.isOpen = false // 드롭다운 닫기
  // console.log(user)

  // for (let index = 0; index < props.workspaceone.memberList.length; index++) {
  //   if (user.uuid === props.workspaceone.memberList.uuId[index])
  //     workspaceone.memberList.userPermission[index] = permission
  // }
  for (let index = 0; index < WorkspaceOneInfo.workspaceInfo.memberList.length; index++) {
    if (WorkspaceOneInfo.workspaceInfo.memberList[index].uuId === user.uuid)
      WorkspaceOneInfo.workspaceInfo.memberList[index].userPermission = userpermission
  }
  const data = {
    workspaceIdx: WorkspaceOneInfo.workspaceInfo.key,
    memberUuid: user.uuid,
    permission: userpermission,
  }
  axios
    .patch(`/api/v1/workspaces/members/${user.uuid}`, data)
    .then((res) => {
      // console.log('memberList (setting component)patch')
      // console.log(res)
      memberInfo.member = res.data

      // user.selectedPermission = userpermission
    })
    .catch((error) => {
      console.error('memberList (setting component)patch 실패 : ', error)
    },
    )
}

function updateWorkspaceName(event) {
  // 사용자가 입력 필드의 내용을 수정할 때 호출됩니다.

  const newName = event

  WorkspaceOneInfo.updateWorkspaceName(newName)
  axios
    .patch(`/api/v1/workspaces/${WorkspaceOneInfo.workspaceInfo.key}`, WorkspaceOneInfo.workspaceInfo)
    .then((res) => {
      // console.log('updateWorkspaceName (setting component)patch-----------------------')
      // console.log(res)
    })
    .catch((error) => {
      console.error('updateWorkspaceName (setting component)patch----------------------- 실패 : ', error)
    },
    )

  // console.log('updateWorkspaceName')
  // console.log(WorkspaceOneInfo.workspaceInfo)
}

function truncateText(text: string, maxLength: number) {
  if (text.length > maxLength)
    return `${text.slice(0, maxLength)}`

  else
    return text
}
// Leave Workspace 버튼 클릭 시 실행될 메서드
function leaveWorkspace() {
  // Leave Workspace 로직 구현

  axios
    .delete(`/api/v1/workspaces/members/${WorkspaceOneInfo.workspaceInfo.key}`)
    .then((res) => {
      // console.log('leaveWorkspace (setting component)delete')
      // console.log(res)
      window.location.reload()
    })
    .catch((error) => {
      console.error('leaveWorkspace (setting component)delete 실패 : ', error)
    },
    )
}

// Delete Workspace 버튼 클릭 시 실행될 메서드
function deleteWorkspace() {
  // Delete Workspace 로직 구현

  axios
    .delete(`/api/v1/workspaces/${WorkspaceOneInfo.workspaceInfo.key}`)
    .then((res) => {
      // console.log('deleteWorkspace (setting component)delete')
      // console.log(res)
      window.location.reload()
    })
    .catch((error) => {
      console.error('deleteWorkspace (setting component)delete : ', error)
    },
    )
}

const isAdmin = computed(() => {
  // Replace the condition with your actual logic
  return WorkspaceOneInfo.workspaceInfo.memberList.some(mber => mber.uuId === userInfo.userInfo.uuid && mber.userPermission == 'admin')
})
</script>

<template>
  <div class="mainInfo">
    <div class="overview">
      <div class="maindiv">
        <h5 class="maindivHeader">
          Information
        </h5>
        <div>워크스페이스 이름과 팀코드, 썸네일 설정</div>
        <div class="workspacemaindiv">
          <div v-if="shouldRender">
            <div class="box" :style="{ backgroundColor: boxColor }">
              <div id="workSpaceListData" class="workspaceId">
                {{ truncateText(WorkspaceOneInfo.workspaceInfo.name, 4) }}
              </div>
            </div>
          </div>
          <div ml-5 w-80>
            <div>워크스페이스 이름 </div>
            <input :value="WorkspaceOneInfo.workspaceInfo.name" type="text" @keyup.enter="updateWorkspaceName($event.target.value)"><!-- v-model="workspaceone.name" -->

            <div class="changeColor_div">
              <div v-for="(color, index) in colors" :key="alphabet[index]" class="changeColor" @click="changeBoxColor(color)">
                <div class="color_type" :style="{ backgroundColor: color }">
                  {{ alphabet[index] }}
                </div>
              </div>
            </div>
          </div>

          <!-- <button class='color_type' v-for="(color, index) in colors" :key="index" @click="changeColor(color)">{{ color }}</button>

            <div class="color_type">
              A
            </div> -->
        </div>

        <div class="maindiv">
          <h5 class="maindivHeader">
            People in this workspace
          </h5>
          <div class="invite_div">
            <input v-model="searchInput" ml-1 class="invite_input" placeholder="  Search by name or email" type="text">
            <button mr-1 p-1 class="invite_box" :disabled="searchInput === ''" :style="{ backgroundColor: searchInput === '' ? 'grey' : 'aqua' }" @click="search">
              Invite
            </button>
          </div>

          <div v-for="m in memberInfo.member" :key="m?.uuid">
            <div class="permission">
              <div class="boxs" style="background: #BDBDBD;">
                <img :src="m?.profileImageUrl" class="profile" alt="User Profile Image">
              </div>

              <div>
                {{ WorkspaceOneInfo.workspaceInfo.memberList.find(item => item.uuId === m?.uuid)?.userPermission }}
                <!-- {{ m.selectedPermission }} -->
              </div>
              <div class="userPermission" @click="toggleDropdown">
                <img src="/dropdown toggle.png">
              </div>
              <div v-if="dropdownData.isOpen" class="dropdown-menu">
                <div @click="selectPermission(m, 'admin')">
                  Admin
                </div>
                <div @click="selectPermission(m, 'member')">
                  Member
                </div>
                <div @click="selectPermission(m, 'viewer')">
                  Viewer
                </div>
              </div>
              <!-- 드롭다운 메뉴 표시 여부에 따라 드롭다운 아이템 표시 -->
            </div>
          </div>
        </div>

        <div class="maindiv">
          <h5 class="goodByeHeader">
            Good Bye workspace
          </h5>
          <div>워크스페이스를 떠나거나 삭제합니다. 데이터에 접근할 수 없게 됩니다.</div>
          <button @click="leaveWorkspace">
            Leave Workspace
          </button>

          <!-- Delete Workspace 버튼 (permission이 'admin'일 때만 표시) -->
          <button v-if="isAdmin" @click="deleteWorkspace">
            Delete Workspace
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.mainInfoHeader{
  height: 13vh;
}

.mainInfo{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  width: 100% ;
}
.overview{
  height: 100vh;
  width: 50%;
}
.workspacemaindiv{
  display: flex;
  width: 100%;
  padding-top: 3em;
  gap: 5%;

}
.maindiv{
  display: block;
  width: 100%;
  padding-top: 3em;

  font-size: var(--font-H5-size);
  font-weight: var(--font-H5-weight);
}
.box{
  margin-top: 5px ;
  border-radius: 10px;
  width: 100px;
  height: 100px;
  border: 2px solid #000; /* 테두리 스타일 및 색상 설정 */
  background-color: #0F4C81; /* 배경색 설정 */
  color:#F0F0F0

}
.color_type{
  text-align: center; /* 가로 중앙 정렬 */
  display: flex;
  align-items: center; /* 세로 중앙 정렬 */
  justify-content: center; /* 세로 중앙 정렬 (다른 요소와 함께 사용할 때 유용) */

}
.workspaceId{
  text-align: center; /* 텍스트 가운데 정렬 */

}
.maindivHeader{

  display: inline-block;
  inline-size: auto; /* 자동으로 글자 크기에 맞게 조절됨 */
  font-size: 24px;
  font-weight:700;
}
.goodByeHeader{

  display: inline-block;
  inline-size: auto; /* 자동으로 글자 크기에 맞게 조절됨 */
  font-size: 24px;
  font-weight:700;
}
.changeColor_div{
  display: flex;
  align-items: center; /* 세로 중앙 정렬 */
}
.changeColor{
    width: 30px;
    /* height: 20%; */
    border-radius: 70%;
    overflow: hidden;
    margin: 0.5%;
    cursor: pointer;
    border: 1px solid gray;
    /* background-color: yellow; 배경색 설정 */

}
.invite_div{
  display: flex; /* 자식 요소를 가로로 정렬하기 위해 Flexbox 레이아웃을 사용합니다. */
  justify-content: space-between; /* 자식 요소 사이에 공간을 균등하게 배치합니다. */
}
.invite_input::placeholder {
  font-size: 85%; /* 힌트 텍스트의 글꼴 크기 설정 */
  color: #888; /* 힌트 텍스트의 색상 설정 */
}
.invite_input {
  font-size: 80%; /* 힌트 텍스트의 글꼴 크기 설정 */
}
/* .name{
  text-align: center; 가로 중앙 정렬
  display: flex;
  align-items: center; 세로 중앙 정렬
  justify-content: center; 세로 중앙 정렬 (다른 요소와 함께 사용할 때 유용)

} */

.permission{
  display: flex;
  flex-direction: row;
  gap: 2%;
  margin-top: 2%;
}
/* .userPermission{
  display: flex;
  flex-direction: row;
  width: 20%;
} */

.userPermission {
    display: flex;
    flex-direction: row;
    width: 20%;
    cursor: pointer;
    user-select: none; /* 드래그 또는 텍스트 선택 방지 */

    /* // 드롭다운 아이콘 스타일링 */
    img {
      width: 20px;
      height: 20px;
    }
  }

  /* // 드롭다운 메뉴 스타일링 */
.dropdown-menu {
  /* position: absolute; */
  top: 100%;
  left: 0;
  background-color: white;
  border: 1px solid #ccc;
  border-top: none;
  z-index: 1;
}

  .dropdown-menu div {
    padding: 8px 16px;
    cursor: pointer;
  }

  .dropdown-menu div:hover {
    background-color: #f0f0f0;
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
.name_email{
  font-size: 2%;
}
.maindivText{
  font-size: 16px;
  font-weight: 400;
  margin-left: 2vh;

}
</style>
