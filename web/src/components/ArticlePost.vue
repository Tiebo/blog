<template>
  <div v-for="data of articleData" :key="data.article.id" class="card">
    <div class="card-body">
      <div class="title" style="margin-bottom: 1vh">
        <span @click="router_to_body(data.article.id)" class="title">{{ data.article.title
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
          <span>作者:{{ data.username }}</span>
          &nbsp;
          <span>{{ data.article.modifyDate }}</span>
          &nbsp;
          <span>标签:{{ data.article.tagsId }}</span>
        </span>
        <span @click="router_to_body(data.article.id)" class="read-article">
          阅读全文
          <i class="bi bi-arrow-right"></i>
        </span>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { useApiStore } from "@/stores/api";
import { useUserStore } from "@/stores/user";
import type { articleData } from '@/types'
import { ref, type Ref } from "vue";
import { useRouter } from "vue-router";

const $api = useApiStore();
const router = useRouter();

let articleData: Ref<articleData[]> = ref([]);

const router_to_body = (id: string) => {

  router.push({
    name: 'articles_body_index',
    params: {
      article_id: id
    }
  })
}

interface resp_type {
  [key: string]: any;
}

$api.apiArticles.getArticlesList({
  page: 1,
  pageSize: 10,
}).then(data => {
    articleData.value = data.data.resData;
});

const router_to_modify_article = (id: string) => {
  router.push({
    name: "update_content_index",
    params: {
      article_id: id,
    }
  })
}

</script>

<style scoped>
.read-article {
  color: blue;
  float: right;
  cursor: pointer;
  transition: 300ms;
  text-decoration: none;
}

.read-article:hover {
  color: #ce1d1d;
  transition: 300ms;
}

.card {
  margin-bottom: 2vh;
  border-radius: 20px;
  background: linear-gradient(145deg, #e3e1e1, #cbc9c9);
  opacity: 0.75;
  box-shadow: 5px 5px 5px #857e7e;
}

.title {
  cursor: pointer;
  font-size: 24px;
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
</style>