<template>
  <div id="articles" class="articles">
    <show>
      <snow-input @keyup.enter.native="e_searchArticle"
                  placeholder="输入回车以开始搜索"
                  v-model="searchInput">
      </snow-input>
      <transition name="el-fade-in-linear">
        <span class="search-result-show" v-visible="searchResultShow">{{`${articlesTotalNumSearch + (articlesTotalNumSearch>1?' results':' result')}`}}</span>
      </transition>
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
            <!--<img v-if="article.titleImgUrl" :src="fileBase+article.titleImgUrl"/>-->
            <!--<img v-if="!article.titleImgUrl" :src="fileBase + defaultImgPath"/>-->
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
        <div class="image-wrapper"  v-if="article.titleImgUrl">
          <div class="image-wrapper-inner">
            <!--<img v-if="article.titleImgUrl" :src="fileBase+article.titleImgUrl"/>-->
            <!--<img v-if="!article.titleImgUrl" :src="fileBase + defaultImgPath"/>-->
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
  import {createNamespacedHelpers} from 'vuex'

  const {mapState, mapGetters, mapMutations, mapActions} = createNamespacedHelpers('article/articles')

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
        searchInput: '',
        searchResultShow: false
      }
    },
    components: {show},
    computed: {
      ...mapState(['articles']),
      ...mapState(['articlesSearch']),
      ...mapState(['stateSearchInput']),
      ...mapGetters(['articlesNum']),
      ...mapGetters(['articlesNumSearch']),
      ...mapGetters(['articlesTotalNumSearch']),
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
      isSearch () {
        let state = this.searchInput && this.searchInput.length > 0
        return state
      }
    },
    watch: {
      'isSearch' (val, oldVal) {
        if (!val) {
          this.searchResultShow = false
          this.CLEAR_ARTICLES_SEARCH_RESULT()
        }
        console.log('刷新搜索状态')
        this.updateClasses()
      },
      'searchInput' (val, oldVal) {
        this.SET_ARTICLES_SEARCH_INPUT(val)
      }
    },
    methods: {
      ...mapActions(['loadArticlePage', 'loadArticleSearchPage']),
      ...mapMutations(['CLEAR_ARTICLES_SEARCH_RESULT', 'SET_ARTICLES_SEARCH_INPUT']),
      l_loadArticlePage () {
        this.loadArticlePage().then(
          () => {
            this.updateClasses()
          }
        )
      },
      e_searchArticle () {
        if (this.searchInput && this.searchInput !== '') {
          this.loadArticleSearchPage('%' + this.searchInput + '%').then(
            () => {
              this.searchResultShow = true
            }
          )
        }
      },
      updateClasses () {
        console.log('刷新文章列表样式', this.isSearch ? '搜索中' : '正常显示中', this.isSearch ? this.articlesNumSearch : this.articlesNum)
        if (this.screenWidth > 1428) {
          console.log('检测到大屏')
          // this.articlesAppendNum = 3 - (this.isSearch ? this.articlesNumSearch : this.articlesNum) % 3
          this.articlesAppendNum = 0
          this.classes.articleAppend = 'articleSmall'
          this.classes.articlesInnerAppend = 'articlesFlexStart'
          this.classes.descAppend = 'descLineClamp3'
        } else if (this.screenWidth > 958) {
          console.log('检测到中屏')
          // this.articlesAppendNum = (this.isSearch ? this.articlesNumSearch : this.articlesNum) % 2
          this.articlesAppendNum = 0
          this.classes.articleAppend = 'articleMiddle'
          this.classes.articlesInnerAppend = 'articlesInnerBetween'
          this.classes.descAppend = 'descLineClamp4'
        } else {
          console.log('检测到小屏')
          // this.articlesAppendNum = (this.isSearch ? this.articlesNumSearch : this.articlesNum) % 2
          this.articlesAppendNum = 0
          this.classes.articleAppend = 'articleLarge'
          this.classes.articlesInnerAppend = 'articlesInnerCenter'
          this.classes.descAppend = 'descLineClamp8'
        }
        console.log('articlesAppendNum', this.articlesAppendNum)
      },
      l_openArticle (_articleId) {
        this.$router.push({path: `/article/${_articleId}`})
      }
    },
    created () {
      this.searchInput = this.stateSearchInput
      if (this.manualFlush || this.articles === null) {
        console.log('刷新文章列表')
        this.l_loadArticlePage()
      }
      this.updateClasses()
    },
    mounted () {
      const that = this
      window.onresize = () => {
        that.screenWidth = `${document.documentElement.clientWidth}`
        that.updateClasses()
      }
      window.onload = () => {
        that.screenWidth = `${document.documentElement.clientWidth}`
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .articles
    .search-result-show
      font-size 14px
      color #626262
      font-family Raleway Arial Helvetica sans-serif
    .articles-inner
      display flex
      margin auto
      padding-top 50px
      width 85%
      text-align center
      flex-direction row
      flex-wrap wrap
      .article
        display block
        margin 30px 10px
        border-radius 6px
        box-shadow 1px 1px 3px #dddddd
        background-color rgba(255, 255, 255, 0.9)
        overflow hidden
        .image-wrapper
          height 100%
          border-collapse collapse
          max-height 50%
          min-height 50%
          overflow hidden
          vertical-align middle
          text-align center
          /*display table*/
          .image-wrapper-inner
            padding 0
            height 100%
            /*display table-cell*/
            /*vertical-align middle*/
            .bg-img
              height 100%
              padding unset
              background-size cover
              background-repeat no-repeat
              background-position center center
        &:hover
          box-shadow: 0 0 30px #cacaca
        .miniContent
          margin 25px 20px 10px 20px
          padding 0
          text-align center
          .title
            font-size 23px
            white-space nowrap
            overflow hidden
            text-overflow ellipsis
          .date
            margin 10px 0
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
            -webkit-line-clamp 4
          .descLineClamp8
            -webkit-line-clamp 8

  .articleLarge
    width 500px
    height 580px

  .articleMiddle
    width 380px
    height 420px

  .articleSmall
    width 320px
    height 380px
  .articlesFlexStart
    justify-content center
  .articlesInnerCenter
    justify-content center
  .articlesInnerBetween
    justify-content center
</style>
