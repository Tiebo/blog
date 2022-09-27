<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft/>
      </div>
      <div class="col-9">
        <ArticlePost />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ArticlePost from '@/components/ArticlePost.vue';
import UserCardLeft from '@/components/UserCardLeft.vue';</script>

<style scoped>

</style>