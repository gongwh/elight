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
    {index: 0, isDisplay: true, path: '/home', displayName: 'Home', style: 'normal'},
    {index: 1, isDisplay: true, path: '/write', displayName: 'Write', style: 'normal'},
    {index: 2, isDisplay: false, name: 'publish', displayName: 'Publish', style: 'normal'},
    {index: 3, isDisplay: false, name: 'editor', displayName: 'Unsaved', style: 'normal', query: {save: true}},
    {index: 4, isDisplay: false, name: 'editor', displayName: 'Abandon', style: 'normal', query: {abandon: true}},
    {index: 5, isDisplay: false, name: 'editor', displayName: 'Edit', style: 'normal', params: {}},
    {index: 6, isDisplay: false, name: 'article', displayName: 'Delete', style: 'normal', query: {delete: true}},
    {index: 7, isDisplay: false, name: 'article', displayName: 'Reading', style: 'reading', query: {}}
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
        if (prop.query) {
          state.topButtonsProps[prop.index].query = prop.query
        }
        if (prop.params) {
          state.topButtonsProps[prop.index].params = prop.params
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
