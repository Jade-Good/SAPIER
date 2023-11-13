import { acceptHMRUpdate, defineStore } from 'pinia'
import { ref } from 'vue'

interface CollectionTree {
  collectionName: string
  apiList: RequestInfo[]
  collectionList: CollectionTree[]
  modifiedTime: string
  request: RequestInfo
}

interface RequestInfo {
  requestName: string
  requestURL: string
  method: string
  headers: Table[]
  body: any
  queryParams: Table[]
  formData: any
  workspacesId: string
  id: string
  modifiedTime: string
  path: string[]
}

interface Response {
  status: number
  headers: any
  data: any
}

interface Table {
  active: boolean
  key: string
  value: string
  description: string
}

export type { CollectionTree }

export const useCollectionStore = defineStore('collection', () => {
  const collection = ref<CollectionTree | null>(null)
  const request = ref<RequestInfo | null>(null)
  const response = ref<Response>()

  return {
    collection,
    request,
    response,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useCollectionStore as any, import.meta.hot))
