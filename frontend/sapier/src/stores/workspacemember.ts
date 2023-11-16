import { acceptHMRUpdate, defineStore } from 'pinia'

interface memberInfo {
  uuid: string
  nickname: string
  email: string
  profileImageUrl: string
}
interface memberInfoList {
  memberList: memberInfo[]
}

export type { memberInfoList }

export const useMemberStore = defineStore(
  'memberList', 
  {
    state : () => {
      const memberInfoList = ref < memberInfoList | null > (null)

      return {
        memberInfoList,
      }
    },
    persist : true
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useMemberStore as any, import.meta.hot))
