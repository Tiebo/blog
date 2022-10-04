<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft />
      </div>
      <div class="col-9">
        <div class="card">
          <div class="card-body">
            <h2 style="text-align: center;">Tags</h2>
            <hr>
            <el-row>
              <el-tag closable @close="handleClose(tag.id)" @click="router_to_articles(tag.id)" v-for="tag of tags"
                :key="tag.id" class="mx-4 tag" size="large"><span>
                  <i class="bi bi-folder-fill">
                  </i>
                  {{ tag.tagName }}
                  {{ tag.articleCounts }}
                </span>
                <br>
              </el-tag>
              <div v-show="useUserStore().is_admin === 1">
                <el-input style="width: 130px;" v-show="inputVisible" ref="InputRef" v-model="inputValue" class="mx-4"
                  size="large" @keyup.enter="handleInputConfirm" @blur="handleInputConfirm" />
                <el-button v-show="!inputVisible" class="button-new-tag mx-4 tag" @click="showInput">
                  + new tags
                </el-button>
              </div>
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
  import { useUserStore } from '@/stores/user';
  import type { tag } from "@/types";
  import { resp_message } from '@/utils/utils';
  import type { ElInput } from 'element-plus';
  import { nextTick, ref } from 'vue';
  import { useRouter } from 'vue-router';

  const $api = useApiStore();
  const router = useRouter();
  const inputValue = ref('')
  const inputVisible = ref(false)
  const InputRef = ref<InstanceType<typeof ElInput>>()
  const userStore = useUserStore();

  let tags = ref<tag[]>([]);

  const refresh_tags = () => {
    $api.apiTags.getHottestTag({}).then(resp => {
      tags.value = resp.data;
    })
  }
  refresh_tags();

  const create_tags = async () => {
    await $api.apiTags.createTags({
      tagName: inputValue.value,
      token: userStore.token,
    }).then(resp => {
      resp_message(resp, "添加成功", refresh_tags);
    })
  }
  const handleClose = async (id: number | string) => {
    await $api.apiTags.removeTags({
      tag_id: id,
      token: userStore.token,
    }).then(resp => {
      resp_message(resp, "删除成功", refresh_tags);
    })
    refresh_tags();
  }
  const router_to_articles = (id: number | string) => {
    router.push({
      name: 'tags_articles_index',
      params: {
        id: id,
      }
    })
  }

  const showInput = () => {
    inputVisible.value = true
    nextTick(() => {
      InputRef.value!.input!.focus()
    })
  }

  const handleInputConfirm = async () => {
    await create_tags();
    refresh_tags();
    inputVisible.value = false
    inputValue.value = '';
  }

</script>


<style scoped>
  .tag {
    color: #4c4c4c;
    background-color: #d7ddf0;
    font-size: 18px;
    height: 45px;
    margin-bottom: 15px;
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