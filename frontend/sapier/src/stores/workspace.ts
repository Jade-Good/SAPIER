import { acceptHMRUpdate, defineStore } from 'pinia'

import type { CollectionTree } from './collection'
import type { UserInfo } from './user'

interface pinnedList {
  id: string
  name: string
}

interface WorkspaceInfo {
  key: string
  name: string
  collectionList: CollectionTree[]
  memberList: UserInfo[]
  modifiedTime: string
  collectionId: string[]
  color: string
  pinnedList: pinnedList[]
  admin: string
}

export type { WorkspaceInfo }

export const useWorkspaceStore = defineStore('workspace', () => {
  const workspaceInfo = ref < WorkspaceInfo | null > (null)
  const selectedWorkspaceIndex = ref<number | null>(0)

  return {
    workspaceInfo,
    updateWorkspaceName(newName: string) {
      if (this.workspaceInfo)
        this.workspaceInfo.name = newName
    },
    selectedWorkspaceIndex,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useWorkspaceStore as any, import.meta.hot))
