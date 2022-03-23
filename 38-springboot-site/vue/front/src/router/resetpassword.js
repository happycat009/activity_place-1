/**
 * 忘记密码 路由
 */
const resetPasswordRoute = {
  path: '/resetpassword',
  component: () => import('@/views/resetpassword/index.vue'),
  children: [
    {
      path: '',
      component: () => import('@/views/resetpassword/index/index.vue'),
      meta: {
        title: '忘记密码',
        requireAuth: false,
      },
    },
  ],
}
export default resetPasswordRoute
