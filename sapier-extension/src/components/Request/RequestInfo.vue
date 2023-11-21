<script setup lang="ts">
const useRequest = ref<any>(null)

const selectMethod = ref('GET')
const copySelectMethod = ref('')
const requestURL = ref('http://')
const copyRequestURL = ref('')
const requestName = ref('New Request')
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

function getRequest() {
  browser.storage.local.get(['request']).then((data) => {
    useRequest.value = data.request
    console.log('request 스토어에 저장 :', data.request)
  })
}
getRequest()

browser.storage.onChanged.addListener(requestChange)
function requestChange(changes, area) {
  const changedItems = Object.keys(changes)
  for (const item of changedItems) {
    if (item === 'request') {
      getRequest()
      console.log(`${item} has changed:`)
      console.log('Old value: ', changes[item].oldValue)
      console.log('New value: ', changes[item].newValue)
    }
  }
}

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

onMounted(() => {
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
})

watch(() => useRequest.value, () => {
  console.log('ㅁㅁㅁㅁㅁㅁㅁㅁ')
  setValues()
})

onUnmounted(() => {
  document.removeEventListener('click', handleDocumentClick)
  window.removeEventListener('mousemove', handleResizing)
  window.removeEventListener('mouseup', stopResizing)
})

// ---------------- 데이터 바인딩과 수정/저장 ----------------
function setValues() {
  console.log('setValues api is 0', useRequest.value)
  if (!useRequest.value)
    return

  console.log('setValues api : ', useRequest.value)

  selectMethod.value = useRequest.value.method
  requestURL.value = useRequest.value.requestURL
  requestName.value = useRequest.value.requestName
  requestBody.value = useRequest.value.body

  if (useRequest.value.headers[0]) {
    console.log('헤더 설정 : ', useRequest.value.headers)
    requestHeaders.rows = copyRows(useRequest.value.headers)
  }

  else { requestHeaders.rows = [{ active: '', key: '', value: '', description: '' }] }

  if (useRequest.value.queryParams[0]) {
    console.log('파라미터 설정 : ', useRequest.value.queryParams)
    queryParams.rows = copyRows(useRequest.value.queryParams)
  }
  else { queryParams.rows = [{ active: '', key: '', value: '', description: '' }] }

  copySelectMethod.value = selectMethod.value
  copyRequestURL.value = requestURL.value
  copyRequestName.value = requestName.value
  copyRequestBody.value = requestBody.value
  copyRequestHeaders.value = JSON.stringify(requestHeaders.rows)
  copyQueryParams.value = JSON.stringify(queryParams.rows)
}

function copyRows(objs: any) {
  const result = []

  let i = 0
  while (objs[`${i}`] !== undefined) {
    const param = objs[`${i}`]
    if (param !== '' && param.active !== '')
      result.push({ active: param.active, key: param.key, value: param.value, description: param.description })
    i = i + 1
  }

  // 대상이 배열인지 확인

  // result.push({ active: '', key: '', value: '', description: '' })
  return result
}

// ---------------- 메서드 리스트 토글기능 ----------------
function setMethodBtnStyle() {
  return {
    /* layout */
    display: 'flex',
    justifyContent: 'space-between',
    padding: '0 0.5rem 0 0.8rem',
    width: '7rem',
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
}

function setMethodColor(method: string) {
  return {
    width: '14rem',
    padding: '0.5rem',

    borderRadius: '5px',

    color: `var(--color-${method})`,
    fontSize: 'var(--font-H2-size)',
    backgroundColor: method === selectMethod.value ? 'var(--color-gray1-hover)' : 'none',

  }
}

function changeMethod(method: string) {
  setMethodColor(method)
  selectMethod.value = method
  toggleMethodList()
}

function toggleMethodList() {
  isMethodList.value = !isMethodList.value
}

function handleDocumentClick(event: MouseEvent) {
  // 클릭 이벤트에서 메서드 목록을 열려 있을 때만 닫도록 처리

  const methodBtn = document.querySelector('.methodBtn')

  if (isMethodList.value && !methodBtn?.contains(event.target as Element))
    isMethodList.value = false
}

// -----------------Request 창 크기 조절--------------------
function setRequestStyle() {
  return {
    /* layout */
    display: 'flex',
    flexDirection: 'column',

    width: '96%',
    height: `${requestHigh.value}px`,
    minHeight: '10.5rem',
    maxHeight: '90%',

    padding: '0.75rem',
    paddingBottom: '3rem',

    overflow: 'auto',

    /* Style */
  }
}

function startResizing(event: MouseEvent) {
  isResizing.value = true
  startY.value = event.clientY
  // console.log('startY.value : ', startY.value)

  const ss = requestHigh.value.match(/\d+/g)
  // console.log('requestHigh.value : ', requestHigh.value)
  if (ss)
    startHeight.value = Number.parseInt(ss.toString())
  // console.log('startHeight.value : ', startHeight.value)
}

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
}

