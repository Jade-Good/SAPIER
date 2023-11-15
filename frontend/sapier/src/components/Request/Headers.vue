<script setup lang="ts">
const useCollection = useCollectionStore()
const isMounted = useMounted()

const requestHeaders = inject('requestHeaders')
const isHighLight = ref([] as boolean[])
const isDeleteBtn = ref([] as boolean[])

if (isMounted)
  setHLArray()

watch(() => useCollection.request, () => {
  setHLArray()
})

function setHLArray() {
  isHighLight.value = []
  isDeleteBtn.value = []

  requestHeaders.rows.forEach(() => {
    isHighLight.value.push(false)
  })
  requestHeaders.rows.forEach(() => {
    isDeleteBtn.value.push(false)
  })
}

function rowHighLight(row: number) {
  isHighLight.value[row] = true
}
function clearHighLight(row: number) {
  isHighLight.value[row] = false
}

function inputTextColor(active: string) {
  return {
    color: active === 'true' ? 'var(--color-black)' : 'var(--color-gray3)',
  }
}

function inputStart(index: number) {
  if (!requestHeaders.rows[index].active) {
    requestHeaders.rows[index].active = 'true'
    requestHeaders.rows.push({ active: '', key: '', value: '', description: '' })
  }
}

function deleteRow(index: number) {
  requestHeaders.rows.splice(index, 1)
  setHLArray()
}
</script>

<template>
  <div class="p-3" select-none>
    <p>Headers</p>
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
        <tr v-for="(head, index) in requestHeaders.rows" :key="index" :class="{ tableHighLite: isHighLight[index] }" @mouseenter="() => { isDeleteBtn[index] = true }" @mouseleave="() => { isDeleteBtn[index] = false }">
          <td>
            <span v-if="head.active === 'true'" @click="() => { head.active = 'false' }">✅</span>
            <span v-else-if="head.active === 'false'" @click="() => { head.active = 'true' }">⬜</span>
            <span v-else />
          </td>
          <td>
            <input v-model="head.key" placeholder="Key" h-full w-full :style="inputTextColor(head.active)" @focus="rowHighLight(index)" @blur="clearHighLight(index)" @input="inputStart(index)">
          </td>
          <td>
            <input v-model="head.value" placeholder="Value" h-full w-full :style="inputTextColor(head.active)" @focus="rowHighLight(index)" @blur="clearHighLight(index)" @input="inputStart(index)">
          </td>
          <td>
            <input v-model="head.description" placeholder="Description" h-full w-full :style="inputTextColor(head.active)" @focus="rowHighLight(index)" @blur="clearHighLight(index)" @input="inputStart(index)">
            <div v-if="isDeleteBtn[index] && head.active !== ''" class="deleteBtn" @click="deleteRow(index)">
              <div i-carbon-trash-can style="width: 1.5rem; height: 1.5rem; transform: translate(-50%,-50%);" />
            </div>
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
  position: relative
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

input::placeholder {
  color : var(--color-gray3)
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

.deleteBtn {
  position: absolute;
  right: 0.5rem;
  top:50%;
  transform:translateY(-50%);

  width: 1.5rem;
  height: 1.5rem;
  padding:  1rem;

  border-radius: 0.2rem;

  cursor: pointer;
}

.deleteBtn:hover {
  background-color: var(--color-gray1);
}

.deleteBtn:active {
  background-color: var(--color-gray1-hover);
}
</style>
