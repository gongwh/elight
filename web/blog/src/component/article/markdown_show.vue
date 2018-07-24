<template>
  <p v-highlightjs class="markdown-body" v-html="html">
  </p>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      html: {
        type: String,
        default: ''
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import url('https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/2.9.0/github-markdown.min.css')
  /*@import url('https://cdnjs.cloudflare.com/ajax/libs/KaTeX/0.8.3/katex.min.css')*/
  /*@import "../md"*/
  @import url('https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/github.min.css')
  /*@import url('https://cdn.bootcss.com/KaTeX/0.8.3/katex.min.css')*/
</style>
