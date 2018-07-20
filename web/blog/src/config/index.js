/**
 * Created by SNOW on 2018.03.09.
 */
require('./http')
import base from './base'
import routerConfig from './router'

export default {
  install (Vue, options) {
    Vue.prototype.fileBase = base.fileBase
    routerConfig.ConfigLoginAuth(options.router, options.store)
  }
}
