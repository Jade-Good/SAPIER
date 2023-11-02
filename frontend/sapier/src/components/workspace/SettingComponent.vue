<script>
import axios from 'axios'

const userInfo = useUserStore()
const isMounted = useMounted()

axios.defaults.withCredentials = true

// if (isMounted) {
//   axios
//     .get(`${import.meta.env.VITE_SERVER_URL}/api/v1/workspaces/members`)
//     .then((res) => {
//       console.log(res)
//       userInfo.user = res.data
//     })
//     .catch((error) => {
//       console.log(error)
//     },
//     )
// }
</script>

<template>
  <div class="mainInfo">
    <div class="overview">
      <div class="maindiv">
        <h5 class="maindivHeader">
          Pinned Collections
        </h5>
        <div>sdf</div>
      </div>

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
        <h5 class="ContributorsHeader">
          Contributors
        </h5>
        <div>sdf</div>
      </div>
    </div>
  </div>
</template>

<!-- <style>
.mainInfoHeader{
  height: 13vh;
}
.mainInfo{
  display: flex;
    flex-direction: column;
    align-items: top;
    justify-content: top;
    height: 100vh; /* 원하는 높이로 설정하세요 */

  /* display: grid;
    place-items: center;
    height: 100vh; 원하는 높이로 설정하세요 */
}
.overview{
  height: 100vh;
}
.maindiv{
  display: block;
  width: 300px;
  padding-top: 20px;
}
.maindivHeader{

  display: inline-block;
  inline-size: auto; /* 자동으로 글자 크기에 맞게 조절됨 */
  font-size: 24px;
  font-weight:700;
}
.ContributorsHeader{

  display: inline-block;
  inline-size: auto; /* 자동으로 글자 크기에 맞게 조절됨 */
  font-size: 24px;
  font-weight:700;
}
.maindivText{
  font-size: 16px;
  font-weight: 400;
  margin-left: 2vh;

}
</style> -->
