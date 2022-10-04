<template>
  <div class="container">
    <div class="card">
      <div class="card-body">
        <img v-show="qq_photo !== ''" class="photo" id="user_photo" :src="qq_photo">
        <img v-show="qq_photo === ''" class="photo" id="user_photo"
          src="https://q3.qlogo.cn/headimg_dl?dst_uin=1459847864&spec=640">
        <div class="username">{{ qq_name }}</div>
        <div class="row" style="margin-top: 1vh">
          <div class="col-3">
            <i class="bi bi-house-fill"></i>
          </div>
          <div class="col-3">
            <i class="bi bi-facebook"></i>
          </div>
          <div class="col-3">
            <i class="bi bi-wechat"></i>
          </div>
          <div class="col-3">
            <i class="bi bi-github"></i>
          </div>
        </div>
      </div>
    </div>
    <div class="card">
      <div class="card-body">
        <span class="label-link">
          <i class="bi bi-arrow-right-square" />&nbsp;快速访问
        </span>
      </div>
      <div class="card-body" style="padding-top: 0">
        <div class="row">
          <div class="col-3">
            <router-link class="quick-link" :to="{name: 'home_index'}">
              主页
            </router-link>
          </div>
          <div class="col-4">
            <a class="quick-link" target="_blank" href="https://blog.csdn.net/cTiebo?spm=1011.2415.3001.5343">
              CSDN
            </a>
          </div>
          <div class="col-5">
            <a class="quick-link" target="_blank" href="https://github.com/Tiebo?tab=repositories">Github</a>
          </div>
        </div>
        <div class="row" style="margin-top: 1vh">
          <div class="col-3">
            <a class="quick-link" target="_blank" href="https://www.zhihu.com/hot">
              知乎
            </a>
          </div>
          <div class="col-4">
            <a class="quick-link" target="_blank" href="https://www.acwing.com/user/myspace/index/184248/">
              Acwing
            </a>
          </div>
          <div class="col-5">
            <a class="quick-link" target="_blank" href="https://www.bilibili.com/">
              Bilibili
            </a>
          </div>
        </div>
      </div>
    </div>
    <div class="card">
      <div class="card-body">
        <span class="label-link">
          <i class="bi bi-archive-fill" />&nbsp;
          文章分类
        </span>
      </div>
      <div class="card-body" style="padding-top: 0">
        <div v-for="category of categories" @click="router_to_index(category.id, 'categories')" :key="category.id"
          class="row classification">
          <div class="col-6" style="text-align: left">
            {{ category.categoriesName }}
          </div>
          <div class="col-6" style="text-align: right">
            ( {{ category.articleCounts }} )
          </div>
        </div>
      </div>
    </div>
    <div class="card">
      <div class="card-body">
        <span class="label-link">
          <i class="bi bi-archive-fill" />&nbsp;
          热门标签
        </span>
      </div>
      <div class="card-body" style="padding-top: 0">
        <div v-for="tag of tags" @click="router_to_index(tag.id, 'tags')" :key="tag.id" class="row classification">
          <div class="col-6" style="text-align: left">
            {{ tag.tagName }}
          </div>
          <div class="col-6" style="text-align: right">
            ( {{ tag.articleCounts }} )
          </div>
        </div>
      </div>
    </div>


    <div class="card">
      <div class="card-body">
        <span class="label-link">
          <i class="bi bi-archive-fill" />&nbsp;
          网站信息
        </span>
      </div>
      <div class="card-body" style="padding-top: 0">
        <div class="row web_info">
          <div class="col-6" style="text-align: left">
            已运行时间
          </div>
          <div class="col-6" style="text-align: right">
            {{ running_time }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { qq_imgUrl } from "@/api/other";
  import { useApiStore } from "@/stores/api";
  import { useUserStore } from "@/stores/user";
  import type { tag } from '@/types/index'
  import { onMounted, ref, type Ref } from 'vue';
  import { useRouter } from "vue-router";

  const $api = useApiStore();
  const userStore = useUserStore();
  const router = useRouter();

  let qq_photo = ref('');
  let qq_name = ref('');
  let tags: Ref<tag[]> = ref([]);
  let categories = ref();
  let running_time = ref('');

  onMounted(() => {
    const timestamp = new Date().getTime() - 1664037977670;
    const minutes: number = Math.floor(timestamp / 1000 / 60)
    const hours: number = Math.floor(minutes / 60 % 24);
    const days: number = Math.floor(minutes / 60 / 24);
    running_time.value = `${days}天${hours}小时`;
    get_qq_info();
  })
  async function get_qq_info() {
    await userStore.updateInfo();
    if (userStore.is_login) {
      if (userStore.qqPhoto && userStore.qqName) {
        qq_photo.value = userStore.qqPhoto;
        qq_name.value = userStore.qqName;
      } else {
        await qq_imgUrl(userStore.qqAccount).then(data => {
          userStore.qqPhoto = data.imgurl;
          userStore.qqName = data.name;
        });
        qq_photo.value = userStore.qqPhoto;
        qq_name.value = userStore.qqName;
      }
    } else {
      qq_photo.value = "https://q3.qlogo.cn/headimg_dl?dst_uin=1459847864&spec=640";
      qq_name.value = "Tiebo";
    }
  }

  $api.apiTags.getHottestTag({}).then(data => {
    tags.value = data.data;
  })

  $api.apiCategories.getCategoriesList({
    token: userStore.token,
  }).then(resp => {
    categories.value = resp.data.categories;
  })
  const router_to_index = (id: string, type: string) => {
    console.log(id)
    let name = "";
    if (type === 'categories')
      name = 'categories_articles_index';
    else
      name = 'tags_articles_index';

    router.push({
      name: name,
      params: {
        id: id,
      }
    })
  }
</script>

<style scoped>

  .classification:hover,
  .classification>* :hover {
    background-color: #a19f9f;
    color: #eabf51;
    cursor: pointer;
  }

  .classification>div>a {
    background-color: #a19f9f;
    text-decoration: none;
  }

  .classification,
  .classification>* {
    transition: 200ms
  }

  .label-link {
    font-size: 16px;
    color: grey;
    font-weight: bold;
    float: left;
  }

  .photo {
    transition: all 0.5s;
    width: 15vh;
    height: 15vh;
    border-radius: 50%;
  }

  .photo:hover {
    border-radius: 20%;
    box-shadow: 5px 10px 10px 5px #797373;
  }

  .username {
    cursor: default;
    margin-top: 1vh;
    font-size: 24px;
    font-family: "Maple Mono", serif;
  }

  .card {
    text-align: center;
  }

  .quick-link {
    text-decoration: none;
    color: #0c0c0c;
    font-size: 16px;
  }
</style>