import { defineStore } from 'pinia'

export const usePullingStore = defineStore('pulling', {
    state: () => {
        return {
            categories_is_article: false,
        }
    },
    actions: {
    }
})
