<script>
</script>

<template>
  <div>
    <div class="mainDiv">
      <div class="recently">
        Recently visited workspaces
      </div>
    </div>
    <div class="recentlyVisited">
      <div class="firstvisitData">
        <div class="leftWorkspace">
          <div class="box">
            <div class="workspaceId">
              b102
            </div>
          </div>
          <div> b102: 8alet의 workspace</div>
        </div>
        <div> <img class="imgClass" src="./Frame 72.svg"></div>
      </div>
      <div class="visitData">
        <div class="leftWorkspace">
          <div class="box">
            <div class="workspaceId">
              My
            </div>
          </div>
          <div> b301 : 일탈싸피의 workspace</div>
        </div>
        <div> <img class="imgClass" src="./Frame 72 (2).svg"> </div>
      </div>
      <div class="visitData">
        <div class="leftWorkspace">
          <div class="box">
            <div class="workspaceId">
              wo
            </div>
          </div>
          <div> b101 : FoodReco의 workspace</div>
        </div>
        <div> <img class="imgClass" src="./Frame 72 (3).svg"> </div>
      </div>
      <div class="visitData">
        <div class="leftWorkspace">
          <div class="box" style="background-color: #658DC6;">
            <div class="workspaceId">
              오픈소스
            </div>
          </div>
          <div> OpenSource : 오픈소스 트랙 예제 프로젝트의 workspace</div>
        </div>
        <div> <img class="imgClass" src="./Frame 72 (4).svg"></div>
      </div>
    </div>
    <div class="recently">
      Notification List
    </div>

    <div class="notificationList">
      <div class="firstNotiData">
        <div class="leftWorkspace">
          <div class="imgCircle">
            <img class="imgClass" src="./Ellipse 3.svg">
          </div>

          <div> Rquest : Login API  | 수정 요청 합니다.</div>
        </div>
        <div class="imgClass">
          <img class="checkImg" src="./check_box.svg" w-8>
          <img class="deleteImg" src="./cancel.svg" w-8>
        </div>
      </div>
      <div class="notiData">
        <div class="leftWorkspace">
          <div class="imgCircle">
            <img class="imgClass" src="./Ellipse 5.svg">
          </div>

          <div> Collection : Member  | 작업 완료 확인해주세요.</div>
        </div>
        <div class="imgClass">
          <img class="checkImg" src="./check_box.svg" w-8>
          <img class="deleteImg" src="./cancel.svg" w-8>
        </div>
      </div>
      <div class="notiData">
        <div class="leftWorkspace">
          <div class="imgCircle">
            <img class="imgClass" src="./Ellipse 3.svg">
          </div>

          <div> Rquest : Logout API  | 태스트 해주세요.</div>
        </div>
        <div class="imgClass">
          <img class="checkImg" src="./check_box.svg" w-8>
          <img class="deleteImg" src="./cancel.svg" w-8>
        </div>
      </div>
      <div class="notiData">
        <div class="leftWorkspace">
          <div class="imgCircle">
            <img class="imgClass" src="./Ellipse 7.svg">
          </div>

          <div> WorkSpace : Apple PIe  | OverView 확인해주세요.</div>
        </div>

        <div class="imgClass">
          <img class="checkImg" src="./check_box.svg" w-8>
          <img class="deleteImg" src="./cancel.svg" w-8>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.leftWorkspace{
  display: flex; /* .workspaceHead를 플렉스 컨테이너로 설정 */
  align-items: center; /* 수평 가운데 정렬 */

}
.recentlyVisited{
/*
  display: flex;
  align-items: center; */
  /* height: 100vh; */

    /* text-align: center; 가로 중앙 정렬 */
  margin-left: 10rem;
  margin-bottom: 0.8rem;
}
.notificationList{
  margin-bottom: 5rem;
  margin-top: 1.5rem;

    /* text-align: center; 가로 중앙 정렬 */
  margin-left: 10rem;
}
.recently{
  font-size: var(--font-H2-size);
  font-weight: 700;
  margin-left: 10rem;
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;

}
.box{
  margin-top: 5px ;
  border-radius: 10px;
  width: 50px;
  height: 50px;
  border: 2px solid #000; /* 테두리 스타일 및 색상 설정 */
  background-color:#0F4C81; /* 배경색 설정 */
  color:#F0F0F0;
  cursor: pointer;
  margin-right: 3rem;

}
.workspaceId{
  text-align: center; /* 텍스트 가운데 정렬 */
    line-height: 50px; /* 텍스트를 수직 중앙으로 정렬 */
  font-size: 14px;

}
.visitData{
  display: flex;
  justify-content: space-between;
    align-items: center;
    width: 90%;
    margin-top:0.5rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #C9C9C9; /* 밑줄 추가 */

    /* height: 100vh; 높이를 조절하세요. */
}
.firstvisitData{
  display: flex;
  justify-content: space-between;
    align-items: center;
    width: 90%;
    padding-top:0.5rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #C9C9C9; /* 밑줄 추가 */
  border-top: 1px solid #C9C9C9; /* 밑줄 추가 */

    /* height: 100vh; 높이를 조절하세요. */

}

.firstNotiData{
  display: flex;
  justify-content: space-between;
    align-items: center;
    width: 90%;
    padding-top:0.5rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #C9C9C9; /* 밑줄 추가 */
  border-top: 1px solid #C9C9C9; /* 밑줄 추가 */

}
.notiData{
  display: flex;
  justify-content: space-between;
    align-items: center;
    width: 90%;
    margin-top:0.5rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #C9C9C9; /* 밑줄 추가 */

}
.imgClass{
  display: flex; /* .workspaceHead를 플렉스 컨테이너로 설정 */
  align-items: center; /* 수평 가운데 정렬 */
  gap:1%
}
.imgCircle{
  display: flex; /* .workspaceHead를 플렉스 컨테이너로 설정 */
  align-items: center; /* 수평 가운데 정렬 */
  gap:1%
}
.imgClass{
  margin-right: 1rem;
}
</style>
