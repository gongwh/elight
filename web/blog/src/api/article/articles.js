/**
 * Created by SNOW on 2018.01.25.
 */
import http from '@/api/util/http'

export const loadArticlePage = (params) => {
  // console.log('loadArticlePage 参数', userId, page, size)
  return http.get('/article/page', params)
}

export const loadArticleSearchPage = (data, params) => http.post('/article/page/search',
  data,
  params)

