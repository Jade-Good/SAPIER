<script lang="ts">
const collectionStore = useCollectionStore()
export default {

  props: {
    collection: Object,
    childCollection: Object,
    level: Number,
    index: Number,
  },
  setup() {
    const saveData = inject('saveData', null)

    return {
      saveData,
    }
  },
  data() {
    return {
      editing: false,
      newName: this.childCollection ? this.childCollection.collectionName : '',
    }
  },
  methods: {
    saveChanges(index) {
      if (this.saveData)
        this.saveData(index)
    },
    addChildCollection(collection, index) {
      const newCollection = createNewCollection()

      if (!collection.collectionList)
        collection.collectionList = []

      collection.collectionList.push(newCollection)
      this.saveChanges(index)
    },

    toggleEditing(collection) {
      collection.editing = !collection.editing
      if (!collection.editing) {
        collection.newName = collection.collectionName
        this.saveCollectionName(collection)
      }
    },

    saveCollectionName(collection, index) {
      collection.collectionName = collection.newName
      this.toggleEditing(collection)
      this.saveChanges(index)
    },
    deleteCollection(collection, index) {
      if (collection.collectionList && collection.collectionList.length > 0) {
        for (const childCollection of collection.collectionList)
          this.deleteCollection(childCollection, index) // 재귀적으로 하위 컬렉션 삭제
      }
      const collectionIndex = this.collection.collectionList.indexOf(collection)
      if (collectionIndex > -1)
        this.collection.collectionList.splice(collectionIndex, 1) // 현재 컬렉션 삭제
      this.saveChanges(index)
    },
    selectAPI(api) {
      collectionStore.request = api
      console.log('자식 api 호출: ', api)
      console.log('스토어에 저장되나?', collectionStore.request)
    },
    addChildRequest(childCollection, index) {
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

      if (childCollection.apiList === null) {
        childCollection.apiList = []
        console.log('null배열 추가')
      }

      childCollection.apiList.push(newApi)
      console.log('수정된 collection.apiList', childCollection.apiList)
      this.saveChanges(index)
    },
    deleteChildRequest(childCollection, api, documentIndex) {
      const index = childCollection.apiList.indexOf(api)
      if (index !== -1) {
        childCollection.apiList.splice(index, 1)
        this.saveChanges(documentIndex)
      }
    },

  },
}
function createNewCollection() {
  const newCollection = {
    collectionName: 'New Child Collection ',
    apiList: [],
    collectionList: [],
    modifiedTime: new Date().toISOString(),

  }

  return newCollection
}
</script>

<template>
  <ul>
    <li v-for="childCollection in collection.collectionList" :key="childCollection.collectionId">
      <span :style="{ marginLeft: `${level * 15}px` }">
        <span v-if="!childCollection.editing">{{ childCollection.collectionName }}</span>
        <input
          v-else
          v-model="childCollection.collectionName"

          @keyup.enter="saveCollectionName(childCollection, index)"
        >
        <button class="btn" @click="toggleEditing(childCollection)">{{ childCollection.editing ? '완료' : '수정' }}</button>

      </span><button class="btn" @click="deleteCollection(childCollection, index)">
        자식 삭제
      </button>
      <button class="btn" @click="addChildCollection(childCollection, index)">
        자식 추가
      </button><button class="btn" @click="addChildRequest(childCollection, index)">
        C리퀘추가
      </button>
      <ul v-if="childCollection.apiList && childCollection.apiList.length > 0" :style="{ marginLeft: `${level * 15}px` }">
        <li v-for="api in childCollection.apiList" :key="api.requestName">
          <a @click="selectAPI(api)">{{ api.requestName }}</a>

          <button class="btn" @click="deleteChildRequest(childCollection, api, index)">
            C리퀘삭제
          </button>
        </li>
      </ul>
      <CollectionTree :collection="childCollection" :level="level + 1" :index="index" />
    </li>
  </ul>
</template>

<style scoped>
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
