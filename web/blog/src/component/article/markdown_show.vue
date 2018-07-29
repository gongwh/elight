<template>
  <div v-highlight class="markdown-body" v-html="html">
  </div>
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
</style>
