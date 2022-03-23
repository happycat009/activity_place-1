/**
 * 走进内江 路由
 */
const cityRoute = {
  path: '/city',
  component: () => import('@/views/city/index.vue'),
  children: [
    {
      path: '',
      component: () => import('@/views/city/index/index.vue'),
      meta: {
        title: '走进内江',
        requireAuth: false,
      },
    },
  ],
}
export default cityRoute
