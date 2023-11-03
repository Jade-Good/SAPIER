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
    <div name="Request" class="rqeHigh" w-full flex flex-col border border-red p-2>
      <div flex select-none flex-gap-1 p-3>
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

      <div flex flex-justify-between>
        <div w-full flex flex-gap-2 border border-rounded p-2>
          <div w-30 border border-rounded p-1 pl-2>
            GET
          </div>
          <div border-l p-1 pl-2>
            <p>http://i9b108.p.ssafy.io/api/v1/bubble/bubblings</p>
          </div>
        </div>

        <div ml-3 w-30 border border-rounded p-3>
          Send
        </div>
      </div>

      <div flex select-none flex-gap-5 p-3>
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
</style>
