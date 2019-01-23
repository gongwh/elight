<template>
  <div id="articles" class="articles" @wheel="e_scrollLoad" @touchmove="e_touchMoveLoad">
    <div class="tags" v-bind:class="scrollClass">
      <snow-tag :name="tag.name" :select="tag.selected" v-for="(tag,index) in state_tagNames" :key="tag.id"
                @toggle="e_toggleSelect(index,tag)"></snow-tag>
      <el-radio v-model="sortBy" label="createTime">发表时间</el-radio>
      <el-radio v-model="sortBy" label="latestModifyDate">更新时间</el-radio>
      <el-radio v-model="sortBy" label="readTotalTimes">阅读次数</el-radio>
    </div>
    <div v-show="isLoading" class="loading">
      <img src="../../assets/loading.gif" alt="">
    </div>
    <div class="articles-inner"
         v-show="!isSearch"
    >
      <div @click="l_openArticle(article.articleId)" class="article"
           v-for="article in articles">
        <div class="image-wrapper">
          <div class="image-wrapper-inner">
            <div class="bg-img" v-if="article.titleImgUrl"
                 :style="{'background-image': 'url(' + fileBase + article.titleImgUrl + ')'} "></div>
            <div class="bg-letter" v-if="!article.titleImgUrl">{{article.titleLetter ? article.titleLetter: '?'}}</div>
          </div>
        </div>
        <div class="miniContent">
          <div class="title"><div class="banner"></div>{{article.title}}</div>
          <div class="desc">{{article.contentTextSubNail}}</div>
          <div class="subscribe">{{article.updateTime}}
            <div>
              <img src="./see.png"/>
              {{article.readTotalTimes ? article.readTotalTimes: 0}}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="articles-inner" v-show="isSearch">
      <div @click="l_openArticle(article.articleId)" class="article"
           v-for="article in articlesSearch">
        <div class="image-wrapper">
          <div class="image-wrapper-inner">
            <div class="bg-img" v-if="article.titleImgUrl"
                 :style="{'background-image': 'url(' + fileBase + article.titleImgUrl + ')'} "></div>
            <div class="bg-letter" v-if="!article.titleImgUrl">{{article.titleLetter ? article.titleLetter: '?'}}</div>
          </div>
        </div>
        <div class="miniContent">
          <div class="title"><div class="banner"></div>{{article.title}}</div>
          <div class="desc">{{article.contentTextSubNail}}</div>
          <div class="subscribe">{{article.updateTime}}
            <div>
              <img src="./see.png"/>
              {{article.readTotalTimes ? article.readTotalTimes: 0}}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import show from '@/component/show'
  import {mapActions, mapGetters, mapMutations, mapState} from 'vuex'
  import {getCookie, setCookie} from '../../util/util'

  export default {
    data () {
      return {
        defaultImgPath: '2018/03/07/17/33/06/e1fc525d-15ba-4112-90b1-335466c1f5ee.jpg',
        searchInput: '',
        searchInputLatest: '',
        searchResultShow: false,
        noArticleNotifyTimes: 10,
        tagsVisible: false,
        isSearch: false,
        isLoading: false,
        sortBy: 'latestModifyDate', // true: sort by createTime. false: sort by updateTime,
        isTagsShow: true
      }
    },
    components: {show},
    computed: {
      // all articles
      ...mapState('article/articles', ['articles']),
      ...mapState('article/articles', ['pagination']),
      ...mapGetters('article/articles', ['articlesNum']),

      // search articles
      ...mapState('article/articles', ['state_tagNames', 'state_searchInput', 'hasSelectedTag', 'state_selectedTagNames']),
      ...mapState('article/articles', ['articlesSearch', 'paginationSearch']),
      ...mapGetters('article/articles', ['articlesNumSearch', 'articlesTotalNumSearch']),

      // auth
      ...mapState('auth', ['userId', 'defaultUserId']),

      // 是否手动刷新
      manualFlush () {
        if (this.$route.query) {
          if (this.$route.query.flush) {
            return this.$route.query.flush
          } else {
            return false
          }
        }
      },
      scrollClass: function () {
        return this.isTagsShow ? 'slot-show' : 'slot-hidden'
      }
    },
    watch: {
      'searchInput' (nv, ov) {
        this.SET_ARTICLES_SEARCH_INPUT(nv)
        this.l_tryChangeIsSearch()
      },
      'isSearch' (val, oldVal) {
        if (val !== oldVal) {
          if (val) {
            this.searchResultShow = false
          } else {
            if (this.articles === null) {
              this.l_clearAndInitArticles()
            }
          }
        }
      },
      'sortBy' (val, oldVal) {
        if (val !== oldVal) {
          setCookie('sortBy', val, 30)
          this.l_clearAndInitArticles()
        }
      }
    },
    created () {
      this.searchInput = this.state_searchInput
      this.l_tryChangeIsSearch()
      if (this.manualFlush || this.articles === null) {
        this.l_clearAndInitArticles()
      }
      this.l_tryInitTagNames()
      this.sortBy = this.l_getSortBy()
      this.l_bindHeadSearchEvent()
      this.$emit('global:HeadSlotTitleChange', 'Elight')
      this.$emit('global:HeadSlotSearchShow', true)
    },
    mounted () {
    },
    methods: {
      ...mapActions('article/articles', ['loadArticlePage', 'searchArticles', 'getSelectedTagNames', 'initTagNames']),
      ...mapActions('tag/tag', ['loadAllTags']),
      ...mapMutations('article/articles', ['CLEAR_ARTICLES_RESULT', 'CLEAR_ARTICLES_SEARCH_RESULT',
        'SET_ARTICLES_SEARCH_INPUT', 'INIT_TAG_NAMES', 'UPDATE_TAG_SELECT']),
      l_bindHeadSearchEvent () {
        const that = this
        that.$on('global:HeadSearchEnter', function (val) {
          that.e_searchArticle()
        })
        that.$on('global:HeadSearchChange', function (val) {
          that.searchInput = val
        })
        that.$on('global:HeadSlotShow', function (show) {
          that.isTagsShow = show
        })
      },
      l_clearAndInitArticles () {
        this.CLEAR_ARTICLES_RESULT()
        if (this.isSearch) {
          this.l_searchArticlePage()
        } else {
          this.l_loadArticlePage()
        }
      },
      l_getSortBy () {
        let sortBy = getCookie('sortBy')
        return sortBy || 'latestModifyDate'
      },
      l_tryInitTagNames () {
        if (!this.state_tagNames) {
          this.initTagNames()
        }
      },
      l_tryChangeIsSearch () {
        if (this.searchInput.length > 0 || this.hasSelectedTag) {
          this.isSearch = true
        } else {
          this.isSearch = false
        }
      },
      l_trySearch () {
        this.l_tryChangeIsSearch()
        if (this.isSearch) {
          this.l_searchArticlePage()
        }
      },
      e_toggleSelect (index, tag) {
        if (tag.selected) {
          this.UPDATE_TAG_SELECT({index: index, selected: false})
        } else {
          this.UPDATE_TAG_SELECT({index: index, selected: true})
        }
        this.CLEAR_ARTICLES_SEARCH_RESULT()
        this.l_trySearch()
      },
      e_searchInputFocus () {
        this.tagsVisible = true
      },
      e_searchInputBlur () {
        this.tagsVisible = false
      },
      e_scrollLoad (e) {
        // console.log(e)
        const that = this
        let scrollTop = document.documentElement.scrollTop || document.body.scrollTop
        let windowHeight = document.documentElement.clientHeight || document.body.clientHeight
        let scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
        if (!that.isLoading) {
          if (scrollTop + windowHeight >= scrollHeight && e.deltaY > 0) {
            if (this.noArticleNotifyTimes >= 10) {
              this.noArticleNotifyTimes = 0
              if (!this.isSearch) {
                that.l_loadArticlePage()
              } else {
                that.l_searchArticlePage()
              }
            } else {
              this.noArticleNotifyTimes++
            }
          }
        }
      },
      e_touchMoveLoad (e) {
        const that = this
        let scrollTop = document.documentElement.scrollTop || document.body.scrollTop
        let windowHeight = document.documentElement.clientHeight || document.body.clientHeight
        let scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
        if (scrollTop + windowHeight + 2 >= scrollHeight) {
          if (!that.isLoading) {
            if (this.noArticleNotifyTimes >= 10) {
              this.noArticleNotifyTimes = 0
              if (!this.isSearch) {
                that.l_loadArticlePage()
              } else {
                that.l_searchArticlePage()
              }
            } else {
              this.noArticleNotifyTimes++
            }
          }
        }
      },
      l_loadArticlePage () {
        const that = this
        let userId = that.userId ? that.userId : that.defaultUserId
        if (!that.pagination) {
          // console.log('首次加载文章', this.pagination)
          that.isLoading = true
          // console.log('that.sortBy', that.sortBy)
          this.loadArticlePage({userId: userId, page: 0, size: 10, sort: that.l_getSortBy() + ',desc'}).then(
            () => {
              that.isLoading = false
            },
            () => {
              that.isLoading = false
            }
          )
        } else if (!that.pagination.last || this.manualFlush) {
          // console.log('加载文章', this.pagination)
          that.isLoading = true
          this.loadArticlePage({
            userId: userId,
            page: (this.pagination.pageNumber + 1),
            size: 10,
            sort: that.l_getSortBy() + ',desc'
          }).then(
            () => {
              that.isLoading = false
            },
            () => {
              that.isLoading = false
            }
          )
        } else {
          this.$notify.info({
            title: '加载文章',
            message: '没有更多文章了',
            offset: 80
          })
        }
      },
      async l_searchArticlePage () {
        const that = this
        let userId = that.userId ? that.userId : that.defaultUserId
        // 搜索旧输入内容
        if (!that.paginationSearch) {
          that.isLoading = true
          // 搜索首页
          let params = new URLSearchParams()
          params.append('page', 0)
          params.append('size', 10)
          params.append('sort', that.l_getSortBy() + ',desc')
          this.searchArticles({
            data: {
              userId: userId,
              title: that.searchInput,
              tagNames: that.state_selectedTagNames
            },
            params
          }).then(
            () => {
              that.searchResultShow = true
              that.isLoading = false
            },
            () => {
              that.isLoading = false
            }
          )
        } else if (!that.paginationSearch.last) {
          // 搜索下一页
          that.isLoading = true
          let params = new URLSearchParams()
          params.append('page', this.paginationSearch.pageNumber + 1)
          params.append('size', 10)
          params.append('sort', that.l_getSortBy() + ',desc')
          that.searchArticles({
            data: {
              userId: userId,
              title: this.searchInput,
              tagNames: this.state_selectedTagNames
            },
            params
          }).then(
            () => {
              that.isLoading = false
            },
            () => {
              that.isLoading = false
            }
          )
        } else {
          this.$notify.info({
            title: '搜索文章',
            message: '没有更多文章了',
            offset: 80
          })
        }
      },
      async e_searchArticle () {
        const that = this
        if (that.isSearch) {
          // 判断是否需要搜索新内容
          let userId = that.userId ? that.userId : that.defaultUserId
          if (that.searchInputLatest !== that.searchInput) {
            // 搜索新输入内容的首页
            that.CLEAR_ARTICLES_SEARCH_RESULT()
            that.isLoading = true
            let params = new URLSearchParams()
            params.append('page', 0)
            params.append('size', 10)
            params.append('sort', that.l_getSortBy() + ',desc')
            that.searchArticles({
              data: {
                userId: userId,
                title: that.searchInput,
                tagNames: that.state_selectedTagNames
              },
              params
            }).then(
              () => {
                that.searchResultShow = true
                that.searchInputLatest = that.searchInput
                that.isLoading = false
              },
              () => {
                that.isLoading = false
              }
            )
          } else {
            that.l_searchArticlePage()
          }
        } else {
          // console.log('不是搜索状态')
        }
      },
      l_openArticle (_articleId) {
        this.$router.push({path: `/article/${_articleId}`})
      }
    },
    destroyed () {
      this.$emit('global:HeadSlotSearchShow', false)
    },
    beforeRouteUpdate (to, from, next) {
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .articles
    font-family "Arial", "Microsoft YaHei", "黑体", "宋体", sans-serif
    .tags
      position fixed
      margin auto
      width 100%
      text-align center
      background-color  #f1f1f1
      transition transform .3s, -webkit-transform .3s
      z-index 2099
      &.slot-show
        transform: translateY(-100%)
      &.slot-hidden
        transform: translateY(0)
      >div
        .snow_tag
          margin 5px 0
    .sort
      margin 10px auto 0 auto
      height 30px
      line-height 30px
      font-size 15px
    .search-result-show
      font-size 14px
      color #626262
      font-family Raleway Arial Helvetica sans-serif
    .articles-inner
      display flex
      margin auto
      max-width 1500px
      padding-top 150px
      text-align center
      flex-direction row
      flex-wrap wrap
      justify-content center
      .article
        top 0
        height 249px
        width 382px
        cursor pointer
        display block
        margin 50px 38px
        position relative
        background-color rgb(255, 255, 255)
        overflow hidden
        padding-bottom 10px
        background-position center center
        box-shadow 0 0 20px rgba(77, 82, 78, 0.27)
        border-radius 3px
        &:hover
          .image-wrapper-inner
            .bg-img
              transform scale(1.2)
              transition all 1s linear
          .miniContent
            .title
              @keyframes spin {
                0% { transform: rotate(360deg) }
                40% { transform: rotate(0deg) }
                100% { transform: rotate(0deg) }
              }
              .banner
                animation spin 4s infinite
        .image-wrapper
          height 100%
          border-collapse collapse
          max-height 50%
          min-height 50%
          overflow hidden
          vertical-align middle
          text-align center
          .image-wrapper-inner
            padding 0
            height 100%
            transition all 0.3s linear
            .bg-img
              height 100%
              padding unset
              background-size cover
              background-repeat no-repeat
              transition all 0.3s linear
            .bg-letter
              height 100%
              line-height 135px
              transition all 0.3s linear
              font-size 64px
              font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol" font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol" font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol" font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"
              background rgba(107,195,147,0.05) linear-gradient(to right, rgba(166, 186, 193, 0.56), rgba(145, 119, 153, 0))
        .miniContent
          padding 10px 10px 0 10px
          text-align left
          font-family "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"
          .title
            text-align left
            overflow hidden
            text-overflow ellipsis
            white-space nowrap
            letter-spacing 0.8px
            font-size 16px
            color #1c4e1a
            font-family 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif
            .banner
              margin-right 8px
              margin-left 2px
              background black
              width 10px
              height 10px
              display inline-block
              content ''
              left -20px
              top 6px
          .desc
            max-height 63px
            margin 5px 0
            font-size 14px
            color rgba(0, 0, 0, 1)
            overflow hidden
            text-overflow ellipsis
            letter-spacing 0.3px
            display -webkit-box
            -webkit-line-clamp 3
            -webkit-box-orient vertical
          .subscribe
            height 15px
            line-height 15px
            text-align left
            margin 8px 0
            font-size 5px
            color darkgray
            > div
              display inline-block
              img
                margin-left 10px
                height 15px
                vertical-align middle
</style>
