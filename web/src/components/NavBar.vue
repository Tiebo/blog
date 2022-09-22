<template>
  <div class="container">
    <nav class="navbar navbar-expand-lg">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">
          <ul>
            <li class="title">
              <router-link class="link" :to="{name: 'home_index'}">
                MyBlog
              </router-link>
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
              <router-link class="link" :to="{name: 'article_index'}">
                <i class="bi bi-archive-fill"></i>
                articles
              </router-link>
            </li>
            <li>
              <router-link class="link" :to="{name: 'tags_index'}">
                <i class="bi bi-tags-fill"></i>
                Tags
              </router-link>
            </li>
            <li>
              <router-link class="link" :to="{name: 'categories_index'}">
                <i class="bi bi-folder-fill"></i>
                Categories
              </router-link>
            </li>
            <li>
              <router-link class="link" :to="{name: 'tags_index'}">
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
import {useUserStore} from "@/stores/user";
import router from "@/router";

const userStore = useUserStore();
if (localStorage.getItem("token")) {
  userStore.token = localStorage.getItem("token") as string;
  userStore.updateInfo();
  router.push({name: "home_index"});
}
</script>
    
<style scoped>
* {
  box-sizing: border-box;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif
}

.container {
  background: transparent;
}

nav ul li {
  list-style: none;
  display: inline-block;
  padding: 5px 15px;
  padding-top: 0;
  margin: 10px;
  font-size: 20px;
  font-weight: bold;
  color: rgb(193, 191, 191);
  position: relative;
  z-index: 2;
  transition: color 0.5s;
}

nav ul li::after {
  content: '';
  background: #964a58;
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
  color: #fff;
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
  color: rgb(193, 191, 191);
}

.link:hover {
  color: #fff;
}
</style>
    