import Vue from 'vue'
import VueRouter from 'vue-router'
import article from './article'
import articles from './articles'
import write from './write'
const login = r => require.ensure([], () => r(require('@/component/login')), 'login')
const index = r => require.ensure([], () => r(require('@/component/index')), 'index')
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: index,
    redirect: '/articles',
    children: [
      article,
      articles,
      write
    ]
  },
  {
    path: '/login',
    component: login,
    meta: {
      title: 'Login'
    }
  },
  {
    path: '/**',
    redirect: 'articles'
  }
]

const router = new VueRouter({
  mode: 'history',
  routes: routes
})

export default router
