import { acceptHMRUpdate, defineStore } from 'pinia'

interface UserInfo {
  uuid: string
  nickname: string
  email: string
  socialId: string
  socialProvider: string
  profileImageUrl: string
  countOfApiRequest: number
  role: string
}

export const useUserStore = defineStore('user', () => {
  const user = ref < UserInfo | null > (null)

  return {
    user,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useUserStore as any, import.meta.hot))
