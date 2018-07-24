/**
 * Created by SNOW on 2018.01.27.
 */
import ARTICLE from '@/component/article/index'
import detail from '@/component/article/detail'

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
