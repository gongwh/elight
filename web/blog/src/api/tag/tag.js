/**
 * Created by SNOW on 2018.01.30.
 */
import http from '@/api/util/http'

export const loadAllTags = (userId) => http.get('/tag', {userId: userId})

export const saveTag = (tag) => http.post('/tag', tag)

export const deleteTag = (tag) => http.delete('/tag', tag)
