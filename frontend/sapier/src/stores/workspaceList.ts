import { acceptHMRUpdate, defineStore } from 'pinia'

import type { workspace } from './workspace'

interface WorkspaceList {
  workspaceList: workspace[]
}

export type { WorkspaceList }

export const useWorkspaceListStore = defineStore('workspaceList', () => {
  const WorkspaceList = ref < WorkspaceList | null > (null)

  return {
    WorkspaceList,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useWorkspaceListStore as any, import.meta.hot))
