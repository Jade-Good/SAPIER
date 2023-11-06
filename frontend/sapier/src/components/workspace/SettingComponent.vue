<script setup>
import axios from 'axios'

const props = defineProps({
  workspaceone: Object,
})

const memberInfo = useUserStore()
const isMounted = useMounted()

const searchInput = ref('')
const boxColor = ref('yellow') // 초기 색상 설정
const colors = ['red', 'blue', 'green', 'purple', 'orange'] // 사용할 색상 목록
const alphabet = ['A', 'B', 'C', 'D', 'E'] // 사용할 색상 목록

function changeBoxColor(color) {
  boxColor.value = color
}
axios.defaults.withCredentials = true

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces/members/${props.workspaceone.key}`)
    .then((res) => {
      console.log('memberList (setting component)가져오기')
      console.log(res)
      memberInfo.member = res.data
    })
    .catch((error) => {
      console.log(error)
    },
    )
}
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
          <div class="box" :style="{ backgroundColor: boxColor }">
            <div id="workSpaceListData" class="workspaceId">
              {{ workspaceone.name }}
            </div>
          </div>
          <div ml-5 w-80>
            <div>워크스페이스 이름 </div>
            <input v-model="workspaceone.name" type="text">

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

          <div
            v-for="m in memberInfo.member" :key="m.uuid"
          >
            <div class="permission">
              <div class="boxs" style="background: #BDBDBD;">
                <img :src="m.profileImageUrl" class="profile" alt="User Profile Image">
              </div>
              <div class="name_email">
                <div class="name">
                  {{ m.nickname }}
                </div>
                <div class="email">
                  {{ m.email }}
                  <!-- {{ workspaceone.memberList.find(item => item.uuid === m.uuid) }} -->
                  <!-- {{ workspaceone }} -->
                  <!-- {{ workspaceone }}  그니까 여기서 if 문으로 uuid가 workspaceone memberList for문으로 돌면서 나온 uuid가 같으면 permission을 가져와서 출력 -->
                </div>
              </div>
              <div class="userPermission">
                <div>
                  {{ workspaceone.memberList.find(item => item.uuId === m.uuid)?.userPermission }}
                </div>
                <img src="/dropdown toggle.png">
              </div>
            </div>
          </div>
        </div>

        <div class="maindiv">
          <h5 class="goodByeHeader">
            Good Bye workspace
          </h5>
          <div>워크스페이스를 떠나거나 삭제합니다. 데이터에 접근할 수 없게 됩니다.</div>
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
}
.box{
  margin-top: 5px ;
  border-radius: 10px;
  width: 100px;
  height: 100px;
  border: 2px solid #000; /* 테두리 스타일 및 색상 설정 */
  background-color: yellow; /* 배경색 설정 */
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
    width: 2%;
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
.userPermission{
  display: flex;
  flex-direction: row;
  width: 20%;
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
