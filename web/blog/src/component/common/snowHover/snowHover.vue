<template>
  <div class="snow_hover_scene" v-show="visible">
    <div class="snow_hover_cube" :style="style">
      <div class="snow_hover_side">
        <el-button>哈哈哈</el-button>
      </div>
      <slot></slot>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      visible: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {}
    },
    watch: {
    },
    computed: {
    },
    created () {
    },
    destroyed () {
    },
    methods: {
      close () {
        this.$emit('update:visible', false)
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .snow_hover_scene
    position absolute
    width: 100%;
    height: 100%;
    /*margin: 75px auto 0;*/
    perspective: 7000px;
    .snow_hover_cube
      position: relative;
      width: 100%;
      height: 100%;
      transform-style: preserve-3d;
      animation: example 0.5s linear alternate;
      animation-fill-mode forwards
      .snow_hover_side
        position: absolute;
        width: 100%;
        height: 100%;
        box-sizing: border-box;
        transform: translateZ(800px);
        background-color: rgba(32, 170, 151, 0.5);
  @keyframes example {
    /*0% { transform: rotateY(0deg); }*/
    /*10% { transform: rotateY(30deg); }*/
    /*90% { transform: rotateY(70deg); }*/
    100% { transform: rotateY(85deg); }
  }
</style>
