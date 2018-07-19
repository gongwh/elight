/**
 * Created by SNOW on 2018.01.25.
 */
import http from '@/api/util/http'

export const loadArticlePage = (userId, page, size) => http.get('/article/page', userId, page, size)
