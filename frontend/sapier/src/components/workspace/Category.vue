<script lang="ts">
import axios from 'axios'
import CollectionTree from '~/components/workspace/CollectionTree.vue'

const collectionStore = useCollectionStore()

axios.defaults.withCredentials = true

export default {
  components: {
    CollectionTree,
  },
  data() {
    return {
      collectionList: [],
    }
  },
  created() {
    this.loadCollectionList()
  },
  methods: {
    async loadCollectionList() {
      const idList = ['6540e7115a40cf75f485d552']
      const collectionId = {
        collectionId: idList,
      }
      try {
        const response = await axios.post (`${import.meta.env.VITE_SERVER_URL}/api/v1/collection/list`, collectionId)

        this.collectionList = response.data[0].collectionList
        collectionStore.collection = response.data
        // console.log('collectionStore:', this.collectionList)
        // console.log('store 저장', collectionStore.collection[0].collectionList)
        // console.log('store apiList', collectionStore.collection[0].apiList)
      }
      catch (error) {
        console.error('리스트 가져오기 실패:', error)
      }
    },
    addRootCollection() {
      const newRootCollection = createNewRootCollection()
      this.collectionList.push(newRootCollection)
    },
    addChildCollection(parentCollection) {
      const newChildCollection = createNewRootCollection()
      parentCollection.collectionList.push(newChildCollection)
    },
    addChildToCollection(parentCollection, newChildCollection) {
      parentCollection.collectionList.push(newChildCollection)
    },

    async saveData() {
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
    },
    toggleEditing(collection) {
      collection.editing = !collection.editing
    },
    async saveCollectionName(collection) {
      collection.collectionName = collection.newName
      this.toggleEditing(collection)
      await this.saveData()
    },

  },
}
function createNewRootCollection() {
  return {
    collectionName: 'New Root Collection',
    apiList: [],
    collectionList: [],
  }
}
</script>

<template>
  <div class="category">
    <WorkspaceTitle />

    <div class="collection-list">
      <h1>Collection List</h1>

      <ul>
        <li v-for="collection in collectionList" :key="collection.collectionName">
          <span :style="{ marginLeft: '0px' }">
            <span v-if="!collection.editing">{{ collection.collectionName }}</span>
            <input
              v-else
              v-model="collection.newName"
              @blur="saveCollectionName(collection)"
              @keyup.enter="saveCollectionName(collection)"
            >
            <button @click="toggleEditing(collection)">{{ collection.editing ? '완료' : '수정' }}</button>
          </span>
          <button @click="addChildCollection(collection)">
            루트에서 자식 추가
          </button>
          <CollectionTree :collection="collection" :level="1" />
        </li>
      </ul>
      <button @click="addRootCollection">
        루트폴더 추가
      </button>
      <br>
      <button @click="saveData">
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
.collection-list {
 /* position: fixed;*/

}
  </style>
