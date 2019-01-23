<template>
  <div class="index" @wheel="e_scrollLoad">
    <myhead></myhead>
    <div class="content">
      <transition name="fade">
        <router-view></router-view>
      </transition>
    </div>
    <myfoot></myfoot>
  </div>
</template>

<script type="text/ecmascript-6">
  import myhead from '@/component/myhead'
  import myfoot from '@/component/myfoot'

  export default {
    data () {
      return {
        hoverVisible: false
      }
    },
    components: {myhead, myfoot},
    methods: {
      e_scrollLoad (e) {
        if (e.deltaY > 0) {
          this.$emit('global:HeadSlotShow', true)
        } else {
          this.$emit('global:HeadSlotShow', false)
        }
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">

  @import '../common/highlight/styles/atom-one-dark.css'
  @import '../common/stylus/md'
  html, body
    margin 0


  .el-message
    z-index 5000

  .index
    background-color #f1f1f1

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
    @keyframes bounce-in {
      0% {
        opacity 0
      }
      100% {
        opacity 1
      }
    }
    .fade-enter-active
      animation bounce-in 1s

    .content
      padding-top 42px
      min-height: calc(100vh - 50px)

    .push
      height 100px

  .snow_hover_scene
    width: 100%;
    height: 100%;
    perspective: 5200px;
    .snow_hover_cube
      min-height: calc(100vh - 50px)
      position: relative;
      width 100%
      height 100%
      transform-style preserve-3d
      transform-origin 0 0 1300px
      .snow_hover_side
        padding-top 42px
        position: absolute;
        width: 100%;
        height: 100%;
        box-sizing: border-box;
      .left
        background-color: rgba(32, 170, 151, 0.5)
        transform: translateX(-1300px) translateZ(-1300px) rotateY(90deg)
      .back
        background-color: rgba(32, 170, 151, 0.5)
        transform: translateZ(-2600px)
      .right
        background-color: rgba(32, 170, 151, 0.5)
        transform: translateX(1300px) translateZ(-1300px) rotateY(90deg)

  .el-radio
    .el-radio__input.is-checked+.el-radio__label
      color unset
    .el-radio__input.is-checked .el-radio__inner
      background unset
      background-color #668c6e
      border-color #668c6e

  .content
    .loading
      height 500px
      text-align center
      img
        margin-top 100px
        height 100px
    .transition-slow
      transition all 5s

</style>
