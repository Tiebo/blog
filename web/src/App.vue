<template>
  <NavBar />
  <RouterView />
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router';
import NavBar from './components/NavBar.vue';
import {useApiStore} from "@/stores/api";
import request from "@/api/request";


</script>

<style>
* {
  font-family: "Maple Mono",serif;
}
</style>
