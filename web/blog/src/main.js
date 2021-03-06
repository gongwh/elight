import Vue from 'vue'
import VueVisible from 'vue-visible'
import App from './App.vue'
import elementUI from 'element-ui'
import mavonEditor from 'mavon-editor'
import highlight from 'highlight.js'
import preview from 'vue-photo-preview'
import EventProxy from 'vue-event-proxy'
import commonUtil from '@/util/common'
import snowDialog from '@/component/common/snowDialog'
import snowHover from '@/component/common/snowHover'
import snowUpload from '@/component/common/snowUpload'
import snowInput from '@/component/common/snowInput'
import snowTooltip from '@/component/common/snowTooltip'
import snowTag from '@/component/common/snowTag'

import store from '@/store'
import router from '@/router'
import globalConfig from '@/config'

import noVueModule from '@/no-vue-module'

Vue.use(noVueModule)

Vue.use(preview)
Vue.use(elementUI)
Vue.use(mavonEditor)
Vue.use(VueVisible)
Vue.use(EventProxy)

Vue.use(snowDialog)
Vue.use(snowHover)
Vue.use(snowUpload)
Vue.use(snowInput)
Vue.use(snowTooltip)
Vue.use(snowTag)

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
  components: {App},
  template: '<App/>'
})
vm.$mount('#app')
