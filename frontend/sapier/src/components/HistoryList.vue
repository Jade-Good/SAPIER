<script lang="ts">
import axios from 'axios';
import { defineComponent, ref, onMounted } from 'vue';
// const historyStore = useHistoryStore()
const collectionStore = useCollectionStore()
// const workspaceStore = useWorkspaceStore()
// const workspaceName = ref('workspace')

export default defineComponent({
    setup(){
        // const route = useRouter()
        const axios = inject('$axios')
        const HistoryListStore = useHistoryListStore()
        const HistoryListRef = ref([]) //필요없을 시 제거
        // const UserStore = useUserStore()
        const workspaceList = useWorkspaceListStore()
        const workspaceListRef = ref([])
        


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
                    console.log('history axios.get 성공, 이름: ', response.data)
            } catch(err){
                console.log('history axios.get 실패', err)
            }
        }
        async function getWorkspaceList(){
            try{
                // const uuid = UserStore.uuid
                // console.log('uuid: ', uuid)
                const response = await axios.get(`/api/v1/workspaces`)
                console.log('workspace axios.get 성공, 이름: ', response.data)
                console.log(response.data)
                
                console.log('workspaceListStore에 저장 시도')
                workspaceList.workspaceList = response.data
                console.log('workspaceListStore에 저장 성공')
                console.log('workspaceListStore: ', workspaceList.workspaceList)
                workspaceListRef.value = response.data
            } catch(err){
                console.log('workspace axios.get 실패', err)
            }
        }
        onMounted(()=>{
            getHistoryList(),
            getWorkspaceList()
        })
        return{
            HistoryListRef,
            workspaceListRef
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
        
        // async getWorkspaceName(workspaceKey){
        //     try{
        //         const response = await axios.get(`/api/v1/workspaces/${workspaceKey}/name`)
        //         workspaceName.value = response.data
        //         console.log("워크스페이스명: ", workspaceName.value, typeof(workspaceName.value))
        //         console.log(response)
                
        //         return workspaceName.value
        //     }
        //     catch(err){
        //         console.log('axios 실패 : ', err)
        //     }
        // },
        truncateText(text, maxLength: number) {
            if (!text)
                return '' // 또는 다른 기본값을 반환할 수 있습니다.

            if (text.length > maxLength)
                return `${text.slice(0, maxLength)}`

            else
                return text
        },
        getWorkspace(workspaceKey, workspaceListRef){
            console.log('workspaceListRef :', workspaceListRef)
            let result
            workspaceListRef.forEach(function(ws){
                console.log(ws.name)
                console.log("target ws key: ", ws.key)
                console.log("input wskey: ", workspaceKey)
                console.log("equal?: ", ws.key == workspaceKey)
                if(ws.key == workspaceKey){
                    result =  ws
                }
            })
            return result
        },
        setWorkspaceColor(workspaceKey, workspaceListRef){
            const ws = this.getWorkspace(workspaceKey, workspaceListRef)
            return {
                background: ws.color
            }
        },
        setStatusColor(statusCode: number){
            console.log('status Code : ', statusCode, statusCode/100)
            let result = '#C9C9C9'
            switch(Math.floor(statusCode / 100)){
                case 2:
                    result = '#6EC465'
                    break
                case 4:
                    result = '#E64F47'
                    break
                case 5:
                    result = '#EFA44A'
                    break
            }
            return {
                color: result
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
                        <div class="flexbox" @click="selectHistory(history)">
                            <!-- <div class="first-box">{{ /*histories.workspaceKey*/ }}[hi]</div> -->
                            <div class="left-box">
                                <div class="first-box">
                                    <div class="box" :style="setWorkspaceColor(histories.workspaceKey, workspaceListRef)">
                                        <div id="workSpaceListData" class="workspaceId">
                                            <!-- {{ truncateText(histories.workspaceKey, 4) }} -->
                                            {{ truncateText(getWorkspace(histories.workspaceKey, workspaceListRef).name, 4) }}
                                        </div>
                                    </div>
                                </div>
                                <!-- <div>{{ getWorkspaceName(histories.workspaceKey) }}</div> -->
                                <!-- <div>{{ history.request.method }}</div> -->
                                <div class="second-box">
                                    <div class="methodContainer">
                                        <img v-if="history.request.method === 'GET'" src="./workspace/get.svg" class="method-icon">
                                        <img v-else-if="history.request.method === 'POST'" src="./workspace/post.svg" class="method-icon">
                                        <img v-else-if="history.request.method === 'DELETE'" src="./workspace/delete-image.svg" class="method-icon">
                                        <img v-else-if="history.request.method === 'PATCH'" src="./workspace/patch.svg" class="method-icon">
                                        <img v-else-if="history.request.method === 'PUT'" src="./workspace/put.svg" class="method-icon">
                                        <img v-else-if="history.request.method === 'OPTION'" src="./workspace/option.svg" class="method-icon">
                                        <img v-else-if="history.request.method === 'HEAD'" src="./workspace/head.svg" class="method-icon">
                                    </div>
                                    <div :style="setStatusColor(history.response.statusCode)" class="statusCode">{{ history.response.statusCode }}</div>
                                </div>
                            </div>
                            <div class="last-box">
                                <div>{{ history.request.requestName }}</div>
                                <div>{{ history.response.responseTime }}ms</div>
                            </div>
                        </div>
                    </li>
                </ul>

            </li>
        </ul>
    </div>
</template>

<style>
.historyList *{
    margin-left: 0px;
    margin-right: 0px;
    padding-left: 0px;
    padding-right: 0px;

}
ul, li{
    margin-left: 0px;
    margin-right: 0px;
    padding-left: 0px;
    padding-right: 0px;
    width: 98%;
}

.date{
    border-top: 2px solid #C9C9C9;
}

.historyList{
    background: #F0F0F0;
    border-color: #B6B6B6;
    display: flex; /* 부모 요소를 플렉스 컨테이너로 설정 */
    align-items: center;
    flex-direction: column;
    overflow:auto;
}
.history{
    border-top: 0.5px solid #C9C9C9;
    /* border-bottom: 0.5px solid #000000; */
    /* border-color: #000000; */
    width: 100%;
}



.flexbox{
    display: flex;
    justify-content: space-between;
    margin-top: 2px;
    margin-bottom: 2px;
}

.left-box{
    display: flex;
    justify-content: flex-start;
    margin-top: 5px;
    margin-bottom: 5px;
}

.first-box{
    margin-left: 5px;
    align-self: center;
}

.second-box{
    /* display: flex; */
    /* justify-content: flex-start; */
    /* align-items: flex-start; */
    /* justify-content: space-around; */
    /* align-items: space-between; */
    flex-direction: column;
    margin-left: 5px;
    align-self: center;
}

.second-box > div{
    text-align: center;
}

.last-box{
    /* display: flex; */
    /* justify-content: flex-start; */
    /* align-items: flex-start; */
    flex-direction: column;
    text-align: end;
    align-self: center;
}

.box{
  margin-top: 5px ;
  border-radius: 10px;
  width: 50px;
  height: 50px;
  border: 2px solid #000; /* 테두리 스타일 및 색상 설정 */
  background-color:#0F4C81; /* 배경색 설정 */
  color:#F0F0F0;
  /* cursor: pointer; */
}
.workspaceId{
  text-align: center; /* 텍스트 가운데 정렬 */
    line-height: 50px; /* 텍스트를 수직 중앙으로 정렬 */
  font-size: 14px;

}
.statusCode{
    font-weight: bold;
}

</style>