<template>
  <div class="card">
    <div v-loading="!is_submit_finish || !is_pulling_finish" element-loading-text="稍等哦~" class="card-body">
      <form @submit.prevent="" class="row g-3">
        <div class="col-md-12">
          <label for="article_title" class="form-label">文章标题</label>
          <input v-model="article_info.title" type="text" class="form-control" id="article_title" placeholder="文章标题">
        </div>
        <div class="col-12">
          <span>文章内容</span>
          <span style="float: right;">
            <label for="article_body" class="form-label">
              <span>
                <el-switch class="el-switch" size="large" width="60px" v-model="value" inline-prompt active-text="查看"
                  active-value="preview" inactive-text="编辑" inactive-value="edit" inactive-color="gray" />
              </span>
            </label>
          </span>
          <div style="text-align: left;">
            <v-md-editor v-model="article_info.body" height="600px" :mode="value" right-toolbar="toc"></v-md-editor>
          </div>
        </div>
        <div class="col-12" style="text-align: right;">
          <!-- Button trigger modal -->
          <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
            提交
          </button>
          <!-- Modal -->
        </div>
      </form>
    </div>
  </div>
  <div class="modal fade" id="exampleModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">提交文章</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <el-form :model="article_info" label-width="120px">
            <el-form-item label="摘要">
              <el-input v-model="article_info.description" type="textarea" />
            </el-form-item>
            <el-form-item label="文章标签">
              <div v-if="type_components">
                <el-checkbox-group v-model="article_info.tags">
                  <el-checkbox v-for="tag of all_tags" :key="tag.id" :label="tag.tagName" />
                </el-checkbox-group>
              </div>
              <div v-else>
                <el-tag v-for="tag in dynamicTags" :key="tag" class="mx-1" closable :disable-transitions="false"
                  @close="handleClose(tag, 'tags')">
                  {{ tag }}
                </el-tag>
                <el-select class="ml-1 w-20" placeholder="添加标签" size="small" style="width:88px">
                  <el-option v-for="tag in all_tags" @click="update_tags(tag.tagName,'tags')" :key="tag.id"
                    :label="tag.tagName" :value="tag.tagName" />
                </el-select>
              </div>
            </el-form-item>
            <el-form-item label="文章分类">
              <div v-if="type_components">
                <el-checkbox-group v-model="article_info.categories">
                  <el-checkbox v-for="category of all_categories" :key="category.id" :label="category.categoriesName" />
                </el-checkbox-group>
              </div>
              <div v-else>
                <el-tag v-for="categories in dynamicCate" :key="categories" class="mx-1" closable
                  :disable-transitions="false" @close="handleClose(categories, 'categories')">
                  {{ categories }}
                </el-tag>
                <el-select class="ml-1 w-20" placeholder="添加标签" size="small" style="width:88px">
                  <el-option v-for="categories in all_categories"
                    @click="update_tags(categories.categoriesName,'categories')" :key="categories.id"
                    :label="categories.categoriesName" :value="categories.categoriesName" />
                </el-select>
              </div>
            </el-form-item>
            <el-form-item label="文章类型">
              <el-radio-group v-model="article_info.type">
                <el-radio label="原创" />
                <el-radio label="转载" />
              </el-radio-group>
            </el-form-item>
            <div v-show="article_info.type === '转载'">
              <el-form-item label="原帖网址">
                <el-input v-model="article_info.article_http" />
              </el-form-item>
            </div>
          </el-form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
          <button type="button" @click="type_article" data-bs-dismiss="modal" class="btn btn-primary">确认</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useApiStore } from '@/stores/api';
  import { useUserStore } from "@/stores/user";
  import type { categories, tag } from "@/types";
  import { resp_message } from '@/utils/utils';
  import { ElInput } from 'element-plus';
  import { onMounted, reactive, ref, type Ref, watch } from 'vue';
  import { useRouter } from 'vue-router';
  // store router
  const $api = useApiStore();
  const userStore = useUserStore();
  const router = useRouter();
  // 常量
  const path_id = router.currentRoute.value.params.id as string;
  const full_path = router.currentRoute.value.fullPath;
  // 标签_分类
  const dynamicTags = ref<string[]>([])
  const dynamicCate = ref<string[]>([])
  const inputVisible = ref('')

  let value = ref('edit');
  // true 表示提交页面， false表示更新页面
  let type_components = ref(true);
  let all_tags: Ref<tag[]> = ref([]);
  let all_categories: Ref<categories[]> = ref([]);
  // 默认为true 已经提交完成
  let is_submit_finish = ref(true);
  // 默认为false 还未拉取成功
  let is_pulling_finish = ref(false)
  let article_info = reactive({
    id: "",
    title: "",
    body: "",
    description: "",
    categories: [] as categories[],
    tags: [] as tag[],
    type: "",
    article_http: "",
  })
  onMounted(() => {
    if (full_path.includes('post')) {
      type_components.value = true;
      is_pulling_finish.value = true;
    } else if (full_path.includes('update')) {
      refresh_info(path_id);
      type_components.value = false;
    }
  })

  watch(() => router.currentRoute.value.fullPath, () => {
    window.location.replace(router.currentRoute.value.fullPath);
  })



  const type_article = () => {
    if (type_components.value) {
      post_article();
    } else {
      update_article();
    }
  }

  // 添加标签
  const update_tags = (data: string, type: string) => {
    if (data) {
      if (type === "tags" && !dynamicTags.value.includes(data))
        dynamicTags.value.push(data);
      else if (type === "categories" && !dynamicCate.value.includes(data))
        dynamicCate.value.push(data);
    }
    inputVisible.value = ''
  }
  // 删除标签
  const handleClose = (data: string, type: string) => {
    if (type === "tags")
      dynamicTags.value.splice(dynamicTags.value.indexOf(data), 1)
    else if (type === "categories")
      dynamicCate.value.splice(dynamicCate.value.indexOf(data), 1)
  }
  const update_article = () => {
    is_submit_finish.value = false;
    get_animation();
    $api.apiArticles.updateArticle({
      id: article_info.id,
      body: article_info.body,
      tags: dynamicTags.value,
      categories: dynamicCate.value,
      title: article_info.title,
      description: article_info.description,
      token: userStore.token,
    }).then(resp => {
      resp_message(resp, "更新成功", () => { });
    })
  }
  const refresh_info = async (id: number | string) => {
    await $api.apiTags.getHottestTag({}).then(resp => {
      all_tags.value = resp.data;
    })

    await $api.apiCategories.getCategoriesList({}).then(resp => {
      all_categories.value = resp.data.categories;
    })

    await $api.apiArticles.getArticleById({
      id: id,
      token: userStore.token,
    }).then(resp => {
      article_info.id = resp.data.article.id;
      article_info.title = resp.data.article.title;
      article_info.body = resp.data.article_body;
      article_info.tags = resp.data.article_tags;
      article_info.categories = resp.data.article_categories;
      article_info.description = resp.data.article.description;
      is_pulling_finish.value = true;
      for (let tag of article_info.tags) {
        dynamicTags.value.push(tag.tagName);
      }
      for (let category of article_info.categories) {
        dynamicCate.value.push(category.categoriesName);
      }
    })
  }

  const post_article = () => {
    get_animation();
    console.log("🚀 -> path_id", path_id);

    $api.apiArticles.postArticle({
      id: path_id,

      title: article_info.title,
      description: article_info.description,
      tags: article_info.tags,
      categories: article_info.categories,
      article_body: article_info.body,
      token: useUserStore().token,
    }).then(resp => {
      resp_message(resp, "添加成功", () => { });
    })
  }
  // 动画
  const get_animation = () => {
    is_submit_finish.value = false;
    setTimeout(() => {
      is_submit_finish.value = true;
    }, 300);
  }

</script>

<style scoped>
  form>* {
    font-size: 20px;
    font-weight: 500;
  }

</style>