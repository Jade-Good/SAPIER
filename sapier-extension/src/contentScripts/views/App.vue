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

const selectCollection = ref(false)
browser.storage.onChanged.addListener(requestChange)
function requestChange(changes, area) {
  const changedItems = Object.keys(changes)
  for (const item of changedItems) {
    if (item === 'request') {
      selectCollection.value = true
      console.log(`${item} has changed:`)
      console.log('Old value: ', changes[item].oldValue)
      console.log('New value: ', changes[item].newValue)
    }
  }
}
</script>

<template>
  <div class="fixed right-0 bottom-0 z-10000 flex items-end font-sans select-none leading-1em m-3">
    <div
      id="sapier"
      transition="opacity duration-300"
      :class="{ hidden: !show, block: show }"
    >
      <div v-if="isLoggedIn" flex class="mid">
        <WorkspaceList w-20 />
        <Category w-50 />
        <RequestInfo v-if="selectCollection" h-full w-full />
        <WorkspaceInfo v-else h-full w-full />
      </div>
      <div v-else class="login-require">
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

<style>
div {
  --font-H0-size: 96px;
  --font-H0-weight: 800;
  --font-H1-size: 32px;
  --font-H1-weight: 800;
  --font-H2-size: 24px;
  --font-H2-weight: 700;
  --font-H3-size: 24px;
  --font-H3-weight: 400;
  --font-H4-size: 20px;
  --font-H4-weight: 400;
  --font-H5-size: 16px;
  --font-H5-weight: 400;
  --font-H6-size: 16px;
  --font-H6-weight: 700;

  --color-blue1: #0F4C81; /* Classic Blue */
  --color-blue1-hover: #0c4577; /* Classic Blue Hover*/
  --color-blue1-active: #0a375f; /* Classic Blue Active*/
  --color-blue2: #658DC6; /* PrOvence */
  --color-blue3: #B5C7D3; /* Baby Blue */

  /* --color-white: White; White */
  --color-gray1: #F0F0F0; /* White Gray */
  --color-gray1-hover: #dedede; /* White Gray Hover*/
  --color-gray1-active: #cfcfcf; /* White Gray Active*/
  --color-gray2: #C9C9C9; /* Lite Gray */
  --color-gray3: #B6B6B6; /* Gray */
  --color-gray4: #838383; /* Dark Gray */
  --color-black: #2E2E2E; /* Black */

  --color-DELETE: #E64F47; /* DEL */
  --color-PATCH: #EFA44A; /* PATCH */
  --color-POST: #6EC465; /* POST */
  --color-GET: #44A1F8; /* GET */
  --color-PUT: #21DEB1; /* PUT */
  --color-OPTIONS: #C98CFF; /* POT */
  --color-HEAD: #FFA6DE; /* HEAD */

  color:var(--color-black);

  user-select: none;
}
::-webkit-scrollbar {
  display: none;
}
.login-require{
  height: 100%;
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  font-size: larger;
}

.mid {
  height: 100%;
}

#sapier {
    position: fixed;
    width: 1200px;
    height: 647px;
    left: 20%;
    top: 15%;
    z-index: 9999999 !important;
    background-color: #ffffff;
    border: 1px solid rgb(182, 182, 182);
    text-align: center;
    box-shadow: rgba(24, 24, 27, 0.75) 10px 15px 10px -5px;
    border-radius: 20px;
  }
  #sapier-main {

  }
  #sapier-header {
    display: fixed;
    padding: 10px;
    background-color: #2196f3;
    color: #fff;
  }
</style>
