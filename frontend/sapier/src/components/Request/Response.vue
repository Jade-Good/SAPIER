<script setup lang="ts">
import axios from 'axios'

const useCollection = useCollectionStore()
const requestCode = ref(0)
const requestHeaders = ref({ test1: '123', test2: 12321 })
const requestBody = ref('testtest')

watch(() => useCollection.response, () => {
  if (useCollection.response) {
    console.log('response : ', useCollection.response)

    requestCode.value = useCollection.response.status
    requestHeaders.value = useCollection.response.headers
    requestBody.value = useCollection.response.data
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
</script>

<template>
  <div h-full>
    <p style="font-size: var(--font-H3-size);" p-3>
      Response{{ }}
    </p>
    <div v-if="requestCode > 0" :style="resultCodeStyle()">
      {{ requestCode }}
    </div>
    <div v-if="requestCode > 0" flex flex-gap-5 style="height: calc(100% - 2rem); padding: 1.25rem 1.25rem 0 1.25rem;">
      <div style="width: 100%; min-width: 20rem; max-width: 100%;" pb-3>
        <p style="font-size: var(--font-H2-size); font-weight: var(--font-H2-weight); border-bottom: 1px solid var(--color-gray3);" mb-2 pb-2>
          HEADERS
        </p>
        <table name="headersTable" w-full>
          <colgroup>
            <col style="width: 30%; ">
            <col style="width: 70%;">
          </colgroup>
          <tbody>
            <tr v-for="(value, key) in requestHeaders" :key="key">
              <td>
                {{ key }}
              </td>
              <td>{{ value }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div
        class="resize-line"
        @mousedown="startResizing"
        @mousemove="handleResizing"
        @mouseup="stopResizing"
      />

      <div style="width: 60%;">
        <p style="font-size: var(--font-H2-size); font-weight: var(--font-H2-weight); border-bottom: 1px solid var(--color-gray3);" pb-2>
          BODY
        </p>
        <textarea class="bodyText" readonly>{{ requestBody }}</textarea>
      </div>
    </div>
  </div>
</template>

<style scoped>
td {
  padding: 0.5rem 1rem;
  user-select: text;
}

table, tr, td {
  border: 1px solid black;
  text-align: left; /* 텍스트를 왼쪽에 정렬합니다. */
  vertical-align: top; /* 텍스트를 상단에 정렬합니다. */
}

textarea:focus {
  outline: none;
}

textarea.bodyText {
  padding: 1rem;
  height: calc(100% - 3rem);
  width: 100%;
  resize: none;
}

.resize-line {
  padding: 1px;
  cursor: ew-resize; /* 세로 크기 조절 커서 모양 */
  width: 5px; /* 가로 너비를 100%로 설정하여 전체 너비에서 크기 조절 가능 */
  background-color:var(--color-gray2); /* 크기 조절 선의 배경색 설정 */
}
</style>
