<template>
  <div class="snow_dialog_wrapper" @click="close" v-show="visible">
    <div class="snow_dialog" :style="style">
      <div class="snow_cro_left_top"></div>
      <div class="snow_cro_right_top"></div>
      <div class="snow_dialog_content">
        <div class="snow_dialog_body">
          <slot></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      type: {
        type: String,
        default: 'absolute'
      },
      position: {
        type: Object,
        default: function () {
          return {x: 0, y: 0}
        }
      },
      visible: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {}
    },
    computed: {
      style: function () {
        return {
          top: this.position.y + 'px',
          left: (this.position.x - 100) + 'px'
        }
      }
    },
    created () {
    },
    methods: {
      close () {
        this.$emit('update:visible', false)
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .snow_dialog_wrapper
    z-index 2000
    position fixed
    top 0
    right 0
    bottom 0
    left 0
    overflow auto
    margin 0
    .snow_dialog
      font-size 0
      position fixed
      width 200px
      text-align center
      .snow_cro_left_top
        display inline-block
        width 30px
        height 30px
        background radial-gradient(circle at 5px 0px, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0) 80%, rgba(255, 255, 255, 1) 80%, rgba(255, 255, 255, 1));
      .snow_cro_right_top
        display inline-block
        width 30px
        height 30px
        background radial-gradient(circle at 25px 0px, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0) 80%, rgba(255, 255, 255, 1) 80%, rgba(255, 255, 255, 1));
      .snow_dialog_content
        box-shadow: 0 0 0 #ffffff, 0 0 0 #ffffff, 0 0 0 #ffffff, 0 15px 15px rgba(202, 202, 202, 0.7);
        font-size 15px
        display table
        width 100%
        background-color rgb(255, 255, 255)
        height 150px
        border-radius 10px
        vertical-align middle
        .snow_dialog_body
          display table-cell
          vertical-align middle
</style>
