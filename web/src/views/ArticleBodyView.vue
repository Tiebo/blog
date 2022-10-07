<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <div class="container">
          <div class="card">
            <div class="card-body ">
              <div class="toc_title">
                <i class="bi bi-list-task"></i>
                文章目录
              </div>
              <hr>
              <div v-loading="pulling_info" class="anchor-tag" :style="{ 
              padding: `10px 0 10px ${anchor.indent * 20}px`, 
              }" v-for="anchor in state.titleData" :key="anchor" @click="handleAnchorClick(anchor)">
                {{ anchor.title }}
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-9">
        <div class="card">
          <div class="card-body markdown" v-loading="pulling_info"
            element-loading-background="rgba(184, 189, 214, 0.75)" element-loading-text="请稍等哦~">
            <span class="title">{{ article.title }}</span>
            <el-button v-if="article.authorId === userStore.id" type="danger" class="delete_article" :icon="Delete"
              circle @click="open" />
            <br>
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
            <v-md-preview v-if="article_body" :text="article_body" ref="preview"></v-md-preview>
            <footer class="body_footer">更新于：{{ article.modifyDate }}</footer>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useApiStore } from "@/stores/api";
  import { useUserStore } from '@/stores/user';
  import { Delete } from '@element-plus/icons-vue'
  import { ElMessage, ElMessageBox } from 'element-plus'
  import { onMounted, onUnmounted, reactive, ref, type Ref } from 'vue';
  import { useRouter } from "vue-router";

  interface resp_type {
    [key: string]: any;
  }

  const $api = useApiStore();
  const router = useRouter();
  const userStore = useUserStore();
  const path_id = router.currentRoute.value.params.article_id as string;
  const state: any = reactive({
    titleData: []
  })
  const preview = ref();

  let article_body = ref('');
  let article_toc: any = ref('');
  let article_categories = ref('');
  let article_author = ref('');
  let article: Ref<resp_type> = ref([]);
  let pulling_info = ref(true);
  const get_article_info = async () => {
    await useApiStore().apiArticles.getArticleById({
      id: path_id
    }).then(async resp => {
      const data: resp_type = resp.data;
      article.value = data.article;
      article_body.value = data.article_body;
      article_author.value = data.article_author;
      pulling_info.value = false;

      let categories_string = "";
      for (let o of data.article_categories) {
        categories_string = categories_string + "/" + o.categoriesName;
      }
      article_categories.value = categories_string.substring(1);
    })
  }

  function handleAnchorClick(anchor: any) {
    const { lineIndex } = anchor
    const heading = preview.value.$el.querySelector(`[data-v-md-line="${lineIndex}"]`)
    if (heading) {
      preview.value.scrollToTarget({
        target: heading,
        scrollContainer: window,
        top: 60
      })
    }
  }

  const open = () => {
    ElMessageBox.confirm(
      '你确定删除这篇文章吗?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    ).then(() => {
      ElMessage({
        type: 'success',
        message: '删除成功',
      })

      router.push({ name: "article_index" });
      $api.apiArticles.deleteArticle({
        body_id: article.value.id,
        token: userStore.token,
      }).then(resp => {
        console.log("delete ", resp);
      })
    }).catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
  }
  onMounted(async () => {
    await get_article_info();

    const anchors = preview.value.$el.querySelectorAll("h1,h2,h3");

    const title = Array.from(anchors).filter((title: any) => {
      return !!title.innerText.trim()
    })
    if (!title.length) {
      state.titleData = []
      return
    }
    const hTags = Array.from(
      new Set(
        title.map((title: any) => {
          return title.tagName
        })
      )
    ).sort()
    state.titleData = title.map((el: any) => {
      return {
        title: el.innerText,
        lineIndex: el.getAttribute('data-v-md-line'),
        indent: hTags.indexOf(el.tagName)
      }
    })
  })
  onUnmounted(() => {
    article_body.value = "";
    article_toc.value = "";
  })
</script>

<style scoped>

  .markdown>.title {
    font-size: 30px;
    margin-bottom: 2vh;
    margin-left: 3vh;
  }

  .markdown>.label {
    font-size: 15px;
    margin-left: 3vh;
    font-weight: 500;
    color: #5d5858
  }

  .delete_article {
    float: right;
    background-color: rgb(226, 81, 81);
  }

  .card {
    font-weight: bold;
    font-size: 20px;
  }

  .body_footer {
    color: #5d5858;
    text-align: left;
    font-size: medium;
  }

  .toc_title {
    font-size: 20px;
  }

  .anchor-tag {
    color: #2c2b2b;
    font-size: 16px;
    font-weight: 800;
    cursor: pointer;
    transition: all 300ms;
  }

  .anchor-tag:hover {
    background-color: rgba(202, 198, 198, 0.8);
    color: rgb(112, 197, 191);
  }

  .toc {
    overflow-y: scroll;
  }
</style>