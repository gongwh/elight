/**
 * Created by SNOW on 2018.03.09.
 */

export default {
  ConfigLoginAuth (router, store) {
    // 页面刷新时，重新赋值token
    if (window.localStorage.getItem('authorization')) {
      // console.log('页面刷新' + 'localStorage, authorization 为' + window.localStorage.getItem('authorization'))
      store.commit('auth/SET_AUTHORIZATION', window.localStorage.getItem('authorization'))
      store.commit('auth/SET_USER_ID', window.localStorage.getItem('userId'))
      store.commit('auth/SET_USERNAME', window.localStorage.getItem('username'))
    }
    if (router && store) {
      router.beforeEach((to, from, next) => {
        // console.log('路由登陆检查', to.meta)
        if (to.meta.requireAuth) {  // 判断该路由是否需要登录权限
          if (store.state.auth.authorization) {  // 通过vuex state获取当前的token是否存在
            next()
          } else {
            next({
              path: '/login',
              query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
            })
          }
        } else if (to.meta.title) { // 页面标签
          document.title = to.meta.title
          next()
        } else {
          // console.log('跳转' + to.path + '成功')
          next()
        }
      })
    } else {
      throw new Error('Login Auto Config Error, "router" or "store" is Empty')
    }
  }
}
