<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft />
      </div>
      <div class="col-9">
        <div class="card">
          <div class="card-body">
            <h1>Categories</h1>
            <hr>
            <div @click="router_to_articles(category.id)" class="context" v-for="category of categories"
              :key="category.id">
              <span>
                <i class="bi bi-folder-fill">
                </i>
                {{ category.categoriesName }}
                ({{ category.articleCounts }})
              </span>
              <br>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup lang='ts'>
  import UserCardLeft from '@/components/UserCardLeft.vue';
  import type { resp_type } from "@/stores/api";
  import { useApiStore } from '@/stores/api';
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';

  const $api = useApiStore();
  const router = useRouter();
  let categories = ref<resp_type>([]);

  $api.apiCategories.getCategoriesList({}).then(resp => {
    categories.value = resp.data.categories;
  })

  const router_to_articles = (id: string | number) => {
    router.push({
      name: 'categories_articles_index',
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

  .context {
    text-align: left;
    margin-bottom: 5px;
    margin-left: 40px;
    font-size: 18px;
    transition: all 200ms;
    cursor: pointer;
  }

  .context:hover {
    color: #79490e;
    background-color: #c1bebe;
    transition: 200ms;
  }


</style>