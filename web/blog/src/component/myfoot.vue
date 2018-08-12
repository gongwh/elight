<template>
  <div class="snow_foot_wrapper">
    <div class="snow_foot">
      <a href='https://gitee.com/gongwh' target="_blank">SNOW</a>
      <br/>
      <br/>
       <a href="http://www.miitbeian.gov.cn/" target="_blank">晋ICP备18008339号</a>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    data () {
      return {}
    },
    methods: {
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .snow_foot_wrapper
    width 100%
    display table
    margin auto
    text-align center
    background-color rgba(232, 232, 232, 0.88)
    height 50px
    font-size 10px
    .snow_foot
      a:link, a:visited
        color #000000
      a:hover
        color #000da3
      height 10px
      display table-cell
      vertical-align middle
      line-height 10px
      img
        display inline-block
        margin 0 20px 0 5px
        width 15px
        height 15px
</style>
