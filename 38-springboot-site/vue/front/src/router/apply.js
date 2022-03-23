/**
 * 补贴项目申报 路由
 */
const applyRoute = {
  path: '/apply',
  component: () => import('@/views/apply/index.vue'),
  children: [
    {
      path: '100080',
      component: () => import('@/views/apply/511000/chi031_100080.vue'),
      meta: {
        title: '内江市2021年推动服务业高质量发展专项资金奖励（企业培育类）',
        requireAuth: true,
      },
    },
    {
      path: '100081',
      component: () => import('@/views/apply/511000/chi031_100081.vue'),
      meta: {
        title:
          '内江市2021年推动服务业高质量发展专项资金奖励（统计基础规范化建设类）',
        requireAuth: true,
      },
    },
    {
      path: '100100',
      component: () => import('@/views/apply/511000/chi031_100100.vue'),
      meta: {
        title: '内江市2021年工业企业流动资金贷款贴息项目',
        requireAuth: true,
      },
    },
  ],
}
export default applyRoute
