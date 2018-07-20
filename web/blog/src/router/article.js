/**
 * Created by SNOW on 2018.01.27.
 */
import ARTICLE from '@/component/article/index'
import article from '@/component/article/article'
import articles from '@/component/article/articles'

export default {
  path: '/article',
  component: ARTICLE,
  children: [
    { path: '/', redirect: '/articles' },
    { path: '/article', name: 'article', component: article },
    { path: '/articles', name: 'articles', component: articles }
  ]
}
