<template>
  <div class="editor">
    <div class="title"></div>
    <mavon-editor ref=md :toolbars="toolbars"
                  v-model="draftTemp.contentMd"
                  :placeholder="begin"
                  @imgAdd="l_imgAdd"
                  @imgDel="l_imgDel"
                  @save="e_save" @change="e_change">
    </mavon-editor>
  </div>
</template>

<script type="text/ecmascript-6" scoped>
  import { mapState, mapActions, mapMutations } from 'vuex'
  import upDown from '@/api/util/updown'

  export default {
    data () {
      return {
        draftTemp: {
          contentMd: '',
          contentHtml: '',
          draftId: null,
          articleId: null,
          userId: '123456'
        },
        draftSetup: {
          contentMd: '',
          contentHtml: '',
          draftId: null,
          articleId: null,
          userId: '123456'
        },
        imgFile: {},
        begin: 'Begin ...',
        autoSave: null,
        toolbars: {
          bold: true, // 粗体
          italic: true, // 斜体
          header: true, // 标题
          underline: true, // 下划线
          strikethrough: true, // 中划线
          mark: true, // 标记
          superscript: true, // 上角标
          subscript: true, // 下角标
          quote: true, // 引用
          ol: true, // 有序列表
          ul: true, // 无序列表
          link: true, // 链接
          imagelink: true, // 图片链接
          code: true, // code
          table: true, // 表格
          fullscreen: false, // 全屏编辑
          readmodel: true, // 沉浸式阅读
          htmlcode: false, // 展示html源码
          help: true, // 帮助
          undo: true, // 上一步
          redo: true, // 下一步
          trash: true, // 清空
          save: true, // 保存（触发events中的save事件）
          navigation: false, // 导航目录
          alignleft: true, // 左对齐
          aligncenter: true, // 居中
          alignright: true, // 右对齐
          subfield: true, // 单双栏模式
          preview: true // 预览
        },
        external_link: {
          hljs_css: function (css) {
            // 这是你的代码高亮配色文件路径
            return 'https://cdn.bootcss.com/highlight.js/9.12.0/styles/' + css + '.min.css'
          }
        }
      }
    },
    computed: {
      ...mapState(['topButtonsProps']),
      ...mapState('write/editor', ['draft', 'sync', 'loading'])
    },
    created () {
      this.l_loadEditor()
      this.l_updateTopButton()
    },
    mounted () {
      this.l_updateTopButton()
    },
    beforeRouteUpdate (to, from, next) {
      if (to.query.abandon) {
        this.l_abandon()
      } else if (to.query.save) {
        this.l_saveDraft()
      }
    },
    beforeDestroy () {
      // 保存文章
      this.l_saveDraft()
      // 关闭顶部相关按钮
      this.SET_BUTTON_STATE({display: false, index: 2})
      this.SET_BUTTON_STATE({display: false, index: 3})
      this.SET_BUTTON_STATE({display: false, index: 4})
    },
    methods: {
      l_imgAdd (pos, $file) {
        this.imgFile[pos] = $file
      },
      l_imgDel (pos) {
        delete this.imgFile[pos]
      },
      l_uploadImg () {
        if (Object.keys(this.imgFile).length !== 0) {
          upDown.upload(this.imgFile).then(
            (data) => {
              for (let i in data) {
                delete this.imgFile[i]
                this.$refs.md.$img2Url(i, this.fileBase + '' + data[i])
              }
            }
          )
        }
      },
      l_loadEditor () {
        if (this.$route.params.articleId) {
          this.l_loadEditorByArticle()
        } else {
          this.l_loadEditorByNewest()
        }
      },
      l_loadEditorByNewest () {
        if (!this.draft) {
          this.loadNewestDraft('123456').then((loadOk) => {
            console.log('加载最新草稿成功', this.draft)
            if (loadOk && this.draft) {
              this.draftTemp = this.draft
              this.l_updateTopButton()
              this.$notify.info({
                title: '编辑器',
                message: '已加载最新草稿',
                offset: 70
              })
            }
          })
        }
        if (this.sync) {
          this.draftTemp = this.draft
          this.l_updateTopButton()
        }
      },
      l_loadEditorByArticle () {
        // draft空                     直接加载
        // draft不空
        //   文章id为空                直接加载
        //   文章id不为空且id相同       不做任何动作
        //   文章id不为空且id不同       提示已有文章正在编辑
        if (!this.draft || !this.draft.articleId) {
          this.loadDraftByArticleId({'articleId': this.$route.params.articleId}).then((loadOk) => {
            console.log('加载文章草稿成功', this.draft)
            if (loadOk && this.draft) {
              this.draftTemp = this.draft
            }
          })
        } else if (this.draft.articleId !== this.$route.params.articleId) {
          this.draftTemp = this.draft
          this.$notify.warning({
            title: '编辑器',
            message: '已有文章正在编辑',
            offset: 70
          })
        } else if (this.draft.articleId === this.$route.params.articleId) {
          this.draftTemp = this.draft
        }
      },
      l_saveDraft () {
        if (!this.sync && this.draftTemp) {
          try {
            this.l_uploadImg()
            this.saveDraft(this.draftTemp)
          } catch (err) {
            this.$notify.error({
              title: '草稿保存',
              message: '保存失败',
              offset: 30
            })
          }
        }
      },
      l_clearEditor () {
        console.group('准备清空store工作区')
        this.clearEditor()
        console.group('准备清空本地工作区')
        // 清空本地工作区，浅拷贝
        this.draftTemp = this.draftSetup
      },
      l_abandon () {
        console.group('准备删除服务器草稿')
        // 删除草稿
        this.deleteDraft(this.draftTemp).then((ok) => {
          if (ok) {
            // 清空工作区
            this.l_clearEditor()
            // 重新加载工作区
            console.group('重新加载工作区')
            this.l_loadEditorByNewest()
          }
        })
      },
      l_autoSave () {
        const _this = this
        if (_this.autoSave) {
          clearTimeout(_this.autoSave)
          _this.autoSave = null
        }
        _this.autoSave = setTimeout(_this.l_autoSave_cb, 20000, _this)
      },
      l_autoSave_cb (_this) {
        _this.l_saveDraft()
      },
      l_updateTopButton () {
        console.log('刷新顶部')
        if (this.draftTemp.contentMd !== '') {
          this.SET_BUTTON_STATE({isDisplay: true, index: 4})
          if (this.sync) {
            this.SET_BUTTON_STATE({isDisplay: true, index: 2})
          } else {
            this.SET_BUTTON_STATE({isDisplay: false, index: 2})
          }
        } else {
          this.SET_BUTTON_STATE({isDisplay: false, index: 2})
          this.SET_BUTTON_STATE({isDisplay: false, index: 3})
          this.SET_BUTTON_STATE({isDisplay: false, index: 4})
        }
        if (this.sync) {
          this.SET_BUTTON_STATE({isDisplay: false, index: 3})
        } else {
          this.SET_BUTTON_STATE({isDisplay: true, index: 3})
        }
      },
      ...mapActions('write/editor', ['saveDraft', 'loadNewestDraft', 'loadDraftByArticleId', 'updateSync', 'deleteDraft', 'clearEditor']),
      ...mapMutations(['SET_BUTTON_STATE']),
      e_save () {
        // 清除自动保存
        if (this.autoSave) {
          clearTimeout(this.autoSave)
          this.autoSave = null
        }
        this.l_saveDraft()
      },
      e_change (md, html) {
        console.log('编辑器更改this.draft', this.draft)
        this.draftTemp.contentMd = md
        this.draftTemp.contentHtml = html
        this.updateSync(false)
        this.l_updateTopButton()
        this.l_autoSave()
      }
    },
    watch: {
      sync () {
        this.l_updateTopButton()
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import '../md'
  .editor
    height 700px

  .v-note-op
    position fixed
    margin-top 50px
  .v-note-panel
    padding-top 10px
    z-index -1
</style>
