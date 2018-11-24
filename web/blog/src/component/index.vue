<template>
  <div class="index" @wheel="e_scrollLoad">
    <myhead :isSlotShow="isSlotShow" :needScroll="true"></myhead>
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
        hoverVisible: false,
        isSlotShow: false
      }
    },
    components: {myhead, myfoot},
    methods: {
      e_scrollLoad (e) {
        // let scrollTop = document.documentElement.scrollTop || document.body.scrollTop
        // let windowHeight = document.documentElement.clientHeight || document.body.clientHeight
        // let scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
        // console.log('scrollTop', scrollTop, 'windowHeight', windowHeight, 'scrollHeight', scrollHeight)
        if (e.deltaY > 0) {
          this.isSlotShow = true
        } else {
          this.isSlotShow = false
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
    background-color rgba(255,255,255,0.98)

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
      padding-top 42px
      min-height: calc(100vh - 50px)
    //*
    //  color rgba(0, 0, 0, 1)
    //  font-family "Raleway", Arial, Helvetica, sans-serif

    .push
      height 100px

  .snow_hover_scene
    /*position absolute*/
    width: 100%;
    height: 100%;
    perspective: 5200px;
    .snow_hover_cube
      min-height: calc(100vh - 50px)
      position: relative;
      width: 100%;
      height: 100%;
      transform-style: preserve-3d;
      // animation: example 10s infinite linear;
      transform-origin 0 0 1300px;
      /*animation-fill-mode forwards*/
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
  @keyframes example {
    10% {
      transform: translateZ(-5200px) rotateY(0deg)
    }
    50% {
      transform: translateZ(-5200px) rotateY(360deg)
    }
    90% {
      transform: translateZ(-5200px) rotateY(0deg)
    }
  }
</style>
