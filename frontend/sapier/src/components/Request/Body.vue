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
      <div class="formStyleBtn">
        raw
        <div i-carbon-chevron-down h-full w-5 />
      </div>
      <div class="textTypeBtn">
        JSON
        <div i-carbon-chevron-down h-full w-5 />
      </div>
    </div>
    <div class="bodyTextBox" mt-2 h-full w-full>
      <br>
    </div>
    <div />
  </div>
</template>

<style scoped>
.bodyTextBox {
  border: 1px solid var(--color-gray3);
}

.formStyleBtn {
  /* layout */
  display: flex;
  justify-content: space-between;
  gap: 0.8rem;

  padding: 0.1rem 0.5rem 0.2rem 0.8rem;

  line-height: 1.8rem;

  /* Style */
  border-radius: 5px;

  color: var(--color-gray4);
  background-color: var(--color-gray1);

  font-size: var(--font-H6-size);
  font-weight: var(--font-H6-weight);
}
.textTypeBtn {
  /* layout */
  display: flex;
  justify-content: space-between;
  gap: 0.8rem;

  padding: 0.1rem 0.5rem 0.2rem 0.8rem;

  line-height: 1.8rem;

  /* Style */
  border-radius: 5px;

  color: var(--color-blue2);

  font-size: var(--font-H6-size);
  font-weight: var(--font-H6-weight);
}
</style>
