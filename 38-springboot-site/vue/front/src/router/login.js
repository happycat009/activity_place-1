/**
 * 登录 路由
 */
const loginRoute = {
  path: '/login',
  component: () => import('@/views/login/index.vue'),
  children: [
    {
      path: '',
      component: () => import('@/views/login/index/index.vue'),
      meta: {
        title: '登录-甜成办',
        requireAuth: false,
      },
    },
  ],
}
export default loginRoute
