<script setup lang="ts">
const axios = inject('$axios')

const useCollection = useCollectionStore()
const userInfo = useUserStore()
const selectedWorkspaceIndex = useWorkspaceStore(0)
const workspaceList = useWorkspaceListStore()

const isMounted = useMounted()

const selectMethod = ref('GET')
const copySelectMethod = ref('')
const requestURL = ref('http://')
const copyRequestURL = ref('')
const requestName = ref('New Request')
const path = ref('Collection1 / Collection2 / ')
const copyRequestName = ref('')
const queryParams = reactive({
  rows: [
    { active: '', key: '', value: '', description: '' },
  ],
})
const copyQueryParams = ref('')
const requestHeaders = reactive({
  rows: [
    { active: '', key: '', value: '', description: '' },
  ],
})
const copyRequestHeaders = ref('')
const requestBody = ref('')
const copyRequestBody = ref('')

const isSaveEnable = computed(() => {
  return copySelectMethod.value !== selectMethod.value
    || copyRequestURL.value !== requestURL.value
    || copyRequestName.value !== requestName.value
    || copyRequestBody.value !== requestBody.value
    || copyRequestHeaders.value !== JSON.stringify(requestHeaders.rows)
    || copyQueryParams.value !== JSON.stringify(queryParams.rows)
})

provide('queryParams', queryParams)
provide('requestHeaders', requestHeaders)
provide('requestBody', requestBody)

const methodList = ref([
  'GET',
  'POST',
  'PUT',
  'PATCH',
  'DELETE',
  'HEAD',
  'OPTIONS',
])
const isMethodList = ref(false)
const requestHigh = ref('500')
const requestTap = ref('Params')

const isResizing = ref(false) // 크기 조절 중 여부
const startY = ref(0) // 크기 조절 시작 지점
const startHeight = ref(0) // 크기 조절 시작 시 Request 엘리먼트의 높이

if (isMounted) {
  setValues()

  // 메서드 리스트 이벤트 등록
  document.addEventListener('click', handleDocumentClick)

  // Request 높이 초기화
  const htmlElement = document.documentElement
  const computedFontSize = window.getComputedStyle(htmlElement).getPropertyValue('font-size')
  const currentRemValue = Number.parseFloat(computedFontSize) * 30

  requestHigh.value = `${currentRemValue}`

  window.addEventListener('mousemove', handleResizing)
  window.addEventListener('mouseup', stopResizing)
};

watch(() => useCollection.request, () => {
  setValues()
})

onUnmounted(() => {
  document.removeEventListener('click', handleDocumentClick)
  window.removeEventListener('mousemove', handleResizing)
  window.removeEventListener('mouseup', stopResizing)
})

// ---------------- 데이터 바인딩과 수정/저장 ----------------
function setValues() {
  if (!useCollection.request)
    return

  // console.log('api : ', useCollection.request)

  requestName.value = useCollection.request.requestName
  path.value = useCollection.request.path
  selectMethod.value = useCollection.request.method
  requestURL.value = useCollection.request.requestURL
  requestBody.value = useCollection.request.body

  if (useCollection.request.headers[0])
    requestHeaders.rows = copyRows(useCollection.request.headers)
  else
    requestHeaders.rows = [{ active: '', key: '', value: '', description: '' }]

  if (useCollection.request.queryParams[0])
    queryParams.rows = copyRows(useCollection.request.queryParams)
  else
    queryParams.rows = [{ active: '', key: '', value: '', description: '' }]

  copySelectMethod.value = selectMethod.value
  copyRequestURL.value = requestURL.value
  copyRequestName.value = requestName.value
  copyRequestBody.value = requestBody.value
  copyRequestHeaders.value = JSON.stringify(requestHeaders.rows)
  copyQueryParams.value = JSON.stringify(queryParams.rows)
}

function copyRows(objs: any) {
  const result = []

  objs.forEach((obj) => {
    if (obj.active !== '')
      result.push({ active: obj.active, key: obj.key, value: obj.value, description: obj.description })
  })

  result.push({ active: '', key: '', value: '', description: '' })
  return result
}

async function requestSave() {
  if (!useCollection.request || !isSaveEnable.value)
    return

  useCollection.request.method = selectMethod.value
  useCollection.request.requestURL = requestURL.value
  useCollection.request.requestName = requestName.value
  useCollection.request.body = requestBody.value
  useCollection.request.headers = requestHeaders.rows
  useCollection.request.queryParams = queryParams.rows

  try {
    // console.log('전송데이터', modifyData)
    // console.log('JSON: ', dataToSave)

    const res = await axios.patch(`/api/v1/collection/modify/${useCollection.selectDocument}`, useCollection.collection)
    // console.log('데이터 저장 성공', res)
  }
  catch (error) {
    console.error('데이터 저장 실패:', error)
  }

  setValues()
}

