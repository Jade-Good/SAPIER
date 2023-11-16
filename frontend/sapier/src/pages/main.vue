<!-- <template>
  <TheNav />
  <div flex class="mid">
    <WorkspaceList />
    <UserInfo w-60 />
    <MainInfo w-full />
  </div> -->
  <!-- <RouterView /> -->
<!-- </template>

<style>
.mid {
  height: calc(100% - 70px);
}
</style>

<route lang="yaml">
  meta:
    requiresAuth: true
</route> -->

<script setup lang="ts">
// import CategoryInfo from '../components/workspace/Category.vue'

const axios = inject('$axios')

const WorkspaceListInfo = useWorkspaceListStore()
const WorkspaceOneInfo = useWorkspaceStore()
const route = useRouter()

const isMounted = useMounted()
// const currentUserComponent = ref<Component | null>(UserInfo)

axios.defaults.withCredentials = true

if (isMounted) {
  axios
    .get(`/api/v1/workspaces`)
    .then((res) => {
      // console.log(res)
      WorkspaceListInfo.WorkspaceList = res.data
    })
    .catch((error) => {
      console.error(error)
    },
    )
}

watch(() => WorkspaceOneInfo.workspaceInfo, () => {
  axios
    .get(`/api/v1/workspaces`)
    .then((res) => {
      // console.log(res)
      WorkspaceListInfo.WorkspaceList = res.data
    })
    .catch((error) => {
      console.error(error)
    },
    )
  route.push('/workspace')
})
</script>

<template>
  <TheNav />
  <div flex class="mid">
    <WorkspaceList />
    <UserInfo />
    <MainInfo w-full />
  </div>
  <RouterView />
</template>

<style scoped>
.mid {
  height: calc(100% - 70px);
}

.list{
  background: #C9C9C9;
  border-color: #B6B6B6;
  display: flex; /* 부모 요소를 플렉스 컨테이너로 설정 */
  align-items: center;
  flex-direction: column;
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

}
.plus-box{
  margin-top: 5px ;
  border-radius: 10px;
  width: 50px;
  height: 50px;
  border: 2px solid #000; /* 테두리 스타일 및 색상 설정 */
  background-color :#658DC6; /* 배경색 설정 */
  color:#F0F0F0;
  cursor: pointer;

}

.workspaceId{
  text-align: center; /* 텍스트 가운데 정렬 */
    line-height: 50px; /* 텍스트를 수직 중앙으로 정렬 */
}

.cross {
  position: relative;
}

.cross::before,
.cross::after {
  content: '';
  position: absolute;
  background-color: #F0F0F0; /* 바의 색상을 설정하세요. */
}

.cross::before {
  width: 2px;
  height: 50%;
  top: 25%;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 50px
}

.cross::after {
  width: 50%;
  height: 2px;
  top: 50%;
  left: 25%;
  transform: translateY(-50%);
  border-radius: 50px

}

.workspaceId:hover {
  cursor: pointer; /* 호버 시 커서를 손가락 아이콘으로 변경 */
}
.active {
  border-color: #000;
}
</style>
