<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <div class="container">
          <div class="card">
            <div class="card-body">
              <h1>文章目录</h1>
              <div id="toc" v-loading="pulling_info">
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-9">
        <div class="card">
          <div class="card-body markdown" v-loading="pulling_info"
            element-loading-background="rgba(184, 189, 214, 0.75)" element-loading-text="请稍等哦~">
            <h1>{{ article.title }}</h1>
            <span class="label">
              <i class="bi bi-person-fill">
                {{ article_author }}
              </i>
              &shy;
              <i class="bi bi-folder-fill">
                {{ article_categories }}
              </i>
              &shy;
              <i class="bi bi-calendar-day-fill">
                {{ article.createDate }}
              </i>
              &shy;
              <i class="bi bi-eye-fill">
                {{ article.viewCounts }} 次浏览
              </i>

            </span>

            <hr>
            <Markdown v-if="article_body" :source="article_body" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import '@/assets/css/github.css'
import Markdown from "@/components/Markdown.vue";
import { useApiStore } from "@/stores/api";
import { onUnmounted, ref, type Ref } from 'vue';
import { useRouter } from "vue-router";

interface resp_type {
  [key: string]: any;
}
const router = useRouter();

const path_id = router.currentRoute.value.params.article_id as string;

let article_body = ref('');
let article_toc: any = ref('');
let article_categories = ref('');
let article_author = ref('');
let article: Ref<resp_type> = ref([]);
let pulling_info = ref(true);

const get_article_info = () => {
  useApiStore().apiArticles.getArticleById({
    id: path_id
  }).then(async resp => {
    const data: resp_type = resp.data;

    article.value = data.article;
    article_body.value = data.article_body;
    article_categories.value = data.article_categories;
    article_author.value = data.article_author;
    pulling_info.value = false;
  })
}
get_article_info();

onUnmounted(() => {
  article_body.value = "";
  article_toc.value = "";
})
</script>

<style scoped>
.markdown>h1 {
  font-size: 36px;
  margin-bottom: 2vh;
}

.markdown>.label {
  font-size: 15px;
  font-weight: 500;
  color: #5d5858
}

.card {
  margin-bottom: 2vh;
  border-radius: 20px;
  background: linear-gradient(145deg, #e3e1e1, #cbc9c9);
  font-weight: bold;
  font-size: 20px;
  box-shadow: 5px 5px 5px #857e7e;
  opacity: 0.85;
}
</style>