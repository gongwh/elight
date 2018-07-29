import Vue from 'vue'
import VueVisible from 'vue-visible'
import App from './App.vue'
import elementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import highlight from 'highlight.js'
// import 'highlight.js/styles/github.css'
import './common/highlight/styles/github.css'
import './common/stylus/md.styl'
import commonUtil from '@/util/common'
import snowDialog from '@/component/common/snowDialog'
import snowUpload from '@/component/common/snowUpload'
import snowInput from '@/component/common/snowInput'

import store from '@/store'
import router from '@/router'
import globalConfig from '@/config'

Vue.use(elementUI)
Vue.use(mavonEditor)
Vue.use(VueVisible)

Vue.use(snowDialog)
Vue.use(snowUpload)
Vue.use(snowInput)

Vue.use(commonUtil)
Vue.config.productionTip = false

Vue.use(globalConfig, {router, store})

Vue.directive('highlight', function (el) {
  let blocks = el.querySelectorAll('pre code')
  blocks.forEach((block) => {
    highlight.highlightBlock(block)
  })
})

/* eslint-disable no-new */
const vm = new Vue({
  router,
  store,
  components: {App, snowDialog, snowUpload, snowInput},
  template: '<App/>'
})
vm.$mount('#app')
