/**
 * Created by SNOW on 2018.01.30.
 */
import http from '@/api/util/http'

export const loadArticle = (articleId) => http.get('/article/' + articleId)

export const saveArticle = (article) => http.post('/article', article)

export const deleteArticle = (article) => http.delete('/article', article)
