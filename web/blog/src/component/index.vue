<template>
  <div class="index">
    <myhead></myhead>
    <div class="content">
      <transition name="fade">
        <router-view></router-view>
      </transition>
      <div class="push"></div>
    </div>
    <myfoot></myfoot>
  </div>
</template>

<script type="text/ecmascript-6">
  import myhead from '@/component/myhead'
  import myfoot from '@/component/myfoot'
  export default {
    components: {myhead, myfoot}
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  img
    width 100%

  a:link {
    text-decoration: none;
  }

  a:active {
    text-decoration: none
  }

  a:hover {
    text-decoration: none;
  }

  a:visited {
    text-decoration: none;
  }

  .fade-enter-active
    transition: all 0.5s ease

  .fade-leave-active
    transition: all 0s ease

  .fade-enter
    opacity: 0

  .fade-leave-active
    opacity: 0

  .content
    *
      z-index 100
    padding-top 42px
    min-height: calc(100vh - 50px)
    *
      color rgba(0, 0, 0, 1)
      font-family "Raleway", Arial, Helvetica, sans-serif

  .push
    height 100px
</style>
