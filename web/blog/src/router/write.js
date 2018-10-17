/**
 * Created by SNOW on 2018.01.27.
 */
const editor = r => require.ensure([], () => r(require('@/component/write/editor')), 'editor')
const write = r => require.ensure([], () => r(require('@/component/write/index')), 'write')

export default {
  path: '/write',
  component: write,
  redirect: '/write/editor',
  meta: {
    requireAuth: true
  },
  children: [
    {
      path: 'editor',
      component: editor,
      meta: {
        requireAuth: true,
        title: 'Writing...'
      }
    }
  ]
}
