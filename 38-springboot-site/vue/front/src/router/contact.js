/**
 * 联系我们 路由
 */
const contactRoute = {
  path: '/contact/:id',
  component: () => import('@/views/contact/index.vue'),
  children: [
    {
      path: '',
      component: () => import('@/views/contact/index/index.vue'),
      meta: {
        title: '联系我们',
        requireAuth: false,
      },
    },
  ],
}
export default contactRoute
