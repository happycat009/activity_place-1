/**
 * 404 路由
 */
const notFoundRoute = {
  path: '/:catchAll(.*)',
  component: () => import('@/views/not-found/index.vue'),
  meta: {
    title: '404',
    requireAuth: false,
  },
}
export default notFoundRoute
