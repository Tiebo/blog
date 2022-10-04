<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft />
      </div>
      <div class="col-9">
        <div class="card">
          <div class="card-body">
            <h2 style="text-align: center;">Categories</h2>
            <el-button v-show="userStore.is_admin === 1" class="edit" type="primary" @click="edit_categories(-1)"
              :icon="Edit" circle />
            <hr>
            <div class="context" v-for="category of categories" :key="category.id">
              <div>
                <span @click="router_to_articles(category.id)" style="cursor: pointer;">
                  <i class="bi bi-folder-fill">
                  </i>
                  {{ category.categoriesName }}
                  ({{ category.articleCounts }})
                </span>
                <span style="float: right;" v-show="userStore.is_admin === 1">
                  <el-button text @click="edit_categories(category.id)" type="primary" :icon="Edit" circle />
                  <el-button text @click="delete_categories(category.id)" type="danger" :icon="Delete" circle />
                </span>
              </div>
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
  import { useUserStore } from '@/stores/user';
  import { resp_message } from '@/utils/utils';
  import { Delete, Edit } from '@element-plus/icons-vue'
  import { ElMessage, ElMessageBox } from 'element-plus';
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';

  const $api = useApiStore();
  const router = useRouter();
  const userStore = useUserStore();
  let categories = ref<resp_type>([]);

  const update_categories_info = () => {
    $api.apiCategories.getCategoriesList({}).then(resp => {
      categories.value = resp.data.categories;
    })
  }
  update_categories_info();
  const router_to_articles = (id: string | number) => {
    router.push({
      name: 'categories_articles_index',
      params: {
        id: id,
      }
    })
  }
  const edit_categories = (id: string | number | undefined) => {
    ElMessageBox.prompt('请输入名称', '提交信息', {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
    }).then(({ value }) => {
      if (id !== -1) {
        $api.apiCategories.updateCategories({
          categories_id: id,
          categories_name: value,
          token: useUserStore().token,
        }).then(resp => {
          resp_message(resp, "修改成功", update_categories_info);
        })
      } else {
        $api.apiCategories.createCategories({
          categoriesName: value,
          token: useUserStore().token,
        }).then(resp => {
          resp_message(resp, "添加成功", update_categories_info);
        })
      }
    }).catch(() => {
      ElMessage({
        type: 'info',
        message: 'Input canceled',
      })
    })
  }
  const delete_categories = (id: number | string) => {
    ElMessageBox.confirm(
      '确定删除此标签么?',
      'Warning',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    ).then(() => {
      $api.apiCategories.dropCategories({
        id: id,
        token: useUserStore().token,
      }).then(resp => {
        resp_message(resp, "删除成功", update_categories_info);
      })
    }).catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
  }
</script>


<style scoped>
  .card {
    text-align: center;
  }

  .context {
    text-align: left;
    margin-bottom: 5px;
    margin-left: 40px;
    font-size: 18px;
    transition: all 200ms;
  }

  .context:hover {
    color: #79490e;
    background-color: #c1bebe;
    transition: 200ms;
  }

  .edit {
    float: right;
    cursor: pointer;
  }

</style>