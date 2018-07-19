/**
 * Created by SNOW on 2018.03.09.
 */

export default {
  ConfigLoginAuth (router, store) {
    if (router && store) {
      router.beforeEach((to, from, next) => {
        console.log('路由登陆检查', to.meta)
        if (to.meta.requireAuth) {  // 判断该路由是否需要登录权限
          if (store.state.token) {  // 通过vuex state获取当前的token是否存在
            next()
          } else {
            next({
              path: '/login',
              query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
            })
          }
        } else {
          next()
        }
      })
    } else {
      throw new Error('Login Auto Config Error, "router" or "store" is Empty')
    }
  }
}
