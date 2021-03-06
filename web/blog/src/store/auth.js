/**
 * Created by SNOW on 2018.07.19
 */
import auth from '@/api/auth'
import base from '@/config/base'

const SET_AUTHORIZATION = 'SET_AUTHORIZATION'
const SET_USER_ID = 'SET_USER_ID'
const SET_USERNAME = 'SET_USERNAME'

// state
const state = {
  defaultUserId: base.defaultUserId, //  server
  defaultUserName: base.defaultUserName
}

// actions
const actions = {
  async login ({commit}, username, password) {
    return auth.login(username, password).then(
      (res) => {
        if (res.headers.authorization) {
          // console.log('登陆成功', res.data.data)
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
        // console.log('登陆失败', e)
        return false
      }
    )
  },
  async logout ({commit}) {
    commit(SET_AUTHORIZATION, null)
    commit(SET_USER_ID, null)
    commit(SET_USERNAME, null)
    window.localStorage.removeItem('authorization')
    window.localStorage.removeItem('userId')
    window.localStorage.removeItem('username')
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
