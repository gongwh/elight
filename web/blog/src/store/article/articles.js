/**
 * Created by SNOW on 2018.01.23.
 */
import * as articlesApi from '@/api/article/articles'
import * as tagApi from '@/api/tag/tag'
import {defaultUserId} from '@/config/base'

// mutations names
const UPDATE_ARTICLES_PAGE = 'UPDATE_ARTICLES_PAGE'
const UPDATE_ARTICLES_SEARCH_PAGE = 'UPDATE_ARTICLES_SEARCH_PAGE'
const INIT_TAG_NAMES = 'INIT_TAG_NAMES'

// state
const state = {
  // 一定要记得设置默认值，可以用来再 mapState的时候保留占位符，否则mapState时候发现没有定义会map失败。
  articles: null,
  pagination: null,
  articlesSearch: null,
  paginationSearch: null,
  state_searchInput: '',
  state_tagNames: null,
  state_selectedTagNames: [],
  hasSelectedTag: false
}

// actions
const actions = {
  async loadArticlePage ({commit}, {userId, page, size}) {
    // console.log('loadArticlePage', userId, page, size)
    return articlesApi.loadArticlePage({userId, page, size}).then(
      (result) => {
        // console.log('加载文章列表成功', result.data)
        commit(UPDATE_ARTICLES_PAGE, result.data)
      },
      (e) => {
        // console.log('加载文章列表失败', e)
      }
    )
  },
  initTagNames ({commit}) {
    let userId = window.localStorage.getItem('userId')
    if (!userId) {
      userId = defaultUserId
    }
    tagApi.loadAllTags(userId).then(
      (res) => {
        commit(INIT_TAG_NAMES, res.data.data)
      }
    )
  },
  async searchArticles ({commit}, {userId, title, tagNames, page, size}) {
    // console.log('搜索文章列表参数', content, page, size)
    return articlesApi.loadArticleSearchPage(userId, title, tagNames, page, size).then(
      (result) => {
        // console.log('搜索文章列表成功', result.data)
        commit(UPDATE_ARTICLES_SEARCH_PAGE, result.data)
      },
      (e) => {
        // console.log('搜索文章列表失败', e)
      }
    )
  }
}

// mutations
const mutations = {
  'UPDATE_TAG_SELECT' (state, {index, selected}) {
    if (state.state_tagNames) {
      state.state_tagNames[index].selected = selected
    }
    state.hasSelectedTag = false
    if (state.state_tagNames) {
      state.state_tagNames.forEach(tag => {
        if (tag.selected) {
          state.hasSelectedTag = true
        }
      })
    }
    state.state_selectedTagNames.splice(0, state.state_selectedTagNames.length)
    if (state.state_tagNames) {
      state.state_tagNames.forEach(tag => {
        if (tag.selected) {
          state.state_selectedTagNames.push(tag.name)
        }
      })
    }
  },
  INIT_TAG_NAMES (state, data) {
    state.state_tagNames = data
  },
  UPDATE_ARTICLES_PAGE (state, data) {
    if (state.articles) {
      state.articles.push(...data.data)
    } else {
      state.articles = data.data
    }
    state.pagination = data.pagination
  },
  UPDATE_ARTICLES_SEARCH_PAGE (state, data) {
    if (state.articlesSearch) {
      state.articlesSearch.push(...data.data)
    } else {
      state.articlesSearch = data.data
    }
    state.paginationSearch = data.pagination
  },
  CLEAR_ARTICLES_RESULT (state) {
    state.articles = null
    state.pagination = null
  },
  CLEAR_ARTICLES_SEARCH_RESULT (state) {
    state.articlesSearch = null
    state.paginationSearch = null
  },
  SET_ARTICLES_SEARCH_INPUT (state, input) {
    state.state_searchInput = input
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
      return state.articlesSearch.length
    }
  },
  articlesTotalNumSearch (state) {
    if (state.paginationSearch === null) {
      return 0
    } else {
      return state.paginationSearch.totalElements
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
