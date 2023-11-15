<script setup lang="ts">
const useCollection = useCollectionStore()
const isMounted = useMounted()

const queryParams = inject('queryParams')
const isHighLight = ref([] as boolean[])

if (isMounted) {
  queryParams.rows.forEach(() => {
    isHighLight.value.push(false)
  })
}

watch(() => useCollection.request, () => {
  queryParams.rows.forEach(() => {
    isHighLight.value.push(false)
  })
})

function rowHighLight(row: number) {
  isHighLight.value[row] = true
}
function clearHighLight(row: number) {
  isHighLight.value[row] = false
}
</script>

<template>
  <div class="p-3" select-none>
    <p>Queary Params</p>
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
        <tr v-for="(param, index) in queryParams.rows" :key="index" :class="{ tableHighLite: isHighLight[index] }">
          <td>
            <span v-if="param.active === 'true'">✅</span>
            <span v-else-if="param.active === 'false'">⬜</span>
            <span v-else />
          </td>
          <td>
            <input v-model="param.key" placeholder="Key" h-full w-full @focus="rowHighLight(index)" @blur="clearHighLight(index)">
          </td>
          <td>
            <input v-model="param.value" placeholder="Value" h-full w-full @focus="rowHighLight(index)" @blur="clearHighLight(index)">
          </td>
          <td>
            <input v-model="param .description" placeholder="Description" h-full w-full @focus="rowHighLight(index)" @blur="clearHighLight(index)">
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
