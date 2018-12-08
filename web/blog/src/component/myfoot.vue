<template>
  <div class="snow_foot_wrapper" id="myFoot">
    <div class="snow_foot">
      <div>
        <img @click="e_goto('https://gitee.com/gongwh')" src="./github.png"/><span @click="e_goto('https://gitee.com/gongwh')">SNOW</span>
        <img @click="e_goto('mailto:13162569078@163.com', '_self')" src="./email.png"/><span @click="e_goto('mailto:13162569078@163.com', '_self')">13162569078@163.com</span>
        <img @click="e_goto('http://www.miitbeian.gov.cn')" src="./gov.png"/><span @click="e_goto('http://www.miitbeian.gov.cn')">晋ICP备18008339号</span>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    data () {
      return {}
    },
    methods: {
      e_goto (path, target = '_black') {
        window.open(path, target)
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .snow_foot_wrapper
    width 100%
    display table
    margin auto
    text-align center
    background-color #f1f1f1
    height 50px
    font-size 10px
    .snow_foot
      height 50px
      line-height 50px
      span
        cursor pointer
      img
        cursor pointer
        margin -3px 5px -3px 10px
        height 16px
</style>
