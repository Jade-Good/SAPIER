<script lang="ts">
export default {
  data() {
    return {
      selectMethod: 'GET',
      methodList: [
        'GET',
        'POST',
        'PUT',
        'PATCH',
        'DELETE',
        'HEAD',
        'OPTIONS',
      ],
      isMethodList: false,

      requestHigh: '600px',
      requestTap: 'Params',
      requestURL: 'http://',
      isResizing: false, // 크기 조절 중 여부
      startY: 0, // 크기 조절 시작 지점
      startHeight: 0, // 크기 조절 시작 시 Request 엘리먼트의 높이
    }
  },
  mounted() {
    // 메서드 리스트 이벤트 등록
    document.addEventListener('click', this.handleDocumentClick)

    // Request 높이 초기화
    const htmlElement = document.documentElement
    const computedFontSize = window.getComputedStyle(htmlElement).getPropertyValue('font-size')
    const currentRemValue = Number.parseFloat(computedFontSize) * 35

    this.requestHigh = `${currentRemValue}px`

    window.addEventListener('mousemove', this.handleResizing)
    window.addEventListener('mouseup', this.stopResizing)
  },

  beforeUnmount() {
    document.removeEventListener('click', this.handleDocumentClick)
    window.removeEventListener('mousemove', this.handleResizing)
    window.removeEventListener('mouseup', this.stopResizing)
  },
  methods: {

    // ---------------- 메서드 리스트 토글기능 ----------------
    setMethodBtnStyle() {
      return {
        /* layout */
        display: 'flex',
        justifyContent: 'space-between',
        padding: '0 0.5rem 0 0.8rem',
        width: '10rem',
        lineHeight: '2rem',

        /* Style */
        borderRadius: '5px',
        outline: this.isMethodList ? '3px solid var(--color-blue2)' : 'none',

        color: 'white',
        backgroundColor: `var(--color-${this.selectMethod})`,

        fontSize: 'var(--font-H2-size)',
        fontWeight: 'var(--font-H2-weight)',

        cursor: 'pointer',
      }
    },
    setMethodColor(method: string) {
      return {
        width: '14rem',
        padding: '0.5rem',

        borderRadius: '5px',

        color: `var(--color-${method})`,
        fontSize: 'var(--font-H2-size)',
        backgroundColor: method === this.selectMethod ? 'var(--color-gray1-hover)' : 'none',

      }
    },

    changeMethod(method: string) {
      this.setMethodColor(method)
      this.selectMethod = method
      this.toggleMethodList()
    },

    toggleMethodList() {
      this.isMethodList = !this.isMethodList
    },

    closeMethodList() {
      this.isMethodList = false
    },
    handleDocumentClick(event: Event) {
      // 클릭 이벤트에서 메서드 목록을 열려 있을 때만 닫도록 처리
      if (this.isMethodList && !this.$el.contains(event.target))
        this.isMethodList = false
    },

    // -----------------Request 창 크기 조절--------------------
    setRequestStyle() {
      return {
        /* layout */
        display: 'flex',
        flexDirection: 'column',

        width: '100%',
        height: this.requestHigh,
        minHeight: '10.5rem',
        maxHeight: '90%',

        padding: '0.75rem',

        overflow: 'auto',

        /* Style */
        border: '1px solid red',
      }
    },

    startResizing(event: MouseEvent) {
      this.isResizing = true
      this.startY = event.clientY

      const ss = this.requestHigh.match(/\d+/g)
      if (ss)
        this.startHeight = Number.parseInt(ss.toString())
    },

    handleResizing(event: MouseEvent) {
      if (this.isResizing) {
        const deltaY = event.clientY - this.startY
        let newHeight = this.startHeight + deltaY

        const htmlElement = document.documentElement
        const computedFontSize = window.getComputedStyle(htmlElement).getPropertyValue('font-size')
        const minHeight = Number.parseFloat(computedFontSize) * 10.5

        const maxHeight = this.$el.clientHeight * 0.9

        if (newHeight < minHeight)
          newHeight = minHeight

        if (newHeight > maxHeight)
          newHeight = maxHeight

        this.requestHigh = `${newHeight}px`

        // Response 엘리먼트의 크기를 조절할 수도 있습니다.

        event.preventDefault()
      }
    },

    stopResizing() {
      this.isResizing = false
    },
    // --------------------------------------------------------
    setResponseStyle() {
      return {
        height: `calc(100% - ${this.requestHigh})`, // 나머지 여백을 설정
        overflow: 'auto',
      }
    },
  },
}
</script>

<template>
  <div h-full flex flex-col border @click.capture="closeMethodList">
    <div name="Request" :style="setRequestStyle()">
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
          <div :style="setMethodBtnStyle()" @click="toggleMethodList()">
            <div m-1>
              {{ selectMethod }}
            </div>
            <div i-carbon-chevron-down h-full />
          </div>

          <div v-if="isMethodList" class="methodList">
            <div v-for="(method, idx) in methodList" :key="idx" :style="setMethodColor(method)" @click="chagneMethod(method)">
              {{ method }}
            </div>
          </div>

          <div style="border-color: var(--color-gray4); font-size: var(--font-H5-size); line-height: 2.5rem;" w-full border-l pl-2>
            <input v-model="requestURL" type="text" w-full pl-2>
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
    <div
      class="resize-line"
      @mousedown="startResizing"
      @mousemove="handleResizing"
      @mouseup="stopResizing"
    />
    <div name="Response" w-full border border-blue :style="setResponseStyle()">
      Response
      <Headers />
    </div>
  </div>
</template>

<style scoped>
input:focus {
  outline: 1px solid var(--color-gray2);
  background-color: white;
  border-radius: 5px;
}

.highlight {
  border-bottom: 3px;
  border-bottom-style: solid;
  border-block-color: var(--color-blue1);
  /* border: solid 1px blue; */
}

.tap {
  color: var(--color-gray4);
  cursor: pointer;
}

.tap:hover {
  color:#2E2E2E;
}

.methodList {
  /* layout */
  position: absolute;
  top: 13rem;

  padding: 0.5rem;

  /* Style */
  border-radius: 5px;
  background-color: white;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.2);

}

.methodList div:hover {
  background-color: var(--color-gray1);
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

  cursor: pointer;
}

.sendBtn:hover {
  background-color: var(--color-blue1-hover);
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

    color: var(--color-gray4);
    background-color: var(--color-gray1);

    font-size: var(--font-H5-size);
    font-weight: var(--font-H5-weight);

    cursor: pointer;
}

.grayBtn:hover {
  background-color: var(--color-gray1-hover);
}

.resize-line {
  cursor: ns-resize; /* 세로 크기 조절 커서 모양 */
  height: 5px; /* 크기 조절 선의 높이 설정 */
  width: 100%; /* 가로 너비를 100%로 설정하여 전체 너비에서 크기 조절 가능 */
  background-color: var(--color-gray3); /* 크기 조절 선의 배경색 설정 */
}
</style>
