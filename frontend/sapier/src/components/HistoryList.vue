<script lang="ts">
// import type { Component } from 'vue'
import { defineComponent, ref, onMounted } from 'vue';

// const axios = inject('$axios')

// const HistoryListStore = useHistoryListStore()

// function getHistoryList(){
//     axios.get(`/api/v1/history`)
//     .then((res) => {
//         console.log(res);
//         HistoryListStore.historyList = res.data;

//     })
// }


// export default {
    // setup(){
    //     const axios = inject('$axios')

    // }
    // data(){
        // return{
        //     historyList: [],
        // }

    // },
    // methods:{
    //     getHistoryList(){
    //         axios.get(`/api/v1/history`)
    //         .then((res) => {
    //             console.log(res);
    //             HistoryListStore.historyList = res.data;

    //         })
    //     }
    // }
// }

export default defineComponent({
    setup(){
        // const route = useRouter()
        const axios = inject('$axios')
        // const HistoryListStore = useHistoryListStore()
        const HistoryList = ref([]) //필요없을 시 제거
        // const UserStore = useUserStore()
        
        async function getHistoryList(){
            try{
                // const uuid = UserStore.uuid
                // console.log('uuid: ', uuid)
                const response = await axios.get(`/api/v1/history`)
                console.log(response.data)
                // HistoryListStore.historyList.value = response.data
                // console.log(HistoryListStore.historyList.value)
                HistoryList.value = response.data
                // response.data.forEach(function(value){
                //     console.log(value)
                //     HistoryListStore.historyList.push(value)
                // })
                // HistoryList = response.data
                console.log('axios.get 성공, 이름: ', response.data)
            } catch(err){
                console.log('axios.get 실패', err)
            }
        }
        onMounted(()=>{
            getHistoryList()
        })
        return{
            HistoryList,
        }

    }
})
</script>
<template>
    <div class="historyList">
        <ul>
            <li v-for="(dailyHistory, dIdx) in HistoryList" :key="dIdx">
                <div>{{ dailyHistory.date }}</div>
                <ul v-for="(histories, wIdx) in dailyHistory.workspaceHistories" :key="wIdx">
                    <!-- <div>{{ histories.workspaceKey }}</div> -->
                    <li class="history" v-for="(history, hIdx) in histories.historyList" :key="hIdx">
                        <div>{{ histories.workspaceKey }}</div>
                        <div>{{ history.request.method }}</div>
                        <div>{{ history.request.requestName }}</div>
                        <div>{{ history.response.statusCode }}</div>
                        <div>{{ history.response.responseTime }}ms</div>
                    </li>
                </ul>
                <!-- <div>{{ history.request.requestName }}</div>
                <div>{{ history.request.method }}</div>
                <div>{{ history.response.status }}</div>
                <div>{{ history.response.responseTime }}</div> -->
            </li>
        </ul>
    </div>
</template>

<style>
.historyList{
    background: #C9C9C9;
    border-color: #B6B6B6;
    display: flex; /* 부모 요소를 플렉스 컨테이너로 설정 */
    align-items: center;
    flex-direction: column;
}
.history{
    border: 1px solid #000000;
    /* border-color: #000000; */
}
</style>