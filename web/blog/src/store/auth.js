/**
 * Created by SNOW on 2018.07.19
 */
import auth from '@/api/auth'
// import base from '@/config/base'

const SET_AUTHORIZATION = 'SET_AUTHORIZATION'
const SET_USER_ID = 'SET_USER_ID'
const SET_USERNAME = 'SET_USERNAME'

// state
const state = {
  // defaultUserId: 'bfc2efcb-ef2b-46e7-9787-e8b6f2099152', // notebook pc
  defaultUserId: 'efa43353-91b1-4b0f-9204-3172d79e4f22', //  server
  // defaultUserId: '80fd5968-95dc-4ebe-84e4-dc684583a52c', //  pc
  defaultUserName: 'Elight'
}

// actions
const actions = {
  async login ({commit}, username, password) {
    return auth.login(username, password).then(
      (res) => {
        if (res.headers.authorization) {
          console.log('登陆成功', res.data.data)
          commit(SET_AUTHORIZATION, res.headers.authorization)
          commit(SET_USER_ID, res.data.data.userId)
          commit(SET_USERNAME, res.data.data.username)
          window.localStorage.setItem('authorization', res.headers.authorization)
          window.localStorage.setItem('userId', res.data.data.userId)
          window.localStorage.setItem('username', res.data.data.username)
          return true
        } else {
          return false
        }
      },
      (e) => {
        console.log('登陆失败', e)
        return false
      }
    )
  },
  async logout ({commit}) {
    auth.logout().then(
      () => {
        commit(SET_AUTHORIZATION, null)
      },
      (e) => {
        console.log('登出失败', e)
      }
    )
  }
}

// mutations
const mutations = {
  SET_AUTHORIZATION (state, authorization) {
    state.authorization = authorization
  },
  SET_USER_ID (state, userId) {
    state.userId = userId
  },
  SET_USERNAME (state, username) {
    state.username = username
  }
}

// getters
const getters = {
  alreadyAuth (state) {
    return state.authorization && state.authorization.length > 0
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
