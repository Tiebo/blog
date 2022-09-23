<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft />
      </div>
      <div class="col-9">
        <div class="card">
          <div class="card-body markdown">
            <h1>{{ article.title }}</h1>
            <span>{{ article.viewCounts }}</span>
            <hr>
            <div id="content"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { marked } from "marked";
import { ref, type Ref } from 'vue';
import { useApiStore } from "@/stores/api";
import { useRouter } from "vue-router";
import UserCardLeft from '@/components/UserCardLeft.vue'
interface resp_type {
  [key: string]: any;
}
const router = useRouter();

const path_id = router.currentRoute.value.params.article_id as string;

let article_body = ref('');
let article: Ref<resp_type> = ref([]);

const get_article_info = () => {
  useApiStore().apiArticles.getArticleById({
    id: path_id
  }).then(resp => {
    const data: resp_type = resp.data;

    article_body.value = data.article_body;
    article.value = data.article;

    document.getElementById('content')!.innerHTML =
      marked.parse(article_body.value);

  })
}
get_article_info();

</script>

<style scoped>
.markdown>h1 {
  text-align: center;
  font-size: 48px;
  margin-bottom: 2vh;
}

.card {
  margin-bottom: 2vh;
  border-radius: 20px;
  background: linear-gradient(145deg, #e3e1e1, #cbc9c9);
  font-weight: bold;
  font-size: 24px;
  box-shadow: 5px 5px 5px #857e7e;
}
</style>