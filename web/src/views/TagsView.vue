<template>
  <div class="container">
    <div class="row">
      <div class="col-3">
        <UserCardLeft />
      </div>
      <div class="col-9">
        <div class="card">
          <div class="card-body">
            <h1>Tags</h1>
            <hr>

            <div class="context" v-for="tag of tags" :key="tag.id">
              <span>
                <i class="bi bi-folder-fill">
                </i>
                {{ tag.tagName }}
                ({{ tag.tagViewCounts }})
              </span>
              <br>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup lang = 'ts'>
import UserCardLeft from '@/components/UserCardLeft.vue';
import { useApiStore } from '@/stores/api';
import type { tag } from "@/types";
import { ref } from 'vue';

const $api = useApiStore();

let tags = ref<tag[]>([]);

$api.apiTags.getHottestTag({}).then(resp => {

  tags.value = resp.data;


})
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
  transition: 200ms;
  cursor: pointer;
}

.context:hover {
  color: #79490e;
  background-color: #c1bebe;
  transition: 200ms;
}
</style>