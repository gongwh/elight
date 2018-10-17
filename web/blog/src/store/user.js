/**
 * Created by SNOW on 2018.07.19
 */
import userApi from '@/api/user'

const UPDATE_PROFILE = 'UPDATE_PROFILE'

// state
const state = {

}

// actions
const actions = {
  async registration (store, user) {
    return userApi.registration(user)
  },
  async profile ({commit}) {
    userApi.profile().then(
      (res) => {
        // console.log('用户信息', res)
        commit(UPDATE_PROFILE, res.data.data)
      },
      (e) => {
      }
    )
  }
}

// mutations
const mutations = {
  UPDATE_PROFILE (state, profile) {
    state.profile = profile
  }
}

// getters
const getters = {
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
