<template >
  <el-affix :offset="0" change="get_change" scroll="scroll">
    <NavBar />
  </el-affix>

  <RouterView style="margin-top: 5vh" />
  <hr>
  <footer>
    <div>私人小屋 ©2022 Created by Tiebo</div>
    <div class="number">
      <a href="https://beian.miit.gov.cn/" target="_blank">
        豫ICP备2022020047号
      </a>
    </div>
  </footer>
  <el-backtop :bottom="100" class="back_top">
    <i class="bi bi-arrow-bar-up"></i>
  </el-backtop>

</template>

<script setup lang="ts">
  import { RouterView } from 'vue-router'
  import NavBar from './components/NavBar.vue' // 移动端 rem 单位适配
</script>

<style>
  * {
    font-family: "Maple Mono", serif;
  }

  footer {
    color: #e3e1e1;
    font-size: 18px;
    text-align: center;
  }

  .back_top {
    transition: background-color 300ms;
  }

  .back_top:hover {
    background-color: #abc0d2 !important;
    transition: background-color 300ms;
  }
</style>