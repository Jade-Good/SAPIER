<script>
export default {
  data() {
    return {
      params: [
        { active: true, key: 'exp_cnt', value: 'Y', description: '경험치 포함 조회 여부' },
        { active: false, key: 'max_cnt', value: 10, description: '조회 리스트 개수 최대값' },
      ],
    }
  },
}
</script>

<template>
  <div class="p-3" h-full flex flex-col>
    <div flex flex-gap-2>
      <div w-16 border border-rounded p-1 pl-2>
        row
      </div>
      <div w-16 p-1 pl-2>
        JSON
      </div>
    </div>
    <div class="bodyTextBox" mt-2 h-full w-full>
      <br>
    </div>
    <div />
  </div>
</template>

<style scoped>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}

th, td {
  padding: 10px 20px;
  text-align: center;
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

.bodyTextBox {
  border: 1px solid var(--color-gray3);
}
</style>
