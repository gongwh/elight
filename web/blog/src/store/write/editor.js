/**
 * Created by SNOW on 2018.01.28.
 */
import writeApi from '@/api/write'

// mutations names
const UPDATE_DRAFT = 'UPDATE_DRAFT'
const UPDATE_SYNC = 'UPDATE_SYNC'
const UPDATE_LOADING = 'UPDATE_LOADING'
const UPDATE_DRAFT_PART = 'UPDATE_DRAFT_PART'

// state
const state = {
  draft: null,
  sync: false,
  loading: false,
  autoSave: null
}

// actions
const actions = {
  loadNewestDraft (context) {
    context.commit(UPDATE_LOADING, true)
    return writeApi.editor.loadNewestDraft().then(
      (result) => {
        if (result.status === 200) {
          if (result.data.data) {
            // console.log('非空草稿', result.data.data)
            context.commit(UPDATE_DRAFT, result.data.data)
            context.commit(UPDATE_SYNC, true)
            return true
          } else {
            // console.log('空草稿')
            return false
          }
        }
      },
      () => {
        context.commit(UPDATE_SYNC, false)
        return false
      })
  },
  loadDraftByArticleId (context, articleId) {
    context.commit(UPDATE_LOADING, true)
    return writeApi.editor.loadDraftByArticleId(articleId).then(
      (result) => {
        // console.log('[store] 加载文章到草稿完成', result)
        if (result.status === 200) {
          if (result.data.data) {
            // console.log('非空草稿')
            context.commit(UPDATE_DRAFT, result.data.data)
            context.commit(UPDATE_SYNC, true)
            return true
          } else {
            // console.log('空草稿')
            return false
          }
        }
      },
      (e) => {
        // console.log('[store] 加载文章到草稿失败', e)
        context.commit(UPDATE_SYNC, false)
        return false
      })
  },
  async saveDraft (context, _draft) {
    context.commit(UPDATE_DRAFT_PART, _draft)
    return await writeApi.editor.saveDraft(context.state.draft).then(
      (result) => {
        context.commit(UPDATE_DRAFT, result.data.data)
        context.commit(UPDATE_DRAFT_PART, _draft)
        context.commit(UPDATE_SYNC, true)
        return true
      },
      () => {
        context.commit(UPDATE_SYNC, false)
        return false
      })
  },
  async deleteDraft (context, draftId) {
    context.commit(UPDATE_LOADING, true)
    // console.log('store准备删除草稿', draftId)
    return await
      writeApi.editor.deleteDraft(draftId).then(
        (result) => {
          if (result.status === 200) {
            // console.log('删除草稿成功')
          }
          context.commit(UPDATE_LOADING, false)
          return true
        },
        () => {
          // console.log('删除草稿失败')
          context.commit(UPDATE_LOADING, false)
          return false
        })
  },
  async updateSync (context, state) {
    context.commit(UPDATE_SYNC, state)
  },
  clearEditor (context) {
    context.commit(UPDATE_DRAFT, null)
    context.commit(UPDATE_SYNC, false)
  }
}

// mutations
const mutations = {
  UPDATE_DRAFT (state, _draft) {
    // console.log('store设置草稿', _draft)
    state.draft = _draft
  },
  UPDATE_DRAFT_PART (state, _draft) {
    // console.log('mutations准备保存草稿', _draft)
    if (state.draft === null) {
      state.draft = _draft
    } else {
      // 保存草稿主要内容
      // console.log('mutations保存草稿', _draft)
      state.draft.contentMd = _draft.contentMd
      // state.draft.contentHtml = _draft.contentHtml
      // console.log('mutation保存后', state.draft)
    }
  },
  UPDATE_SYNC (state, sync) {
    // console.log('草稿同步状态', sync)
    state.sync = sync
  },
  UPDATE_LOADING (state, loading) {
    state.loading = loading
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}
