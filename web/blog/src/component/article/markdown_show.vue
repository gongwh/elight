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
  @import url('https://cdn.bootcss.com/github-markdown-css/2.9.0/github-markdown.css')
  @import url('https://cdn.bootcss.com/KaTeX/0.8.3/katex.min.css')
  @import url('https://cdn.bootcss.com/highlight.js/9.12.0/styles/github.min.css')
  @import '../md'
</style>
