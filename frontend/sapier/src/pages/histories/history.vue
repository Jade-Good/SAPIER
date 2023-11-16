<script setup>
// const collectionStore = useCollectionStore()
const axios = inject('$axios')

const collectionStore = useCollectionStore()
// const historyStore = useHistoryStore()
const HistoryListInfo = useHistoryListStore()
const isMounted = useMounted()
const route = useRouter()

if(isMounted){
    axios
        .get(`/api/v1/history`)
        .then((res) => {
            HistoryListInfo.historyList = res.data
        }).catch((error) =>{
            console.error('히스토리 리스트 조회 실패 : ', error)
        })
}

// export default {
//     data(){
//         return{
            
//         }
//     }
// }

</script>

<template>
    <TheNav />
    <div flex class="mid">
        <HistoryList w-100 />
        <!-- HistoryInfo 추후 수정 -->
        <!-- <HistoryInfo h-full w-full /> -->
        <RequestInfo v-if="collectionStore.request" h-full w-full />
        <HistoryInfo v-else h-full w-full />
    </div>
</template>

<style>
.mid{
    height: calc(100% - 70px);
}
</style>