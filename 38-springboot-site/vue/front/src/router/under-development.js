/**
 * 页面开发中 路由
 */
const notFoundRoute = {
  path: '/under-development',
  component: () => import('@/views/under-development/index.vue'),
  meta: {
    title: '页面搭建中',
    requireAuth: false,
  },
}
export default notFoundRoute
