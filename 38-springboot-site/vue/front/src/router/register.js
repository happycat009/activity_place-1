/**
 * 注册 路由
 */
const registerRoute = {
  path: '/register',
  component: () => import('@/views/register/index.vue'),
  children: [
    {
      path: '',
      component: () => import('@/views/register/index/index.vue'),
      meta: {
        title: '企业注册',
        requireAuth: false,
      },
    },
  ],
}
export default registerRoute
