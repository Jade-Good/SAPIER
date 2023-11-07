<script lang="ts">
import axios from 'axios'

axios.defaults.withCredentials = true
export default defineComponent({
  setup() {
    const collectionStore = useCollectionStore()
    const collectionList = ref([])
    const workspaceStore = useWorkspaceStore()

    watchEffect(() => {
      const workspaceIdx = workspaceStore.selectedWorkspaceIndex
      const idList: string[] = []
      if (idList !== null && workspaceStore.workspaceInfo && workspaceStore.workspaceInfo[workspaceIdx].collectionId) {
        for (let i = 0; i < workspaceStore.workspaceInfo[workspaceIdx].collectionId.length; i++)
          idList.push(workspaceStore.workspaceInfo[workspaceIdx].collectionId)
      }

      else { idList.length = 0 }

      const collectionIds = Array.from({ length: idList.length })
      // const plainArray = idList.map(item => item[0])
      const plainArray = idList[0]

      for (let i = 0; i < collectionIds.length; i++)
        collectionIds[i] = plainArray[i]
      const collectionId = {
        collectionId: collectionIds,
      }

      if (idList.length > 0) {
        axios.post(`${import.meta.env.VITE_SERVER_URL}/api/v1/collection/list`, collectionId)
          .then((response) => {
            collectionStore.collection = response.data
            collectionList.value.length = 0
            for (let i = 0; i < response.data.length; i++)
              collectionList.value.push(response.data[i].collectionList)

            console.log('성공', collectionList.value)
          })
          .catch((error) => {
            console.error('Error:', error)
          })
      }
    })

    const addRootCollection = () => {
      const newRootCollection = createNewRootCollection()
      collectionList.value.push(newRootCollection)

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
        const res = await axios.patch(`${import.meta.env.VITE_SERVER_URL}/api/v1/collection/modify`, modifyData)
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

    const deleteCollection = (collection) => {
      if (collection.collectionList && collection.collectionList.length > 0) {
        for (const childCollection of collection.collectionList)
          deleteCollection(childCollection)
      }
      const collectionIndex = collectionList.value.indexOf(collection)
      if (collectionIndex > -1) {
        collectionList.value.splice(collectionIndex, 1)
        saveData()
      }
    }

    const createNewRootCollection = () => {
      return {
        collectionName: 'New Root Collection',
        apiList: [],
        collectionList: [],
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
    }
  },
})
</script>

<template>
  <div class="category">
    <WorkspaceTitle />

    <div v-for="documentId in collectionList" :key="documentId.collectionId" class="collection-list">
      <h1>Collection List</h1>

      <ul>
        <li v-for="collection in documentId" :key="collection.collectionName">
          <span :style="{ marginLeft: '0px' }">
            <span v-if="!collection.editing">{{ collection.collectionName }}</span>
            <input
              v-else
              v-model="collection.newName"
              @blur="saveCollectionName(collection)"
              @keyup.enter="saveCollectionName(collection)"
            >
            <button class="btn" @click="toggleEditing(collection)">{{ collection.editing ? '완료' : '수정' }}</button>
          </span>
          <button class="er" @click="addChildCollection(collection)">
            루트에서 자식 추가
          </button>
          <button class="er" @click="deleteCollection(collection)">
            루트 삭제
          </button>
          <CollectionTree :collection="collection" :level="1" />
        </li>
      </ul><button class="er" @click="addRootCollection">
        루트폴더 추가
      </button>
      <br>
      <button class="btn" @click="saveData">
        저장
      </button>
    </div>
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
