/**
 * 新闻详情 路由
 */
const newsRoute = {
  path: '/news/:id',
  component: () => import('@/views/news/index.vue'),
  children: [
    {
      path: '',
      component: () => import('@/views/news/index/index.vue'),
      meta: {
        title: '活动头条',
        requireAuth: false,
      },
    },
  ],
}
export default newsRoute
