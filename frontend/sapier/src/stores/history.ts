import { acceptHMRUpdate, defineStore } from 'pinia'
import { ref } from 'vue'

// import type { UserInfo } from './user'

interface HistoryInfo{
    createdTime: any
    historyId: string
    request: RequestInfo
    response: Response
    workSpaceId: string
    uuid: string
}

interface RequestInfo {
    requestName: string
    requestURL: string
    method: string
    headers: Table[]
    body: string
    queryParams: Table[]
    path: string[]
}
  
interface Response {
    statusCode: number
    statusText: string
  
    responseHeaders: any
    responseBody: string
  
    responseTime:number
  
    errorMsg: string
    errorStackTrace: string
    
}
  
interface Table {
    active: string
    key: string
    value: string
    description: string
}

export type { HistoryInfo }

export const useHistoryStore = defineStore('history', ()=>{
    const history = ref < HistoryInfo | null > (null)
    const request = ref < RequestInfo | null >(null)
    const response = ref < Response >()

    return {
        history,
        request,
        response
    }
})

if (import.meta.hot)
    import.meta.hot.accept(acceptHMRUpdate(useHistoryStore as any, import.meta.hot))