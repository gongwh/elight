<template>
  <div class="editor">
    <div class="editor_wrapper" v-loading="isLoading">
      <mavon-editor ref=md :toolbars="toolbars"
                    v-model="draftTemp.contentMd"
                    :placeholder="begin"
                    :externalLink="externalLink"
                    @imgAdd="l_imgAdd"
                    @imgDel="l_imgDel"
                    @save="e_save" @change="e_change">
      </mavon-editor>
      <div class="publish_wrapper" v-show="publishVisible">
        <div class="publish">
          <div class="main">
            <div class="title">
              <div class="text">Title</div>
              <el-input placeholder="" v-model="articleTemp.title">
              </el-input>
            </div>
            <div class="tag">
              <div class="text">Tag</div>
              <div class="tag_inputs">
                <div class="tag_input" v-for="(tagName,index) in tagNames" :key="index">
                  <el-input placeholder="" v-model="tagNames[index]">
                  </el-input>
                  <i class="el-icon-remove" @click="tagNames.splice(index,1)"></i>
                </div>
                <i class="el-icon-circle-plus" @click="tagNames.push('')"></i>
              </div>
            </div>
            <div class="upload-img">
              <div class="text">Figure</div>
              <div>
                <el-upload
                  :action="fileBase"
                  :multiple="false"
                  :name="titleImgName"
                  :limit="1"
                  :on-remove="e_whenTitleImgRemove"
                  :on-success="e_whenTitleImgUploadSuccess">
                  <i class="el-icon-circle-plus"></i>
                </el-upload>
              </div>
            </div>
            <div class="handle">
              <el-button type="primary" size="medium" @click="e_closePublish">Cancel</el-button>
              <el-button type="primary" size="medium" @click="e_publish">Publish</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import {mapActions, mapMutations, mapState} from 'vuex'
  import upDown from '@/api/util/updown'

  const htmlToText = require('html-to-text')

  export default {
    data () {
      return {
        draftTemp: {
          contentMd: '',
          draftId: null,
          articleId: null
        },
        draftSetup: {
          contentMd: '',
          draftId: null,
          articleId: null
        },
        articleTemp: {},
        articleSetup: {
          contentMd: '',
          draftId: null,
          articleId: null
        },
        titleImgName: 'titleImg',
        tagNames: [],
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
        externalLink: {
          hljs_css: false,
          markdown_css: false
        },
        isLoading: true,
        publishVisible: false
      }
    },
    computed: {
      ...mapState(['topButtonsProps']),
      ...mapState('write/editor', ['draft', 'sync']),
      ...mapState('article/article', ['article'])
    },
    created () {
      this.l_loadEditor()
      this.l_updateTopButton()
      this.$emit('global:HeadSlotShow', false)
    },
    destroyed () {
      this.$emit('global:HeadSlotShow', true)
    },
    mounted () {
      this.l_updateTopButton()
    },
    beforeRouteUpdate (to, from, next) {
      if (to.query.abandon) {
        this.l_abandon()
      } else if (to.query.publish) {
        this.e_openPublish()
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
    activate () {
      this.l_autoSave()
    },
    deactivate () {
      this.l_clearAutoSave()
    },
    methods: {
      ...mapActions('write/editor', ['saveDraft', 'loadNewestDraft', 'loadDraftByArticleId', 'updateSync', 'deleteDraft', 'clearEditor']),
      ...mapActions('article/article', ['saveArticle', 'loadArticle', 'addSiteSearch', 'updateSiteSearch']),
      ...mapMutations(['SET_BUTTON_STATE']),
      e_whenTitleImgRemove (file, fileList) {
        this.articleTemp.titleImageUrl = ''
      },
      e_whenTitleImgUploadSuccess (response, file, fileList) {
        if (response.message === 'success') {
          this.articleTemp.titleImgUrl = response.data[this.titleImgName]
        }
      },
      e_closePublish () {
        this.publishVisible = false
      },
      e_publish () {
        const _this = this
        // 保存文章
        // console.log('准备发布保存文章', this.draftTemp)
        let tags = []
        for (let i = 0; i < _this.tagNames.length; i++) {
          if (this.tagNames[i] && this.tagNames[i] !== '') {
            tags.push({'name': _this.tagNames[i]})
          }
        }
        _this.articleTemp.tags = tags
        _this.articleTemp.contentMd = this.draftTemp.contentMd
        _this.articleTemp.contentHtml = this.l_filterAndReplaceImg(_this.articleTemp.contentHtml)
        _this.articleTemp.contentText = htmlToText.fromString(_this.articleTemp.contentHtml, {
          wordwrap: 130
        })
        // console.log('发布前情况', _this.articleTemp)
        _this.saveArticle(this.articleTemp).then(saveOk => {
          if (saveOk) {
            if (this.draftTemp.articleId) {
              this.updateSiteSearch(this.draftTemp.articleId)
            } else {
              this.addSiteSearch(this.draftTemp.articleId)
            }
            // 删除草稿
            if (_this.draftTemp.draftId) {
              // console.log('准备删除草稿', this.draftTemp)
              _this.deleteDraft(this.draftTemp.draftId).then((res) => {
                if (res) {
                  _this.l_onPublishSuccess()
                } else {
                  _this.l_onPublishError()
                }
              })
            } else {
              _this.l_onPublishSuccess()
            }
          } else {
            _this.l_onPublishError()
          }
        })
      },
      l_onPublishSuccess () {
        const _this = this
        // 清除编辑器
        _this.l_clearEditor()
        // console.log('删除草稿成功', this.draftTemp)
        // 刷新并跳转到文章列表
        _this.$router.push({path: '/articles/list', query: {flush: true}})
        // 保存成功
        _this.$notify.success({
          title: '文章发布',
          message: '成功',
          offset: 80
        })
      },
      l_onPublishError () {
        const _this = this
        _this.$message.error({
          title: '文章发布',
          message: '失败',
          offset: 35
        })
      },
      l_imgAdd (pos, $file) {
        this.imgFile[pos] = $file
        // console.log('添加图片 contentHtml', this.articleTemp.contentHtml)
      },
      l_imgDel (pos) {
        delete this.imgFile[pos]
      },
      async l_uploadImg () {
        const that = this
        return new Promise(
          function (resolve, reject) {
            // console.log('准备上传图片', that.imgFile, that.imgFile.length)
            upDown.upload(that.imgFile).then(
              (data) => {
                // console.log('上传成功', data)
                for (let i in data) {
                  delete that.imgFile[i]
                  that.$refs.md.$img2Url(i, that.fileBase + '' + data[i])
                  // that.$refs.md.$img2Url(i, data[i])
                }
                // console.log('上传图片完成 contentHtml', that.articleTemp.contentHtml)
                resolve(true)
              },
              () => {
                reject(false)
              }
            )
          }
        )
      },
      l_loadEditor () {
        // console.log('编辑器加载')
        if (this.$route.query.articleId) {
          // console.log('加载文章到编辑器')
          this.l_loadEditorByArticle()
        } else {
          // console.log('加载最新草稿到编辑器')
          this.l_loadEditorByNewest()
        }
      },
      l_loadEditorByNewest () {
        this.loadNewestDraft().then((loadOk) => {
          // console.log('加载最新草稿成功', this.draft)
          if (loadOk && this.draft) {
            // console.log('store draft', this.draft, 'draftTemp', this.draftTemp)
            this.draftTemp = this.draft
            this.l_updateTopButton()
            if (this.draftTemp && this.draftTemp.articleId) {
              this.l_loadAndApplyArticle(this.draftTemp.articleId)
            }
            this.$notify.info({
              title: '编辑器',
              message: '已加载最新草稿',
              offset: 80
            })
          }
          this.isLoading = false
        }, () => {
          this.isLoading = false
        })
        if (this.sync) {
          this.draftTemp = this.draft
          this.l_updateTopButton()
          this.isLoading = false
        }
      },
      l_loadAndApplyArticle (articleId) {
        const that = this
        that.loadArticle(articleId).then(
          (ok) => {
            if (ok) {
              that.l_applyArticle()
              that.isLoading = false
            }
          },
          (e) => {
            this.$notify.error({
              title: '编辑器',
              message: '加载文章到编辑器失败',
              offset: 80
            })
          }
        )
      },
      l_applyArticle () {
        if (this.article) {
          this.articleTemp = this.article
          if (this.articleTemp.tags) {
            for (let tag of this.articleTemp.tags) {
              this.tagNames.push(tag.name)
            }
          }
          document.title = this.articleTemp.title
        }
      },
      async l_loadEditorByArticle () {
        const that = this
        // draft空                     直接加载
        // draft不空
        //   文章id为空                直接加载
        //   文章id不为空且id相同       不做任何动作
        //   文章id不为空且id不同       提示已有文章正在编辑
        // console.log('判断是否加载文章到草稿')
        that.loadDraftByArticleId(that.$route.query.articleId).then((loadOk) => {
          if (loadOk && that.draft) {
            // console.log('加载文章到草稿成功', that.draft)
            that.draftTemp = that.draft
            that.l_loadAndApplyArticle(that.draftTemp.articleId)
          }
        })
      },
      l_saveDraft () {
        // console.log('文章是否要不存？')
        if (!this.sync && this.draftTemp) {
          try {
            if (Object.keys(this.imgFile).length !== 0) {
              this.l_uploadImg().then(() => {
                // console.log('准备保存草稿')
                this.saveDraft(this.draftTemp)
              })
            } else {
              this.saveDraft(this.draftTemp)
            }
          } catch (err) {
            this.$notify.error({
              title: '草稿保存',
              message: '保存失败',
              offset: 80
            })
          }
        }
      },
      l_clearEditor () {
        this.l_clearAutoSave()
        // console.group('准备清空store工作区')
        this.clearEditor()
        // console.group('准备清空本地工作区')
        // 清空本地工作区，浅拷贝
        this.draftTemp = this.draftSetup
        this.articleTemp = this.articleSetup
        // console.log('清除结果', this)
        this.l_updateTopButton()
      },
      l_abandon () {
        // console.group('准备删除服务器草稿')
        // 删除草稿
        if (this.draftTemp.draftId) {
          this.deleteDraft(this.draftTemp.draftId).then((ok) => {
            // console.log('删除后台草稿', ok)
            if (ok) {
              // 清空工作区
              this.l_clearEditor()
              // 重新加载工作区
              // console.group('重新加载工作区')
              // this.l_loadEditorByNewest()
            }
          })
        } else {
          this.l_clearEditor()
        }
      },
      l_autoSave () {
        const _this = this
        this.l_clearAutoSave()
        _this.autoSave = setTimeout(_this.l_autoSave_cb, 20000, _this)
      },
      l_clearAutoSave () {
        const _this = this
        if (_this.autoSave) {
          clearTimeout(_this.autoSave)
          _this.autoSave = null
          // console.log('自动保存清除成功', _this.autoSave)
        }
      },
      l_autoSave_cb (_this) {
        _this.l_saveDraft()
      },
      l_updateTopButton () {
        // console.log('刷新顶部')
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
      e_save () {
        // 清除自动保存
        if (this.autoSave) {
          clearTimeout(this.autoSave)
          this.autoSave = null
        }
        this.l_saveDraft()
      },
      l_filterAndReplaceImg (html) {
        // 给图片添加预览支持
        return html.replace(/<img src="((http|https|ftp):\/\/(.*?)\/file\/(.*?))" alt="(.*?)" \/>/g, '<img preview="0" preview-text="$5" src="' + this.fileBase + '$4" alt="$5" />')
      },
      e_change (md, html) {
        // console.log('编辑器更改this.draft', this.draft)
        this.draftTemp.contentMd = md
        this.articleTemp.contentHtml = html
        this.updateSync(false)
        this.l_updateTopButton()
        this.l_autoSave()
      },
      e_openPublish () {
        this.publishVisible = true
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
  // @import 'mavon-editor/dist/css/index.css'
</style>

<style lang="stylus" rel="stylesheet/stylus">
  .editor
    height 800px
    .editor_wrapper
      padding-top 0
      height 100%
      .v-note-wrapper
        height 100%

  .v-note-help-wrapper
    top 30px

  .v-note-help-content
    margin auto
    .v-note-help-show
      padding: 2%

  .v-note-op
    position fixed

  .v-note-edit
    background white

  .v-note-panel
    top 40px

  .v-note-wrapper
    background none
    // min-height 700px
    margin unset
    padding unset

  .v-note-wrapper .v-note-panel .v-note-edit.divarea-wrapper .content-input-wrapper
    -moz-box-sizing border-box /*Firefox3.5+*/
    -webkit-box-sizing border-box /*Safari3.2+*/
    -o-box-sizing border-box /*Opera9.6*/
    -ms-box-sizing border-box /*IE8*/
    box-sizing border-box
    margin-top 18px

  .publish_wrapper
    .el-input, .el-input__inner
      /*font-size 25px*/
      height 32px
      width 80px
      line-height 42px

    z-index 5000
    position absolute
    top 0
    right 0
    bottom 0
    left 0
    overflow auto
    margin 0
    background-color rgba(123, 123, 123, 0.89)
    .publish
      max-width 400px
      min-width 200px
      margin 300px auto 0 auto
      font-size 0
      text-align center
      padding 30px
      border-radius 7px
      background-color rgb(249, 249, 249)
      box-shadow 0 0 20px rgba(122, 122, 119, 0.98)
      .main
        font-size 20px
        .text
          width 90px
          text-align right
          margin-right 10px
        .title, .tag, .upload-img, .handle
          text-align left
          margin 5px 0
          line-height 42px
          > div
            display inline-block
        .tag
          .tag_inputs
            max-width 260px
            height 42px
            line-height 42px
            margin auto
            i
              cursor pointer
            > div, i
              display inline-block
            .tag_input
              padding-right 10px
        .title
          .el-input, .el-input__inner
            width 250px
        .upload-img
          ul
            display inline-flex
            vertical-align middle
            max-width 230px
            white-space nowrap
            text-overflow: ellipsis
            li
              margin 0
        .handle
          text-align center

    .disableUpload
      .el-upload--picture-card
        display none
</style>
