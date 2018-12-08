<template>
  <div class="show">
    <!--:style="{background: 'url(' + this.fileBase + this.backgroundUrl + ')'}"-->
    <!--<img ref="showImg" :src="this.fileBase + this.backgroundUrl"/>-->
    <div class="show-bg" :style="{'background-image': 'url(' + this.fileBase + this.backgroundUrl + ')'} "></div>
    <div class="show-inner" >
        <div class="title"><span v-html="title"></span></div>
      <div class="detail"><span v-html="detail"></span></div>
      <slot></slot>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      title: {
        type: String,
        require: true,
        default: ''
      },
      backgroundUrl: {
        type: String,
        require: true,
        default: '2018/12/08/17/40/35/4176363a-1e43-45b1-8dc5-7f341880598b.jpg'
      },
      detail: {
        type: String,
        require: true,
        // default: 'To see the world, things dangerous to come to' +
        //   '<br/>To see behind walls, to draw closer' +
        //   '<br/>To find each other and to feel'
        default: ''
      }
    },
    created () {
    },
    data () {
      return {
      }
    },
    computed: {
    },
    methods: {},
    watch: {
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "../common/stylus/index.styl"
  .show
    display table
    position relative
    height 200px
    width 100%
    .show-inner
      display table-cell
      position relative
      z-index 3
      text-align center
      vertical-align middle
      margin auto
      background-attachment fixed
      .title
        font-size 30px
        font-weight 600
      .detail
        font-size 18px
        font-family "Arial","Microsoft YaHei","黑体","宋体",sans-serif
    .show-bg
      background-size cover
      background-repeat no-repeat
      background-position center center
      position absolute
      width 100%
      filter blur(10px)
      opacity 0.3
      height 200px
      margin: auto
      z-index 1
      overflow hidden
      img
        height 300px
        width 100%
        max-height 300px
        min-width 300px
</style>
