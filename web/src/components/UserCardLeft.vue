<template >
  <div class="container">
    <div class="card">
      <div class="card-body">
        <img class="photo " :src="qq_photo" alt="头像">
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
          <div class="col-4">主页</div>
          <div class="col-4">CSDN</div>
          <div class="col-4">Github</div>
        </div>
        <div class="row" style="margin-top: 1vh">
          <div class="col-4">知乎</div>
          <div class="col-4">vue3</div>
          <div class="col-4">Acwing</div>
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
        <div v-for="category of categories" @click="router_to_index(category.id, 'category')" :key="category.id"
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
            ( {{ tag.tagViewCounts }} )
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
import { onMounted, ref, type Ref } from 'vue';
import type { tag } from '@/types/index'

const $api = useApiStore();
const userStore = useUserStore();

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
  width: 15vh;
  height: 15vh;
  border-radius: 50%;
}

.username {
  margin-top: 1vh;
  font-size: 24px;
  font-family: "Maple Mono", serif;
}

.card {
  margin-bottom: 2vh;
  text-align: center;
  border-radius: 20px;
  background: linear-gradient(145deg, #e3e1e1, #cbc9c9);
  box-shadow: 5px 5px 5px #857e7e;
  opacity: 0.75;
}
</style>