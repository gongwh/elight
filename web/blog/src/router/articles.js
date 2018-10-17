/**
 * Created by SNOW on 2018.01.27.
 */
const articleList = r => require.ensure([], () => r(require('@/component/articles/list')), 'articleList')
const ARTICLES = r => require.ensure([], () => r(require('@/component/articles/index')), 'ARTICLES')

export default {
  path: '/articles',
  component: ARTICLES,
  redirect: '/articles/list',
  children: [
    {
      path: 'list',
      component: articleList,
      meta: {
        title: 'Article list'
      }
    }
  ]
}
