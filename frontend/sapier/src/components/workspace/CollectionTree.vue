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
    addChildCollection(parentCollection) {
      const newCollection = createNewCollection()

      if (!parentCollection.collectionList)
        parentCollection.collectionList = []

      parentCollection.collectionList.push(newCollection)
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
