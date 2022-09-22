import TagsViewVue from '@/views/TagsView.vue';
import UserLoginView from '@/views/UserLoginView.vue';
import { createRouter, createWebHistory } from 'vue-router';
import homeView from '../views/HomeView.vue';
import NotFound from '../components/NotFound.vue';
import CategoriesView from '@/views/CategoriesView.vue';
import { useUserStore } from "@/stores/user";
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home_index',
            component: homeView,
        },
        {
            path: '/archives',
            name: 'archives_index',
            component: homeView,
            meta: {
                requestAuth: true,
            }
        },
        {
            path: '/tags',
            name: 'tags_index',
            component: TagsViewVue,
            meta: {
                requestAuth: true,
            }
        },
        {
            path: '/categories',
            name: 'categories_index',
            component: CategoriesView,
            meta: {
                requestAuth: true,
            }
        },
        {
            path: '/login',
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
    }
    else {
        next();
    }
});
export default router;
