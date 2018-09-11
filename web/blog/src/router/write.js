/**
 * Created by SNOW on 2018.01.27.
 */
import WRITE from '@/component/write/index'
import editor from '@/component/write/editor'
import publish from '@/component/write/publish'

export default {
  path: '/write',
  component: WRITE,
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
    },
    {
      path: 'publish',
      component: publish,
      meta: {
        requireAuth: true,
        title: 'Publishing...'
      }
    }
  ]
}
