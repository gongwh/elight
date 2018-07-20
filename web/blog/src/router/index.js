import Vue from 'vue'
import VueRouter from 'vue-router'
import article from './article'
import write from './write'
import login from '@/component/login'
import index from '@/component/index'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: index,
    redirect: 'article',
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

const router = new VueRouter({
  mode: 'history',
  routes: routes
})

export default router
