/**
 * Created by SNOW on 2018.03.09.
 */
require('./axios')
import {service as serviceConfig} from './base'
import routerConfig from './router'

export default {
  install (Vue, options) {
    Vue.prototype.fileBase = serviceConfig.fileBase
    routerConfig.ConfigLoginAuth(options.router, options.store)
  }
}
