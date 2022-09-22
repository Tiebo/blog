import { defineStore } from 'pinia'

export const useArticlesStore = defineStore('articles', {
    state: () => {
        return {
            id: "",
        }
    },
    actions: {}
})
