/**
 * Created by SNOW on 2018.03.03.
 */
import http from './http'
import axios from 'axios'

export default {

  // 传入文件数组或者文件列表对象
  // 返回后台保存的内容
  // 格式如下
  // {'源文件名1': '上传后服务器地址1','源文件名2': '上传后服务器地址2',}
  upload (files) {
    // 第一步.将图片上传到服务器.
    const formData = new FormData()
    for (const _img in files) {
      formData.append(_img, files[_img])
    }
    return http.formPost(axios.defaults.fileUrl, formData).then(
      (res) => {
        if (res.data.code === 0) {
          return res.data.data
        } else {
          return {}
        }
      },
      () => {
        this.$notify.error({
          title: '上传图片',
          message: '失败'
        })
        return null
      })
  }
}
