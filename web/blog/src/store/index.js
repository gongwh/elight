/**
 * Created by SNOW on 2018.01.23.
 */
import Vue from 'vue'
import Vuex from 'vuex'
import article from './article'
import write from './write'
import auth from './auth'
import user from './user'

Vue.use(Vuex)

const state = {
  topButtonsProps: [
    {index: 0, isDisplay: true, path: '/', displayName: 'Home', tip: 'Home', style: 'normal'},
    {needAuth: true, index: 1, isDisplay: true, path: '/write', displayName: 'Write', tip: 'begin write', style: 'normal'},
    {needAuth: true, index: 2, isDisplay: false, path: '/write/publish', displayName: 'Publish', style: 'normal'},
    {needAuth: true, index: 3, isDisplay: false, path: '/write/editor', displayName: 'Unsaved', tip: 'save draft', style: 'normal', query: {save: true}},
    {
      index: 4,
      needAuth: true,
      isDisplay: false,
      path: '/write/editor',
      displayName: 'Abandon',
      tip: 'abandon this draft',
      style: 'normal',
      query: {abandon: true}
    },
    {index: 5, needAuth: true, isDisplay: false, path: '/write/editor', displayName: 'Edit', tip: 'edit this article', style: 'normal', query: {}},
    {index: 6, needAuth: true, isDisplay: false, path: '/article', displayName: 'Delete', tip: 'delete this article', style: 'normal', query: {delete: true}},
    {index: 7, isDisplay: false, path: '/article', displayName: 'Reading', tip: 'latest read', style: 'reading'}
  ]
}

const mutations = {
  SET_BUTTON_STATE (state, prop) {
    // index 必须有
    if (prop) {
      state.topButtonsProps[prop.index].isDisplay = prop.isDisplay
      if (prop.isDisplay) {
        if (prop.style) {
          state.topButtonsProps[prop.index].style = prop.style
        }
        if (prop.displayName) {
          state.topButtonsProps[prop.index].displayName = prop.displayName
        }
        if (prop.path) {
          state.topButtonsProps[prop.index].path = prop.path
        }
        if (prop.params) {
          state.topButtonsProps[prop.index].params = prop.params
        }
        if (prop.query) {
          state.topButtonsProps[prop.index].query = prop.query
        }
      }
    }
  }
}

export default new Vuex.Store({
  namespaced: true,
  state,
  mutations,
  modules: {
    article,
    write,
    user,
    auth
  }
})
