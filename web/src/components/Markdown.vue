<template>
  <MarkdownIt :source="source"/>
</template>

<script lang="ts">
import '@/assets/css/github.css'
import MarkdownItAbbr from 'markdown-it-abbr'
import MarkdownItDeflist from 'markdown-it-deflist'
import MarkdownItEmoji from 'markdown-it-emoji'
import MarkdownItSub from 'markdown-it-sub'
import MarkdownItSup from 'markdown-it-sup'
import MarkdownToc from 'markdown-it-toc-done-right'
import { reactive } from 'vue'
import MarkdownIt from 'vue3-markdown-it';

export default {
  name:"Markdown",
  components: {
    MarkdownIt
  },
  props: {
    source: {
      type: String,
      required: true,
    },
  },
  mounted() {
  },
  setup() {
    const plugins = reactive([
      {
        plugin: MarkdownItAbbr
      }, {
        plugin: MarkdownItSub
      }, {
        plugin: MarkdownItSup
      }, {
        plugin: MarkdownItDeflist
      }, {
        plugin: MarkdownItEmoji
      }, {
        plugin: MarkdownToc
      }
    ]);

    return {
      plugins,
    }
  }
}

</script>



<style scoped>

</style>