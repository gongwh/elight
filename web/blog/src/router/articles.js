/**
 * Created by SNOW on 2018.01.27.
 */
import ARTICLES from '@/component/articles/index'
import articleList from '@/component/articles/list'

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
