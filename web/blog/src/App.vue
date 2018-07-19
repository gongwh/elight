<template>
  <div id="app" class="app">
    <router-view></router-view>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'app'
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  html, body
    margin 0
    padding 0
    background-color #f7f7f7
  ::-webkit-scrollbar {display:none}

</style>
