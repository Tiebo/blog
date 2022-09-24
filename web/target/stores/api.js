import { defineStore } from 'pinia';
import api from '../api/index';
export const useApiStore = defineStore('api', {
    state: () => {
        return {
            apiArticles: api.articles,
            apiTags: api.tags,
            apiUser: api.user,
            apiCategories: api.categories
        };
    }
});
