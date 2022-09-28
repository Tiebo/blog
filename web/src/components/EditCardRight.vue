<template>
  <div class="card">
    <div v-loading="!is_submit_finish || !is_pulling_finish" element-loading-text="稍等哦~" class="card-body">
      <form @submit.prevent="" class="row g-3">

        <div class="col-md-3">
          <label for="article_title" class="form-label">分类</label>
          <br>
          <el-select v-model="article_info.categories" style="width: 100%;" placeholder="Select" size="large">
            <el-option
                v-for="category in select_categories"
                :key="category.id"
                :label="category.categoriesName"
                :value="category.id"
            />
          </el-select>
        </div>
        <div class="col-md-6" style="text-align: center">
          <label for="article_title" class="form-label">文章标题</label>
          <input v-model="article_info.title"
                 type="text"
                 class="form-control"
                 id="article_title"
                 placeholder="文章标题">
        </div>
        <div class="col-md-3">
          <label for="article_title" class="form-label">标签</label>
          <br>
          <el-select v-model="article_info.tags" style="width: 100%;" placeholder="Select" size="large">
            <el-option
                v-for="tag in select_tags"
                :key="tag.id"
                :label="tag.tagName"
                :value="tag.id"
            />
          </el-select>
        </div>
        <div class="col-12">
          <label for="description" class="form-label">描述</label>
          <textarea v-model="article_info.description" type="text" class="form-control" id="description"
                    placeholder="描述" rows="3"></textarea>
        </div>

        <div style="text-align: right;" class="col-12">
          <span style="margin-right: 40%;">文章内容</span>
          <label for="article_body" class="form-label">
                  <span>
                    <el-switch class="el-switch" size="large" width="60px" v-model="value" inline-prompt
                               active-text="查看"
                               active-value="preview"
                               inactive-text="编辑"
                               inactive-value="edit"
                               inactive-color="gray"/>
                  </span>
          </label>
          <div style="text-align: left;">
            <v-md-editor
                v-model="article_info.body"
                height="600px"
                :mode="value"
                right-toolbar="toc"
            ></v-md-editor>
          </div>
        </div>
        <div class="col-12" style="text-align: right;">
          <el-button type="primary" @click="type_article">提交</el-button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useApiStore } from '@/stores/api';
import { useUserStore } from "@/stores/user";
import type { categories, tag } from "@/types";
import { ElMessage } from 'element-plus';
import { onMounted, reactive, ref, type Ref, watch } from 'vue';
import { useRouter } from 'vue-router';

const $api = useApiStore();
const router = useRouter();

let value = ref('edit');
// true 表示提交页面， false表示更新页面
let type_components = ref('true');
let select_tags: Ref<tag[]> = ref([]);
let select_categories: Ref<categories[]> = ref([]);
// 默认为true 已经提交完成
let is_submit_finish = ref(true);
// 默认为false 还未拉取成功
let is_pulling_finish = ref(false)

let article_info = reactive({
  id: "",
  title: "",
  body: "",
  description: "",
  categories: "",
  tags: "",
})
const path_id = router.currentRoute.value.params.article_id as string;
const full_path = router.currentRoute.value.fullPath;

onMounted(() => {

  if (full_path.includes('post')) {
    type_components.value = true;
    is_pulling_finish.value = true;
  } else if (full_path.includes('update')) {
    get_article_info(path_id);
    type_components.value = false;
  }
})

watch(() => router.currentRoute.value.fullPath, () => {
  window.location.replace(router.currentRoute.value.fullPath);
})

$api.apiTags.getHottestTag({}).then(resp => {
  select_tags.value = resp.data;
})

$api.apiCategories.getCategoriesList({}).then(resp => {
  select_categories.value = resp.data.categories;
})

const type_article = () => {
  if (type_components.value) {
    post_article();
  } else {
    update_article();
  }
}

const update_article = () => {
  is_submit_finish.value = false;
  get_animation();
  $api.apiArticles.updateArticle({
    id: article_info.id,
    body: article_info.body,
    tags: article_info.tags,
    categories: article_info.categories,
    title: article_info.title,
    description: article_info.description,
    token: useUserStore().token,
  }).then(resp => {
    console.log(resp)
  })
}
const get_article_info = async (id: number) => {
  await $api.apiArticles.getArticleById({
    id: id,
  }).then(resp => {
    article_info.id = resp.data.article.id;
    article_info.title = resp.data.article.title;
    article_info.body = resp.data.article_body;
    article_info.tags = resp.data.article_tags;
    article_info.categories = resp.data.article_categories;
    article_info.description = resp.data.article.description;
    is_pulling_finish.value = true;
    console.log("article_info.tags: ", article_info.tags);
  })
}

const post_article = () => {
  get_animation();
  $api.apiArticles.postArticle({
    id: path_id,
    title: article_info.title,
    description: article_info.description,
    tags: article_info.tags,
    categories: article_info.categories,
    article_body: article_info.body,
    token: useUserStore().token,
  }).then(resp => {
    console.log(resp.data)
  })
}
// 动画
const get_animation = () => {
  is_submit_finish.value = false;
  setTimeout(() => {
    get_message();
    is_submit_finish.value = true;
  }, 300);
}
// 弹出信息
const get_message = () => {
  ElMessage({
    message: 'Congrats, this is a success message.',
    type: 'success',
    offset: 250,
    duration: 1000
  })
}

</script>

<style scoped>
form > * {
  font-size: 20px;
  font-weight: 500;
}

.card {
  margin-bottom: 2vh;
  border-radius: 20px;
  background: linear-gradient(145deg, #e3e1e1, #cbc9c9);
  box-shadow: 5px 5px 5px #857e7e;
  opacity: 0.85;
}
</style>