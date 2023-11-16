import { acceptHMRUpdate, defineStore } from 'pinia'
import { ref } from 'vue'

interface CollectionTree {
  collectionName: string
  apiList: RequestInfo[]
  collectionList: CollectionTree[]
  modifiedTime: string
  request: RequestInfo
  collapsed: boolean
  id:string
}

interface RequestInfo {
  requestName: string
  requestURL: string
  method: string
  headers: Table[]
  body: string
  queryParams: Table[]
  path: string
}

interface Response {
  statusCode: number
  statusText: string

  responseHeaders: any
  responseBody: string

  responseTime: number

  errorMsg: string
  errorStackTrace: string

}

interface Table {
  active: string
  key: string
  value: string
  description: string
}

export type { CollectionTree }

export const useCollectionStore = defineStore(
  'collection',
  {
    state: () => {
      const collection = ref<CollectionTree | null>(null)
      const request = ref<RequestInfo | null>(null)
      const response = ref<Response>()
      const selectDocument = ref()

      return {
        collection,
        request,
        response,
        selectDocument,
      }
    },
    persist: true,
  },
)

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useCollectionStore as any, import.meta.hot))
