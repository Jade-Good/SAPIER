<script setup lang="ts">
import VueJsoneditor from 'vue3-ts-jsoneditor'

const requestBody = inject('requestBody')
</script>

<template>
  <div class="p-3" h-full flex flex-col>
    <!-- <div flex flex-gap-2>
      <div class="formStyleBtn">
        raw
        <div i-carbon-chevron-down h-full w-5 />
      </div>
      <div class="textTypeBtn">
        JSON
        <div i-carbon-chevron-down h-full w-5 />
      </div>
    </div> -->
    <VueJsoneditor
      v-model:json="requestBody" h-full
      w-full
      height="300"
      mode="text"
      :query-languages-ids="queryLanguages"
      @error="onError"
      @focus="onFocus"
      @blur="onBlur"
    />

    <div />
  </div>
</template>

<style scoped>
/*
.bodyTextBox {
  border: 1px solid var(--color-gray3);
  white-space: pre;
}

.formStyleBtn {
  display: flex;
  justify-content: space-between;
  gap: 0.8rem;

  padding: 0.1rem 0.5rem 0.2rem 0.8rem;

  line-height: 1.8rem;

  border-radius: 5px;

  color: var(--color-gray4);
  background-color: var(--color-gray1);

  font-size: var(--font-H6-size);
  font-weight: var(--font-H6-weight);
}
.textTypeBtn {
  display: flex;
  justify-content: space-between;
  gap: 0.8rem;

  padding: 0.1rem 0.5rem 0.2rem 0.8rem;

  line-height: 1.8rem;

  border-radius: 5px;

  color: var(--color-blue2);

  font-size: var(--font-H6-size);
  font-weight: var(--font-H6-weight);
}
*/
</style>
