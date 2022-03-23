/**
 * 甜企云社区 路由
 */
const communityRoute = {
  path: '/community',
  component: () => import('@/views/community/index.vue'),
  children: [
    {
      path: '',
      component: () => import('@/views/community/index/index.vue'),
      meta: {
        title: '甜企云社区',
        requireAuth: false,
        keepAlive: true,
      },
    },
  ],
}
export default communityRoute
