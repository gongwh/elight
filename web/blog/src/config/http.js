/**
 * Created by SNOW on 2018.03.09.
 */
const base = require('./base')
import axios from 'axios'
import store from '../store'
import router from '../router'

axios.defaults.baseURL = base.serviceUrl
axios.defaults.fileUrl = base.fileUrl

// http request 拦截器
axios.interceptors.request.use(
  config => {
    if (store.state.authorization) {
      config.headers.Authorization = `bearer ${store.state.authorization}`
    }
    return config
  },
  err => {
    return Promise.reject(err)
  })

// http response 拦截器
axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 跳转到登录页面
          router.replace({
            path: '/login',
            query: {redirect: router.currentRoute.fullPath}
          })
      }
    }
    return Promise.reject(error.response.data)
  })
