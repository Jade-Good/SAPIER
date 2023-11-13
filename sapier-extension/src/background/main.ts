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
  // 리디렉션된 URL을 확인
  if (changeInfo.url && changeInfo.url.includes('https://sapier.co.kr/login/redirect')) {
    // eslint-disable-next-line no-console
    console.log('로그인 성공')
    // 필요한 로직 수행, 예를 들어 로그인 상태를 저장
    browser.storage.local.set({ loggedIn: true })
    // 필요한 경우 팝업에 메시지 전송
    browser.runtime.sendMessage({ loggedIn: true })
  }
})

// 11/13
// browser.runtime.onMessage.addListener((message, sender, sendResponse) => {
//   if (message.type === 'saveUserData') {
//     // chrome.storage에 데이터 저장
//     browser.storage.local.set({ sapierUserData: message.data }).then((data) => {
//       // eslint-disable-next-line no-console
//       console.log(`사용자 데이터 저장됨${data.email}`)
//     })
//   }
// })
