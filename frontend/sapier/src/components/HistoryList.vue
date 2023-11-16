<script lang="ts">
import axios from 'axios';
import { defineComponent, ref, onMounted } from 'vue';
// const historyStore = useHistoryStore()
const collectionStore = useCollectionStore()
const workspaceName = ref(String)

export default defineComponent({
    setup(){
        // const route = useRouter()
        const axios = inject('$axios')
        const HistoryListStore = useHistoryListStore()
        const HistoryListRef = ref([]) //필요없을 시 제거
        // const UserStore = useUserStore()
        


        async function getHistoryList(){
            try{
                // const uuid = UserStore.uuid
                // console.log('uuid: ', uuid)
                const response = await axios.get(`/api/v1/history`)
                console.log(response.data)
                
                console.log('historyListStore에 저장 시도')
                HistoryListStore.historyList = response.data
                console.log('historyListStore에 저장 성공')
                console.log('HistoryListStore: ', HistoryListStore.historyList)
                // console.log(HistoryListStore.historyList.value)
                HistoryListRef.value = response.data
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
            HistoryListRef,
        }
            
    },
    data(){
        return{

        }
    },
    methods:{
        selectHistory(history){
            // historyStore.history = history
            // historyStore.request = history.request
            // historyStore.response = history.response
            collectionStore.request = history.request
            collectionStore.response = history.response

            console.log('선택한 history 호출: ', history)
            console.log('스토어에 저장 확인: ', collectionStore)
            console.log('request: ', collectionStore.request)
            console.log('response: ', collectionStore.response)
        },
        // async getAndDisplayWorkspaceName(workspaceKey) {
        //     try {
        //     const workspaceName = await this.getWorkspaceName(workspaceKey);
        //     console.log("워크스페이스명 : ", workspaceName, typeof(workspaceName));
        //     // 여기서 workspaceName을 사용하거나 데이터에 할당합니다.
        //     return workspaceName
        //     } catch (err) {
        //     console.log('에러 발생 : ', err);
        //     }
        // },
        async getWorkspaceName(workspaceKey){
            try{
                const response = await axios.get(`/api/v1/workspaces/${workspaceKey}/name`)
                workspaceName.value = response.data
                console.log("워크스페이스명: ", workspaceName.value, typeof(workspaceName.value))
                console.log(response)
                
                return workspaceName.value
            }
            catch(err){
                console.log('axios 실패 : ', err)
            }
        }
    }
})
</script>
<template>
    <div class="historyList">
        <ul>
            <li v-for="(dailyHistory, dIdx) in HistoryListRef" :key="dIdx">
                <div class="date">{{ dailyHistory.date }}</div>
                <ul v-for="(histories, wIdx) in dailyHistory.workspaceHistories" :key="wIdx">
                    <!-- <div>{{ histories.workspaceKey }}</div> -->
                    <li class="history" v-for="(history, hIdx) in histories.historyList" :key="hIdx">
                        <div @click="selectHistory(history)">
                            <div>{{ histories.workspaceKey }}</div>
                            <div>{{ getWorkspaceName(histories.workspaceKey) }}</div>
                            <div>{{ history.request.method }}</div>
                            <div class="methodContainer">
                                <img v-if="history.request.method === 'GET'" src="./workspace/get.svg" class="method-icon">
                                <img v-else-if="history.request.method === 'POST'" src="./workspace/post.svg" class="method-icon">
                                <img v-else-if="history.request.method === 'DELETE'" src="./workspace/delete-image.svg" class="method-icon">
                                <img v-else-if="history.request.method === 'PATCH'" src="./workspace/patch.svg" class="method-icon">
                                <img v-else-if="history.request.method === 'PUT'" src="./workspace/put.svg" class="method-icon">
                                <img v-else-if="history.request.method === 'OPTION'" src="./workspace/option.svg" class="method-icon">
                                <img v-else-if="history.request.method === 'HEAD'" src="./workspace/head.svg" class="method-icon">
                            </div>
                            <div>{{ history.request.requestName }}</div>
                            <div>{{ history.response.statusCode }}</div>
                            <div>{{ history.response.responseTime }}ms</div>
                        </div>
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
.date{
    border-bottom: 2px solid #000000;
}

.historyList{
    background: #C9C9C9;
    border-color: #B6B6B6;
    display: flex; /* 부모 요소를 플렉스 컨테이너로 설정 */
    align-items: center;
    flex-direction: column;
    overflow:auto;
}
.history{
    border-top: 1px solid #000000;
    border-bottom: 1px solid #000000;
    /* border-color: #000000; */
}

</style>