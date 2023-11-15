<script setup>
// import WorkSpaceInfo from './WorkSpaceInfo.vue'
// import RequestInfo from './RequestInfo.vue'
const axios = inject('$axios')

const collectionStore = useCollectionStore()
const WorkspaceListInfo = useWorkspaceListStore()
const isMounted = useMounted()
const route = useRouter()

if (isMounted) {
  axios
    .get(`/api/v1/workspaces`)
    .then((res) => {
      // console.log('워크스페이스 리스트 : ', res)
      WorkspaceListInfo.workspaceInfo = res.data
      // dataElement.textContent = WorkspaceListInfo.workSpaceInfo.
    })
    .catch((error) => {
      console.error('워크스페이스 리스트 조회 실패 : ', error)
    },
    )
}
// watch(() => WorkspaceListInfo.workspaceInfo, () => {
//   if (WorkspaceListInfo.workspaceInfo == null)
//     route.push('/main')
// })
</script>

<template>
  <TheNav />
  <div flex class="mid">
    <WorkspaceList w-20 />
    <Category w-60 />
    <!-- <CollectionInfo w-60 /> -->
    <RequestInfo v-if="collectionStore.request" h-full w-full />
    <WorkSpaceInfo v-else h-full w-full />
    <!-- <RequestInfo h-full w-full /> -->
  </div>
  <!-- <RouterView /> -->

  <!-- <TheFooter />
    <div mx-auto mt-5 text-center text-sm opacity-50>
      [Default Layout]
    </div> -->
</template>

<style>
.mid {
  height: calc(100% - 70px);
}
</style>
