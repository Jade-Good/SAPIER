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
              for (let i = 0; i < data.length; i++) {
                collectionList.value.push(data[i].collectionList)
                console.log('collectionList:', data[i].collectionList)
              }
            }
          })
          .catch((error) => {
            console.error('Error:', error)
          })
      }
      if (idList.length <= 0)
        collectionStore.value = null
    })

    const selectAPI = (api) => {
      collectionStore.value.request = api
    }

    const documentName = ref<string[]>([])

    async function getDocumentName(index: number) {
      try {
        const collectionId = idList[index]

        const response = await fetch(`https://sapier.co.kr/api/v1/collection/${collectionId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${accessToken}`,
          },
        })
        documentName.value[index] = response.data
        console.log('axios.get 성공, 이름:', response.data)
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
      <ul v-show="!documentId.collapsed">
        <li v-for="collection in documentId" :key="collection.collectionName">
          <div class="setRow">
            <span :style="{ marginLeft: '6px' }" class="collname">
              <div class="boxSize" @click="toggleCollapse(collection)">
                <img v-if="collection.collapsed" src="/assets/workspace/close.svg" class="folderOpenImg">
                <img v-else src="/assets/workspace/open.svg" class="folderOpenImg">
                <img src="/assets/workspace/folder.svg" class="folderImg">
              </div>
            </span>
          </div>
          <ul v-show="!collection.collapsed" :style="{ marginLeft: '6px' }">
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
              </div>
            </li>
            <!-- <CollectionTree :collection="collection" :level="2" :index="index" /> -->
          </ul>
        </li>
      </ul>
      <br>
    </div>
  </div>
</template>

<style scoped>
.category{
  position: relative;
  overflow-x: auto;
  white-space: nowrap;
  background-color: #F0F0F0;

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
div.setRow , span.collname{
  display: flex;
  align-items: center;
  justify-content: space-between;
}

li {
  color: var(--color-gray4);
  font-size: var(--font-H6-size);
  font-weight: var(--font-H5-weight);

}

.documentNameDiv {
  color: var(--color-gray4);
}

.boxSize{
  display: flex;
  align-items: center; /* 수직 가운데 정렬 */
  /* justify-content: space-between */
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
</style>
