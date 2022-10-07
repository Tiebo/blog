<template>
  <div v-for="data of articleData" :key="data.article.id" class="card">
    <div class="card-body">
      <div class="title" style="margin-bottom: 1vh">
        <span @click="router_to_body(data.article.id)">{{ data.article.title
        }}</span>
        <span class="update" v-show="data.article.authorId === useUserStore().id"
          @click="router_to_modify_article(data.article.id)">
          <i class="bi bi-brush"></i>
        </span>
      </div>
      <div class="description" style="margin-bottom: 1vh">
        <span v-show="data.article.weight === 1" class="weight">置顶</span>
        &nbsp;&nbsp;&nbsp;&nbsp;{{ data.article.description }}
      </div>
      <div class="intro">
        <span style="float: left">
          <span>
            <i class="bi bi-person-fill">{{ data.username }} </i>
          </span>
          &shy;
          <span>
            <i class="bi bi-calendar-day-fill">
              {{ data.article.createDate.substring(0, 10) }}
            </i>
          </span>
          &shy;
          <span>
            <i class="bi bi-tags-fill">
              {{ data.tags }}
            </i>
          </span>
        </span>
        <span @click="router_to_body(data.article.id)" class="read-article">
          阅读全文
          <i class="bi bi-arrow-right"></i>
        </span>
      </div>
    </div>

  </div>
  <div v-show="current_page < article_counts" style="text-align: center;">
    <button class="transition" @click="refresh_page()">
      更早的文章
    </button>
  </div>
</template>

<script lang="ts" setup>
  import { useApiStore } from "@/stores/api";
  import { useUserStore } from "@/stores/user";
  import type { articleData } from '@/types'
  import { onMounted, ref, watch } from "vue";
  import { useRouter } from "vue-router";

  const $api = useApiStore();
  const router = useRouter();
  const full_path = () => router.currentRoute.value.fullPath
  const path_id = router.currentRoute.value.params.id;

  let articleData = ref<articleData[]>([]);
  let current_page = ref(10);
  let article_counts = ref(0);

  onMounted(() => {
    judge_view();
  })
  watch(full_path, () => {
    judge_view();
  })
  const judge_view = () => {
    if (full_path().includes('categories')) {
      get_articles_categories();
    } else if (full_path().includes('tags')) {
      get_articles_tags();
    } else {
      get_articles_list();
    }
  }

  const get_articles_tags = () => {
    $api.apiTags.getArticlesByTags({
      page: 1,
      pageSize: 10,
      tags: path_id,
    }).then(resp => {
      articleData.value = resp.data.resData;
    })
  }
  const get_articles_categories = () => {
    $api.apiCategories.getArticlesByCategories({
      page: 1,
      pageSize: current_page.value,
      categories: path_id,
    }).then(resp => {
      articleData.value = resp.data.resData;
    })
  }
  const router_to_body = (id: string) => {
    router.push({
      name: 'articles_body_index',
      params: {
        article_id: id
      }
    })
  }
  const get_articles_list = () => {
    $api.apiArticles.getArticlesList({
      page: 1,
      pageSize: current_page.value,
    }).then(resp => {
      article_counts.value = resp.data.article_counts;
      articleData.value = resp.data.resData;
    });
  }
  const refresh_page = () => {
    current_page.value += 5;
    judge_view();
  }
  const router_to_modify_article = (id: string) => {
    router.push({
      name: "update_content_index",
      params: {
        id: id,
      }
    })
  }

</script>

<style scoped>

  .read-article {
    font-size: 16px;
    font-weight: 800;
    color: rgb(35, 35, 245);
    float: right;
    cursor: pointer;
    transition: 200ms;
    text-decoration: none;
  }

  .read-article:hover {
    color: #ce1d1d;
    transition: 300ms;
  }


  .title {
    cursor: pointer;
    font-size: 26px;
    font-weight: 600;
    text-align: center;
  }

  .update {
    float: right;
    cursor: pointer;
  }

  .weight {
    width: 200px;
    color: #d91e40;
    font-weight: 600;
    border: solid;
  }

  .intro {
    cursor: default;
    font-size: 14px;
    color: #726e6e;
  }

  .description {
    font-size: 18px;
    color: #353535;
    font-weight: 550;
  }

  .transition {
    background: linear-gradient(145deg, #f0eeee, #cbc9c9);
    opacity: 0.8;
    height: 60px;
    width: 180px;
    border-radius: 50px;
    font-weight: 600;
    color: #726e6e;
    transition: all 300ms;
  }

  .transition:hover {
    background: linear-gradient(145deg, #e5ef8f, #d0ed65);
    color: cadetblue;
  }
</style>