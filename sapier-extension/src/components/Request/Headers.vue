<script setup lang="ts">
const useRequest = ref<any>(null)
const requestHeaders = inject('requestHeaders')
const isHighLight = ref([] as boolean[])

// browser.storage.onChanged.addListener(requestChange)
// function requestChange(changes, area) {
//   const changedItems = Object.keys(changes)
//   for (const item of changedItems) {
//     if (item === 'reqeust') {
//       useRequest.value = item
//       console.log(`${item} has changed:`)
//       console.log('Old value: ', changes[item].oldValue)
//       console.log('New value: ', changes[item].newValue)
//     }
//   }
// }

browser.storage.local.get(['request']).then((data) => {
  useRequest.value = data.request
  console.log('request 스토어에 저장 :', data.request)
})

onMounted(setHLArray)

watch(() => useRequest.value, () => {
  setHLArray()
})

function setHLArray() {
  isHighLight.value = []
  console.log('1111111')
  requestHeaders.rows.forEach(() => {
    isHighLight.value.push(false)
  })
}

function rowHighLight(row: number) {
  isHighLight.value[row] = true
}
function clearHighLight(row: number) {
  isHighLight.value[row] = false
}
</script>

<template>
  <div select-none>
    <table name="headersTable" class="w-full text-left" select-none>
      <colgroup>
        <col class="act">
        <col class="key">
        <col class="val">
        <col class="desc">
      </colgroup>
      <thead>
        <tr>
          <th>Active</th>
          <th>Key</th>
          <th>Value</th>
          <th>Description</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(head, index) in requestHeaders.rows" :key="index" :class="{ tableHighLite: isHighLight[index] }">
          <td>
            <span v-if="head.active">✅</span>
            <span v-else-if="head.active == null" />
            <span v-else>⬜</span>
          </td>
          <td>
            <input v-model="head.key" placeholder="Key" h-full w-full @focus="rowHighLight(index)" @blur="clearHighLight(index)">
          </td>
          <td>
            <input v-model="head.value" placeholder="Value" h-full w-full @focus="rowHighLight(index)" @blur="clearHighLight(index)">
          </td>
          <td>
            <input v-model="head.description" placeholder="Description" h-full w-full @focus="rowHighLight(index)" @blur="clearHighLight(index)">
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
table,
th,
td {
  border: 1px solid var(--color-gray3);
  border-collapse: collapse;
}

th {
  padding: 0.5rem 1rem;
  height: 3rem;
}

td {
  padding: 0.5rem;
  height: 3rem;
}

input {
  padding: 0 0.5rem;
  text-overflow: ellipsis;
  background-color: inherit;
}

input:focus {
  outline: 1px solid var(--color-gray2);
  background-color: white;
  border-radius: 5px;
}

td:first-child {
  text-align: center;
}

.tableHighLite {
  background-color: var(--color-gray1);
}

.selectInput {
  background-color: red;
}

.act {
  width: 5%;
}

.key {
  width: 25%;
}

.val {
  width: 25%;
}

.desc {
  width: 45%;
}
</style>
