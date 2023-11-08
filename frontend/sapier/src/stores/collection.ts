import { acceptHMRUpdate, defineStore } from 'pinia'
import { ref } from 'vue'

interface CollectionTree {
  collectionName: string
  apiList: Api[]
  collectionList: CollectionTree[]
  modifiedTime: string
}

interface Api {
  apitName: string
  url: string
  method: string
  headers: []
  body: []
  params: []
  path: []
  workspacesId: string
}

export type { CollectionTree }

export const useCollectionStore = defineStore('collection', () => {
  const collection = ref<CollectionTree | null>(null)
  const api = ref<Api>()

  return {
    collection,
    api,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useCollectionStore as any, import.meta.hot))
