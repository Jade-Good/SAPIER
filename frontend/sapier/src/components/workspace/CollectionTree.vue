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

    toggleEditing(collection, index) {
      collection.editing = !collection.editing
      if (!collection.editing) {
        collection.newName = collection.collectionName
        this.saveCollectionName(collection, index)
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
    toggleCollapse(collection) {
      collection.collapsed = !collection.collapsed
      // console.log('토글 함수 호출', collection.collapsed)
    },
  },
}
function createNewCollection() {
  const newCollection = {
    collectionName: 'New Child Collection ',
    apiList: [],
    collectionList: [],
    modifiedTime: new Date().toISOString(),
    collapsed: false,
  }

  return newCollection
}
</script>

<template>
  <ul>
    <li v-for="childCollection in collection.collectionList" :key="childCollection.collectionId">
      <div class="setRow">
        <span :style="{ marginLeft: `${level * 6}px` }" class="collname">
          <div class="boxSize" @click="toggleCollapse(childCollection)">
            <img src="./folder.svg" class="folderImg">
            <span v-if="!childCollection.editing" class="rootCollName">{{ childCollection.collectionName }}</span>
            <input
              v-else
              v-model="childCollection.collectionName"
              class="nameChange"

              @keyup.enter="saveCollectionName(childCollection, index)"
            ></div>
          <!-- <button class="btn" @click="toggleEditing(childCollection)">{{ childCollection.editing ? '완료' : '수정' }}</button> -->

        </span>
        <div class="dropdown">
          <!-- <div class="dropdown-btn"> -->
          <img src="./etc.svg" class="dropdown-btn"> <!-- 뒤 -->
          <!-- </div> -->
          <ul class="dropdown-list">
            <li @click="addChildCollection(childCollection, index)">
              add collection
            </li>
            <li @click="deleteCollection(childCollection, index)">
              delete collection
            </li>
            <li @click="addChildRequest(childCollection, index)">
              add request
            </li>
            <li @click="toggleEditing(childCollection, index)">
              modify name
            </li>
          </ul>
        </div>
      </div>

      <!-- <button class="btn" @click="deleteCollection(childCollection, index)">
        자식 삭제
      </button>
      <button class="btn" @click="addChildCollection(childCollection, index)">
        자식 추가
      </button><button class="btn" @click="addChildRequest(childCollection, index)">
        C리퀘추가
      </button> -->
      <ul v-show="!childCollection.collapsed">
        <ul v-if="childCollection.apiList && childCollection.apiList.length > 0" :style="{ marginLeft: `${level * 6}px` }">
          <li v-for="api in childCollection.apiList" :key="api.requestName" class="divBlock">
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
              <a class="delReqName" @click="selectAPI(api)">{{ api.requestName }}</a>
              <div class="dropdown">
                <div class="setRow">
                  <img src="./etc.svg" class="dropdown-btn-del">
                  <ul class="dropdown-list-del">
                    <li @click="deleteChildRequest(childCollection, api, index)">
                      delete request
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          <!-- <button class="btn" @click="deleteChildRequest(childCollection, api, index)">
            C리퀘삭제
          </button> -->
          </li>
        </ul>
        <CollectionTree :collection="childCollection" :level="level + 1" :index="index" />
      </ul>
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
  max-width: 100%;
  margin-bottom: 7px;
  margin-top: 5px;
}
.dropdown-list {
  display: none;
  position: absolute;
  top: 100%;
  left: -700%;
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
  max-width: 50%;          /* 최대 너비 설정 (원하는 값으로 조정) */
  margin-right: auto;
}

.rootCollName{
  white-space: nowrap;       /* 텍스트가 줄 바꿈되지 않도록 설정 */
  overflow: hidden;          /* 넘치는 텍스트를 숨김 */
  text-overflow: ellipsis;   /* 넘치는 텍스트에 '...'을 표시하여 잘림을 나타냄 */
  max-width: 85%;
}
.requestBox {
  display: flex;
  align-items: center; /* 수직 가운데 정렬 */
  justify-content: space-between
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
</style>
