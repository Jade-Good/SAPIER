<script lang="ts">
import axios from 'axios'

axios.defaults.withCredentials = true

export default {

  props: {
    collection: Object,
    childCollection: Object,
    level: Number,
  },
  data() {
    return {
      editing: false,
      newName: this.childCollection ? this.childCollection.collectionName : '',
    }
  },
  methods: {
    addChildCollection(collection) {
      const newCollection = createNewCollection()

      if (!collection.collectionList)
        collection.collectionList = []

      collection.collectionList.push(newCollection)
    },

    toggleEditing(collection) {
      this.editing = !this.editing
      if (!this.editing) {
        // Editing mode is toggled off; reset newName if needed
        this.newName = collection.collectionName
      }
    },
    saveCollectionName(collection) {
      collection.collectionName = this.newName
      this.toggleEditing(collection)
      this.$emit('update-collection-name', collection)
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
      // console.log('자식 api 호출: ', api)
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
          v-model="newName"
          @blur="saveCollectionName(childCollection)"
          @keyup.enter="saveCollectionName(childCollection)"
        >
        <button @click="toggleEditing(childCollection)">{{ editing ? '완료' : '수정' }}</button>
      </span>
      <button @click="addChildCollection(childCollection)">
        자식 추가
      </button>
      <CollectionTree :collection="childCollection" :level="level + 1" />
    </li>
  </ul>
</template>
