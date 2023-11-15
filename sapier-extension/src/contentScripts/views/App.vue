<script setup lang="ts">
import { useToggle } from '@vueuse/core'
import 'uno.css'

const [show, toggle] = useToggle(false)

const isLoggedIn = ref(false)
browser.storage.local.get(['loggedIn']).then((data) => {
  // eslint-disable-next-line no-console
  console.log(`로그인 검사 ${data.loggedIn}`)
  isLoggedIn.value = data.loggedIn
})

browser.storage.onChanged.addListener(requestChange)
const selectCollection = ref(false)
function requestChange(changes, area) {
  const changedItems = Object.keys(changes)
  for (const item of changedItems) {
    if (item === 'collection') {
      selectCollection.value = true
      console.log(`${item} has changed:`)
      console.log('Old value: ', changes[item].oldValue)
      console.log('New value: ', changes[item].newValue)
    }
  }
}
</script>

<template>
  <div class="fixed right-0 bottom-0 m-5 z-100 flex items-end font-sans select-none leading-1em">
    <div
      id="sapier"
      class="bg-white text-gray-800 rounded-lg shadow w-max h-min"
      p="x-4 y-2"
      m="y-auto r-2"
      transition="opacity duration-300"
      :class="{ hidden: !show, block: show }"
    >
      <div v-if="isLoggedIn" flex class="mid">
        <WorkspaceList w-20 />
        <Category w-60 />
        <RequestInfo v-if="selectCollection" h-full w-full />
        <WorkspaceInfo v-else h-full w-full />
      </div>
      <div v-else>
        로그인을 해주세요.
      </div>
    </div>
    <button
      class="flex w-10 h-10 rounded-full shadow cursor-pointer border-none"
      bg="teal-600 hover:teal-700"
      @click="toggle()"
    >
      <pixelarticons-power class="block m-auto text-white text-lg" />
    </button>
  </div>
</template>

<style scoped>
.mid {
  height: calc(100% - 70px);
}
#sapier {
    position: fixed;
    width: 900px;
    height: 647px;
    left: 20%;
    top: 15%;
    z-index: 9999999 !important;
    background-color: #f1f1f1;
    border: 1px solid #d3d3d3;
    text-align: center;
  }
  #sapier-main {
    position: absolute;
    width: 900px;
    height: 647px;
    background-color: whitesmoke;
    text-align: center;
    color: rgb(24, 24, 27);
    box-shadow: rgba(24, 24, 27, 0.75) 10px 15px 10px -5px;
    cursor: default;
    font-size: 13px;
    border-radius: 5px;
  }
  #sapier-header {
    display: fixed;
    padding: 10px;
    background-color: #2196f3;
    color: #fff;
  }
</style>
