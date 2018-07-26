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
  articlesSearch: null,
  paginationSearch: {}
}

// actions
const actions = {
  async loadArticlePage ({commit}, page, size) {
    return articlesApi.loadArticlePage(undefined, page, size).then(
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
    return articlesApi.loadArticleSearchPage(undefined, content, page, size).then(
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
    state.articlesSearch = articles
    state.paginationSearch = pagination
  },
  CLEAR_ARTICLES_SEARCH_RESULT (state) {
    state.articlesSearch = null
    state.paginationSearch = {}
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
  },
  articlesNumSearch (state) {
    if (state.articlesSearch === null) {
      return 0
    } else {
      return state.paginationSearch.length
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