// ---------------- 메서드 리스트 토글기능 ----------------
function setMethodBtnStyle() {
  return {
    /* layout */
    display: 'flex',
    justifyContent: 'space-between',
    padding: '0 0.5rem 0 0.8rem',
    width: '10rem',
    lineHeight: '2rem',

    /* Style */
    borderRadius: '5px',
    outline: isMethodList.value ? '3px solid var(--color-blue2)' : 'none',

    color: 'white',
    backgroundColor: `var(--color-${selectMethod.value})`,

    fontSize: 'var(--font-H2-size)',
    fontWeight: 'var(--font-H2-weight)',

    cursor: 'pointer',
  }
};

function setMethodColor(method: string) {
  return {
    width: '14rem',
    padding: '0.5rem',

    borderRadius: '5px',

    color: `var(--color-${method})`,
    fontSize: 'var(--font-H2-size)',
    backgroundColor: method === selectMethod.value ? 'var(--color-gray1-active)' : 'none',

  }
};

function changeMethod(method: string) {
  setMethodColor(method)
  selectMethod.value = method
  toggleMethodList()
};

function toggleMethodList() {
  isMethodList.value = !isMethodList.value
};

function handleDocumentClick(event: MouseEvent) {
  // 클릭 이벤트에서 메서드 목록을 열려 있을 때만 닫도록 처리

  const methodBtn = document.querySelector('.methodBtn')

  if (isMethodList.value && !methodBtn?.contains(event.target as Element))
    isMethodList.value = false
};

// -----------------Request 창 크기 조절--------------------
function setRequestStyle() {
  return {
    /* layout */
    display: 'flex',
    flexDirection: 'column',

    width: '100%',
    height: `${requestHigh.value}px`,
    minHeight: '10.5rem',
    maxHeight: '90%',

    padding: '0.75rem',
    paddingBottom: '3rem',

    overflow: 'auto',
  }
};

function startResizing(event: MouseEvent) {
  isResizing.value = true
  startY.value = event.clientY
  // console.log('startY.value : ', startY.value)

  const ss = requestHigh.value.match(/\d+/g)
  // console.log('requestHigh.value : ', requestHigh.value)
  if (ss)
    startHeight.value = Number.parseInt(ss.toString())
  // console.log('startHeight.value : ', startHeight.value)
};

function handleResizing(event: MouseEvent) {
  if (isResizing.value) {
    const deltaY = event.clientY - startY.value

    let newHeight = startHeight.value + deltaY

    const htmlElement = document.documentElement
    const computedFontSize = window.getComputedStyle(htmlElement).getPropertyValue('font-size')
    const minHeight = Number.parseFloat(computedFontSize) * 10.5

    const maxHeight = window.innerHeight * 0.8

    if (newHeight < minHeight)
      newHeight = minHeight

    if (newHeight > maxHeight)
      newHeight = maxHeight

    requestHigh.value = `${newHeight}`

    // Response 엘리먼트의 크기를 조절할 수도 있습니다.

    event.preventDefault()
  }
};

function stopResizing() {
  isResizing.value = false
};
// --------------------------------------------------------
function setResponseStyle() {
  return {
    height: `calc(100% - ${requestHigh.value}px)`, // 나머지 여백을 설정
    overflow: 'auto',
    // paddingBottom: '3rem',

  }
};

// Request 전송
async function sendAPI() {
  const sendData = {
    requestURL: convertParamsToURL(),
    method: selectMethod.value,
    headers: getActiveHeaders(),
    body: requestBody.value,
  }

  // console.log('sendData : ', sendData)

  try {
    const res = await axios.post(`/api/v1/collection/request`, sendData)
    // console.log('API 전송 성공', res.data)

    useCollection.response = res.data

    saveHistory(sendData)
  }
  catch (error) {
    console.error('API 전송 실패:', error)
  }
};

function convertParamsToURL() {
  let url = requestURL.value

  if (queryParams.rows.length > 0)
    url += '?'

  queryParams.rows.forEach((row) => {
    if (row.active === 'true')
      url += `${row.key}=${row.value}&`
  })

  url = url.substring(0, url.length - 1)

  return url
}

function getActiveHeaders() {
  const headers = {}

  requestHeaders.rows.forEach((row) => {
    if (row.active === 'true')
      headers[row.key] = row.value
  })

  return headers
}

// Reuqest history 저장
async function saveHistory(sendData: any) {
  console.log(useCollection.request)
  const history = {
    request: {
      requestName: requestName.value,
      requestURL: sendData.requestURL,
      method: sendData.method,
      headers: requestHeaders.rows,
      body: requestBody.value,
      path: path
    },
    response: useCollection.response,
    uuid: userInfo.userInfo?.uuid,
    workspaceId: workspaceList.WorkspaceList[selectedWorkspaceIndex.selectedWorkspaceIndex].key,
  }
  console.log(history.request)

  // console.log('history : ', history)

  try {
    const res = await axios.post(`/api/v1/history/save`, history)

    // console.log('History 저장 성공 : ', res)
  }
  catch (error) {
    console.error('History 저장 실패', error)
  }
}
</script>

