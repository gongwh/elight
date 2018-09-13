/**
 * Created by SNOW on 2018.08.26.
 */
import * as tagApi from '@/api/tag/tag'

const SET_TAGS = 'SET_TAGS'

const state = {
  tags: []
}

const actions = {
  async loadAllTags ({commit}, {userId}) {
    let tagListData = await tagApi.loadAllTags(userId)
    // console.log('加载TAGS', userId, tagListData)
    commit(SET_TAGS, tagListData.data.data)
  }
}

const mutations = {
  SET_TAGS (state, tags) {
    state.tags.splice(0, state.tags.length)
    state.tags.push(...tags)
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}
