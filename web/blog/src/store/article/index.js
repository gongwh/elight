/**
 * Created by SNOW on 2018.01.25.
 */
import articles from './articles'
import article from './article'
export default {
  namespaced: true, // 针对上一层article使用namespace
  modules: {
    article,
    articles
  }
}
