import Vue from 'vue'
import VueRouter from 'vue-router'
import article from './article'
import write from './write'
import login from '@/component/login'
import store from '../store'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: login,
    children: [
      article,
      write,
      {path: '/login', component: login}
    ]
  },
  {
    path: '/*',
    redirect: '/'
  }
]

// 页面刷新时，重新赋值token
if (window.localStorage.getItem('token')) {
  store.commit('SET_AUTHORIZATION', window.localStorage.getItem('token'))
}

const router = new VueRouter({
  mode: 'history',
  routes: routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(r => r.meta.requireAuth)) {
    if (store.state.token) {
      next()
    } else {
      next({
        path: '/login',
        query: {redirect: to.fullPath}
      })
    }
  } else {
    next()
  }
})

export default router
