/**
 * Created by SNOW on 2018.01.25.
 */
import http from '@/api/util/http'

export const loadArticlePage = ({userId, page, size}) => {
  // console.log('loadArticlePage 参数', userId, page, size)
  return http.get('/article/page', {userId: userId, page: page, size: size})
}

export const loadArticleSearchPage = (userId, title, tagNames, page, size) => http.post('/article/page/search?page=' + page + '&size=' + size, {
  userId: userId,
  title: title,
  tagNames: tagNames
})

