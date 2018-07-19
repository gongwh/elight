import Vue from 'vue'
import VueVisible from 'vue-visible'
import App from './App.vue'
import elementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import 'highlight.js/styles/googlecode.css' // 样式文件
import commonUtil from '@/util/common'
import VueHighlightJS from 'vue-highlightjs'
import snowDialog from '@/component/common/snowDialog'
import snowUpload from '@/component/common/snowUpload'

import store from '@/store'
import router from '@/router'
import globalConfig from '@/config'

Vue.use(elementUI)
Vue.use(mavonEditor)
Vue.use(VueVisible)
Vue.use(VueHighlightJS)

Vue.use(snowDialog)
Vue.use(snowUpload)

Vue.use(commonUtil)
Vue.config.productionTip = false

Vue.use(globalConfig, {router, store})

/* eslint-disable no-new */
const vm = new Vue({
  router,
  store,
  components: {App, snowDialog, snowUpload},
  template: '<App/>'
})
vm.$mount('#app')
