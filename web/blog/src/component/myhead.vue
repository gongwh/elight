<template>
  <div class="my_head" @scroll="l_onScroll">
    <div class="title">
      <div class="title-inner">
        <div class="me">
            <span>
              Snow
            </span>
        </div>
        <div class="navigation">
          <div class="navigation_block" @click=l_pushRout(prop) :class="prop.style" v-visible="prop.isDisplay"
               v-for="(prop,index) in topButtonsProps" :id="l_generateNavigationBlockId(index)">
            {{prop.displayName}}
          </div>
        </div>
      </div>
    </div>
    <img @click="l_backToTop" src="./3d-up-arrow.png" class="back_to_top" alt="top"/>
  </div>
</template>

<script type="text/ecmascript-6">
  import { mapState, mapMutations } from 'vuex'
  export default {
    data () {
      return {}
    },
    computed: {
      ...mapState(['topButtonsProps'])
    },
    methods: {
      ...mapMutations(['SET_BUTTON_STATE']),
      l_generateNavigationBlockId (index) {
        return 'navigation_block_' + index
      },
      l_pushRout (_prop) {
        console.log('准备跳转', _prop)
        this.$router.push(_prop)
      },
      l_onScroll () {
        console.log('监测到scroll')
      },
      l_backToTop () {
        window.scrollBy(0, -30)
        let scrolldelay = setTimeout(this.l_backToTop, 5)
        let sTop = document.documentElement.scrollTop + document.body.scrollTop
        if (sTop <= 0) clearTimeout(scrolldelay)
      }
    },
    watch: {
      '$route' (to) {
        console.log('路由监控', to)
        if (to.path === this.topButtonsProps[1].to || to.redirectedFrom === this.topButtonsProps[1].to) {
          this.SET_BUTTON_STATE({display: true, index: 2})
        }
      }
    }
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  *
    color rgba(0, 0, 0, 1)

  /*font-family Georgia*/
  a:link {
    text-decoration: none;
  }

  a:active {
    text-decoration: none
  }

  a:hover {
    text-decoration: none;
  }

  a:visited {
    text-decoration: none;
  }

  .my_head
    width 100%
    position fixed
    background-color rgba(230, 230, 230, 1)
    z-index 2000
    transition transform .5s;
    transform translateZ(0);
    .title
      position relative
      height 42px
      .title-inner
        height 30px
        width 80%
        line-height 30px
        margin auto
        padding-top 6px
        vertical-align middle
        overflow hidden
        div
          display inline-block
          height 100%
        .me
          font-size 25px
          font-weight 500
        .navigation
          float right
          font-size 18px
          text-align center
          .navigation_block
            width 100px
            cursor pointer
            overflow hidden
          .normal
            font-weight 300
          .reading
            width 200px
            /*font-weight 600*/
            /*color #383fff*/
            text-overflow ellipsis
            white-space nowrap
    .back_to_top
      display block
      position absolute
      line-height 42px
      width 32px
      height 32px
      top 5px
      right 0
      cursor pointer
</style>
