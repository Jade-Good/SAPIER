<script lang="ts">
import type RequestInfo from '../Request/RequestInfo.vue'

export default defineComponent({
  setup() {
    const route = useRouter()
    const axios = inject('$axios')
    const collectionStore = useCollectionStore()
    const collectionList = ref([])
    const workspaceStore = useWorkspaceStore()
    const workspaceListStore = useWorkspaceListStore()
    const requestStore = useRequestStore()
    const idList: string[] = []

    watchEffect(() => {
      idList.length = 0
      const workspaceIdx = workspaceStore.selectedWorkspaceIndex

      if (idList !== null && workspaceStore.workspaceInfo
    && workspaceStore.workspaceInfo.collectionList) {
        for (let i = 0; i < workspaceStore.workspaceInfo.collectionList.length; i++)
          idList.push(workspaceStore.workspaceInfo.collectionList[i].collectionKey)
      }
      else { idList.length = 0 }

      console.log('idList : ', idList)

      const collectionId = {
        collectionId: idList,
      }

      console.log('collectionId : ', JSON.stringify(collectionId))

      collectionList.value.length = 0
      if (idList.length > 0) {
        axios.post(`/api/v1/collection/list`, collectionId)
          .then((response) => {
            collectionStore.collection = response.data
            for (let i = 0; i < response.data.length; i++)
              collectionList.value.push(response.data[i].collectionList)
              // collectionList.value.push(reactive(response.data[i].collectionList))

            // console.log('성공', collectionList.value)
          })
          .catch((error) => {
            console.error('Error:', error)
          })
      }
      if (idList.length <= 0) {
        collectionStore.collection = null
        console.log('널 : ', collectionStore.collection)
      }
      // documentName.value = response
      // console.log('axios.get 성공, 이름:', response)
    })

    const addRootCollection = (documentIdx) => {
      const newRootCollection = createNewRootCollection()
      collectionList.value[documentIdx].push(newRootCollection)
      console.log('')
      saveData()
    }

    const addChildCollection = (parentCollection) => {
      const newChildCollection = createNewRootCollection()
      parentCollection.collectionList.push(newChildCollection)
      saveData()
    }

    const addChildToCollection = (parentCollection, newChildCollection) => {
      parentCollection.collectionList.push(newChildCollection)
    }

    const saveData = async () => {
      const modifyData = collectionStore.collection
      const dataToSave = JSON.stringify(collectionStore.collection)

      try {
        console.log('JSON: ', dataToSave)

        const res = await axios.patch(`/api/v1/collection/modify`, modifyData)
        console.log('데이터 저장 성공', res)
      }
      catch (error) {
        console.error('데이터 저장 실패:', error)
      }
    }
    provide('saveData', saveData)

    const toggleEditing = (collection) => {
      collection.editing = !collection.editing
    }

    const saveCollectionName = async (collection) => {
      collection.collectionName = collection.newName
      toggleEditing(collection)
      await saveData()
    }

    const deleteCollection = (parentCollection, deleteToCollection) => {
      const deleteRecursive = (list) => {
        for (let i = 0; i < list.length; i++) {
          const currentCollection = list[i]

          if (currentCollection === deleteToCollection) {
            // 해당 컬렉션을 찾으면 삭제하고 종료
            list.splice(i, 1)
            return true // 삭제 완료
          }

          if (currentCollection.collectionList && currentCollection.collectionList.length > 0) {
            // 재귀적으로 하위 컬렉션에서 찾기
            const deleted = deleteRecursive(currentCollection.collectionList)

            if (deleted && currentCollection.collectionList.length === 0) {
              // 하위 컬렉션이 비어 있으면 현재 컬렉션도 삭제
              list.splice(i, 1)
              return true // 삭제 완료
            }
          }
        }

        return false // 삭제되지 않음
      }

      // deleteRecursive 함수를 호출하여 삭제 수행
      deleteRecursive(parentCollection)
      saveData()
    }

    const createNewRootCollection = () => {
      return {
        collectionName: 'New Root Collection',
        apiList: [],
        collectionList: [],
        modifiedTime: new Date().toISOString(),
      }
    }

    const selectAPI = (api) => {
      collectionStore.request = api
      console.log('부모 api 호출: ', api)
      console.log('스토어에 저장되나?', collectionStore.request)
    }

    const documentName = ref<string[]>([])
    async function getDocumentName(index: number) {
      try {
        const collectionId = idList[index]

        const response = await axios.get(`/api/v1/collection/${collectionId}`)
        documentName.value[index] = response.data
        console.log('axios.get 성공, 이름:', response.data)
      }
      catch (error) {
        console.error('axios.get 실패', error)
      }

      return documentName.value
    }
    onMounted(async () => {
      for (let i = 0; i < idList.length; i++)
        await getDocumentName(i)
    })

    const addRootRequest = (collection) => {
      const newApi = {
        body: {},

        createdTime: new Date().toISOString(),

        formData: {},

        headers: {},

        method: 'GET',

        modifiedTime: new Date().toISOString(),

        queryParams: {},

        requestName: 'New Request',

        requestURL: '',
        // workspacesId: workspaceStore.workspaceInfo?.key,
        id: '',

      }

      if (collection.apiList === null) {
        collection.apiList = []
        console.log('null배열 추가')
      }

      collection.apiList.push(newApi)
      console.log('수정된 collection.apiList', collection.apiList)
      saveData()
    }

    const deleteRootRequest = (collection, api) => {
      const index = collection.apiList.indexOf(api)
      if (index !== -1) {
        collection.apiList.splice(index, 1)
        saveData()
      }
    }

    return {
      collectionList,
      addRootCollection,
      addChildCollection,
      addChildToCollection,
      saveData,
      toggleEditing,
      saveCollectionName,
      deleteCollection,
      selectAPI,
      getDocumentName,
      documentName,
      addRootRequest,
      deleteRootRequest,
    }
  },
})
</script>

