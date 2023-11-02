<script>
</script>

<template>
  <div h-full flex flex-col border>
    <div name="Request" w-full border border-red class="rqeHigh" p-2>
      <div flex flex-gap-1 p-3>
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

      <div flex flex-gap-5 p-3>
        <div class="highlight">
          <p>Params</p>
        </div>
        <div color-gray>
          <p>Headers</p>
        </div>
        <div color-gray>
          <p>Body</p>
        </div>
        <div color-gray>
          <p>Settings</p>
        </div>
      </div>

      <Params />
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
  border-block-color: #0F4C81;
  /* border: solid 1px blue; */
}
</style>
