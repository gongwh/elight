<template>
  <div class="article" id="article">
    <div class="article_wrapper">
      <snow-dialog :visible.sync='deleteDialogVisible' :position="delDialogPosition">
        <div>are you sure ?</div>
        <div class="dialog_content">
          <el-button type="primary" @click="l_cancelDelete">cancel</el-button>
          <el-button type="primary" @click="l_confirmDelete">sure</el-button>
        </div>
      </snow-dialog>
      <show :title="articleTemp.title" :detail="articleTemp.updateTime" :backgroundUrl="articleTemp.titleImgUrl"></show>
      <div v-loading="loading" v-if="!loading" class="article_body">
        <markdownShow :html="articleTemp.contentHtml"></markdownShow>
      </div>
    </div>
    <div class="loading">
      <img v-show="loading" src="../../assets/loading.gif" alt="">
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import show from '@/component/show'
  import {mapActions, mapMutations, mapState} from 'vuex'
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
      ...mapActions('article/article', ['loadArticle', 'deleteArticle', 'deleteSiteSearch']),
      ...mapMutations(['SET_BUTTON_STATE']),
      l_exportAsPdf () {
        window.print()
      },
      l_openDeleteDialog () {
        this.l_setDialogPosition()
        this.deleteDialogVisible = true
      },
      l_setDialogPosition () {
        this.delDialogPosition = this.GetElementBottomMiddlePosition('navigation_block_6')
        this.delDialogPosition.y = 35
      },
      l_filterAndReplaceImg (html) {
        // 给图片添加预览支持
        return html.replace(/<img src="((http|https|ftp):\/\/(.*?)\/api\/file\/(.*?))" alt="(.*)" \/>/g, '<img preview="0" preview-text="$5" src="' + this.fileBase + '$4" alt="$5" />')
      },
      l_loadArticle () {
        const that = this
        if (!this.article || !this.article.articleId || this.article.articleId !== this.$route.params.articleId) {
          this.loadArticle(this.$route.params.articleId).then(({success, message}) => {
            if (success) {
              that.l_onLoadFinished()
            } else {
              that.$notify.warning({
                title: '文章加载',
                message: message,
                offset: 80
              })
            }
          }
          )
        } else {
          that.l_onLoadFinished()
        }
      },
      l_onLoadFinished () {
        this.NavigateToTop()
        this.articleTemp = this.article
        document.title = this.articleTemp.title
        this.l_updateTopButtonAddEdit()
        this.l_updateTopButtonAddExport()
        this.l_updateTopButtonAddDel()
        this.loading = false
        const that = this
        that.$nextTick(function () {
          that.$previewRefresh()
        })
        this.$emit('global:HeadSlotTitleChange', this.articleTemp.title)
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
          this.deleteSiteSearch(this.articleTemp.articleId)
          if (delOk) {
            this.$router.push({path: '/articles/list', query: {flush: true}})
            this.$notify.success({
              title: '删除文章',
              message: '成功',
              offset: 80
            })
          } else {
            this.$notify.error({
              title: '删除文章',
              message: '失败',
              offset: 80
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
              index: 8,
              path: `/article/${this.articleTemp.articleId}`
            }
          )
        } else {
          this.SET_BUTTON_STATE(
            {
              isDisplay: false,
              index: 8
            }
          )
        }
      },
      l_updateTopButtonAddExport () {
        if (this.article) {
          // console.log('文章已准备好编辑')
          this.SET_BUTTON_STATE(
            {
              isDisplay: true,
              index: 7,
              path: `/article/${this.article.articleId}`
            }
          )
        }
      },
      l_updateTopButtonRemoveExport () {
        this.SET_BUTTON_STATE(
          {
            isDisplay: false,
            index: 7
          }
        )
      },
      l_updateTopButtonAddEdit () {
        // console.log('article detail', this.article)
        if (this.article) {
          if (this.article.articleId && this.article.userId === window.localStorage.getItem('userId')) {
            // console.log('文章已准备好编辑')
            this.SET_BUTTON_STATE(
              {
                isDisplay: true,
                index: 5,
                query: {articleId: this.article.articleId}
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
          if (this.article.articleId && this.article.userId === window.localStorage.getItem('userId')) {
            this.SET_BUTTON_STATE(
              {
                isDisplay: true,
                index: 6,
                path: `/article/${this.article.articleId}`
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
      },
      l_beforePrint () {
        document.getElementById('myHead').style.display = 'none'
        document.getElementById('myFoot').style.display = 'none'
      },
      l_afterPrint () {
        document.getElementById('myHead').style.display = 'unset'
        document.getElementById('myFoot').style.display = 'table'
      },
      l_addEventListener () {
        window.addEventListener('beforeprint', this.l_beforePrint)
        window.addEventListener('afterprint', this.l_afterPrint)
      },
      l_removeEventListener () {
        window.removeEventListener('beforeprint', this.l_beforePrint)
        window.removeEventListener('afterprint', this.l_afterPrint)
      }
    },
    created () {
      this.l_loadArticle()
      this.l_addEventListener()
      this.$emit('global:HeadSlotShow', false)
    },
    mounted () {
    },
    beforeRouteUpdate (to, from, next) {
      // console.log('to', to)
      if (to.query) {
        if (to.query.delete) {
          this.l_openDeleteDialog()
        } else if (to.query.exp) {
          // console.log('准备导出', to.query.exp)
          this.l_exportAsPdf()
        } else {
          this.l_updateTopButtonUpdateReading()
          next()
        }
      } else {
        this.l_updateTopButtonUpdateReading()
        next()
      }
    },
    beforeDestroy () {
      this.l_updateTopButtonUpdateReading()
      this.l_updateTopButtonRemoveEdit()
      this.l_updateTopButtonRemoveExport()
      this.l_updateTopButtonRemoveDel()
      this.l_removeEventListener()
    },
    watch: {
      '$route' (to) {
        // console.log('路由监控-article', to)
        this.l_loadArticle()
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">

  .pswp--open
    z-index 5000

  .pswp__caption__center
    text-align center

  .article
    img
      cursor pointer
    .article_wrapper
      width 100%
      margin auto
      min-height 500px
      .title
        font-size 23px
        font-weight 600
      .date
        font-family "Lora", serif
        margin 10px 0
      .article_body
        width 45%
        min-width 800px
        margin 30px auto
        padding 0 30px 0 0

  .loading
    height 100px
    text-align center
    img
      height 100px
  @media (max-width: 767px) {
    .markdown-body {
      padding 15px;
    }
  }

</style>
