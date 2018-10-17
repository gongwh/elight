/**
 * Created by SNOW on 2018.01.27.
 */
const ARTICLE = r => require.ensure([], () => r(require('@/component/article/index')), 'ARTICLE')
const detail = r => require.ensure([], () => r(require('@/component/article/detail')), 'detail')

export default {
  path: '/article/:articleId',
  component: ARTICLE,
  children: [
    {
      path: '',
      component: detail,
      meta: {
        title: 'Article detail'
      }
    }
  ]
}
