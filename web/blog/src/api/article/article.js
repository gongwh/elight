/**
 * Created by SNOW on 2018.01.30.
 */
import http from '@/api/util/http'
import {host} from '@/config/base'

export const loadArticle = (articleId) => http.get('/article/' + articleId)

export const saveArticle = (article) => http.post('/article', article)

export const deleteArticle = (article) => http.delete('/article', article)

export const addSiteSearch = (articleId) => http.post('/urls', '' + host + 'article/' + articleId,
  {
    baseURL: 'http://data.zz.baidu.com/',
    params: {
      site: 'www.zhangzhuowei.com',
      token: 'ab1bq7OFCqugfFbm'
    }
  }
)

export const updateSiteSearch = (articleId) => http.post('update', '' + host + 'article/' + articleId,
  {
    baseUrl: 'http://data.zz.baidu.com/',
    params: {
      site: 'www.zhangzhuowei.com',
      token: 'ab1bq7OFCqugfFbm'
    }
  }
)

export const deleteSiteSearch = (articleId) => http.post('del', '' + host + 'article/' + articleId,
  {
    baseUrl: 'http://data.zz.baidu.com/',
    params: {
      site: 'www.zhangzhuowei.com',
      token: 'ab1bq7OFCqugfFbm'
    }
  }
)
