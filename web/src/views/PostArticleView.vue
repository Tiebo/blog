<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft/>
      </div>
      <div class="col-9">
        <div class="card">
          <div v-loading="!is_submit_finish || !is_pulling_finish" element-loading-text="稍等哦~" class="card-body">
            <form @submit.prevent="" class="row g-3">

              <div class="col-md-3">
                <label for="article_title" class="form-label">分类</label>
                <br>
                <el-select v-model="article_info.categories" style="width: 100%;" placeholder="Select" size="large" >
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
                <el-select v-model="article_info.tags" style="width: 100%;" placeholder="Select" size="large" >
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
                               active-text="查看" inactive-text="编辑" inactive-color="gray"/>
                  </span>
                </label>
                <textarea v-show='!value' v-model="article_info.body" class="form-control" id="article_body"
                          rows="15"></textarea>
                <el-scrollbar  v-if="value" max-height="80vh" native="native">
                  <Markdown  v-if="value" :source="article_info.body" class="article_body_markdown"/>
                </el-scrollbar>
              </div>
              <div class="col-12" style="text-align: right;">
                <el-button type="primary" @click="post_article">提交</el-button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup lang='ts'>
import Markdown from "@/components/Markdown.vue";
import UserCardLeft from '@/components/UserCardLeft.vue';
import { useApiStore } from '@/stores/api';
import { useUserStore } from "@/stores/user";
import { ElMessage } from 'element-plus';
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';

const $api = useApiStore();
const router = useRouter();
const path_id = router.currentRoute.value.params.article_id as string;

let value = ref(false);
let select_tags = ref('');
let select_categories = ref('');
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

$api.apiTags.getHottestTag({}).then(resp => {
  select_tags.value = resp.data;
})
$api.apiCategories.getCategoriesList({}).then(resp => {
  select_categories.value = resp.data.categories;
})


const get_animation = () => {
  is_submit_finish.value = false;

  setTimeout(() => {
    get_message();
    is_submit_finish.value = true;
  }, 300);
}

const post_article = async () => {
  is_submit_finish.value = false;
  await $api.apiArticles.postArticle({
    id: useUserStore().id,
    title: article_info.title,
    description: article_info.description,
    tags: article_info.tags,
    categories: article_info.categories,
    article_body: article_info.body,
    token: useUserStore().token,
  }).then(resp => {
    console.log(resp.data)
  })
  is_pulling_finish.value = true;
}

const get_message = () => {
  ElMessage({
    message: 'Congrats, this is a success message.',
    type: 'success',
    offset: 250,
    duration: 1000
  })
}
onMounted(() => {
  is_pulling_finish.value = true;
})

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

.article_body_markdown {
  padding: 20px;
  text-align: left;
  background-color: #cbc9c9;
  border-radius: 20px;
}

</style>