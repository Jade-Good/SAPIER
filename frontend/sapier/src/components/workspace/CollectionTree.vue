<script lang="ts">
const collectionStore = useCollectionStore()
export default {

  props: {
    collection: Object,
    childCollection: Object,
    level: Number,
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
    saveChanges() {
      if (this.saveData)
        this.saveData()
    },
    addChildCollection(collection) {
      const newCollection = createNewCollection()

      if (!collection.collectionList)
        collection.collectionList = []

      collection.collectionList.push(newCollection)
      this.saveChanges()
    },

    toggleEditing(collection) {
      collection.editing = !collection.editing
      if (!collection.editing) {
        collection.newName = collection.collectionName
        this.saveCollectionName(collection)
      }
    },

    saveCollectionName(collection) {
      collection.collectionName = collection.newName
      this.toggleEditing(collection)
      this.saveChanges()
    },
    deleteCollection(collection) {
      if (collection.collectionList && collection.collectionList.length > 0) {
        for (const childCollection of collection.collectionList)
          this.deleteCollection(childCollection) // 재귀적으로 하위 컬렉션 삭제
      }
      const collectionIndex = this.collection.collectionList.indexOf(collection)
      if (collectionIndex > -1)
        this.collection.collectionList.splice(collectionIndex, 1) // 현재 컬렉션 삭제
      this.saveChanges()
    },
    selectAPI(api) {
      collectionStore.request = api
      console.log('자식 api 호출: ', api)
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

          @keyup.enter="saveCollectionName(childCollection)"
        >
        <button class="btn" @click="toggleEditing(childCollection)">{{ childCollection.editing ? '완료' : '수정' }}</button>

      </span><button class="btn" @click="deleteCollection(childCollection)">
        자식 삭제
      </button>
      <button class="btn" @click="addChildCollection(childCollection)">
        자식 추가
      </button>
      <ul v-if="childCollection.apiList && childCollection.apiList.length > 0" :style="{ marginLeft: `${level * 15}px` }">
        <li v-for="api in childCollection.apiList" :key="api.requestName">
          <a @click="selectAPI(api)">{{ api.requestName }}</a>
        </li>
      </ul>
      <CollectionTree :collection="childCollection" :level="level + 1" />
    </li>
  </ul>
</template>

<style scoped>
.btn{
  border: 1px solid black;
  background-color: white;
  color: black;
}
</style>
