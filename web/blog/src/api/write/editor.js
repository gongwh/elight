/**
 * Created by SNOW on 2018.01.30.
 */
import http from '@/api/util/http'

export const saveDraft = (draft) => http.post('/draft', draft)

export const deleteDraft = (draft) => http.delete('/draft', draft)

export const loadNewestDraft = (userId) => http.get('/draft/newest', {userId})

export const loadDraftByArticleId = (articleId) => http.get('/draft/article', articleId)
