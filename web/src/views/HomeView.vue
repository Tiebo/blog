<template>
  <div class="container">
    <div class="container">
      <header class="nav-fixed nav-visible">
        <div class="site-info">
          <h1 class="site-title">Tiebo的小屋</h1>
        </div>
        <div class="scroll-down">
          <i class="bi fa-4x bi-arrow-down scroll-down-icon" aria-hidden="true" style="cursor:pointer"></i>
        </div>
      </header>
      <div class="row">
        <div class="col-3">
          <UserCardLeft />
        </div>
        <div class="col-9">
          <ArticlePost />
        </div>
      </div>

    </div>
  </div>
</template>


<script setup lang='ts'>
  import UserCardLeft from "@/components/UserCardLeft.vue";
  import ArticlePost from "@/components/ArticlePost.vue";

</script>


<style scoped>
  .scroll-down {
    color: aliceblue;
    text-align: center;
    margin-bottom: 20vh;
  }

  /* 箭头动画 */
  @keyframes move {
    0% {
      transform: translate(0px, 0px);
    }

    50% {
      color: grey;
      transform: translate(0px, 30px);
    }

    100% {
      transform: translate(0px, 0px);
    }
  }


  .scroll-down-icon {
    display: inline-block;
    position: absolute;
    animation: move 2s infinite;
    -webkit-animation: move 2s infinite;
  }

  .site-info {
    width: 100%;
    height: 80vh;
    display: flex;
    justify-content: center;
  }

  .site-title {
    color: aliceblue;
    margin-top: 22%;
    text-shadow: 2px 2px 4px;
  }
</style>