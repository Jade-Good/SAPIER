<script lang="ts">
export default {
  data() {
    return {
      headers: [
        { key: 'server', value: 'NWS' },
        { key: 'date', value: 'Wed, 25 Oct 2023 02:26:26 GMT +18' },
        { key: 'content-type', value: 'text/html' },
        { key: 'location', value: 'http://www.naver.com/' },
        { key: 'vary', value: 'Accept-Encoding,User-Agent' },
        { key: 'referrer-policy', value: 'unsafe-url' },
        { key: 'server', value: 'NWS' },
        { key: 'date', value: 'Wed, 25 Oct 2023 02:26:26 GMT +18' },
        { key: 'content-type', value: 'text/html' },
        { key: 'location', value: 'http://www.naver.com/' },
        { key: 'vary', value: 'Accept-Encoding,User-Agent' },
        { key: 'referrer-policy', value: 'unsafe-url' },
        { key: 'server', value: 'NWS' },
        { key: 'date', value: 'Wed, 25 Oct 2023 02:26:26 GMT +18' },
        { key: 'content-type', value: 'text/html' },
        { key: 'location', value: 'http://www.naver.com/' },
        { key: 'vary', value: 'Accept-Encoding,User-Agent' },
        { key: 'referrer-policy', value: 'unsafe-url' },
      ],
      body:
      `{
          "id" : "d3wer132qwer2",
          "model_version" : "v2.0.3.prod",
          "imges" : [
              {
                  "id" : "d3wer132qwer2",
                  "model_version" : "v2.0.3.prod",
                  "id" : "d3wer132qwer2",
                  "model_version" : "v2.0.3.prod",
                  "seed" : "asdsdd3wer132qwer2",
              }
          ],
          "id" : "d3wer132qwer2",
          "model_version" : "v2.0.3.prod",
          "imges" : [
              {
                  "id" : "d3wer132qwer2",
                  "model_version" : "v2.0.3.prod",
                  "id" : "d3wer132qwer2",
                  "model_version" : "v2.0.3.prod",
                  "seed" : "asdsdd3wer132qwer2",
              }
          ],
          "id" : "d3wer132qwer2",
          "model_version" : "v2.0.3.prod",
          "imges" : [
              {
                  "id" : "d3wer132qwer2",
                  "model_version" : "v2.0.3.prod",
                  "id" : "d3wer132qwer2",
                  "model_version" : "v2.0.3.prod",
                  "seed" : "asdsdd3wer132qwer2",
              }
          ],
          "id" : "d3wer132qwer2",
          "model_version" : "v2.0.3.prod",
          "imges" : [
              {
                  "id" : "d3wer132qwer2",
                  "model_version" : "v2.0.3.prod",
                  "id" : "d3wer132qwer2",
                  "model_version" : "v2.0.3.prod",
                  "seed" : "asdsdd3wer132qwer2",
              }
          ],
      }`,
    }
  },
  mounted() {
  },
  methods: {

    // startResizing(event: MouseEvent) {
    //   this.isResizing = true
    //   this.startY = event.clientY

    //   const ss = this.requestHigh.match(/\d+/g)
    //   if (ss)
    //     this.startHeight = Number.parseInt(ss.toString())
    // },

    // handleResizing(event: MouseEvent) {
    //   if (this.isResizing) {
    //     const deltaY = event.clientY - this.startY
    //     let newHeight = this.startHeight + deltaY

    //     const htmlElement = document.documentElement
    //     const computedFontSize = window.getComputedStyle(htmlElement).getPropertyValue('font-size')
    //     const minHeight = Number.parseFloat(computedFontSize) * 10.5

    //     const maxHeight = this.$el.clientHeight * 0.9

    //     if (newHeight < minHeight)
    //       newHeight = minHeight

    //     if (newHeight > maxHeight)
    //       newHeight = maxHeight

    //     this.requestHigh = `${newHeight}`

    //     // Response 엘리먼트의 크기를 조절할 수도 있습니다.

    //     event.preventDefault()
    //   }
    // },

    // stopResizing() {
    //   this.isResizing = false
    // },
  },
}
</script>

<template>
  <div h-full>
    <p style="font-size: var(--font-H3-size);" p-3>
      Response
    </p>
    <div class="resultCode">
      202
    </div>
    <div flex flex-gap-5 style="height: calc(100% - 2rem); padding: 1.25rem 1.25rem 0 1.25rem;">
      <div style="width: 100%; min-width: 20rem; max-width: 100%;" pb-3>
        <p style="font-size: var(--font-H2-size); font-weight: var(--font-H2-weight); border-bottom: 1px solid var(--color-gray3);" mb-2 pb-2>
          HEADERS
        </p>
        <table name="headersTable" w-full>
          <colgroup>
            <col style="width: 30%; ">
            <col style="width: 70%;">
          </colgroup>
          <tbody>
            <tr v-for="(head, index) in headers" :key="index">
              <td>
                {{ head.key }}
              </td>
              <td>{{ head.value }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div
        class="resize-line"
        @mousedown="startResizing"
        @mousemove="handleResizing"
        @mouseup="stopResizing"
      />

      <div style="width: 60%;">
        <p style="font-size: var(--font-H2-size); font-weight: var(--font-H2-weight); border-bottom: 1px solid var(--color-gray3);" pb-2>
          BODY
        </p>
        <textarea class="bodyText" readonly>{{ body }}</textarea>
      </div>
    </div>
  </div>
</template>

<style scoped>
td {
  padding: 0.5rem 1rem;
  user-select: text;
}

table, tr, td {
  border: 1px solid black;
  text-align: left; /* 텍스트를 왼쪽에 정렬합니다. */
  vertical-align: top; /* 텍스트를 상단에 정렬합니다. */
}

textarea:focus {
  outline: none;
}

textarea.bodyText {
  padding: 1rem;
  height: calc(100% - 3rem);
  width: 100%;
  resize: none;
}
.resultCode {

  padding: 0.5rem 1.5rem;

  background-color: var(--color-POST);
  color: white;
  font-family: Atkinson Hyperlegible;
  font-size: 36px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

.resize-line {
  padding: 1px;
  cursor: ew-resize; /* 세로 크기 조절 커서 모양 */
  width: 5px; /* 가로 너비를 100%로 설정하여 전체 너비에서 크기 조절 가능 */
  background-color:var(--color-gray2); /* 크기 조절 선의 배경색 설정 */
}
</style>
