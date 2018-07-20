/**
 * Created by SNOW on 2018.01.23.
 */
import * as articlesApi from '@/api/article/articles'

// mutations names
const UPDATE_ARTICLES = 'UPDATE_ARTICLES'

// state
const state = {
  // 一定要记得设置默认值，可以用来再 mapState的时候保留占位符，否则mapState时候发现没有定义会map失败。
  articles: null,
  pagination: {}
}

// actions
const actions = {
  async loadArticlePage ({commit}, page, size) {
    const result = await articlesApi.loadArticlePage(page, size)
    console.log('加载文章列表', result)
    commit(UPDATE_ARTICLES, result.data.data)
  }
}

// mutations
const mutations = {
  UPDATE_ARTICLES (state, articles) {
    state.articles = articles
  },
  UPDATE_ARTICLES_PAGE (state, articles, pagination) {
    state.articles = articles
    state.pagination = pagination
  }
}

// getters

const getters = {
  articlesNum (state) {
    if (state.articles === null) {
      return 0
    } else {
      return state.articles.length
    }
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
