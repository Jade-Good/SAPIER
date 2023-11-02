<script>
export default {
  data() {
    return {
      params: [
        { active: true, key: 'exp_cnt', value: 'Y', description: '경험치 포함 조회 여부' },
        { active: false, key: 'max_cnt', value: 10, description: '조회 리스트 개수 최대값' },
        {},
      ],
    }
  },
}
</script>

<template>
  <div class="p-3" select-none>
    <p>Query Params</p>
    <table class="w-full text-left" select-none>
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
        <tr v-for="(param, index) in params" :key="index">
          <td>
            <span v-if="param.active">✅</span>
            <span v-else-if="param.active == null" />
            <span v-else>⬜</span>
          </td>
          <td>
            <input v-model="param.key" placeholder="Key">
          </td>
          <td>
            <input v-model="param.value" placeholder="Value">
          </td>
          <td>
            <input v-model="param.description" placeholder="Description">
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
table, th, td {
  border: 1px solid 2E2E2E;
  border-collapse: collapse;
}

th, td {
  padding: 10px 20px;
}

td:first-child {
  text-align: center;
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
