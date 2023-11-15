<script lang="ts">
export default defineComponent({
  setup() {
    const collectionStore = ref<any>(null)
    const collectionList = ref([])
    const workspaceStore = ref<any>(null)
    const idList: string[] = []
    const accessToken = ref<any>(null)

    browser.storage.local.get(['workspace']).then((data) => {
      workspaceStore.value = data.workspace
      console.log(`워크스페이스 : ${data.workspace.name}`)
    })

    browser.storage.local.get(['collection']).then((data) => {
      collectionStore.value = data.collection
      console.log(`컬렉션 : ${data.collection}`)
    })

    browser.storage.local.get(['token']).then((data) => {
      // eslint-disable-next-line no-console
      console.log(`토큰 넣기 ${data.token}`)
      accessToken.value = data.token
    })

    watchEffect(() => {
      idList.length = 0
      console.log('시작전에 검사: ', workspaceStore.value)

      if (idList !== null && workspaceStore.value && workspaceStore.value.collectionList) {
        console.log('일단도착: ', workspaceStore.value.collectionList)
        for (const item of workspaceStore.value.collectionList) {
          console.log('집어넣기: ', item.collectionKey)
          idList.push(item.collectionKey)
        }
      }
      else {
        idList.length = 0
      }

      console.log('idList : ', idList)

      const collectionId = {
        collectionId: idList,
      }

      collectionList.value.length = 0
      if (idList.length > 0 && accessToken.value) {
        fetch('https://sapier.co.kr/api/v1/collection/list', {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${accessToken.value}`,
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(collectionId),
        })
          .then((response) => {
            if (!response.ok)
              throw new Error('네트워크 응답이 정상적이지 않습니다.')
            return response.text()
          })
          .then((text) => {
            try {
              return JSON.parse(text)
            }
            catch (error) {
              console.error('JSON 파싱 오류:', error)
              // 파싱 오류 처리
            }
          })
          .then((data) => {
            if (data) {
              collectionStore.value = data
              console.log('response:', data)
              console.log('collectionStore: ', collectionStore.value)
              browser.storage.local.set({ collection: data })
              for (let i = 0; i < data.length; i++) {
                collectionList.value.push(data[i].collectionList)
                console.log('collectionList:', data[i].collectionList)
              }
            }
          })
          .then(() => {
            for (let i = 0; i < idList.length; i++)
              getDocumentName(i)
          })
          .catch((error) => {
            console.error('Error:', error)
          })
      }
      if (idList.length <= 0)
        collectionStore.value = null
    })

    const selectAPI = (api) => {
      if (collectionStore.value) {
        collectionStore.value.request = api
        browser.storage.local.set({ collection: collectionStore })
        console.log('자식 api 호출: ', api)
        console.log('스토어에 저장되나?', collectionStore.value.request)
        browser.storage.local.get(['collection']).then((data) => {
          console.log('스토어에 w저장 :', data.collection)
        })
      }
    }

    const documentName = ref<string[]>([])

    async function getDocumentName(index: number) {
      try {
        const collectionId = idList[index]

        await fetch(`https://sapier.co.kr/api/v1/collection/${collectionId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${accessToken.value}`,
          },
        })
          .then((response) => {
            if (!response.ok)
              throw new Error('네트워크 응답이 정상적이지 않습니다.')
            return response.text()
          })
          .then((text) => {
            documentName.value[index] = text
            console.log('axios.get 성공, 이름:', text)
          })
      }
      catch (error) {
        console.error('axios.get 실패', error)
      }

      return documentName.value
    }

    onMounted(async () => {
      // console.log('idList getDocumentName 하는중', idList.length)
      // for (let i = 0; i < idList.length; i++) {
      //   await getDocumentName(i)
      //   console.log('getDocumentName 하는중', idList.length)
      // }
    })

    const toggleCollapse = (collection) => {
      collection.collapsed = !collection.collapsed
      // console.log('토글 함수 호출', collection.collapsed)
    }

    return {
      collectionList,
      selectAPI,
      getDocumentName,
      documentName,
      toggleCollapse,
    }
  },
})
</script>

<template>
  <div class="category">
    <!-- <WorkspaceTitle /> -->

    <div v-for="(documentId, index) in collectionList" :key="documentId.collectionId" class="collection-list">
      <!-- <h1>Collection List</h1> -->

      <div class="documentNameDiv" @click="toggleCollapse(documentId)">
        <img v-if="documentId.collapsed" src="/assets/workspace/close.svg" class="folderOpenImg">
        <img v-else src="/assets/workspace/open.svg" class="folderOpenImg">
        <img src="/assets/workspace/folder.svg" class="docFolderImg"> <div class="leftSortDocument">
          {{ documentName[index] }}
        </div>
      </div>
      <ul v-show="!documentId.collapsed" my2>
        <li v-for="collection in documentId" :key="collection.collectionName">
          <div class="setRow">
            <span :style="{ marginLeft: '6px' }" class="collname">
              <div class="boxSize" @click="toggleCollapse(collection)">
                <img v-if="collection.collapsed" src="/assets/workspace/close.svg" class="folderOpenImg">
                <img v-else src="/assets/workspace/open.svg" class="folderOpenImg">
                <img src="/assets/workspace/folder.svg" class="folderImg">
                <span v-if="!collection.editing" class="rootCollName">{{ collection.collectionName }}</span>
                <input
                  v-else
                  v-model="collection.newName"
                  class="nameChange"
                >
              </div>
            </span>
          </div>
          <ul v-show="!collection.collapsed" :style="{ marginLeft: '25px' }">
            <li v-for="api in collection.apiList" :key="api.requestName" class="divBlock">
              <div class="requestBox">
                <div class="methodContainer">
                  <img v-if="api.method === 'GET'" src="/assets/workspace/get.svg" class="method-icon">
                  <img v-else-if="api.method === 'POST'" src="/assets/workspace/post.svg" class="method-icon">
                  <img v-else-if="api.method === 'DELETE'" src="/assets/workspace/delete-image.svg" class="method-icon">
                  <img v-else-if="api.method === 'PATCH'" src="/assets/workspace/patch.svg" class="method-icon">
                  <img v-else-if="api.method === 'PUT'" src="/assets/workspace/put.svg" class="method-icon">
                  <img v-else-if="api.method === 'OPTION'" src="/assets/workspace/option.svg" class="method-icon">
                  <img v-else-if="api.method === 'HEAD'" src="/assets/workspace/head.svg" class="method-icon">
                </div>
                <a class="delReqName" @click="selectAPI(api)">
                  {{ api.requestName }}
                </a>
              </div>
            </li>
            <CollectionTree :collection="collection" :level="2" :index="index" />
          </ul>
        </li>
      </ul>
      <br>
    </div>
  </div>
</template>

<style scoped>
ul{
  padding-left: 0px;
  list-style-type:none
}
.category{
  width: 25rem;
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
