import TagsViewVue from '@/views/TagsView.vue';
import UserLoginView from '@/views/UserLoginView.vue';
import { createRouter, createWebHistory } from 'vue-router';
import homeView from '../views/HomeView.vue';
import NotFound from '../views/NotFound.vue'
import CategoriesView from '@/views/CategoriesView.vue'
import { useUserStore } from "@/stores/user";
import ArticlesView from '@/views/ArticlesView.vue'
import ArticleBodyView from '@/views/ArticleBodyView.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home_index',
            component: homeView,
        },
        {
            path: '/articles/',
            name: 'article_index',
            component: ArticlesView,
        },
        {
            path: '/articles/:article_id/',
            name: 'articles_body_index',
            component: ArticleBodyView,

        },
        {
            path: '/tags/',
            name: 'tags_index',
            component: TagsViewVue,
            meta: {
                requestAuth: true,
            }
        },
        {
            path: '/categories/',
            name: 'categories_index',
            component: CategoriesView,
            meta: {
                requestAuth: true,
            }
        },
        {
            path: '/login/',
            name: 'login_index',
            component: UserLoginView,
        },
        {
            path: '/404/',
            name: "404",
            component: NotFound,
        },
        {
            path: "/:catchAll(.*)",
            redirect: "/404/"
        }
    ]
});

router.beforeEach((to, from, next) => {
    if (to.meta.requestAuth && !useUserStore().is_login) {
        next({ name: "login_index" });
    } else {
        next();
    }
})

export default router;
