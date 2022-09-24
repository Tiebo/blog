<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft />
      </div>
      <div class="col-9">
        <div class="card">
          <div v-loading="!is_submit_finish || !is_pulling_finish" element-loading-text="稍等哦~" class="card-body">
            <form @submit.prevent="update_article" class="row g-3">
              <div class="col-md-6">
                <label for="article_title" class="form-label">文章标题</label>
                <input v-model="article_info.title" type="text" class="form-control" id="article_title"
                  :placeholder="article_info.title">
              </div>
              <div class="col-md-3">
                <label for="categories" class="form-label">分类</label>
                <select id="inputState" class="form-select">
                  <option selected>Choose...</option>
                  <option>...</option>
                </select>
              </div>
              <div class="col-md-3">
                <label for="inputState" class="form-label">标签</label>
                <select id="inputState" class="form-select">
                  <option selected>Choose...</option>
                  <option>...</option>
                </select>
              </div>
              <div class="col-12">
                <label for="description" class="form-label">描述</label>
                <input v-model="article_info.description" type="text" class="form-control" id="description"
                  placeholder="description">
              </div>

              <div style="text-align: right;" class="col-12">

                <span style="margin-right: 40%;">文章内容</span>
                <label for="article_body" class="form-label">
                  <span>
                    <el-switch class="el-switch" size="large" width="60px" v-model="value" inline-prompt
                      active-text="查看" inactive-text="编辑" inactive-color="gray" />
                  </span>
                </label>
                <textarea v-show='!value' v-model="article_info.body" class="form-control" id="article_body"
                  rows="15"></textarea>

                <div v-show="value" id="article_body_markdown" class="article_body_markdown" rows="15">
                </div>
              </div>
              <div class="col-12" style="text-align: right;">
                <el-button :plain="true" @click="get_markdown" type="primary">
                  保存
                </el-button>
                <el-button :plain="true" @click="update_article" type="primary">
                  提交
                </el-button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup lang = 'ts'>
import UserCardLeft from '@/components/UserCardLeft.vue';
import { useApiStore } from '@/stores/api';
import { useRouter } from 'vue-router';
import { reactive, ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { ElMessage } from 'element-plus';
import { marked } from 'marked';

const $api = useApiStore();
const router = useRouter();
const value = ref(false)
const path_id = router.currentRoute.value.params.article_id as string;

let is_submit_finish = ref(true);
let is_pulling_finish = ref(false)
let article_info = reactive({
  id: "",
  title: "",
  body: "",
  body_markdown: "",
  description: "",
  categories: "",
  tags: {},
})

const get_markdown = () => {
  is_submit_finish.value = false;

  setTimeout(() => {
    get_message();
    is_submit_finish.value = true;
  }, 400);

  article_info.body_markdown = marked.parse(article_info.body);
  document.getElementById("article_body_markdown")!.innerHTML =
    article_info.body_markdown;
}

const get_article_info = () => {
  $api.apiArticles.getArticleById({
    id: path_id,
  }).then(resp => {
    article_info.id = resp.data.article.id;
    article_info.title = resp.data.article.title;
    article_info.body = resp.data.article_body;
    article_info.body_markdown = marked.parse(article_info.body);
    article_info.tags = resp.data.article_tags;
    article_info.categories = resp.data.article_categories;
    article_info.description = resp.data.article.description;
    document.getElementById("article_body_markdown")!.innerHTML =
      article_info.body_markdown;
    is_pulling_finish.value = true;
  })
}

get_article_info();


const update_article = async () => {
  is_submit_finish.value = false;
  let timestamp = new Date().getTime();
  await $api.apiArticles.updateArticle({
    id: article_info.id,
    body: article_info.body,
    tags: article_info.tags,
    categories: article_info.categories,
    title: article_info.title,
    description: article_info.description,
    token: useUserStore().token,
  }).then(resp => {
    if (resp.data.error_message === "success") {
      setTimeout(() => {
        is_submit_finish.value = true;
        get_message();
      }, 350 - (new Date().getTime()) + timestamp);
    }
  })
}
const get_message = () => {
  ElMessage({
    message: 'Congrats, this is a success message.',
    type: 'success',
    offset: 200,
    duration: 1000
  })
}


</script>


<style scoped>
form>* {
  font-size: 20px;
  font-weight: 500;
}

.card {
  margin-bottom: 2vh;
  border-radius: 20px;
  background: linear-gradient(145deg, #e3e1e1, #cbc9c9);
  box-shadow: 5px 5px 5px #857e7e;
  opacity: 0.75;
}

.article_body_markdown {
  padding: 20px;
  text-align: left;
  background-color: #cbc9c9;
  border-radius: 20px;
}
</style>