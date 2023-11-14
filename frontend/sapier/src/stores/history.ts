import { acceptHMRUpdate, defineStore } from 'pinia'
import { ref } from 'vue'

// import type { UserInfo } from './user'

interface HistoryInfo{
    historyId: string
    request: RequestInfo
    workSpaceId: string
    uuid: string
}

interface RequestInfo{
    requestName: string
    requestURL: string
    method: string
    headers: any
    body: any
    queryParams: any
    formData: any
    workspacesId: string
    id: string
    modifiedTime: string
}

interface Response{
    status: number
    headers: any
    data: []
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