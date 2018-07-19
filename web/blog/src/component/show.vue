<template>
  <div class="show">
    <!--:style="{background: 'url(' + this.fileBase + this.backgroundUrl + ')'}"-->
    <!--<img ref="showImg" :src="this.fileBase + this.backgroundUrl"/>-->
    <div class="show-bg" :style="{background: 'url(' + this.fileBase + this.backgroundUrl + ')'}"></div>
    <div class="show-inner">
      <div class="title"><span>{{title}}</span></div>
      <div class="detail"><span>{{detail}}</span></div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      title: {
        type: String,
        require: true,
        default: 'SNOW(D)'
      },
      backgroundUrl: {
        type: String,
        require: true,
        default: '2018/03/07/17/33/06/e1fc525d-15ba-4112-90b1-335466c1f5ee.jpg'
      },
      detail: {
        type: String,
        require: true,
        default: 'He is rich enough that wants nothing.'
      }
    },
    created () {
    },
    data () {
      return {
//        clipTop: 0
      }
    },
    computed: {
//      style () {
//        return {
//          top: -(this.clipTop - 42) + 'px'
//        }
//      }
    },
    methods: {},
    watch: {
//      backgroundUrl (to, from) {
//        const _that = this
//        const loader = this.ImgLoader
//        console.log('加载图片', this.fileBase, to)
//        loader(this.fileBase + to, function () {
//          _that.$notify.info({
//            title: '图片大小',
//            message: this.width + ' x ' + this.height
//          })
//          _that.clipTop = (this.height - 300) / 2
//          console.log(this.width + ' x ' + this.height)
//        })
//         this.GetImgRGBaster()
//      }
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
      width 100%
      filter blur(10px)
      opacity 0.7
      height 300px
      position absolute
      z-index 1
      overflow hidden
      img
        height 300px
        width 100%
        max-height 300px
        min-width 300px
</style>
