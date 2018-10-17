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
    let result = {}
    let pendingCount = 0
    return new Promise(
      function (resolve, reject) {
        for (const _img in files) {
          const formData = new FormData()
          formData.append(_img, files[_img])
          pendingCount++
          http.formPost(axios.defaults.fileUrl, formData).then(
            (res) => {
              // console.log('文件上传返回', res)
              if (res.status === 200) {
                Object.keys(res.data.data).forEach(function (key) {
                  result[key] = res.data.data[key]
                })
              }
              pendingCount--
              if (pendingCount === 0) {
                return resolve(result)
              }
            },
            (e) => {
              pendingCount--
              if (pendingCount === 0) {
                return resolve(result)
              }
            })
        }
      }
    )
  }
}
