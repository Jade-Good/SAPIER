<script lang="ts">
import type { Component } from 'vue'
// import { ref } from 'vue'
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
        const route = useRouter()
        const axios = inject('$axios')
        const HistoryListStore = useHistoryListStore()
        const HistoryList = ref([]) //필요없을 시 제거
        const UserStore = useUserStore()
        
        async function getHistoryList(){
            try{
                const uuid = UserStore.uuid
                const response = await axios.get(`/api/v1/history?uuid=${uuid}`)
                HistoryListStore.historyList.value = response.data
                // response.data.forEach(function(value){
                //     console.log(value)
                //     HistoryListStore.historyList.push(value)
                // })
                HistoryList = response.data
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
    <ul>
        <li v-for="history in /*HistoryListStore.historyList*/HistoryList" :key="history._id">
            <div>{{ history.request.requestName }}</div>
            <div>{{ history.request.method }}</div>
            <div>{{ history.response.status }}</div>
            <div>{{ history.response.responseTime }}</div>
        </li>
    </ul>
</template>