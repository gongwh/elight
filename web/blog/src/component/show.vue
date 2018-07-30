<template>
  <div class="show">
    <!--:style="{background: 'url(' + this.fileBase + this.backgroundUrl + ')'}"-->
    <!--<img ref="showImg" :src="this.fileBase + this.backgroundUrl"/>-->
    <div class="show-bg" :style="{'background-image': 'url(' + this.fileBase + this.backgroundUrl + ')'} "></div>
    <div class="show-inner" >
        <div class="title"><span>{{title}}</span></div>
      <div class="detail"><span>{{detail}}</span></div>
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
        default: 'You'
      },
      backgroundUrl: {
        type: String,
        require: true,
        default: '2018/03/07/17/33/06/e1fc525d-15ba-4112-90b1-335466c1f5ee.jpg'
      },
      detail: {
        type: String,
        require: true,
        default: 'All Izz well.'
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
    height 300px
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
    .show-bg
      background-size cover
      background-repeat no-repeat
      background-position center center
      position absolute
      width 100%
      filter blur(15px)
      opacity 0.3
      height 300px
      margin: auto
      z-index 1
      overflow hidden
      img
        height 300px
        width 100%
        max-height 300px
        min-width 300px
</style>
