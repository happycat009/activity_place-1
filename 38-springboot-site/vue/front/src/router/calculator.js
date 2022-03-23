/**
 * 政策计算器 路由
 */
const calculatorRoute = {
  path: '/calculator',
  component: () => import('@/views/calculator/index.vue'),
  children: [
    {
      path: '',
      component: () => import('@/views/calculator/index/index.vue'),
      meta: {
        title: '政策计算器-甜成办',
        requireAuth: true,
      },
    },
  ],
}
export default calculatorRoute
