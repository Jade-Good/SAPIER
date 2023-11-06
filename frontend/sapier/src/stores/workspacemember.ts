import { acceptHMRUpdate, defineStore } from 'pinia'

interface memberInfo {
  uuid: string
  nickname: string
  email: string
  profileImageUrl: string
}

export type { memberInfo }

export const useMemberStore = defineStore('member', () => {
  const memberInfo = ref < memberInfo | null > (null)

  return {
    memberInfo,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useMemberStore as any, import.meta.hot))