<template>
  <div class="category">
    <WorkspaceTitle />

    <div v-for="(documentId, index) in collectionList" :key="documentId.collectionId" class="collection-list">
      <h1>Collection List</h1>
      <p>도큐먼트 : {{ documentName[index] }}</p>
      <ul>
        <li v-for="collection in documentId" :key="collection.collectionName">
          <span :style="{ marginLeft: '15px' }">

            <span v-if="!collection.editing">{{ collection.collectionName }}</span>

            <input
              v-else
              v-model="collection.newName"
              @blur="saveCollectionName(collection)"
              @keyup.enter="saveCollectionName(collection)"
            >

            <button class="btn" @click="toggleEditing(collection)">{{ collection.editing ? '완료' : '수정' }}</button>
          </span>
          <button class="btn" @click="addChildCollection(collection)">
            루트에서 자식 추가
          </button>
          <button class="btn" @click="deleteCollection(documentId, collection)">
            루트 삭제
          </button><button class="btn" @click="addRootRequest(collection)">
            R리퀘추가
          </button>
          <ul :style="{ marginLeft: '15px' }">
            <li v-for="api in collection.apiList" :key="api.requestName">
              <a @click="selectAPI(api)">
                {{ api.requestName }}
              </a>
              <button class="btn" @click="deleteRootRequest(collection, api)">
                R리퀘삭제
              </button>
            </li>
          </ul>
          <CollectionTree :collection="collection" :level="2" />
        </li>
      </ul>
      <button class="btn" @click="addRootCollection(index)">
        루트폴더 추가
      </button>
      <br>
      <button class="btn" @click="saveData">
        저장
      </button>
    </div><button class="er">
      document 추가
    </button>
  </div>
</template>

<style scoped>
.category{
  position: relative;
  overflow-x: auto;
  white-space: nowrap;

}
.btn{
  border: 1px solid black;
  background-color: white;
  color: black;
}
.er{
  border: 1px solid black;
  background-color: red;
  color: white;
}
  </style>
