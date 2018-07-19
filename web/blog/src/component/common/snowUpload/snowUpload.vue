<template>
  <div class="snowUploadWrapper snowUpload">
    <a v-show="inputShow" href="javascript:" class="inputWrapper snowUpload">
      <input type="file" ref="fileUpload" class="snowUpload" value="点击上传" name="点击上传" @change="l_processFile">
    </a>
    <div v-show="!inputShow" class="imgWrapper snowUpload">
      <img class="snowUpload" src="" ref="showImg" @click="l_removeFile" alt="">
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    data () {
      return {
        inputShow: true,
        file: null,
        uploadUrl: null,
        fr: new FileReader()
      }
    },
    created () {
    },
    methods: {
      l_processFile () {
        if (this.$refs.fileUpload.files.length > 0) {
          this.file = this.$refs.fileUpload.files[0]
          this.l_updateImg()
          this.l_updateInput()
        }
      },
      l_updateInput () {
        console.log(!this.file)
        this.inputShow = !this.file
      },
      l_updateImg () {
        if (this.file) {
          this.l_loadFr()
          this.fr.readAsDataURL(this.file)
        } else {
          this.$refs.showImg.src = ''
        }
      },
      l_loadFr () {
        const _that = this
        console.log(_that.fr)
        if (!_that.fr.onload) {
          _that.fr.onload = function () {
            _that.$refs.showImg.src = _that.fr.result
          }
        }
      },
      l_removeFile () {
        this.file = null
        this.l_updateImg()
        this.l_updateInput()
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .snowUpload
    height 100px
    width 110px
    cursor pointer
    border-radius 10px
  .snowUploadWrapper
    display table
    border 1px solid #5f6162
  .inputWrapper
    color #282927
    overflow hidden
    display table-cell
    vertical-align middle
    text-align center
    input
      right 0
      top 0
      opacity 0
  .inputWrapper
    :hover
      color #444
      background #eee
      border-color #ccc
      text-decoration none
</style>
