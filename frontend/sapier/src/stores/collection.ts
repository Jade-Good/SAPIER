import { acceptHMRUpdate, defineStore } from 'pinia'
import { ref } from 'vue'

interface CollectionTree {
  collectionName: string
  apiList: Request[]
  collectionList: CollectionTree[]
  modifiedTime: string
}

interface Request {
  requestName: string
  requestURL: string
  method: string
  requestHeaders: any
  requestBody: any
  params: any
  path: any
  workspacesId: string
}

interface Response {
  status: number
  headers: any
  data: any
}

export type { CollectionTree }

export const useCollectionStore = defineStore('collection', () => {
  const collection = ref<CollectionTree | null>(null)
  const request = ref<Request>()
  const response = ref<Response>()

  return {
    collection,
    request,
    response,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useCollectionStore as any, import.meta.hot))
