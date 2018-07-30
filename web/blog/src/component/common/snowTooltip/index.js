/**
 * Created by SNOW on 2018.03.06.
 */
import snowTooltip from './snowTooltip.vue'
const tooltip = {
  install: function (Vue) {
    Vue.component('snowTooltip', snowTooltip)
  }
}
export default tooltip
