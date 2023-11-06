import { acceptHMRUpdate, defineStore } from 'pinia'

interface CollectionTree {
  collectionName: string
  apiList: string
  collectionList: CollectionTree[]
  modifiedTime: string
}

export type { CollectionTree }

export const useCollectionStore = defineStore('collection', () => {
  const collection = ref < CollectionTree | null > (null)

  return {
    collection,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useCollectionStore as any, import.meta.hot))
