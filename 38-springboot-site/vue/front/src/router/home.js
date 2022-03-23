/**
 * 首页 路由
 */
const homeRoute = [
  // 路由重定向
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/home',
    component: () => import('@/views/home/index.vue'),
    meta: {
      title: '首页',
      requireAuth: false,
    },
    children: [
      {
        path: 'questionnaire/:id',
        component: () => import('@/views/home/questionnaire/index.vue'),
        meta: {
          title: '企业满意度问卷调查',
          requireAuth: false,
        },
      },
      {
        path: 'declaration-progress',
        component: () => import('@/views/under-development/index.vue'),
        meta: {
          title: '页面搭建中',
          requireAuth: true,
        },
      },
      {
        path: 'under-development',
        component: () => import('@/views/under-development/index.vue'),
        meta: {
          title: '页面搭建中',
          requireAuth: false,
        },
      },
      {
        path: 'publicity',
        component: () => import('@/views/home/publicity/index.vue'),
        children: [
          {
            path: '',
            component: () => import('@/views/home/publicity/index/index.vue'),
            meta: {
              title: '兑付名单查询',
              requireAuth: false,
            },
          },
          {
            path: 'details/:id',
            component: () => import('@/views/home/publicity/details/index.vue'),
            meta: {
              title: '阳光公示详情',
              requireAuth: false,
            },
          },
          {
            path: 'complaint',
            name: 'complaint',
            component: () =>
              import('@/views/home/publicity/complaint/index.vue'),
            meta: {
              title: '投诉举报',
              requireAuth: false,
            },
          },
        ],
      },
    ],
  },
]

export default homeRoute
