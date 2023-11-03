import { acceptHMRUpdate, defineStore } from 'pinia'

import type { CollectionInfo } from './collection'
import type { UserInfo } from './user'

interface WorkSpaceInfo {
  name: string
  collectionList: CollectionInfo[]
  memberList: UserInfo[]
  modifiedTime: string
}

export type { WorkSpaceInfo }

export const useWorkSpaceStore = defineStore('workspace', () => {
  const workspaceInfo = ref < WorkSpaceInfo | null > (null)

  return {
    workspaceInfo,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useWorkSpaceStore as any, import.meta.hot))
