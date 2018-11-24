/**
 * Created by SNOW on 2018.03.09.
 */
const base = require('./base')
import axios from 'axios'
import store from '../store'
import router from '../router'

axios.defaults.baseURL = base.serviceUrl
axios.defaults.fileUrl = base.fileBase
axios.defaults.headers['Content-Type'] = 'application/json'

// http request 拦截器
axios.interceptors.request.use(
  config => {
    // console.log('准备发送请求,此时的store', store.state)
    if (store.state.auth.authorization) {
      config.headers.Authorization = `${store.state.auth.authorization}`
    }
    config.headers['Cache-Control'] = 'max-age=3600'
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
        case 401: {
          console.log('401 router.currentRoute', router.currentRoute)
          if (store.state.auth.authorization) {
            router.replace({
              path: '/login',
              query: {redirect: router.currentRoute.fullPath}
            })
          } else {
            router.push({
              path: '/articles/list'
            })
          }
          break
        }
        case 403: {
          console.log('403 router.currentRoute', router.currentRoute)
          router.push({
            path: '/articles/list'
          })
          break
        }
      }
    }
    return Promise.reject(error.response.data)
  })
