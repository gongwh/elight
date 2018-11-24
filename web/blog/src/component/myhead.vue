<template>
  <div>
    <snow-dialog :visible.sync='profileDialogVisible' :position="profileDialogPosition">
      <div class="dialog_content">
        <el-button type="primary" @click="e_login">login</el-button>
        <el-button type="primary" @click="e_logout">logout</el-button>
      </div>
    </snow-dialog>
    <div class="my-head" id="myHead">
      <div v-bind:class="scrollClass" class="app-header">
        <div class="title">
          <div class="title-inner">
            <snow-tooltip content="login in">
              <div class="me">
                <span id="navigation_block_username" @click="e_openDeleteDialog">
                  {{`${username? username: defaultUserName}`}}
                </span>
              </div>
            </snow-tooltip>
            <div class="navigation">
              <div v-for="(prop,index) in topButtonsProps">
                <snow-tooltip :content="prop.tip">
                  <div class="navigation_block" @click=l_pushRout(prop) :class="prop.style"
                       v-visible="prop.isDisplay"
                       :id="l_generateNavigationBlockId(index)">
                    {{prop.displayName}}
                  </div>
                </snow-tooltip>
              </div>
            </div>
          </div>
        </div>
        <img @click="NavigateToTop" src="./3d-up-arrow.png" class="back_to_top" alt="top"/>
      </div>
      <div v-bind:class="scrollClass" class="custom-header">
        <div class="title">
          <div class="title-inner">
            <snow-tooltip content="login in">
              <div class="me">
                <span @click="e_gotoElight">
                  {{slotTitle}}
                </span>
              </div>
            </snow-tooltip>
            <div class="navigation">
              <snow-input class="header-search" v-visible="isSlotSearchShow" @enter="e_searchEnter"
                          @change="e_searchChange"
                          placeholder=""
                          v-model="searchInput">
              </snow-input>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import {mapGetters, mapMutations, mapState, mapActions} from 'vuex'

  export default {
    props: {
      needScroll: {
        type: Boolean,
        default: true
      },
      isSlotShow: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {
        profileDialogVisible: false,
        profileDialogPosition: {x: 0, y: 35},
        slotTitle: 'Elight',
        isSlotSearchShow: false
      }
    },
    computed: {
      ...mapState(['topButtonsProps']),
      ...mapState('auth', ['defaultUserName', 'username']),
      scrollClass: function () {
        if (this.needScroll) {
          return this.isSlotShow ? 'slot-show' : 'slot-hidden'
        } else {
          return 'slot-hidden'
        }
      }

    },
    created () {
      const that = this
      this.$on('global:HeadSlotTitleChange', function (title) {
        that.slotTitle = title
      })
      this.$on('global:HeadSlotSearchShow', function (show) {
        that.isSlotSearchShow = show
      })
    },
    methods: {
      ...mapMutations(['SET_BUTTON_STATE']),
      ...mapGetters('auth', ['alreadyAuth']),
      ...mapActions('auth', ['logout']),
      e_gotoElight () {
        window.open('http://www.zhangzhuowei.com/article/f8cfbd2c-dfde-4f00-b0bd-9f72df709265', '_black')
      },
      e_searchEnter (val) {
        console.log('e_searchEnter', val)
        this.$emit('global:HeadSearchEnter', val)
      },
      e_searchChange (val) {
        this.$emit('global:HeadSearchChange', val)
      },
      e_login () {
        this.$router.push({path: '/login'})
      },
      e_logout () {
        this.logout()
        location.reload()
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
        // console.log('准备跳转', _prop)
        this.$router.push(_prop)
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

  .my-head
    // background-image linear-gradient(120deg,#155799,#159957)
    width 100%
    position fixed
    background-color #f1f1f1
    z-index 2100
    height 42px
    overflow hidden
    .app-header, .custom-header
      height 42px
      transition transform .3s, -webkit-transform .3s
    .slot-show
      transform: translateY(-100%)
    .slot-hidden
      transform: translateY(0)
    .title
      position relative
      height 42px
      .title-inner
        height 30px
        /*width 90%*/
        line-height 30px
        margin auto
        padding-top 6px
        vertical-align middle
        overflow hidden
        div
          display inline-block
          height 100%
        .me
          margin-left 80px
          font-size 25px
          font-weight 500
          cursor pointer
        .navigation
          float right
          font-size 18px
          text-align center
          .header-search
            position absolute
            right 83px
            top 3px
            .el-input__prefix, .el-input__suffix
              height 38px
          .navigation_block
            width 85px
            cursor pointer
            overflow hidden
            display block
          .normal
            font-weight 300
          .reading
            width 100px
            /*font-weight 600*/
            /*color #383fff*/
            margin-right 30px
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
