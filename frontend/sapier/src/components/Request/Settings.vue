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
  <div class="p-3">
    <p>Settings</p>
  </div>
</template>

<style>
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
</style>
