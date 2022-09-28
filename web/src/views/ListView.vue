<template>
  <div class="container">
    <div class="card">
      <div class="card-body">
        <v-md-editor ></v-md-editor>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useApiStore } from '@/stores/api';
import { ref } from 'vue';

const $api = useApiStore();

  let content = ref('');

  $api.apiArticles.getArticleById({
    id: 1,
  }).then(resp => {
    content.value = resp.data.article_body;
  })

</script>

<style scoped></style>