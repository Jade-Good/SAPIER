<script setup lang="ts">
const { parentCollectionList, level, index, documentIdx, path} = defineProps(['parentCollectionList', 'level', 'index', 'documentIdx', 'path'])

const collectionStore = useCollectionStore()
const saveData = inject<Function>('saveData')

// const editing = ref(false)
// const newName = computed(collection.childCollection ? collection.childCollection.collectionName : '')

function saveChanges() {
  if (saveData)
    saveData(documentIdx)
}

function addChildCollection() {
  const newCollection = createNewCollection()

  if (!parentCollectionList[index].collectionList)
  parentCollectionList[index].collectionList = []

  parentCollectionList[index].collectionList.push(newCollection)
  saveChanges()
}

function toggleEditing() {
  parentCollectionList[index].editing = !parentCollectionList[index].editing
}

function saveCollectionName() {
  toggleEditing()
  saveChanges()
}

function deleteCollection() {
  parentCollectionList.splice(index, 1) // 현재 컬렉션 삭제
  saveChanges()
}

function getPath(idx: number, collectionName:string) {
  return `${path} ${collectionName} / `
}

function selectAPI(api: any, index: any) {
  collectionStore.request = api
  collectionStore.selectDocument = index
  collectionStore.request.path = path

  // console.log('부모 api 호출: ', api)
  // console.log('스토어에 저장되나?', collectionStore.request)
}
function addChildRequest() {
  const newApi = {
    body: '',
    headers: [],
    method: 'GET',
    queryParams: [],
    requestName: 'New Request',
    requestURL: 'http://',
    id: '',
    createdTime: new Date().toISOString(),
    modifiedTime: new Date().toISOString(),
  }

  console.log('parentCollectionList[index] : ', parentCollectionList[index])

  if (parentCollectionList[index].apiList === null)
    parentCollectionList[index].apiList = []
  console.log('parentCollectionList[index].apiList : ', parentCollectionList[index].apiList)

  parentCollectionList[index].apiList.push(newApi)
  // console.log('수정된 collection.apiList', childCollection.apiList)
  saveChanges()
}

function deleteChildRequest(idx :number) {
  parentCollectionList[index].apiList.splice(idx, 1)
  saveChanges()
}

function toggleCollapse() {
  parentCollectionList[index].collapsed = !parentCollectionList[index].collapsed
  // console.log('토글 함수 호출', collection.collapsed)
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
  <!-- <li v-for="childCollection in collection.collectionList" :key="childCollection.collectionId"> -->
  <div class="setRow" @click="toggleCollapse()">

    <span :style="{ paddingLeft: `${level}rem` }" class="collname">
      <div class="boxSize">
        <img v-if="parentCollectionList[index].collapsed" src="./close.svg" class="folderOpenImg">
        <img v-else src="./open.svg" class="folderOpenImg">
        <img src="./folder.svg" class="folderImg">

        <span v-if="!parentCollectionList[index].editing" class="rootCollName">{{ parentCollectionList[index].collectionName }}</span>
        <input
          v-else
          v-model="parentCollectionList[index].collectionName"
          class="nameChange"
          @blur="saveCollectionName()"
          @keyup.enter="saveCollectionName()"
        >
      </div>
    </span>

    <div class="dropdown">
      <img src="./etc.svg" class="dropdown-btn">
      <ul class="dropdown-list">
        <li @click.stop="addChildCollection()">
          add collection
        </li>
        <li @click.stop="deleteCollection()">
          delete collection
        </li>
        <li @click.stop="addChildRequest()">
          add request
        </li>
        <li @click.stop="toggleEditing()">
          modify name
        </li>
      </ul>
    </div>
  </div>

  <ul v-show="!parentCollectionList[index].collapsed">
    <div v-for="(collectionItem, idx) in parentCollectionList[index].collectionList" :key="idx">
      <CollectionTree :parentCollectionList="parentCollectionList[index].collectionList" :level="level + 1" :index="idx" :documentIdx="documentIdx"
            :path="getPath(idx, collectionItem.collectionName)" />
    </div>

    <ul v-if="parentCollectionList[index].apiList && parentCollectionList[index].apiList.length > 0">
      <li v-for="(api, idx) in parentCollectionList[index].apiList" :key="idx" class="divBlock">
        <div class="requestBox" :style="{ paddingLeft: `${level + 1}rem` }" @click="selectAPI(api, index)">
          <div class="methodContainer">
            <img v-if="api.method === 'GET'" src="./get.svg" class="method-icon">
            <img v-else-if="api.method === 'POST'" src="./post.svg" class="method-icon">
            <img v-else-if="api.method === 'DELETE'" src="./delete-image.svg" class="method-icon">
            <img v-else-if="api.method === 'PATCH'" src="./patch.svg" class="method-icon">
            <img v-else-if="api.method === 'PUT'" src="./put.svg" class="method-icon">
            <img v-else-if="api.method === 'OPTION'" src="./option.svg" class="method-icon">
            <img v-else-if="api.method === 'HEAD'" src="./head.svg" class="method-icon">
          </div>
          <a class="delReqName">{{ api.requestName }}</a>
          <div class="dropdown">
            <div class="setRow">
              <img src="./etc.svg" class="dropdown-btn-del">
              <ul class="dropdown-list-del">
                <li @click="deleteChildRequest(idx)">
                  delete request
                </li>
              </ul>
            </div>
          </div>
        </div>
      </li>
    </ul>

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
  cursor: pointer;
}

.setRow:hover {
  background-color: var(--color-gray1-hover);
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
  border-radius: 0.3rem;

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
  border-radius: 0.3rem;

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
  justify-content: space-between;
  padding-left: 1rem;
  cursor: pointer;
}

.requestBox:hover {
  background-color: var(--color-gray1-hover);
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
