import axios from 'axios'

export default {
  request: (config) => {
    return axios.request(config).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  get: (url, params = {}) => {
    // console.log('AXIOS GET', params)
    return axios.get(url, {params: params}).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  post: (url, data, params) => {
    return axios.post(url, data, {params: params}).then(response => {
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
  put: (url, data, config) => {
    return axios.put(url, data, config).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  delete: (url, _data) => {
    // console.log('删除操作,url=' + url + 'data=', _data)
    return axios.delete(url, {data: _data}).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  }
}
