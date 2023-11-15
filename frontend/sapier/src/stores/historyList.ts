import { acceptHMRUpdate, defineStore } from "pinia"
import type { HistoryInfo } from './history'

interface HistoryList{
    historyList: HistoryInfo[]
}

export type { HistoryList }

export const useHistoryListStore = defineStore('historyList', ()=>{
    const HistoryList = ref < HistoryList | null > (null)

    return {
        HistoryList,
    }
})

if(import.meta.hot)
    import.meta.hot.accept(acceptHMRUpdate(useHistoryListStore as any, import.meta.hot))