function stopResizing() {
  isResizing.value = false
}
// --------------------------------------------------------
function setResponseStyle() {
  return {
    height: `calc(100% - ${requestHigh.value}px)`, // 나머지 여백을 설정
    overflow: 'auto',
    // paddingBottom: '3rem',

  }
}

async function sendAPI() {
  const sendData = {
    requestURL: requestURL.value,
    method: selectMethod.value,
    headers: {},
    body: '',
  }

  // console.log('sendData : ', sendData)

  try {
    // console.log('API 전송 성공', res.data)
    browser.storage.local.get(['token']).then(async (value) => {
      await fetch('https://sapier.co.kr/api/v1/collection/request', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': `Bearer ${value.token}`,
        },
        body: JSON.stringify(sendData),
      })
        .then((response) => {
          // console.log(response)
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
          // 데이터 할당
          browser.storage.local.set({ response: data })
          console.log('response 할당 성공', data)
          return data
        })
        .catch(error => console.error('Error:', error))
    })
  }
  catch (error) {
    console.error('API 전송 실패:', error)
  }
}
</script>

<template>
  <div h-full flex flex-col border>
    <div name="Request" :style="setRequestStyle()">
      <div flex-justify-between h-14 flex>
        <div w-full flex flex-gap-3 border border-rounded p-2 style="border-color: var(--color-gray4);">
          <div class="methodBtn" :style="setMethodBtnStyle()" @click="toggleMethodList()">
            <div m-1 style="color: var(--color-white);">
              {{ selectMethod }}
            </div>
          </div>
          <div v-if="isMethodList" class="methodList">
            <div
              v-for="(method, idx) in methodList" :key="idx" :style="setMethodColor(method)"
              @click="changeMethod(method)"
            >
              {{ method }}
            </div>
          </div>

          <div
            style="border-color: var(--color-gray4); font-size: var(--font-H5-size); line-height: 2.5rem;" w-full
            border-l
          >
            <input v-model="requestURL" type="text" w-full>
          </div>
        </div>
        <div class="sendBtn" @click="sendAPI()">
          <div flex flex-gap-2 flex-justify-center style="color: var(--color-white);">
            <div i-carbon-send-alt pt-8 />
            <div pt-4 style="color: var(--color-white);">
              Send
            </div>
          </div>
        </div>
      </div>

      <div flex flex-gap-5>
        <div :class="[requestTap !== 'Params' ? 'tap' : 'highlight']" @click="requestTap = 'Params'">
          <p>Params</p>
        </div>
        <div :class="[requestTap !== 'Headers' ? 'tap' : 'highlight']" @click="requestTap = 'Headers'">
          <p>Headers</p>
        </div>
        <div :class="[requestTap !== 'Body' ? 'tap' : 'highlight']" @click="requestTap = 'Body'">
          <p>Body</p>
        </div>
      </div>

      <Params v-if="requestTap === 'Params'" />
      <Headers v-if="requestTap === 'Headers'" />
      <Body v-if="requestTap === 'Body'" />
    </div>
    <div class="resize-line" @mousedown="startResizing" @mousemove="handleResizing" @mouseup="stopResizing" />
    <Response name="Response" w-full border border-blue :style="setResponseStyle()" />
  </div>
</template>

<style scoped>
input {
  font-size: var(--font-H4-size);
  border: none;
  outline: none;
}

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

  /* Style */
  border-radius: 5px;
  background-color: white;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.2);

}

.methodList div:hover {
  background-color: var(--color-gray1);
}

.sendBtn {
  /* layout */
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1px;

  width: 7rem;
  height: 3rem;
  margin-left: 1rem;
  margin-top: 0.3rem;

  /* Style */
  border-radius: 5px;

  font-size: var(--font-H4-size);

  color: white;
  background-color: var(--color-blue1);

  cursor: pointer;
}

.sendBtn:hover {
  background-color: var(--color-blue1-hover);
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
