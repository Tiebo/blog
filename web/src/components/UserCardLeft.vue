<template>
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
      <div v-for="tag of tags" @click="router_to_categories(tag.id)" :key="tag.id" class="row classification">
        <div class="col-6" style="text-align: left">
          {{ tag.tagName }}
        </div>
        <div class="col-6" style="text-align: right">
          ( {{ tag.tagViewCounts }} )
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { qq_imgUrl } from "@/api/other";
import { useApiStore } from "@/stores/api";
import { useUserStore } from "@/stores/user";
import { ref } from 'vue';

const $api = useApiStore();
const userStore = useUserStore();

let qq_photo = ref('');
let qq_name = ref('');
let tags: any = ref([]);

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
get_qq_info();

$api.apiTags.getHottestTag({}).then(data => {
  tags.value = data.data;
})

const router_to_categories = (id: string) => {
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

.classification>* {
  transition: 300ms;
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

}
</style>