<template>
  <div h-full flex flex-col border>
    <div name="Request" :style="setRequestStyle()">
      <div flex flex-justify-between pb-3 pl-3>
        <div flex flex-gap-1 line-height-9>
          <p color-gray>
            {{ path }}
          </p>
          <input v-model="requestName">
        </div>
        <div flex flex-gap-3>
          <div :class="isSaveEnable ? 'grayBtn' : 'grayBtnOff'" @click="requestSave">
            <div i-carbon-save />
            Save
          </div>
          <div class="grayBtn">
            <div i-carbon-copy-file />
            Copy
          </div>
        </div>
      </div>

      <div flex-justify-betwee h-14 flex>
        <div w-full flex flex-gap-4 border border-rounded p-2 style="border-color: var(--color-gray4);">
          <div class="methodBtn" :style="setMethodBtnStyle()" @click="toggleMethodList()">
            <div m-1>
              {{ selectMethod }}
            </div>
            <div i-carbon-chevron-down h-full />
          </div>

          <div v-if="isMethodList" class="methodList">
            <div v-for="(method, idx) in methodList" :key="idx" :style="setMethodColor(method)"
              @click="changeMethod(method)">
              {{ method }}
            </div>
          </div>

          <div style="border-color: var(--color-gray4); font-size: var(--font-H5-size); line-height: 2.5rem;" w-full
            border-l pl-2>
            <input v-model="requestURL" type="text" w-full pl-2>
          </div>
        </div>

        <div class="sendBtn" @click="sendAPI()">
          <div flex flex-gap-2 flex-justify-center>
            <div i-carbon-send-alt pt-8 />
            Send
          </div>
        </div>
      </div>

      <div flex flex-gap-5 p-3>
        <div :class="[requestTap !== 'Params' ? 'tap' : 'highlight']" @click="requestTap = 'Params'">
          <p>Params</p>
        </div>
        <div :class="[requestTap !== 'Headers' ? 'tap' : 'highlight']" @click="requestTap = 'Headers'">
          <p>Headers</p>
        </div>
        <div :class="[requestTap !== 'Body' ? 'tap' : 'highlight']" @click="requestTap = 'Body'">
          <p>Body</p>
        </div>
        <div :class="[requestTap !== 'Settings' ? 'tap' : 'highlight']" @click="requestTap = 'Settings'">
          <p>Settings</p>
        </div>
      </div>

      <Params v-if="requestTap === 'Params'" />
      <Headers v-if="requestTap === 'Headers'" />

      <Body v-if="requestTap === 'Body'" />
      <Settings v-if="requestTap === 'Settings'" />
    </div>
    <div class="resize-line" @mousedown="startResizing" @mousemove="handleResizing" @mouseup="stopResizing" />
    <Response name="Response" w-full :style="setResponseStyle()" />
  </div>
</template>

<style scoped>
input:focus {
  outline: 1px solid var(--color-gray2);
  background-color: white;
  border-radius: 5px;
}

.highlight {
  border-bottom: 3px;
  border-bottom-style: solid;
  border-block-color: var(--color-blue1);
  /* border: solid 1px blue; */
}

.tap {
  color: var(--color-gray4);
  cursor: pointer;
}

.tap:hover {
  color: #2E2E2E;
}

.methodList {
  /* layout */
  position: absolute;
  top: 13rem;

  padding: 0.5rem;

  z-index: 3;

  /* Style */
  border-radius: 5px;
  background-color: white;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.2);

}

.methodList div:hover {
  background-color: var(--color-gray1);
}

.methodList div:active {
  background-color: var(--color-gray1-hover);
}

.sendBtn {
  /* layout */
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1px;

  width: 10rem;
  margin-left: 1rem;

  /* Style */
  border-radius: 5px;

  font-size: var(--font-H3-size);

  color: white;
  background-color: var(--color-blue1);

  cursor: pointer;
}

.sendBtn:hover {
  background-color: var(--color-blue1-hover);
}

.sendBtn:active {
  background-color: var(--color-blue1-active);
}

.grayBtn {
  /* layout */
  display: flex;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.6rem 1rem;
  line-height: 1.2rem;

  /* Style */
  border-radius: 5px;

  color: var(--color-gray4);
  background-color: var(--color-gray1);

  font-size: var(--font-H5-size);
  font-weight: var(--font-H5-weight);

  cursor: pointer;
}

.grayBtnOff {
  /* layout */
  display: flex;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.6rem 1rem;
  line-height: 1.2rem;

  /* Style */
  border-radius: 5px;

  color: var(--color-gray2);
  background-color: var(--color-gray1);

  font-size: var(--font-H5-size);
  font-weight: var(--font-H5-weight);

  cursor: auto;
}

.grayBtn:hover {
  background-color: var(--color-gray1-hover);
}

.grayBtn:active {
  background-color: var(--color-gray1-active);
}

.resize-line {
  cursor: ns-resize;
  /* 세로 크기 조절 커서 모양 */
  height: 5px;
  /* 크기 조절 선의 높이 설정 */
  width: 100%;
  /* 가로 너비를 100%로 설정하여 전체 너비에서 크기 조절 가능 */
  background-color: var(--color-gray3);
  /* 크기 조절 선의 배경색 설정 */
}
</style>
