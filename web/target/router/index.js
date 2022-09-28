import {useUserStore} from "@/stores/user";
import ArticleBodyView from '@/views/ArticleBodyView.vue';
import ArticlesView from '@/views/ArticlesView.vue';
import CategoriesView from '@/views/CategoriesView.vue';
import TagsViewVue from '@/views/TagsView.vue';
import UserLoginView from '@/views/UserLoginView.vue';
import {createRouter, createWebHistory} from 'vue-router';
import CategoriesArticlesView from '../views/CategoriesArticles.vue';
import homeView from '../views/HomeView.vue';
import ListView from '../views/ListView.vue';
import NotFound from '../views/NotFound.vue';
import PostArticleView from '../views/PostArticleView.vue';
import TagsArticlesView from "@/views/TagsArticlesView.vue";

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
        },
        {
            path: '/tags/:id/',
            name: 'tags_articles_index',
            component: TagsArticlesView,
        },
        {
            path: '/categories/',
            name: 'categories_index',
            component: CategoriesView,
        },
        {
            path: '/categories/:id/',
            name: 'categories_articles_index',
            component: CategoriesArticlesView,
        },
        {
            path: '/content/:article_id/',
            name: 'update_content_index',
            component: UpdateArticleView,
            meta: {
                requestAuth: true,
            }
        },
        {
            path: '/content/post/:userId/',
            name: 'post_content_index',
            component: PostArticleView,
            meta: {
                requestAuth: true,
            }
        },
        {
            path: '/list/',
            name: 'list_index',
            component: ListView,
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
        next({ name: "home_index" });
    }
    else {
        next();
    }
});
export default router;
