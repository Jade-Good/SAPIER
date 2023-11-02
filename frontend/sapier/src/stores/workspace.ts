import { acceptHMRUpdate, defineStore } from 'pinia'

interface WorkSpaceInfo {
  name: string
  collectionList: string
  memberList: string
  modifiedTime: string
}

export const useWorkspaceStore = defineStore('workspace', () => {
  const workSpaceInfo = ref < WorkSpaceInfo | null > (null)

  return {
    workSpaceInfo,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useWorkspaceStore as any, import.meta.hot))
