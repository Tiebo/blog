<template>
  <div class="container">
    <v-md-editor v-model="text" height="500px" />

  </div>
</template>

<script>
export default {
  data() {
    return {
      text: '$$sum_{i=1}^n a_i=0$$',
    };
  },
};
</script>