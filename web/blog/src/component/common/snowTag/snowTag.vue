<template>
  <div class="snow_tag" :class="selected?'snow_tag_selected':''" @click="e_toggleState">
    {{name}}
    <slot></slot>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      name: [String],
      selected: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {
        _selected: false
      }
    },
    watch: {},
    computed: {},
    created () {
      this._selected = this.selected
    },
    destroyed () {
    },
    methods: {
      e_toggleState () {
        this._selected = !this._selected
        this.$emit('update:selected', this._selected)
        if (this._selected) {
          this.l_selected()
        } else {
          this.l_unselected()
        }
      },
      e_gotoTagArticles () {
        console.log('路由推向文章')
        this.$router.push({path: '/articles/list', query: {tagName: this.name}})
      },
      l_selected () {
        this.$emit('selected')
      },
      l_unselected () {
        this.$emit('unselected')
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .snow_tag
    font-family -apple-system, SF UI Text, Arial, PingFang SC, Hiragino Sans GB, Microsoft YaHei, WenQuanYi Micro Hei, sans-serif
    font-size 14px
    display inline-block
    background transparent
    transition background 0.5s linear
    border-radius 3px
    color #646464
    height 24px
    line-height 24px
    padding 0 10px 0 20px
    position relative
    margin 10px 10px 10px 0
    text-decoration none
    cursor pointer
    &:before
      background #a49edd
      border-radius 10px
      box-shadow inset 0 1px rgba(0, 0, 0, 0.25)
      content ''
      height 6px
      left 10px
      position absolute
      width 6px
      top 9px
    /*
  &:after
    background #fff
    border-bottom 13px solid transparent
    border-left 10px solid #eee
    border-top 13px solid transparent
    content ''
    position absolute
    right 0
    top 0
    */
    &:hover
      background #578062 linear-gradient(to right, #60996f, #997798)
      // background-image linear-gradient(120deg, #60996f, #997798)
      /*transition: background-color .2s;*/
      // background-color rgba(56, 56, 56, 0.88)
      color white
  .snow_tag_selected
    background #578062 linear-gradient(to right, #60996f, #997798)
  /*&:hover::after*/
  /*border-left-color crimson*/
</style>
