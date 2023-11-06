<script setup>
import axios from 'axios'

const props = defineProps({
  workspaceone: Object,
})
const memberInfo = useMemberStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces/members/${props.workspaceone.key}`)
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
watch(() => props.workspaceone, async (newWorkspaceOne) => {
  if (newWorkspaceOne) {
    try {
      const res = await axios.get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces/members/${newWorkspaceOne.key}`)
      console.log('memberList 가져오기')
      console.log(res)
      memberInfo.member = res.data
    }
    catch (error) {
      console.log(error)
    }
  }
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

      <div class="maindiv">
        <div class="OC_maindiv_add">
          <h5 class="maindivHeader">
            Pinned Collections
          </h5>
          <img src="/add.png" class="pin_add_image">
        </div>
        <div class="maindivText">
          sdf
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
                <div id="workSpaceListData" class="workspaceId" @click="showInfoComponent(workspace)">
                  <div class="boxs" style="background: #BDBDBD;">
                    <img :src="m.profileImageUrl" class="profile" alt="User Profile Image">s
                  </div>
                  <div class="name">
                    {{ m.nickname }}
                  </div>
                <!-- <div>
                  {{ m.email }}
                </div> -->
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
.pin_add_image{
  width: auto; /* 이미지의 원본 크기를 유지 */
  max-height: 1.2em; /* 이미지의 최대 높이 설정 */
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
