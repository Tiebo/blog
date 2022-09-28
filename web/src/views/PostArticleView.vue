<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft/>
      </div>
      <div class="col-9">
        <EditCardRight/>
      </div>
    </div>
  </div>
</template>


<script setup lang='ts'>
import EditCardRight from "@/components/EditCardRight.vue";
import UserCardLeft from '@/components/UserCardLeft.vue';</script>

<style scoped>
</style>