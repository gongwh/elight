/**
 * Created by SNOW on 2018.01.30.
 */
import http from '@/api/util/http'

export const saveDraft = (draft) => http.post('/draft', draft)

export const deleteDraft = (draftId) => http.delete('/draft/' + draftId)

export const loadNewestDraft = (userId) => http.get('/draft/newest', {userId: userId})

export const loadDraftByArticleId = (articleId) => http.get('/draft/article', {articleId: articleId})
