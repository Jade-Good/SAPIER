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

function getAccessToken(url: string | URL | undefined) {
  const urlObj = new URL(url)
  return urlObj.searchParams.get('accessToken')
}

// 11/12 feat: OnUpdated.addListener
browser.tabs.onUpdated.addListener((tabId, changeInfo, tab) => {
  // console.log(browser.runtime.getURL(''))
  // 리디렉션된 URL을 확인
  // https://sapier.co.kr/login/redirect
  if (changeInfo.url && changeInfo.url.includes(browser.runtime.getURL('/dist/options/index.html?forExtension=Y&accessToken='))) {
    // 필요한 로직 수행, 예를 들어 로그인 상태를 저장
    browser.storage.local.set({ loggedIn: true })

    // eslint-disable-next-line no-console
    console.log('onUpdated: 로그인 성공')

    const accessToken = getAccessToken(tab.url)

    browser.storage.local.set({ token: accessToken })
    // eslint-disable-next-line no-console
    console.log(`토큰: ${accessToken}`)

    // fetch('https://sapier.co.kr/api/v1/users', {
    //   method: 'GET',
    //   headers: {
    //     'Content-Type': 'application/json',
    //     'Accept': 'application/json',
    //     'Authorization': `Bearer ${accessToken}`,
    //   },
    // })
    //   .then((response) => {
    //     console.log(response)
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
    getWorkspaceList()
  }
})

async function getWorkspaceList() {
  await browser.storage.local.get(['token']).then(async (value) => {
    await fetch('https://sapier.co.kr/api/v1/workspaces', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': `Bearer ${value.token}`,
      },
    })
      .then((response) => {
        // console.log(response)
        if (!response.ok)
          throw new Error('네트워크 응답이 정상적이지 않습니다.')
        return response.text()
      })
      .then((text) => {
        try {
          return JSON.parse(text)
        }
        catch (error) {
          console.error('JSON 파싱 오류:', error)
          // 파싱 오류 처리
        }
      })
      .then((data) => {
        // 데이터 할당
        browser.storage.local.set({ workspaceList: data })
        console.log('workspaceList 할당 성공', data)
        return data
      })
      .then((data) => {
        if (data.length !== 0) {
          browser.storage.local.set({ workspace: data[0] })
          browser.storage.local.set({ workspaceIndex: 0 })
          console.log('workspace, workspaceIndex 할당 성공', data[0], 0)
        }
      })
      .catch(error => console.error('Error:', error))
  })
}
