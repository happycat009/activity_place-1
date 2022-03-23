/**
 * demo 路由
 */
const demoRoute = {
  path: '/demo',
  component: () => import('@/views/demo/index.vue'),
  meta: {
    title: '使用示例',
    icon: 'home-outlined',
    requireAuth: false,
  },
}
export default demoRoute
