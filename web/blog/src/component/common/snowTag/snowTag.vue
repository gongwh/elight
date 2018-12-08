<template>
  <div class="snow_tag" :class="select?'snow_tag_select':''" @click="e_toggle">
    {{name}}
    <slot></slot>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      name: [String],
      select: {type: Boolean, default: false}
    },
    data () {
      return {
      }
    },
    watch: {},
    computed: {},
    created () {
    },
    destroyed () {
    },
    methods: {
      e_toggle () {
        this.$emit('toggle', !this.select)
      },
      e_gotoTagArticles () {
        // console.log('路由推向文章')
        this.$router.push({path: '/articles/list', query: {tagName: this.name}})
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .snow_tag
    font-family 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif;
    font-size 14px
    display inline-block
    background transparent
    border-radius 3px
    color #646464
    height 24px
    line-height 24px
    padding 0 10px 0 20px
    position relative
    margin 10px 10px 10px 0
    text-decoration none
    cursor pointer
    background rgba(93, 128, 109, 0.05) linear-gradient(to right, rgba(96, 153, 111, 0.05), rgba(153, 119, 152, 0.05))
    transition all 0.3s linear
    &:before
      background #d8d8d8
      border-radius 10px
      box-shadow inset 0 1px rgba(0, 0, 0, 0.25)
      content ''
      height 6px
      left 10px
      position absolute
      width 6px
      top 9px
    &:hover
      background #578062 linear-gradient(to right, #60996f, #997798)
      color white
      transition all 0.5s linear
      &:before
        background #f9f9f9
  .snow_tag_select
    background #578062 linear-gradient(to right, #60996f, #997798)
    transform scale(1.1)
    color white

</style>
