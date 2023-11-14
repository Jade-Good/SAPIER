import { onMessage, sendMessage } from 'webext-bridge/background'
import type { Tabs } from 'webextension-polyfill'

// only on dev mode
if (import.meta.hot) {
  // @ts-expect-error for background HMR
  import('/@vite/client')
  // load latest content script
  import('./contentScriptHMR')
}

browser.runtime.onInstalled.addListener((): void => {
  // eslint-disable-next-line no-console
  console.log('Extension installed')
  browser.storage.local.set({ loggedIn: false })
})

let previousTabId = 0

// communication example: send previous tab title from background page
// see shim.d.ts for type declaration
browser.tabs.onActivated.addListener(async ({ tabId }) => {
  if (!previousTabId) {
    previousTabId = tabId
    return
  }

  let tab: Tabs.Tab

  try {
    tab = await browser.tabs.get(previousTabId)
    previousTabId = tabId
  }
  catch {
    return
  }

  // eslint-disable-next-line no-console
  console.log('previous tab', tab)
  sendMessage('tab-prev', { title: tab.title }, { context: 'content-script', tabId })
})

onMessage('get-current-tab', async () => {
  try {
    const tab = await browser.tabs.get(previousTabId)
    return {
      title: tab?.title,
    }
  }
  catch {
    return {
      title: undefined,
    }
  }
})

// 11/12 feat: OnUpdated.addListener
browser.tabs.onUpdated.addListener((tabId, changeInfo, tab) => {
  // console.log(browser.runtime.getURL(''))
  // 리디렉션된 URL을 확인
  // https://sapier.co.kr/login/redirect
  if (changeInfo.url && changeInfo.url.includes(browser.runtime.getURL(''))) {
    // 필요한 로직 수행, 예를 들어 로그인 상태를 저장
    browser.storage.local.set({ loggedIn: true })

    // eslint-disable-next-line no-console
    console.log('로그인 성공')

    // 쿠키 저장
    // const baseUrl = browser.runtime.getURL('/dist/login/index.html')
    // browser.cookies.get({ url: baseUrl, name: 'accessToken' }).then((cookie: any) => {
    //   if (cookie) {
    //     browser.storage.sync.set({ cookie })
    //     // eslint-disable-next-line no-console
    //     console.log('쿠키 저장')
    //   }
    //   else {
    //     browser.storage.sync.set({ cookie: null })
    //     // eslint-disable-next-line no-console
    //     console.log('쿠키 저장 실패')
    //   }
    // })

    // // 필요한 경우 팝업에 메시지 전송
    // browser.runtime.sendMessage({ loggedIn: true })

    // const axios = require('axios')
    // axios.defaults.withCredentials = true

    // axios로 유저 정보 요청
    // axios.get('https://localhost:8080/api/v1/user')
    //   .then((response: { data: any }) => {
    //     // 요청 성공 시 수행할 로직
    //   // eslint-disable-next-line no-console
    //     console.log('유저 정보:', response.data)
    //   })
    //   .catch((error: any) => {
    //     // 에러 처리

    //     console.error('유저 정보 요청 실패:', error)
    //   })

    // fetch('http://localhost:8080/api/v1/user', {
    //   method: 'GET',
    //   headers: {
    //     'Content-Type': 'application/json',
    //     'Accept': 'application/json',
    //   },
    //   credentials: 'same-origin',
    // })
    //   .then((response) => {
    //     console.error(response)
    //     if (!response.ok)
    //       throw new Error('네트워크 응답이 정상적이지 않습니다.')
    //     return response.text()
    //   })
    //   .then((text) => {
    //     try {
    //       return JSON.parse(text)
    //     }
    //     catch (error) {
    //       console.error('JSON 파싱 오류:', error)
    //       // 파싱 오류 처리
    //     }
    //   })
    //   .then(data => console.log(data))
    //   .catch(error => console.error('Error:', error))
  }
})

// 11/13
browser.runtime.onMessage.addListener((message, sender, sendResponse) => {
  // eslint-disable-next-line no-console
  console.log(`리스너 수신완료 ${message}`)
  if (message && message.loggedIn === true) {
    // const urlOnboarding = `chrome-extension://${browser.runtime.id}/dist/otions/index.html`
    // browser.tabs.create({ url: urlOnboarding })
    // eslint-disable-next-line no-console
    console.log(`리디렉션 수신완료 ${message}`)
    // chrome.storage에 데이터 저장
    // browser.storage.local.set({ sapierUserData: message.data }).then((data) => {
    //   // eslint-disable-next-line no-console
    //   console.log(`사용자 데이터 저장됨${data.email}`)
    // })
  }
})
