<template>
  <div id="articles" class="articles" @wheel="l_scrollLoad">
    <show>
      <snow-input @keyup.enter.native="e_searchArticle"
                  @focus="e_searchInputFocus"
                  @blur="e_searchInputBlur"
                  placeholder=""
                  v-model="searchContent.searchInput">
      </snow-input>

      <div class="tags">
        <snow-tag :name="tag.name" :selected.sync="tag.selected" v-for="tag in tagsTemp" :key="tag.id"
                  @selected="e_tagSelected(tag)"
                  @unselected="e_tagUnSelected(tag)"></snow-tag>
      </div>
    </show>
    <div class="articles-inner"
         :class="classes.articlesInnerAppend"
         v-show="!isSearch"
    >
      <div @click="l_openArticle(article.articleId)" class="article"
           :class="classes.articleAppend"
           v-for="article in articles">
        <div class="image-wrapper" v-if="article.titleImgUrl">
          <div class="image-wrapper-inner">
            <div class="bg-img"
                 :style="{'background-image': 'url(' + fileBase + (!(article.titleImgUrl)?defaultImgPath:article.titleImgUrl) + ')'} "></div>
          </div>
        </div>
        <div class="miniContent">
          <div class="title">{{article.title}}</div>
          <div class="date">{{article.updateTime}}</div>
          <div :class="`${article.titleImgUrl?classes.descAppend:''}`" class="desc">{{article.contentTextSubNail}}</div>
        </div>
      </div>
      <div v-for="n in articlesAppendNum" :class="classes.articleAppend" class="article" v-visible="false"></div>
    </div>
    <div class="articles-inner"
         :class="classes.articlesInnerAppend"
         v-show="isSearch"
    >
      <div @click="l_openArticle(article.articleId)" class="article"
           :class="classes.articleAppend"
           v-for="article in articlesSearch">
        <div class="image-wrapper" v-if="article.titleImgUrl">
          <div class="image-wrapper-inner">
            <div class="bg-img"
                 :style="{'background-image': 'url(' + fileBase + (!(article.titleImgUrl)?defaultImgPath:article.titleImgUrl) + ')'} "></div>
          </div>
        </div>
        <div class="miniContent">
          <div class="title">{{article.title}}</div>
          <div class="date">{{article.updateTime}}</div>
          <div :class="`${article.titleImgUrl?classes.descAppend:''}`" class="desc">{{article.contentTextSubNail}}</div>
        </div>
      </div>
      <div v-for="n in articlesAppendNum" :class="classes.articleAppend" class="article" v-visible="false"></div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import show from '@/component/show'
  import {mapActions, mapGetters, mapMutations, mapState} from 'vuex'

  export default {
    data () {
      return {
        screenWidth: `${document.documentElement.clientWidth}`,
        classes: {
          articleAppend: '',
          articlesInnerAppend: '',
          descAppend: ''
        },
        articlesAppendNum: 0,
        defaultImgPath: '2018/03/07/17/33/06/e1fc525d-15ba-4112-90b1-335466c1f5ee.jpg',
        searchContent: null,
        searchContentLatest: '',
        searchResultShow: false,
        noArticleNotifyTimes: 10,
        tagsVisible: false,
        isSearch: false,
        tagsTemp: []
      }
    },
    components: {show},
    computed: {
      // all articles
      ...mapState('article/articles', ['articles']),
      ...mapState('article/articles', ['pagination']),
      ...mapGetters('article/articles', ['articlesNum']),

      // search articles
      ...mapState('article/articles', ['stateSearchContent']),

      ...mapState('article/articles', ['articlesSearch']),
      ...mapState('article/articles', ['paginationSearch']),
      ...mapGetters('article/articles', ['articlesNumSearch']),
      ...mapGetters('article/articles', ['articlesTotalNumSearch']),

      // tags articles
      // ...mapState('article/articles', ['articlesTags']),
      // ...mapState('article/articles', ['paginationTags']),

      // auth
      ...mapState('auth', ['userId', 'defaultUserId']),

      // user tags
      ...mapState('tag/tag', ['tags']),
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
      'searchContent': {
        handler () {
          this.l_tryChangeIsSearch()
        },
        deep: true
      },
      'isSearch' (val, oldVal) {
        if (!val) {
          this.searchResultShow = false
        }
        this.updateClasses()
      }
    },
    methods: {
      ...mapActions('article/articles', ['loadArticlePage', 'searchArticles']),
      ...mapActions('tag/tag', ['loadAllTags']),
      ...mapMutations('article/articles', ['CLEAR_ARTICLES_RESULT', 'CLEAR_ARTICLES_SEARCH_RESULT', 'SET_ARTICLES_SEARCH_CONTENT']),
      l_tryChangeIsSearch () {
        if (this.searchContent.searchInput.length > 0 || this.searchContent.selectedTagNamesSet.size > 0) {
          this.isSearch = true
        } else {
          this.isSearch = false
        }
        this.SET_ARTICLES_SEARCH_CONTENT(this.searchContent)
      },
      l_trySearch () {
        this.l_tryChangeIsSearch()
        if (this.isSearch) {
          console.log('发起搜索')
          this.l_searchArticlePage()
        }
      },
      e_tagSelected (tag) {
        tag.selected = true
        this.searchContent.selectedTagNamesSet.add(tag.name)
        this.CLEAR_ARTICLES_SEARCH_RESULT()
        this.l_trySearch()
      },
      e_tagUnSelected (tag) {
        tag.selected = false
        this.searchContent.selectedTagNamesSet.delete(tag.name)
        this.CLEAR_ARTICLES_SEARCH_RESULT()
        this.l_trySearch()
      },
      l_loadAllTags () {
        let userId = this.userId ? this.userId : this.defaultUserId
        this.loadAllTags({userId: userId})
      },
      e_searchInputFocus () {
        this.tagsVisible = true
      },
      e_searchInputBlur () {
        this.tagsVisible = false
      },
      l_scrollLoad (e) {
        // console.log('滚轮滚动事件', 'deltaY', e.deltaY)
        const that = this
        let scrollTop = document.documentElement.scrollTop || document.body.scrollTop
        let windowHeight = document.documentElement.clientHeight || document.body.clientHeight
        let scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
        if (scrollTop + windowHeight >= scrollHeight && e.deltaY > 0) {
          // that.l_loadArticlePage()
          if (this.noArticleNotifyTimes >= 6) {
            this.noArticleNotifyTimes = 0
            // console.log('到底了要加载')
            if (!this.isSearch) {
              that.l_loadArticlePage()
            } else {
              that.l_searchArticlePage()
            }
          } else {
            // console.log('到底了不加载')
            this.noArticleNotifyTimes++
          }
        }
      },
      l_loadArticlePage () {
        let userId = this.userId ? this.userId : this.defaultUserId
        if (!this.pagination) {
          // console.log('首次加载文章', this.pagination)
          this.loadArticlePage({userId: userId, page: 0, size: 10}).then(
            () => {
              this.updateClasses()
            }
          )
        } else if (!this.pagination.last || this.manualFlush) {
          // console.log('加载文章', this.pagination)
          this.loadArticlePage({userId: userId, page: (this.pagination.pageNumber + 1), size: 10}).then(
            () => {
              this.updateClasses()
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
      l_searchArticlePage () {
        let userId = this.userId ? this.userId : this.defaultUserId
        // 搜索旧输入内容
        if (!this.paginationSearch) {
          // 搜索首页
          this.searchArticles({
            userId: userId,
            title: this.searchContent.searchInput,
            tagNames: this.searchContent.selectedTagNamesSet,
            page: 0,
            size: 10
          }).then(
            () => {
              this.searchResultShow = true
              this.updateClasses()
            }
          )
        } else if (!this.paginationSearch.last) {
          // 搜索下一页
          this.searchArticles({
            userId: userId,
            title: this.searchContent.searchInput,
            tagNames: this.searchContent.selectedTagNamesSet,
            page: (this.paginationSearch.pageNumber + 1),
            size: 10
          }).then(
            () => {
              this.updateClasses()
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
      e_searchArticle () {
        if (this.isSearch) {
          // 判断是否需要搜索新内容
          let userId = this.userId ? this.userId : this.defaultUserId
          if (JSON.stringify(this.searchContentLatest) !== JSON.stringify(this.searchContent)) {
            // 搜索新输入内容的首页
            this.CLEAR_ARTICLES_SEARCH_RESULT()
            this.searchArticles({
              userId: userId,
              title: this.searchContent.searchInput,
              tagNames: this.searchContent.selectedTagNamesSet,
              page: 0,
              size: 10
            }).then(
              () => {
                this.searchResultShow = true
                this.searchContentLatest = this.searchContent
                this.updateClasses()
              }
            )
          } else {
            this.l_searchArticlePage()
          }
        } else {
          console.log('不是搜索状态')
        }
      },
      updateClasses () {
        // console.log('刷新文章列表样式', this.isSearch ? '搜索中' : '正常显示中', this.isSearch ? this.articlesNumSearch : this.articlesNum)
        if (this.screenWidth > 1428) {
          // console.log('检测到大屏')
          // this.articlesAppendNum = 3 - (this.isSearch ? this.articlesNumSearch : this.articlesNum) % 3
          this.articlesAppendNum = 0
          this.classes.articleAppend = 'articleSmall'
          this.classes.articlesInnerAppend = 'articlesFlexStart'
          this.classes.descAppend = 'descLineClamp3'
        } else if (this.screenWidth > 958) {
          // console.log('检测到中屏')
          // this.articlesAppendNum = (this.isSearch ? this.articlesNumSearch : this.articlesNum) % 2
          this.articlesAppendNum = 0
          this.classes.articleAppend = 'articleSmall'
          this.classes.articlesInnerAppend = 'articlesInnerBetween'
          this.classes.descAppend = 'descLineClamp4'
        } else {
          // console.log('检测到小屏')
          // this.articlesAppendNum = (this.isSearch ? this.articlesNumSearch : this.articlesNum) % 2
          this.articlesAppendNum = 0
          this.classes.articleAppend = 'articleSmall'
          this.classes.articlesInnerAppend = 'articlesInnerCenter'
          this.classes.descAppend = 'descLineClamp8'
        }
        // console.log('articlesAppendNum', this.articlesAppendNum)
      },
      l_openArticle (_articleId) {
        this.$router.push({path: `/article/${_articleId}`})
      }
    },
    created () {
      this.searchContent = this.stateSearchContent
      this.tagsTemp = this.tags
      this.tagsTemp.forEach(tag => {
        if (this.searchContent.selectedTagNamesSet.has(tag.name)) {
          tag.selected = true
        } else {
          tag.selected = false
        }
      })
      if (this.manualFlush || this.articles === null) {
        // console.log('刷新文章列表')
        this.CLEAR_ARTICLES_RESULT()
        this.l_loadArticlePage()
      }
      this.updateClasses()
      this.l_loadAllTags()
      this.l_trySearch()
    },
    mounted () {
      const that = this
      window.addEventListener('resize', function () {
        that.screenWidth = `${document.documentElement.clientWidth}`
        that.updateClasses()
      })
      window.addEventListener('load', function () {
        that.screenWidth = `${document.documentElement.clientWidth}`
      })
      // window.addEventListener('scroll', that.l_scrollLoad)
    },
    destroyed () {
      // const that = this
      // window.removeEventListener('scroll', that.l_scrollLoad)
    },
    beforeRouteUpdate (to, from, next) {
      console.log('query刷新', to.query)
      if (to.query.tagName) {
        // this.searchInput = to.query.tagName
      }
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
      padding-top 50px
      width 95%
      text-align center
      flex-direction row
      flex-wrap wrap
      .article
        cursor pointer
        display block
        margin 30px 10px
        border-radius 6px
        box-shadow 1px 1px 3px #dddddd
        background-color #f4f4f4fa
        overflow hidden
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
        &:hover
          box-shadow: 0 0 30px #cacaca
        .miniContent
          margin 15px 10px 10px 10px
          padding 0
          text-align center
          .title
            font-size 15px
            white-space nowrap
            overflow hidden
            text-overflow ellipsis
          .date
            margin 5px 0
            font-size 15px
          .desc
            color rgba(0, 0, 0, 0.7)
            display -webkit-box
            -webkit-box-orient vertical
            text-overflow ellipsis
            overflow hidden
          .descLineClamp3
            -webkit-line-clamp 3
          .descLineClamp4
            -webkit-line-clamp 3
          .descLineClamp8
            -webkit-line-clamp 3

  .articleLarge
    width 500px
    height 580px

  .articleMiddle
    width 380px
    height 420px

  .articleSmall
    width 220px
    height 280px

  .articlesFlexStart
    justify-content center

  .articlesInnerCenter
    justify-content center

  .articlesInnerBetween
    justify-content center
</style>
