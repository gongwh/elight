import axios from 'axios'

export default {
  get: (url, params = {}) => {
    axios.defaults.params = params
    return axios.get(url, {params: params}).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  post: (url, data) => {
    return axios.post(url, data).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  formPost: (url, data) => {
    return axios.post(url, data, {headers: { 'Content-Type': 'multipart/form-data' }}).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  put: (url, data) => {
    return axios.put(url, data).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  delete: (url, _data) => {
    console.log('删除操作,url=' + url + 'data=', _data)
    return axios.delete(url, {data: _data}).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  }
}
