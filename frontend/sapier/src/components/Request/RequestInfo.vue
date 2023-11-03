<script>
export default {
  data() {
    return {
      // requestTap: 'Params',
      requestTap: 'Headers',
      // requestTap: 'Body',
      // requestTap: 'Settings',
    }
  },
}
</script>

<template>
  <div h-full flex flex-col border>
    <div name="Request" class="rqeHigh" w-full flex flex-col border border-red p-3>
      <div flex flex-justify-between pb-3 pl-3>
        <div flex flex-gap-1 line-height-9>
          <p color-gray>
            Server
          </p>
          <p color-gray>
            /
          </p>
          <p color-gray>
            Bubble
          </p>
          <p color-gray>
            /
          </p>
          <p>버블 리스트 조회</p>
        </div>
        <div flex flex-gap-3>
          <div class="grayBtn">
            <div i-carbon-save />
            Save
          </div>
          <div class="grayBtn">
            <div i-carbon-copy-file />
            Copy
          </div>
        </div>
      </div>

      <div flex-justify-betwee h-14 flex>
        <div w-full flex flex-gap-4 border border-rounded p-2 style="border-color: var(--color-gray4);">
          <div class="methodBtn">
            <div m-1>
              GET
            </div>
            <div i-carbon-chevron-down h-full />
          </div>
          <div border-l pl-4 style="border-color: var(--color-gray4); font-size: var(--font-H5-size); line-height: 2.5rem;">
            http://i9b108.p.ssafy.io/api/v1/bubble/bubblings
          </div>
        </div>

        <div class="sendBtn">
          <div flex flex-gap-2 flex-justify-center>
            <div i-carbon-send-alt pt-8 />
            Send
          </div>
        </div>
      </div>

      <div flex flex-gap-5 p-3>
        <div :class="[requestTap !== 'Params' ? 'tap' : 'highlight']" @click="requestTap = 'Params'">
          <p>Params</p>
        </div>
        <div :class="[requestTap !== 'Headers' ? 'tap' : 'highlight']" @click="requestTap = 'Headers'">
          <p>Headers</p>
        </div>
        <div :class="[requestTap !== 'Body' ? 'tap' : 'highlight']" @click="requestTap = 'Body'">
          <p>Body</p>
        </div>
        <div :class="[requestTap !== 'Settings' ? 'tap' : 'highlight']" @click="requestTap = 'Settings'">
          <p>Settings</p>
        </div>
      </div>

      <Params v-if="requestTap === 'Params'" />
      <Headers v-if="requestTap === 'Headers'" />
      <Body v-if="requestTap === 'Body'" />
      <Settings v-if="requestTap === 'Settings'" />
    </div>
    <div name="Response" w-full border border-blue class="resHigh">
      Response
    </div>
  </div>
</template>

<style scoped>
.rqeHigh {
  height: 90%;
}

.resHigh {
  height: 10%;
}

.highlight {
  border-bottom: 3px;
  border-bottom-style: solid;
  border-block-color: var(--color-blue1);
  /* border: solid 1px blue; */
}

.tap {
  color: var(--color-gray4);

  &:hover{
    color:#2E2E2E;
  }
}

.methodBtn {
  /* layout */
  display: flex;
  justify-content: space-between;
  padding: 0 0.5rem 0 0.8rem;
  width: 10rem;
  line-height: 2rem;

  /* Style */
  border-radius: 5px;

  color: white;
  background-color: var(--color-get);

  font-size: var(--font-H2-size);
  font-weight: var(--font-H2-weight);

}

.sendBtn {
  /* layout */
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1px;

  width: 10rem;
  margin-left: 1rem;

  /* Style */
  border-radius: 5px;

  font-size: var(--font-H3-size);

  color: white;
  background-color: var(--color-blue1);
}

.grayBtn {
    /* layout */
    display: flex;
    justify-content: center;
    gap: 0.5rem;
    padding: 0.6rem 1rem;
    line-height: 1.2rem;

    /* Style */
    border-radius: 5px;

    color: var(--color-gray3);
    background-color: var(--color-gray1);

    font-size: var(--font-H5-size);
    font-weight: var(--font-H5-weight);
}
</style>
