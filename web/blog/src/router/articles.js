/**
 * Created by SNOW on 2018.01.27.
 */
import ARTICLES from '@/component/articles/index'
import articleList from '@/component/articles/list'

export default {
  path: '/articles',
  component: ARTICLES,
  children: [
    { path: '', redirect: 'list' },
    {
      path: 'list',
      component: articleList,
      meta: {
        title: 'Article list'
      }
    }
  ]
}
