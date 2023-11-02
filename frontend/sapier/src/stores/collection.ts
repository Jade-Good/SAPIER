import { acceptHMRUpdate, defineStore } from 'pinia'

interface CollectionInfo {
  collectionName: string
  apiList: string
  modifiedTime: string
}

export type { CollectionInfo }

export const useCollectionStore = defineStore('collection', () => {
  const collection = ref < CollectionInfo | null > (null)

  return {
    collection,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useCollectionStore as any, import.meta.hot))
