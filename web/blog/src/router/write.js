/**
 * Created by SNOW on 2018.01.27.
 */
import WRITE from '@/component/write/index'
import editor from '@/component/write/editor'
import publish from '@/component/write/publish'

export default {
  path: '/write',
  component: WRITE,
  meta: {
    requireAuth: true
  },
  children: [
    {
      path: '', redirect: 'editor'
    },
    {
      path: 'editor',
      component: editor,
      meta: {
        title: 'Writing...'
      }
    },
    {
      path: 'publish',
      component: publish,
      meta: {
        title: 'Publishing...'
      }
    }
  ]
}
