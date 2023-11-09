import { acceptHMRUpdate, defineStore } from 'pinia'
import { ref } from 'vue'

interface CollectionTree {
  collectionName: string
  apiList: Api[]
  collectionList: CollectionTree[]
  modifiedTime: string
}

interface Request {
  requestName: string
  requestURL: string
  method: string
  requestHeaders: []
  requestBody: []
  params: []
  path: []
  workspacesId: string
}

interface Response extends Request {
  responseCode: number
  responseHeaders: []
  responseBody: []
  time: number
}

export type { CollectionTree }

export const useCollectionStore = defineStore('collection', () => {
  const collection = ref<CollectionTree | null>(null)
  const request = ref<Request>()
  const response = ref<Response>()

  return {
    collection,
    request,
    response
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useCollectionStore as any, import.meta.hot))
