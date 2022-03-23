/**
 * 401 未完成认证路由
 */
const unauthorizedRoute = {
  path: '/401',
  component: () => import('@/views/unauthorized/index.vue'),
  meta: {
    title: '401',
    requireAuth: false,
  },
}
export default unauthorizedRoute
