/**
 * Created by SNOW on 2018.01.23.
 */
import * as articlesApi from '@/api/article/articles'

// mutations names
const UPDATE_ARTICLES_PAGE = 'UPDATE_ARTICLES_PAGE'
const UPDATE_ARTICLES_SEARCH_PAGE = 'UPDATE_ARTICLES_SEARCH_PAGE'

// state
const state = {
  // 一定要记得设置默认值，可以用来再 mapState的时候保留占位符，否则mapState时候发现没有定义会map失败。
  articles: null,
  pagination: {},
  search: {}
}

// actions
const actions = {
  async loadArticlePage ({commit}, page, size) {
    return articlesApi.loadArticlePage(null, page, size).then(
      (result) => {
        console.log('加载文章列表成功', result.data)
        commit(UPDATE_ARTICLES_PAGE, result.data.data, result.data.pagination)
      },
      (e) => {
        console.log('加载文章列表失败', e)
      }
    )
  },
  async loadArticleSearchPage ({commit}, content, page, size) {
    console.log('搜索文章列表参数', content, page, size)
    return articlesApi.loadArticleSearchPage(null, content, page, size).then(
      (result) => {
        console.log('搜索文章列表成功', result.data)
        commit(UPDATE_ARTICLES_SEARCH_PAGE, result.data.data, result.data.pagination)
      },
      (e) => {
        console.log('搜索文章列表失败', e)
      }
    )
  }
}

// mutations
const mutations = {
  UPDATE_ARTICLES_PAGE (state, articles, pagination) {
    state.articles = articles
    state.pagination = pagination
  },
  UPDATE_ARTICLES_SEARCH_PAGE (state, articles, pagination) {
    state.search.articles = articles
    state.search.pagination = pagination
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
