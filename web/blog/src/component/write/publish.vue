<template>
  <!--<div>-->
  <!--<scroll-lock>-->
  <!--<div class="publish_wrapper">-->
  <div class="publish_wrapper" v-show="visible">
    <div class="publish">
      <div class="main">
        <div class="title">
          <div class="text">Title</div>
          <el-input placeholder="" v-model="draftTemp.title">
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
              :on-remove="l_handleRemove"
              :on-success="l_uploadSuccess">
              <i class="el-icon-circle-plus"></i>
            </el-upload>
          </div>
        </div>
        <div class="handle">
          <el-button type="primary" size="medium" @click="e_close">Cancel</el-button>
          <el-button type="primary" size="medium" @click="l_publish">Publish</el-button>
        </div>
      </div>
    </div>
  </div>
  <!--</scroll-lock>-->
  <!--</div>-->
</template>

<script type="text/ecmascript-6">
  import {mapActions, mapState} from 'vuex'

  const htmlToText = require('html-to-text')

  export default {
    props: {
      visible: {
        type: Boolean,
        Default: false
      }
    },
    data () {
      return {
        publishIng: false,
        tagNames: [],
        draftTemp: {},
        titleImageUrl: '',
        titleImgName: 'titleImg'
      }
    },
    computed: {
      ...mapState('write/editor', ['draft'])
    },
    mounted () {
      this.l_loadDraft()
    },
    watch: {
      'visible' (nv) {
        if (nv) {
          console.log('重新加载草稿到发布组建')
          this.l_loadDraft()
        }
      }
    },
    methods: {
      ...mapActions('article/articles', ['loadArticles']),
      ...mapActions('article/article', ['saveArticle']),
      ...mapActions('write/editor', ['deleteDraft', 'loadNewestDraft', 'clearEditor']),
      l_handleRemove (file, fileList) {
        this.titleImageUrl = ''
      },
      l_uploadSuccess (response, file, fileList) {
        // console.log('上传图片结果', response)
        if (response.status === 0) {
          this.titleImageUrl = response.data[this.titleImgName]
          this.draftTemp.titleImgUrl = this.titleImageUrl
          // console.log('上传图片成功', this.draftTemp)
        }
      },
      l_loadDraft () {
        // 加载标题和tag
        if (!this.draft) {
          // 跳转到编辑器
          // this.$router.push({path: '/write/editor'})
        } else {
          console.log('加载草稿到发布组建')
          this.draftTemp = this.draft
          this.draftTemp.contentText = htmlToText.fromString(this.draftTemp.contentHtml, {
            wordwrap: 130
          })
        }
      },
      l_publish () {
        // 保存文章
        // console.log('准备发布保存文章', this.draftTemp)
        let tags = []
        for (let i = 0; i < this.tagNames.length; i++) {
          if (this.tagNames[i] && this.tagNames[i] !== '') {
            tags.push({'name': this.tagNames[i]})
          }
        }
        this.draftTemp.tags = tags
        this.saveArticle(this.draftTemp).then(saveOk => {
          if (saveOk) {
            // 删除草稿
            this.deleteDraft(this.draftTemp)
            // 清除编辑器
            this.clearEditor()
            // 刷新并跳转到文章列表
            this.$router.push({path: '/articles/list', query: {flush: true}})
            // 保存成功
            this.$notify.success({
              title: '文章发布',
              message: '成功',
              offset: 80
            })
          } else {
            this.$message.error({
              title: '文章发布',
              message: '失败',
              offset: 80
            })
          }
        })
      },
      e_close () {
        this.$emit('update:visible', false)
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .publish_wrapper

    .el-input, .el-input__inner
      font-size 25px
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
