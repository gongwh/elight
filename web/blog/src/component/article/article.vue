<template>
  <div class="article" id="article">
    <div class="articleWrapper">
      <snowDialog :visible.sync='deleteDialogVisible' :position="delDialogPosition">
        <div>确认删除?</div>
        <div class="dialog_content">
          <el-button type="primary" @click="l_cancelDelete">取 消</el-button>
          <el-button type="primary" @click="l_confirmDelete">确 定</el-button>
        </div>
      </snowDialog>
      <!--<el-dialog :visible.sync='deleteDialogVisible' :modal="false"></el-dialog>-->
      <show :title="articleTemp.title" :detail="articleTemp.updateTime" :backgroundUrl="articleTemp.titleImgUrl"></show>
      <div v-loading="loading" v-if="!loading" class="article_wrapper">
        <markdownShow :html="articleTemp.contentHtml"></markdownShow>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import show from '@/component/show'
  import { mapState, mapActions, mapMutations } from 'vuex'
  import markdownShow from '@/component/article/markdown_show'

  export default {
    data () {
      return {
        loading: true,
        articleTemp: {
          title: '',
          contentHtml: '',
          updateTime: ''
        },
        articleTempSetup: {
          title: '',
          contentHtml: '',
          updateTime: ''
        },
        deleteDialogVisible: false,
        delDialogPosition: {x: 0, y: 35}
      }
    },
    components: {show, markdownShow},
    computed: {
      ...mapState('article/article', ['article'])
    },
    methods: {
      ...mapActions('article/article', ['loadArticle', 'deleteArticle']),
      ...mapMutations(['SET_BUTTON_STATE']),
      l_openDialog () {
        this.l_setDialogPosition()
        this.deleteDialogVisible = true
      },
      l_setDialogPosition () {
        this.delDialogPosition = this.GetElementBottomMiddlePosition('navigation_block_6')
        this.delDialogPosition.y = 35
      },
      l_loadArticle () {
        console.log('加载文章', this.$route)
        if (!this.article || !this.article.articleId || this.article.articleId !== this.$route.query.articleId) {
          this.loadArticle(this.$route.query.articleId).then(loadOk => {
            if (loadOk) {
              this.l_gotoPageTop()
              this.articleTemp = this.article
            }
          })
        } else {
          this.articleTemp = this.article
        }
        this.l_updateTopButtonAddEdit()
        this.l_updateTopButtonAddDel()
        this.loading = false
      },
      l_gotoPageTop () {
        document.documentElement.scrollTop = document.body.scrollTop = 0
      },
      l_cancelDelete () {
        this.deleteDialogVisible = false
      },
      l_confirmDelete () {
        this.deleteDialogVisible = false
        this.l_deleteArticle()
      },
      l_deleteArticle () {
        this.deleteArticle(this.articleTemp).then((delOk) => {
          if (delOk) {
            this.$router.push({name: 'articles', params: {flush: true}})
            this.$notify.success({
              title: '删除文章',
              message: '成功',
              offset: 30
            })
          } else {
            this.$notify.error({
              title: '删除文章',
              message: '失败',
              offset: 30
            })
          }
        })
      },
      l_updateTopButtonUpdateReading () {
        if (this.article && this.article.articleId) {
          this.SET_BUTTON_STATE(
            {
              isDisplay: true,
              displayName: this.article.title,
              index: 7,
              query: {articleId: this.article.articleId}
            }
          )
        } else {
          this.SET_BUTTON_STATE(
            {
              isDisplay: false,
              index: 7
            }
          )
        }
      },
      l_updateTopButtonAddEdit () {
        if (this.article) {
          if (this.article.articleId) {
            console.log('文章已准备好编辑')
            this.SET_BUTTON_STATE(
              {
                isDisplay: true,
                index: 5,
                params: {articleId: this.article.articleId}
              }
            )
          }
        }
      },
      l_updateTopButtonRemoveEdit () {
        this.SET_BUTTON_STATE(
          {
            isDisplay: false,
            index: 5
          }
        )
      },
      l_updateTopButtonAddDel () {
        if (this.article) {
          if (this.article.articleId) {
            this.SET_BUTTON_STATE(
              {
                isDisplay: true,
                index: 6
              }
            )
          }
        }
      },
      l_updateTopButtonRemoveDel () {
        this.SET_BUTTON_STATE(
          {
            isDisplay: false,
            index: 6
          }
        )
      }
    },
    created () {
      this.l_loadArticle()
    },
    mounted () {
    },
    beforeRouteUpdate (to, from, next) {
      if (to.query && to.query.delete) {
        this.l_openDialog()
      } else {
        this.l_updateTopButtonUpdateReading()
        next()
      }
    },
    beforeDestroy () {
      this.l_updateTopButtonUpdateReading()
      this.l_updateTopButtonRemoveEdit()
      this.l_updateTopButtonRemoveDel()
    },
    watch: {
      '$route' (to) {
        console.log('路由监控-article', to)
        this.l_loadArticle()
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus" scoped>

  .dialog_content
    margin-top 30px

  .article
    .article_wrapper
      *
        z-index 100
      margin auto
      margin-top 40px
      width 60%
      min-height 500px
      .title
        font-size 23px
        font-weight 600
      .date
        font-family "Lora", serif
        margin 10px 0

  .article-body {
    box-sizing border-box;
    min-width 200px;
    max-width 980px;
    margin 0 auto;
    padding 45px;
  }

  @media (max-width: 767px) {
    .markdown-body {
      padding 15px;
    }
  }

</style>
