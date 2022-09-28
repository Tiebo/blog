<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft />
      </div>
      <div class="col-9">

        <div class="card">
          <div class="card-body">
            <h1>Tags</h1>
            <hr>
            <el-row>
              <el-tag @click="router_to_articles(tag.id)" v-for="tag of tags" :key="tag.id" class="mx-4 tag"
                size="large"><span>
                  <i class="bi bi-folder-fill">
                  </i>
                  {{ tag.tagName }}
                  x{{ tag.tagViewCounts }}
                </span>
                <br>
              </el-tag>
            </el-row>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup lang = 'ts'>
  import UserCardLeft from '@/components/UserCardLeft.vue';
  import { useApiStore } from '@/stores/api';
  import type { tag } from "@/types";
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';

  const $api = useApiStore();
  const router = useRouter();

  let tags = ref<tag[]>([]);

  $api.apiTags.getHottestTag({}).then(resp => {
    tags.value = resp.data;
  })

  const router_to_articles = (id: number | string) => {
    router.push({
      name: 'tags_articles_index',
      params: {
        id: id,
      }
    })
  }
</script>


<style scoped>
  .card {
    margin-bottom: 2vh;
    text-align: center;
    border-radius: 20px;
    background: linear-gradient(145deg, #e3e1e1, #cbc9c9);
    box-shadow: 5px 5px 5px #857e7e;
    opacity: 0.75;

  }

  .tag {
    color: #4c4c4c;
    font-size: 18px;
    height: 45px;
    margin-bottom: 5px;
    transition: all 300ms;
    cursor: pointer;
    border-radius: 10px;
  }

  .tag:hover {
    background-color: #f69f85;
    transition: all 300ms;
    color: #ffffff;
  }
</style>