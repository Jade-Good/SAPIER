<script setup lang="ts">
import JsonPretty from 'vue-json-pretty'
import 'vue-json-pretty/lib/styles.css'

const useCollection = useCollectionStore()
const requestCode = ref(0)
const requestHeaders = ref({})
const requestBody = ref({})

watch(() => useCollection.response, () => {
  if (useCollection.response) {
    // console.log('response : ', useCollection.response)

    requestCode.value = useCollection.response.statusCode
    requestHeaders.value = useCollection.response.responseHeaders
    requestBody.value = JSON.parse(useCollection.response.responseBody)
  }
})

function resultCodeStyle() {
  let method = ''

  switch (Math.floor(requestCode.value / 100)) {
    case 2:
      method = 'POST'
      break
    case 4:
      method = 'DELETE'
      break
    case 5:
      method = 'PATCH'
      break
  }

  return {
    padding: '0.5rem 1.5rem',

    backgroundColor: `var(--color-${method})`,
    color: 'white',
    fontFamily: 'Atkinson Hyperlegible',
    fontSize: '36px',
    fontStyle: 'normal',
    fontWeight: '700',
    lineHeight: 'normal',
  }
}
</script>

<template>
  <div h-full>
    <p style="font-size: var(--font-H3-size);" p-3>
      Response
    </p>
    <div v-if="requestCode > 0" :style="resultCodeStyle()">
      {{ requestCode }}
    </div>
    <div v-if="requestCode > 0" class="resHeaders">
      <div style="width: 40%;">
        <p
          style="font-size: var(--font-H2-size); font-weight: var(--font-H2-weight); border-bottom: 1px solid var(--color-gray3);"
          mb-2 pb-2
        >
          HEADERS
        </p>
        <div style="overflow: auto;  max-height: 100%;">
          <table class="headersTable" w-18>
            <colgroup>
              <col style="width: 30%; ">
              <col style="width: 70%;">
            </colgroup>
            <tbody>
              <tr v-for="(value, key) in requestHeaders" :key="key">
                <td> {{ key }} </td>
                <td>{{ value }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div style="width: 60%;">
        <p
          style="font-size: var(--font-H2-size); font-weight: var(--font-H2-weight); border-bottom: 1px solid var(--color-gray3);"
          pb-2
        >
          BODY
        </p>
        <JsonPretty :data="requestBody" style="user-select: text; overflow: scroll;" h-full />
      </div>
    </div>
  </div>
</template>

<style scoped>
td {
  padding: 0.5rem 1rem;
  user-select: text;
}

table,
tr,
td {
  border: 1px solid black;
  text-align: left;
  /* 텍스트를 왼쪽에 정렬합니다. */
  vertical-align: top;
  /* 텍스트를 상단에 정렬합니다. */
  word-wrap: break-word;
  /* 너무 긴 텍스트가 있는 경우 자동 줄바뀜 활성화 */
}

.resize-line {
  padding: 1px;
  cursor: ew-resize;
  /* 세로 크기 조절 커서 모양 */
  width: 5px;
  /* 가로 너비를 100%로 설정하여 전체 너비에서 크기 조절 가능 */
  background-color: var(--color-gray2);
  /* 크기 조절 선의 배경색 설정 */
}

.resHeaders {
  display: flex;
  gap: 1.25rem;
  width: 100%;
  height: calc(100% - 12rem);
  padding: 1.25rem 1.25rem 0 1.25rem;

}

.headersTable {
  table-layout: fixed;
  /* 테이블 레이아웃을 고정으로 설정 */
  width: 100%;
  /* 테이블 전체 너비 설정 */
}
</style>
