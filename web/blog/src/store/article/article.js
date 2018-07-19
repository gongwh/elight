/**
 * Created by SNOW on 2018.02.01.
 */
import * as articleApi from '@/api/article/article'

// mutations names
const UPDATE_ARTICLE = 'UPDATE_ARTICLE'

// state
const state = {
  // 一定要记得设置默认值，可以用来再 mapState的时候保留占位符，否则mapState时候发现没有定义会map失败。
  article: null
}

// actions
const actions = {
  loadArticle ({commit}, articleId) {
    return articleApi.loadArticle(articleId).then(
      (result) => {
        if (result.data.code === 0) {
          commit(UPDATE_ARTICLE, result.data.data)
          return true
        }
      },
      () => {
        return false
      })
  },
  saveArticle ({commit}, article) {
    return articleApi.saveArticle(article).then((result) => {
      commit(UPDATE_ARTICLE, result.data.data)
      return true
    }, () => {
      return false
    })
  },
  async deleteArticle (context, article) {
    return await
      articleApi.deleteArticle(article).then(
        (result) => {
          if (result.data.code === 0) {
            console.log('删除文章成功')
            context.commit(UPDATE_ARTICLE, null)
            return true
          } else {
            return false
          }
        },
        () => {
          console.log('删除文章失败')
          return false
        })
  }
}

// mutations
const mutations = {
  UPDATE_ARTICLE (state, article) {
    state.article = article
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}
