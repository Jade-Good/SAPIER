<script setup lang="ts">
const axios = inject('$axios')
const collectionStore = useCollectionStore()
const collectionList = ref([])
const workspaceStore = useWorkspaceStore()
const documentName = ref<string[]>([])
const documentCollapsed = ref<boolean[]>([])
const documentEdit = ref<boolean[]>([])
// const workspaceListStore = useWorkspaceListStore()
const idList: string[] = []

watchEffect(() => {
  idList.length = 0
  const workspaceIdx = workspaceStore.selectedWorkspaceIndex
  documentCollapsed.value = []
  documentEdit.value = []

  if (idList !== null && workspaceStore.workspaceInfo
    && workspaceStore.workspaceInfo.collectionList) {
    for (let i = 0; i < workspaceStore.workspaceInfo.collectionList.length; i++) {
      idList.push(workspaceStore.workspaceInfo.collectionList[i].collectionKey)
      documentCollapsed.value.push(false)
      documentEdit.value.push(false)
    }
  }
  else { idList.length = 0 }

  // console.log('idList : ', idList)

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
        console.log('성공', collectionList.value)
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

function addRootCollection(documentIdx: number) {
  const newRootCollection = createNewRootCollection()
  collectionList.value[documentIdx].push(newRootCollection)
  // console.log('Root collection 호출', newRootCollection)
  saveData(documentIdx)
}

// function addChildCollection(parentCollection, index) {
//   const newChildCollection = createNewRootCollection()
//   parentCollection.collectionList.push(newChildCollection)
//   saveData(index)
// }

// function addChildToCollection(parentCollection, newChildCollection) {
//   parentCollection.collectionList.push(newChildCollection)
// }

async function saveData(documentIndex: number) {
  const modifyData = collectionStore.collection
  // const dataToSave = JSON.stringify(collectionStore.collection)
  // console.log('현재 인덱스 호출: ', documnetIndex)

  try {
    console.log('전송데이터', modifyData)
    // console.log('JSON: ', dataToSave)
    
    console.log('url', `/api/v1/collection/modify/${documentIndex}`)
    const res = await axios.patch(`/api/v1/collection/modify/${documentIndex}`, modifyData)
    console.log('데이터 저장 성공', res)
  }
  catch (error) {
    console.error('데이터 저장 실패:', error)
  }
}
provide('saveData', saveData)

function toggleEditing(documentIdx: number) {
  documentEdit.value[documentIdx] = !documentEdit.value[documentIdx]
  saveData(documentIdx)
}

async function saveCollectionName(documentIdx: number) {
  collectionList.value[documentIdx].collectionName = documentName.value[documentIdx]
  toggleEditing(documentIdx)
  saveData(documentIdx)
  documentEdit.value[documentIdx] = false
}

function deleteDocument(index: number) {
  collectionList.value.splice(index, 1)
  saveData(documentIdx)
}

function createNewRootCollection() {
  return {
    collectionName: 'New Root Collection',
    apiList: [],
    collectionList: [],
    modifiedTime: new Date().toISOString(),
    collapsed: false,
  }
}

// const busStore = useEventBusStore()

function getPath(index: number, collectionName: string) {
  return `${documentName.value[index]} / ${collectionName} / `
}

async function getDocumentName(index: number) {
  try {
    const collectionId = idList[index]

    const response = await axios.get(`/api/v1/collection/${collectionId}`)
    documentName.value[index] = response.data
    // console.log('axios.get 성공, 이름:', response.data)
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

function toggleCollapse(index: number) {
  documentCollapsed.value[index] = !documentCollapsed.value[index]
  // console.log('토글 함수 호출', documentCollapsed.value[index])
}
</script>

<template>
  <div class="category">
    <WorkspaceTitle />

    <div v-for="(document, documentIdx) in collectionList" :key="documentIdx" class="collection-list">
      <div class="documentNameDiv" @click="toggleCollapse(documentIdx)">
        <img v-if="documentCollapsed[documentIdx]" src="./close.svg" class="folderOpenImg">
        <img v-else src="./open.svg" class="folderOpenImg">
        <img src="./folder.svg" class="docFolderImg">

        <span v-if="!documentEdit[documentIdx]" class="leftSortDocument">{{ documentName[documentIdx] }}</span>
        <input v-else v-model="documentName[documentIdx]" class="nameChange" @blur="saveCollectionName(documentIdx)"
          @keyup.enter="saveCollectionName(documentIdx)" />

        <div class="dropdown">
          <div i-carbon-button-centered class="dropdown-btn" mr-2 />
          <ul class="dropdown-list">
            <li @click.stop="addRootCollection(documentIdx)">
              Add Collection
            </li>
            <li @click.stop="deleteDocument(documentIdx)">
              Delete Document
            </li>
            <li @click.stop="toggleEditing(documentIdx)">
              Modify Document
            </li>
          </ul>
        </div>
      </div>

      <div v-show="!documentCollapsed[documentIdx]">
        <div v-for="(collection, index) in document" :key="index">
          <CollectionTree :parentCollectionList="document" :level="1" :index="index" :documentIdx="documentIdx"
            :path="getPath(documentIdx, collection.collectionName)" />
        </div>
      </div>
      <br>
    </div>
  </div>
</template>

<style scoped>
.category {
  width: 25rem;
  position: relative;
  overflow-x: auto;
  white-space: nowrap;
  background-color: #F0F0F0;
}

.btn {
  border: 1px solid black;
  background-color: white;
  color: black;
  width: 35px;
  height: 35px;
}

.er {
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

.setRow .dropdown-btn .dropdown-btn-del {
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

.setRow:hover {
  background-color: var(--color-gray1-hover);
}

.dropdown-list {
  display: none;
  position: absolute;
  top: 100%;
  left: -200%;
  list-style-type: none;
  margin: 0;
  padding: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1;
  border-radius: 0.3rem;
  transform: translateX(-50%);
}

.dropdown-list-del {
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

.dropdown-btn,
.dropdown-btn-del {
  width: 20px;
  height: 20px;
  /* display: flex;
  align-items: center;
  justify-content: center; */
  padding-right: 10px;
}

div.setRow,
span.collname {
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

.dropdown-btn:hover+.dropdown-list,
.dropdown-btn-del:hover+.dropdown-list-del,
.dropdown-list:hover {
  display: block;
}

.dropdown-btn-del+.dropdown-list-del:hover {
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
  cursor: pointer;
}

.documentNameDiv:hover {
  background-color: var(--color-gray1-hover);
}

.nameChange {
  width: 40 px;
}

.boxSize {
  display: flex;
  align-items: center;
  /* 수직 가운데 정렬 */
  /* justify-content: space-between */
}

.delReqName {
  white-space: nowrap;
  /* 텍스트가 줄 바꿈되지 않도록 설정 */
  overflow: hidden;
  /* 넘치는 텍스트를 숨김 */
  text-overflow: ellipsis;
  /* 넘치는 텍스트에 '...'을 표시하여 잘림을 나타냄 */
  max-width: 80%;
  /* 최대 너비 설정 (원하는 값으로 조정) */
  margin-right: auto;
}

.rootCollName {
  white-space: nowrap;
  /* 텍스트가 줄 바꿈되지 않도록 설정 */
  overflow: hidden;
  /* 넘치는 텍스트를 숨김 */
  text-overflow: ellipsis;
  /* 넘치는 텍스트에 '...'을 표시하여 잘림을 나타냄 */
  max-width: 100%;

}

.requestBox {
  display: flex;
  align-items: center;
  /* 수직 가운데 정렬 */
  justify-content: space-between;
  margin-bottom: 5px;
  margin-top: 5px;
  padding-left: 1rem;

}

.requestBox:hover {
  background-color: var(--color-gray1-hover);
}

.folderImg {
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

.docFolderImg {
  margin-left: 3px;
  margin-right: 5px;
}

.documentNameDiv {
  display: flex;
  align-items: center;
  justify-content: center;
  /* 수평 가운데 정렬 */
  margin-bottom: 7px;
}

.leftSortDocument {
  white-space: nowrap;
  /* 텍스트가 줄 바꿈되지 않도록 설정 */
  overflow: hidden;
  /* 넘치는 텍스트를 숨김 */
  text-overflow: ellipsis;
  /* 넘치는 텍스트에 '...'을 표시하여 잘림을 나타냄 */
  max-width: 100%;
  /* 최대 너비 설정 (원하는 값으로 조정) */
  margin-right: auto;
}

.addImg:hover {
  transform: scale(1.5);
  /* hover시 1.2배 확대 */
  /* 다른 스타일 변경이 필요하면 여기에 추가하세요. */
}
</style>
