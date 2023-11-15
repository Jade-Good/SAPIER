<script setup>
// const collectionStore = useCollectionStore()
const historyStore = useHistoryStore()

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
        <HistoryList w-80 />
        <!-- HistoryInfo 추후 수정 -->
        <!-- <HistoryInfo h-full w-full /> -->
        <RequestInfo v-if="historyStore.request" h-full w-full />
    </div>
</template>

<style>
.mid{
    height: calc(100% - 70px);
}
</style>