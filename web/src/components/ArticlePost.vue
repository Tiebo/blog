<template>
  <div v-for="article of articles" :key="article.id" class="card">
    <div class="card-body">
      <div class="title" style="margin-bottom: 1vh">{{ article.title }}</div>
      <div class="description" style="margin-bottom: 1vh">{{ article.description }}</div>
      <div class="intro">
        <span style="float: left">
          <span>作者:{{ article.authorId }}</span>
          &nbsp;
          <span>{{ article.createDate }}</span>
          &nbsp;
          <span>标签:{{ article.tagsId }}</span>
        </span>
        <router-link :to="{name: 'articles_body_index', 
        params: {
          article_id: article.id
        }}" class="read-article">
          阅读全文
          <i class="bi bi-arrow-right"></i>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { useApiStore } from "@/stores/api";
import { ref } from "vue";

const $api = useApiStore();
let articles: any = ref([]);

$api.apiArticles.getArticlesList({
  page: 1,
  pageSize: 10,
}).then(data => {
  articles.value = data.data.articles;
});
</script>

<style scoped>
.read-article {
  color: blue;
  float: right;
  cursor: pointer;
  transition: 300ms;
}

.read-article:hover {
  color: #ce1d1d;
  transition: 300ms;
}

.card {
  margin-bottom: 2vh;
  text-align: center;
  border-radius: 20px;
  background: linear-gradient(145deg, #e3e1e1, #cbc9c9);

  box-shadow: 5px 5px 5px #857e7e;
}

.title {
  font-size: 24px;
}
</style>