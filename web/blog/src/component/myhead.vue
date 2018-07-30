<template>
  <div>
    <snowDialog :visible.sync='profileDialogVisible' :position="profileDialogPosition">
      <div class="dialog_content">
        <el-button type="primary" @click="e_login">重新登陆</el-button>
      </div>
    </snowDialog>
    <div class="my_head" @scroll="l_onScroll">
      <div class="title">
        <div class="title-inner">
          <snow-tooltip content="login out">
            <div class="me">
              <span id="navigation_block_username" @click="e_openDeleteDialog">
                {{username}}
              </span>
            </div>
          </snow-tooltip>
          <div class="navigation">
            <div v-for="(prop,index) in topButtonsProps">
              <snow-tooltip :content="prop.tip">
                <div class="navigation_block" @click=l_pushRout(prop) :class="prop.style" v-visible="prop.isDisplay"
                     :id="l_generateNavigationBlockId(index)">
                  {{prop.displayName}}
                </div>
              </snow-tooltip>
            </div>
          </div>
        </div>
      </div>
      <img @click="NavigateToTop" src="./3d-up-arrow.png" class="back_to_top" alt="top" />
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import {mapMutations, mapState} from 'vuex'

  export default {
    data () {
      return {
        profileDialogVisible: false,
        profileDialogPosition: {x: 0, y: 35}
      }
    },
    computed: {
      ...mapState(['topButtonsProps']),
      ...mapState('auth', ['username'])
    },
    methods: {
      ...mapMutations(['SET_BUTTON_STATE']),
      e_login () {
        this.$router.push({path: '/login'})
      },
      e_openDeleteDialog () {
        this.l_setDialogPosition()
        this.profileDialogVisible = true
      },
      l_setDialogPosition () {
        this.profileDialogPosition = this.GetElementBottomMiddlePosition('navigation_block_username')
        this.profileDialogPosition.y = 35
      },
      l_generateNavigationBlockId (index) {
        return 'navigation_block_' + index
      },
      l_pushRout (_prop) {
        console.log('准备跳转', _prop)
        this.$router.push(_prop)
      },
      l_onScroll () {
        console.log('监测到scroll')
      }
    },
    watch: {
      '$route' (to) {
        // console.log('路由监控', to)
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
    z-index 2100
    transition transform .5s;
    transform translateZ(0);
    .title
      position relative
      height 42px
      .title-inner
        height 30px
        width 90%
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
          cursor pointer
        .navigation
          float right
          font-size 18px
          text-align center
          .navigation_block
            width 80px
            cursor pointer
            overflow hidden
            display block
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
