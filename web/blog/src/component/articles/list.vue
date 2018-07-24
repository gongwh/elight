<template>
  <div id="articles" class="articles">
    <show title="xxx" detail="He is rich enough that wants nothing"></show>
    <div class="articles-inner" :class="classes.articlesInnerAppend">
      <div @click="l_openArticle(article.articleId)" class="article"
           :class="classes.articleAppend"
           v-for="article in articles">
        <div class="image-wrapper">
          <div class="image-wrapper-inner">
            <img v-if="article.titleImgUrl" :src="fileBase+article.titleImgUrl"/>
            <img v-if="!article.titleImgUrl" :src="fileBase + defaultImgPath"/>
          </div>
        </div>
        <div class="content">
          <div class="title">{{article.title}}</div>
          <div class="date">{{article.updateTime}}</div>
          <div :class="classes.descAppend" class="desc">{{article.contentText}}</div>
        </div>
      </div>
      <div v-for="n in articlesAppendNum" :class="classes.articleAppend" class="article" v-visible="false"></div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import show from '@/component/show'
  import {createNamespacedHelpers} from 'vuex'

  const {mapState, mapGetters, mapActions} = createNamespacedHelpers('article/articles')
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
        defaultImgPath: '2018/03/07/17/33/06/e1fc525d-15ba-4112-90b1-335466c1f5ee.jpg'
      }
    },
    components: {show},
    computed: {
      ...mapState(['articles']),
      ...mapGetters(['articlesNum']),
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
    methods: {
      ...mapActions(['loadArticlePage']),
      updateClasses () {
        if (this.screenWidth > 1428) {
          this.articlesAppendNum = 3 - this.articlesNum % 3
          this.classes.articleAppend = 'articleSmall'
          this.classes.articlesInnerAppend = 'articlesInnerBetween'
          this.classes.descAppend = 'descLineClamp2'
        } else if (this.screenWidth > 958) {
          this.articlesAppendNum = this.articlesNum % 2
          this.classes.articleAppend = 'articleMiddle'
          this.classes.articlesInnerAppend = 'articlesInnerBetween'
          this.classes.descAppend = 'descLineClamp4'
        } else {
          this.classes.articleAppend = 'articleLarge'
          this.classes.articlesInnerAppend = 'articlesInnerCenter'
          this.classes.descAppend = 'descLineClamp8'
        }
      },
      l_openArticle (_articleId) {
        this.$router.push({ path: `/article/${_articleId}` })
      }
    },
    created () {
      if (this.manualFlush || this.articles === null) {
        console.log('刷新文章列表')
        this.loadArticlePage(0, 10)
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
          border-radius 6px
          display table
          border-collapse collapse
          max-height 50%
          min-height 50%
          overflow hidden
          vertical-align middle
          text-align center
          .image-wrapper-inner
            padding 0
            display table-cell
            vertical-align middle
            .image
              height 100%

        &:hover
          box-shadow: 0 0 30px #cacaca
        .content
          margin 25px 20px
          padding 0
          text-align center
          .title
            font-size 23px
          /*font-weight 600*/
          .date
            margin 10px 0
            font-size 15px

          .desc
            color rgba(0, 0, 0, 0.7)
            display -webkit-box
            -webkit-box-orient vertical
            text-overflow ellipsis
            overflow hidden
          .descLineClamp2
            -webkit-line-clamp 2
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

  .articlesInnerCenter
    justify-content center

  .articlesInnerBetween
    justify-content space-between
</style>
