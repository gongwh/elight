<template>
  <div class="publish">
    <div class="publish_inner">
      <div class="input_area">
        <el-input placeholder="请输文章标题" v-model="draftTemp.title">
          <template slot="prepend">Title</template>
        </el-input>
        <el-input placeholder="以英文逗号分割" v-model="tags" :disabled="true">
          <template slot="prepend">Tags</template>
        </el-input>
        <div>
          <el-upload
            :action="fileBase"
            list-type="picture-card"
            :multiple="false"
            :name="titleImgName"
            :on-remove="l_handleRemove"
            :on-success="l_uploadSuccess">
            <i class="el-icon-plus"></i>
          </el-upload>
        </div>
        <el-button @click="l_publish">发布<i class="el-icon-upload el-icon--right"></i></el-button>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import { mapState, mapActions } from 'vuex'
  const htmlToText = require('html-to-text')

  export default {
    data () {
      return {
        publishIng: false,
        tags: '',
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
    methods: {
      ...mapActions('article/articles', ['loadArticles']),
      ...mapActions('article/article', ['saveArticle']),
      ...mapActions('write/editor', ['deleteDraft', 'loadNewestDraft', 'clearEditor']),
      l_handleRemove (file, fileList) {
        this.titleImageUrl = ''
      },
      l_uploadSuccess (response, file, fileList) {
        if (response.code === 0) {
          this.titleImageUrl = response.data[this.titleImgName]
          this.draftTemp.titleImgUrl = this.titleImageUrl
        }
      },
      l_loadDraft () {
        // 加载标题和tag
        if (!this.draft) {
          // 跳转到编辑器
          this.$router.push({name: 'editor'})
        } else {
          this.draftTemp = this.draft
          this.draftTemp.contentText = htmlToText.fromString(this.draftTemp.contentHtml, {
            wordwrap: 130
          })
        }
      },
      l_publish () {
        // 保存文章
        this.saveArticle(this.draftTemp).then(saveOk => {
          if (saveOk) {
            // 删除草稿
            this.deleteDraft(this.draftTemp)
            // 清除编辑器
            this.clearEditor()
            // 刷新并跳转到文章列表
            this.gotoArticles()
            // 保存成功
            this.$notify.success({
              title: '文章发布',
              message: '成功',
              offset: 30
            })
          } else {
            this.$message.error({
              title: '文章发布',
              message: '失败',
              offset: 30
            })
          }
        })
      },
      gotoArticles () {
        this.$router.push({name: 'articles', params: {flush: true}})
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .publish_inner
    margin auto
    margin-top 50px
    width 30%
    .input_area
      .el-button
        width 100%
      .el-upload
        width 100%
      .uploadImg
        width 60px
        height 100px

  .disableUpload
    .el-upload--picture-card
      display none
</style>
