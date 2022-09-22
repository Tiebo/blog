<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <div class="card">
          <div class="card-body">
            文章目录
          </div>
        </div>
      </div>
      <div class="col-9">
        <div class="card">
          <div class="card-body" id="content">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { ref, onUnmounted } from 'vue';
import { marked } from 'marked';
import {useArticlesStore} from "@/stores/articles";
let article_body = ref('');

async function get_article_body(id: string) {
   await axios.request({
    url: "http://127.0.0.1:4000/articles/info/body/",
    method: "GET",
    params: {
      body_id: id,
    }
  }).then(resp => {
    article_body.value = resp.data.data.article.articleBody;
    document.getElementById('content')!.innerHTML =
        marked.parse(resp.data.data.article.articleBody);
  })
}

console.log(useArticlesStore().id)
get_article_body(useArticlesStore().id);
onUnmounted(() => {
  useArticlesStore().id = "";
})
</script>

<style scoped>

.card {
  margin-bottom: 2vh;
  border-radius: 20px;
  background: linear-gradient(145deg, #e3e1e1, #cbc9c9);
  font-weight: bold;
  font-size: 24px;
  box-shadow: 5px 5px 5px #857e7e;
}
</style>