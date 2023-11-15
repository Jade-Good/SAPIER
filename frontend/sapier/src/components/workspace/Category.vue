<script lang="ts">
export default defineComponent({
  setup() {
    const axios = inject('$axios')
    const collectionStore = useCollectionStore()
    const collectionList = ref([])
    const workspaceStore = useWorkspaceStore()
    const workspaceListStore = useWorkspaceListStore()
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

      // console.log('collectionId : ', JSON.stringify(collectionId))

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
      if (idList.length <= 0)
        collectionStore.collection = null
        // console.log('널 : ', collectionStore.collection)

      // documentName.value = response
      // console.log('axios.get 성공, 이름:', response)
    })

    const addRootCollection = (documentIdx) => {
      const newRootCollection = createNewRootCollection()
      collectionList.value[documentIdx].push(newRootCollection)
      // console.log('Root collection 호출', newRootCollection)
      saveData(documentIdx)
    }

    const addChildCollection = (parentCollection, index) => {
      const newChildCollection = createNewRootCollection()
      parentCollection.collectionList.push(newChildCollection)
      saveData(index)
    }

    const addChildToCollection = (parentCollection, newChildCollection) => {
      parentCollection.collectionList.push(newChildCollection)
    }

    const saveData = async (documnetIndex) => {
      const modifyData = collectionStore.collection
      const dataToSave = JSON.stringify(collectionStore.collection)
      // console.log('현재 인덱스 호출: ', documnetIndex)
      const nowIndex = documnetIndex
      try {
        // console.log('전송데이터', modifyData)
        // console.log('JSON: ', dataToSave)

        const res = await axios.patch(`/api/v1/collection/modify/${nowIndex}`, modifyData)
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

    const saveCollectionName = async (collection, index) => {
      collection.collectionName = collection.newName
      toggleEditing(collection)
      await saveData(index)
    }

    const deleteCollection = (parentCollection, deleteToCollection, index) => {
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
      saveData(index)
    }

    const createNewRootCollection = () => {
      return {
        collectionName: 'New Root Collection',
        apiList: [],
        collectionList: [],
        modifiedTime: new Date().toISOString(),
        collapsed: false,
      }
    }

    // const busStore = useEventBusStore()

    const selectAPI = (api) => {
      collectionStore.request = api
      // console.log('부모 api 호출: ', api)
      // console.log('스토어에 저장되나?', collectionStore.request)
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

    const addRootRequest = async (collection, index) => {
      const newApi = {
        body: '',

        createdTime: new Date().toISOString(),

        headers: [],

        method: 'GET',

        modifiedTime: new Date().toISOString(),

        queryParams: [],

        requestName: 'New Request',

        requestURL: '',
        id: '',

      }

      if (collection.apiList === null)
        collection.apiList = []
        // console.log('null배열 추가')

      collection.apiList.push(newApi)
      // console.log('수정된 collection.apiList', collection.apiList)
      await saveData(index)
    }

    const deleteRootRequest = (collection, api, documnetIndex) => {
      const index = collection.apiList.indexOf(api)
      if (index !== -1) {
        collection.apiList.splice(index, 1)
        saveData(documnetIndex)
      }
    }
    // const addCollectionDocument = async () => {
    //   let newDocumentId = ''
    //   try {
    //     const response = await axios.post(`/api/v1/collection`)
    //     console.log('axios.post  성공', response)

    //     try {
    //       const resLast = await axios.get(`/api/v1/collection/last`)
    //       console.log('axios get 성공', resLast)
    //       newDocumentId = resLast.data
    //     }
    //     catch (error) {
    //       console.error(error)
    //     }

    //     try {
    //       const newCollectionInfo = {
    //         collectionKey: newDocumentId,
    //         collectionName: 'New Document',
    //       }
    //       // console.log('컬렉션 인포 : ', JSON.stringify(newCollectionInfo))
    //       const workspaceId = workspaceStore.workspaceInfo?.key
    //       const res = await axios.post(`/api/v1/workspaces/${workspaceId}`, newCollectionInfo)
    //       // console.log('워크스페이스에 추가 axios 성공', res)
    //     }
    //     catch (error) {
    //       console.error(error)
    //     }
    //   }
    //   catch (error) {
    //     console.error(error)
    //   }
    // }
    const toggleCollapse = (collection) => {
      collection.collapsed = !collection.collapsed
      // console.log('토글 함수 호출', collection.collapsed)
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
      addCollectionDocument,
      toggleCollapse,
    }
  },
})
</script>

<template>
  <div class="category">
    <WorkspaceTitle />

    <div v-for="(documentId, index) in collectionList" :key="documentId.collectionId" class="collection-list">
      <!-- <h1>Collection List</h1> -->

      <div class="documentNameDiv" @click="toggleCollapse(documentId)">
        <img src="./folder.svg" class="docFolderImg"> <div class="leftSortDocument">
          {{ documentName[index] }}
        </div>
        <img src="./add.svg" class="addImg" @click="addRootCollection(index)">
      </div>
      <ul v-show="!documentId.collapsed">
        <li v-for="collection in documentId" :key="collection.collectionName">
          <div class="setRow">
            <span :style="{ marginLeft: '6px' }" class="collname">
              <div class="boxSize" @click="toggleCollapse(collection)">
                <img src="./folder.svg" class="folderImg">
                <span v-if="!collection.editing" class="rootCollName">{{ collection.collectionName }}</span>

                <input
                  v-else
                  v-model="collection.newName"
                  class="nameChange"
                  @blur="saveCollectionName(collection, index)"
                  @keyup.enter="saveCollectionName(collection, index)"
                ></div>
              <!-- <div class="dropdown">
                <div class="dropdown-btn">
                  <img src="./etc.svg" class="dropdown-btn">
                </div>
                <ul class="dropdown-list">
                  <li @click="addChildCollection(collection, index)">
                    add collection
                  </li>
                  <li @click="deleteCollection(documentId, collection, index)">
                    delete collection
                  </li>
                  <li @click="addRootRequest(collection, index)">
                    add request
                  </li>
                  <li>delete request</li>
                </ul>
              </div> -->
              <!-- <button class="btn" @click="toggleEditing(collection)">{{ collection.editing ? '완료' : '수정' }}</button> -->
            </span>

            <div class="dropdown">
              <!-- <div class="dropdown-btn"> -->
              <img src="./etc.svg" class="dropdown-btn"> <!-- 뒤 -->
              <!-- </div> -->
              <ul class="dropdown-list">
                <li @click="addChildCollection(collection, index)">
                  add collection
                </li>
                <li @click="deleteCollection(documentId, collection, index)">
                  delete collection
                </li>
                <li @click="addRootRequest(collection, index)">
                  add request
                </li>
                <li @click="toggleEditing(collection)">
                  modify name
                </li>
              </ul>
            </div>
          </div>

          <!-- <button class="btn" @click="addChildCollection(collection, index)">
            루트에서 자식 추가
          </button>
          <button class="btn" @click="deleteCollection(documentId, collection, index)">
            루트 삭제
          </button><button class="btn" @click="addRootRequest(collection, index)">
            R리퀘추가
          </button> -->
          <ul v-show="!collection.collapsed" :style="{ marginLeft: '6px' }">
            <li v-for="api in collection.apiList" :key="api.requestName" class="divBlock">
              <div class="requestBox">
                <div class="methodContainer">
                  <img v-if="api.method === 'GET'" src="./get.svg" class="method-icon">
                  <img v-else-if="api.method === 'POST'" src="./post.svg" class="method-icon">
                  <img v-else-if="api.method === 'DELETE'" src="./delete-image.svg" class="method-icon">
                  <img v-else-if="api.method === 'PATCH'" src="./patch.svg" class="method-icon">
                  <img v-else-if="api.method === 'PUT'" src="./put.svg" class="method-icon">
                  <img v-else-if="api.method === 'OPTION'" src="./option.svg" class="method-icon">
                  <img v-else-if="api.method === 'HEAD'" src="./head.svg" class="method-icon">
                </div>
                <a class="delReqName" @click="selectAPI(api)">
                  {{ api.requestName }}
                </a><div class="dropdown">
                  <div class="setRow">
                    <img src="./etc.svg" class="dropdown-btn-del">
                    <ul class="dropdown-list-del">
                      <li @click="deleteRootRequest(collection, api, index)">
                        delete request
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <!-- <button class="btn" @click="deleteRootRequest(collection, api, index)">
                R리퀘삭제
              </button> -->
            </li>

            <CollectionTree :collection="collection" :level="2" :index="index" />
          </ul>
        </li>
      </ul>
      <!-- <button class="btn" @click="addRootCollection(index)">
        루트폴더 추가
      </button> -->
      <br>
      <!-- <button class="btn" @click="saveData">
        저장
      </button> -->
    </div><button class="er" @click="addCollectionDocument()">
      document 추가
    </button>
  </div>
</template>

<style scoped>
.category{
  position: relative;
  overflow-x: auto;
  white-space: nowrap;
  background-color: #F0F0F0;

}
.btn{
  border: 1px solid black;
  background-color: white;
  color: black;
  width: 35px;
  height: 35px;
}
.er{
  border: 1px solid black;
  background-color: red;
  color: white;
}

.dropdown {
  position: relative;
  display: inline-block;
  background-color: transparent;
}

.dropdown-btn-del {
  position: relative;
  display: inline-block;
  background-color: transparent;
}
.setRow .dropdown-btn .dropdown-btn-del{
  background-color: #fff;
  padding: 8px 12px;
  cursor: pointer;
  background-color: transparent;

}

.setRow {
  /* position: relative; */
  /* height: 100vh; */
  position: relative;
  /* height: 100vh; */
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 5px;
  margin-top: 5px;
}
.dropdown-list {
  display: none;
  position: absolute;
  top: 100%;
  left: -680%;
  list-style-type: none;
  margin: 0;
  padding: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1;
  border-radius: 15px;

}

.dropdown-list-del  {
  display: none;
  position: absolute;
  top: 100%;
  left: -600%;
  list-style-type: none;
  margin: 0;
  padding: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1;
  border-radius: 15px;

}

.dropdown-btn, .dropdown-btn-del{
  width: 20px;
  height: 20px;
  /* display: flex;
  align-items: center;
  justify-content: center; */
  padding-right: 10px;
}

div.setRow , span.collname{
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.nameChange {
  width: 90%;

}

.dropdown-list li {
  padding: 8px 12px;
  cursor: pointer;
  color: var(--color-gray4);
}

.dropdown-list-del li {
  padding: 8px 12px;
  cursor: pointer;
  color: var(--color-gray4);
}
/*
.dropdown-btn img {
  background-color: var(--color-gray4)
} */

.dropdown-btn:hover + .dropdown-list,
.dropdown-btn-del:hover + .dropdown-list-del,
.dropdown-list:hover {
  display: block;
}
.dropdown-btn-del + .dropdown-list-del:hover{
  display: block;
}

li {
  color: var(--color-gray4);
  font-size: var(--font-H6-size);
  font-weight: var(--font-H5-weight);

}
.dropdown-list li:hover {
  background-color: #f1f1f1;
}

.dropdown-list-del li:hover {
  background-color: #f1f1f1;
}

.documentNameDiv {
  color: var(--color-gray4);
}

.nameChange{
  width: 40 px;
}

.boxSize{
  display: flex;
  align-items: center; /* 수직 가운데 정렬 */
  /* justify-content: space-between */
}
.delReqName {
  white-space: nowrap;       /* 텍스트가 줄 바꿈되지 않도록 설정 */
  overflow: hidden;          /* 넘치는 텍스트를 숨김 */
  text-overflow: ellipsis;   /* 넘치는 텍스트에 '...'을 표시하여 잘림을 나타냄 */
  max-width: 80%;          /* 최대 너비 설정 (원하는 값으로 조정) */
  margin-right: auto;
}

.rootCollName{
  white-space: nowrap;       /* 텍스트가 줄 바꿈되지 않도록 설정 */
  overflow: hidden;          /* 넘치는 텍스트를 숨김 */
  text-overflow: ellipsis;   /* 넘치는 텍스트에 '...'을 표시하여 잘림을 나타냄 */
  max-width: 100%;

}
.requestBox {
  display: flex;
  align-items: center; /* 수직 가운데 정렬 */
  justify-content: space-between;
  margin-bottom: 5px;
  margin-top: 5px;
}
.folderImg{
  padding-right: 5px;
}

.methodContainer {
  display: flex
}

.method-icon {
  margin-right: 5px;
}

.addImg {
  width: 15px;
  height: 15px;
  margin-right: 10px;
  align-items: center;
}

.docFolderImg{
  margin-left: 3px;
  margin-right: 5px;
}
.documentNameDiv{
  display: flex;
  align-items: center;
  justify-content: center; /* 수평 가운데 정렬 */
  margin-bottom: 7px;
}
.leftSortDocument {
  white-space: nowrap;       /* 텍스트가 줄 바꿈되지 않도록 설정 */
  overflow: hidden;          /* 넘치는 텍스트를 숨김 */
  text-overflow: ellipsis;   /* 넘치는 텍스트에 '...'을 표시하여 잘림을 나타냄 */
  max-width: 100%;          /* 최대 너비 설정 (원하는 값으로 조정) */
  margin-right: auto;
}
.addImg:hover {
  transform: scale(1.5); /* hover시 1.2배 확대 */
  /* 다른 스타일 변경이 필요하면 여기에 추가하세요. */
}
</style>
