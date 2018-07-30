<template>
  <div class="snow_tooltip">
    <el-tooltip :content="content" :placement="placement" effect="light" :open-delay="700" :hide-after="0" :enterable="true">
      <slot></slot>
    </el-tooltip>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      placement: {
        type: String,
        default: 'bottom'
      },
      content: {
        type: String,
        default: '提示内容'
      }
    },
    data () {
      return {
      }
    },
    watch: {
    },
    computed: {},
    created () {
    },
    destroyed () {
    },
    methods: {
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  /*.snow_tooltip*/

</style>
