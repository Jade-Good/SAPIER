import { acceptHMRUpdate, defineStore } from 'pinia'

import type { CollectionInfo } from './collection'
import type { UserInfo } from './user'

interface WorkspaceInfo {
  name: string
  collectionList: CollectionInfo[]
  memberList: UserInfo[]
  modifiedTime: string
}

export type { WorkspaceInfo }

export const useWorkspaceStore = defineStore('workspace', () => {
  const workspaceInfo = ref < WorkspaceInfo | null > (null)

  return {
    workspaceInfo,
    updateWorkspaceName(newName: string) {
      if (this.workspaceInfo)
        this.workspaceInfo.name = newName
    },
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useWorkspaceStore as any, import.meta.hot))
