<script>
export default {
    data(){
        return{
            
        }
    }
}
</script>

<template>
    <TheNav />
    <div flex class="mid">
        <HistoryList w-80 />
        <!-- HistoryInfo 추후 수정 -->
        <HistoryInfo h-full w-full />
    </div>
</template>

<style>
.mid{
    height: calc(100% - 70px);
}
</style>