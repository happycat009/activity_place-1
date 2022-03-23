/**
 * 政策直通车 路由
 */
const policyRoute = {
  path: '/policy',
  redirect: '/policy/index/supporting-projects',
  component: () => import('@/views/policy/index.vue'),
  children: [
    {
      path: 'index',
      component: () => import('@/views/policy/index/index.vue'),
      meta: {
        title: '政策直通车',
        requireAuth: false,
      },
      children: [
        {
          path: 'supporting-projects',
          component: () =>
            import('@/views/policy/supporting-projects/index.vue'),
          meta: {
            title: '扶持项目-政策直通车',
            requireAuth: false,
          },
        },
        {
          path: 'guide',
          component: () => import('@/views/policy/guide/index.vue'),
          meta: {
            title: '政策导读-政策直通车',
            requireAuth: false,
          },
        },
      ],
    },
    {
      path: 'details',
      component: () => import('@/views/policy/details/index.vue'),
      meta: {
        title: '政策详情',
        requireAuth: false,
      },
      children: [
        {
          path: 'match-report/:id',
          component: () => import('@/views/policy/match-report/index.vue'),
          meta: {
            title: '匹配报告-政策详情',
            requireAuth: true,
          },
        },
        {
          path: 'service-guide/:id',
          component: () => import('@/views/policy/service-guide/index.vue'),
          meta: {
            title: '申报指南-政策详情',
            requireAuth: false,
          },
        },
        {
          path: 'online-declaration/:id',
          component: () => import('@/views/under-development/index.vue'),
          meta: {
            title: '在线申报-政策详情',
            requireAuth: true,
          },
        },
      ],
    },
  ],
}
export default policyRoute
