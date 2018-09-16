<template>
  <div id="app" class="app">
    <router-view></router-view>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'app',
    created () {
      console.log('%c%s', 'font-size: 20px;', 'SNOW www.gitee.com/gongwh')
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
</style>
