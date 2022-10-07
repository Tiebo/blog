<template>
  <div :class="scrollTop <= 120 ? 'container ': 'container affix' ">
    <nav class="navbar navbar-expand-lg">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">
          <ul>
            <li class="title">
              <router-link v-show="!useUserStore().is_login" class="link" :to="{name: 'home_index'}">
                MyBlog
              </router-link>
              <div v-show="useUserStore().is_login" class="logo link">
                <el-dropdown>
                  <span class="el-dropdown-link">
                    <img :src="useUserStore().qqPhoto" class="user_photo">
                    &shy;
                    {{ useUserStore().qqName }}
                    <el-icon class="el-icon--right">
                      <arrow-down />
                    </el-icon>
                  </span>
                  <template #dropdown>
                    <el-dropdown-menu style="font-weight: bold">
                      <el-dropdown-item>我的空间</el-dropdown-item>
                      <router-link class="router_link" :to="{name: 'post_content_index',
                      params:{
                        id : userStore.id? userStore.id: '0',
                      }}">
                        <el-dropdown-item divided>

                          发表文章
                        </el-dropdown-item>
                      </router-link>

                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </li>
          </ul>
        </a>
        <div class="collapse navbar-collapse" id="navbarText">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          </ul>
          <ul>
            <li>
              <router-link class="link" :to="{name: 'home_index'}">
                <i class="bi bi-house-heart-fill"></i>
                Home
              </router-link>
            </li>
            <li>
              <el-dropdown>
                <span class="el-dropdown-link">
                  <router-link class="link" :to="{name : 'article_index'}">
                    <i class="bi bi-archive-fill"></i>
                    articles
                    <el-icon class="el-icon--right">
                      <arrow-down />
                    </el-icon>
                  </router-link>
                </span>
                <template #dropdown>
                  <el-dropdown-menu style="font-weight: bold">
                    <router-link class="router_link" :to="{name: 'tags_index'}">
                      <el-dropdown-item>
                        <i class="bi bi-tags-fill"></i>
                        Tags
                      </el-dropdown-item>
                    </router-link>
                    <router-link class="router_link" :to="{name: 'categories_index'}">
                      <el-dropdown-item divided>
                        <i class="bi bi-folder-fill"></i>
                        Categories
                      </el-dropdown-item>
                    </router-link>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </li>
            <li>
              <router-link class="link" :to="{name: 'list_index'}">
                <i class="bi bi-bar-chart-line-fill"></i>
                Commits
              </router-link>
            </li>
            <li>
              <router-link class="link" :to="{name: 'list_index'}">
                <i class="bi bi-music-note-list"></i>
                List
              </router-link>
            </li>
            <li v-show="!userStore.is_login">
              <router-link class="link" :to="{name: 'login_index'}">
                <i class="bi bi-signpost-fill"></i>
                Login
              </router-link>
            </li>
            <li v-show="userStore.is_login">
              <router-link class="link" @click="userStore.logout()" :to="{name: 'home_index'}">
                <i class="bi bi-signpost-fill"></i>
                Logout
              </router-link>
            </li>
          </ul>
        </div>
      </div>

    </nav>
  </div>
</template>

<script setup lang="ts">
  import { useUserStore } from "@/stores/user";
  import { ArrowDown } from '@element-plus/icons-vue'
  import { ref } from "vue";

  const userStore = useUserStore();
  if (localStorage.getItem("token")) {
    userStore.token = localStorage.getItem("token") as string;
    userStore.updateInfo();
  }
  let scrollTop = ref(document.documentElement.scrollTop);

  window.addEventListener("scroll", function () {
    scrollTop.value = document.documentElement.scrollTop;
  })

</script>

<style scoped>
  .logo>img {
    width: 6vh;
    border-radius: 50%;
  }

  .logo>span {
    font-size: 24px;
    color: #ffffff;
  }

  * {
    box-sizing: border-box;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif
  }

  nav>*,
  nav {
    padding-top: 0;
  }

  .container {
    height: 8vh;
    transition: 300ms;
  }

  .affix {
    background-color: #4e4f51;
    opacity: 0.85;
    border-radius: 50px;
  }

  nav ul li {
    list-style: none;
    display: inline-block;
    padding: 0 15px 5px;
    margin: 10px;
    font-size: 20px;
    font-weight: bold;
    color: #ffffff;
    position: relative;
    z-index: 2;
    transition: color 0.5s;
  }

  nav ul li::after {
    content: '';
    background: #b94b5e;
    width: 100%;
    height: 100%;
    border-radius: 30px;
    position: absolute;
    top: 100%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: -1;
    opacity: 0;
    transition: top 0.5s, opacity 0.5s;
  }

  nav ul li:hover {
    color: #ffffff;
  }

  nav ul li:hover::after {
    top: 50%;
    opacity: 1;
  }

  .title {
    font-size: 36px;
    font-weight: 500;
  }

  .link {
    text-decoration: none;
    color: #e5e5e5;
    font-weight: 550;
  }

  .link:hover {
    color: #fff;
  }

  .user_photo {
    width: 5vh;
    border-radius: 50%;
    opacity: 0.85;
  }

  .el-dropdown-link {
    margin-top: 3px;
    font-size: 20px;
    color: #ffffff;
    transition: all 0.5s;
  }

  .router_link {
    text-decoration: none;
    color: #857e7e;
  }
</style>
    