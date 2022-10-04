<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft />
      </div>
      <div class="col-9">

        <div v-for="data of articleData" :key="data.article.id" class="card">
          <div class="card-body">
            <div class="title" style="margin-bottom: 1vh">
              <span @click="router_to_body(data.article.id)" class="title">{{
              data.article.title
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
                <span>
                  <i class="bi bi-person-fill">{{ data.username }} </i>
                </span>
                &shy;
                <span>
                  <i class="bi bi-calendar-day-fill">
                    {{ data.article.createDate.substring(0, 10) }}
                  </i>
                </span>
                &shy;
                <span>
                  <i class="bi bi-folder-fill">
                    {{ data.categories }}
                  </i>
                </span>
              </span>
              <span @click="router_to_body(data.article.id)" class="read-article">
                阅读全文
                <i class="bi bi-arrow-right"></i>
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import UserCardLeft from '@/components/UserCardLeft.vue'
  import { useApiStore } from "@/stores/api";
  import { useUserStore } from "@/stores/user";
  import { ref, watch } from "vue";
  import { useRouter } from "vue-router";

  const $api = useApiStore();
  const router = useRouter();
  const path_id = () => router.currentRoute.value.params.id;

  watch(path_id, () => {
    get_articles_tags();
  })
  const get_articles_tags = () => {
    $api.apiTags.getArticlesByTags({
      page: 1,
      pageSize: 10,
      tags: path_id(),
    }).then(resp => {
      articleData.value = resp.data.resData;
    })
  }
  get_articles_tags();

  let articleData = ref<resp_type>([]);

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

  .intro {
    cursor: default;
    font-size: 14px;
    color: #726e6e;
  }
</style>