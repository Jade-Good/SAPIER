<script>
import axios from 'axios'

const userInfo = useUserStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true

if (isMounted) {
  axios
    .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces/members`)
    .then((res) => {
      console.log(res)
      userInfo.user = res.data
    })
    .catch((error) => {
      console.log(error)
    },
    )
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
        <h5 class="maindivHeader">
          Contributors
        </h5>
        <div class="maindivText">
          sdf
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.mainInfo {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  width: 1220px;
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

.maindivText {
  font-size: 1em;
  font-weight: 400;
  margin-left: 0.5em;
}
</style>
