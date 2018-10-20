<template>
  <div id="articles" class="articles" @wheel="e_scrollLoad" @touchmove="e_touchMoveLoad">
    <show>
      <snow-input @keyup.enter.native="e_searchArticle"
                  @focus="e_searchInputFocus"
                  @blur="e_searchInputBlur"
                  placeholder=""
                  v-model="searchInput">
      </snow-input>
      <div class="tags">
        <snow-tag :name="tag.name" :select="tag.selected" v-for="(tag,index) in state_tagNames" :key="tag.id"
                  @toggle="e_toggleSelect(index,tag)"></snow-tag>
      </div>
    </show>
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
          <div class="title">{{article.title}}</div>
          <div class="desc">{{article.contentTextSubNail}}</div>
          <div class="subscribe">{{article.updateTime}}
            <div>
              <img src="./see.png"/>
              {{article.readStatistic ? article.readStatistic.total: 0}}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="articles-inner"
         v-show="isSearch"
    >
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
          <div class="title">{{article.title}}</div>
          <div class="desc">{{article.contentTextSubNail}}</div>
          <div class="subscribe">{{article.updateTime}}<img src="./see.png"/></div>
        </div>
      </div>
    </div>
    <div class="loading">
      <img v-show="isLoading" src="../../assets/loading.gif" alt="">
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import show from '@/component/show'
  import {mapActions, mapGetters, mapMutations, mapState} from 'vuex'

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
        isLoading: false
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
      }
    },
    watch: {
      'searchInput' (nv, ov) {
        this.SET_ARTICLES_SEARCH_INPUT(nv)
        this.l_tryChangeIsSearch()
      },
      'isSearch' (val, oldVal) {
        if (!val) {
          this.searchResultShow = false
        }
      }
    },
    created () {
      this.searchInput = this.state_searchInput
      this.l_tryChangeIsSearch()
      if (this.manualFlush || this.articles === null) {
        this.CLEAR_ARTICLES_RESULT()
        if (this.isSearch) {
          this.l_searchArticlePage()
        } else {
          this.l_loadArticlePage()
        }
        this.l_tryInitTagNames()
      }
    },
    mounted () {
    },
    methods: {
      ...mapActions('article/articles', ['loadArticlePage', 'searchArticles', 'getSelectedTagNames', 'initTagNames']),
      ...mapActions('tag/tag', ['loadAllTags']),
      ...mapMutations('article/articles', ['CLEAR_ARTICLES_RESULT', 'CLEAR_ARTICLES_SEARCH_RESULT',
        'SET_ARTICLES_SEARCH_INPUT', 'INIT_TAG_NAMES', 'UPDATE_TAG_SELECT']),
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
          this.loadArticlePage({userId: userId, page: 0, size: 10}).then(
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
          this.loadArticlePage({userId: userId, page: (this.pagination.pageNumber + 1), size: 10}).then(
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
            offset: 35
          })
        }
      },
      async l_searchArticlePage () {
        const that = this
        let userId = that.userId ? that.userId : that.defaultUserId
        // 搜索旧输入内容
        that.isLoading = true
        if (!that.paginationSearch) {
          // 搜索首页
          this.searchArticles({
            userId: userId,
            title: that.searchInput,
            tagNames: that.state_selectedTagNames,
            page: 0,
            size: 10
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
          that.searchArticles({
            userId: userId,
            title: this.searchInput,
            tagNames: this.state_selectedTagNames,
            page: (this.paginationSearch.pageNumber + 1),
            size: 10
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
            offset: 35
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
            that.searchArticles({
              userId: userId,
              title: that.searchInput,
              tagNames: that.state_selectedTagNames,
              page: 0,
              size: 10
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
    },
    beforeRouteUpdate (to, from, next) {
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .articles
    font-family "Arial", "Microsoft YaHei", "黑体", "宋体", sans-serif
    .tags
      margin-top 14px
    .search-result-show
      font-size 14px
      color #626262
      font-family Raleway Arial Helvetica sans-serif
    .articles-inner
      display flex
      margin auto
      max-width 1200px
      text-align center
      flex-direction row
      flex-wrap wrap
      justify-content center
      .article
        height 280px
        width 280px
        cursor pointer
        display block
        margin 30px 18px
        background-color rgba(255, 255, 255, 0.98)
        overflow hidden
        padding-bottom 10px
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
            .bg-img
              height 100%
              padding unset
              background-size cover
              background-repeat no-repeat
              background-position center center
            .bg-letter
              height 100%
              line-height 135px
              background #f9f9f9 linear-gradient(to right, rgba(144, 195, 194, 0.17), #dedfea42)
              font-size 50px
              font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol" font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol" font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol" font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"
        /*&:hover*/
        /*box-shadow: 0 0 30px #cacaca*/
        .miniContent
          padding-top 5px
          text-align left
          font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol" font-family -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"
          .title
            text-align left
            font-size 16px
            color #080809
            font-family 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif
            white-space nowrap
            overflow hidden
            text-overflow ellipsis
            &::before
              margin-right 10px
              background #212226
              width 10px
              height 10px
              display inline-block
              content ''
              left -20px
              top 6px
          .desc
            height 54px
            margin 5px 0
            font-size 14px
            color rgba(0, 0, 0, 0.7)
            overflow hidden
            text-overflow ellipsis
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
    .loading
      height 100px
      text-align center
      img
        height 100px
</style>
