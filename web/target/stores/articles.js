import { defineStore } from 'pinia';
import { useApiStore } from '@/stores/api';
export const useArticlesStore = defineStore('articles', {
    state: () => {
        return {
            author: new Map
        };
    },
    actions: {}
});
