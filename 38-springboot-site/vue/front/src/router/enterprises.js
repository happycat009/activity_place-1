/**
 * 企业中心 路由
 */
const enterprisesRoute = [
  {
    path: '/enterprises',
    redirect: '/enterprises/info/basic',
  },
  {
    path: '/enterprises',
    component: () => import('@/views/enterprises/index.vue'),
    children: [
      {
        path: 'info',
        redirect: '/enterprises/info/basic',
        component: () => import('@/views/enterprises/info/index.vue'),
        children: [
          {
            path: 'basic',
            component: () => import('@/views/enterprises/info/basic.vue'),
            meta: {
              title: '基本信息-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'personnel',
            component: () => import('@/views/enterprises/info/personnel.vue'),
            meta: {
              title: '人事信息-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'operating',
            component: () => import('@/views/enterprises/info/operating.vue'),
            meta: {
              title: '经营数据-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'honor',
            component: () => import('@/views/enterprises/info/honor.vue'),
            meta: {
              title: '荣誉资质-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'intellectual',
            component: () =>
              import('@/views/enterprises/info/intellectual.vue'),
            meta: {
              title: '知识产权-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'projects',
            component: () => import('@/views/enterprises/info/projects.vue'),
            meta: {
              title: '重点项目-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'tax',
            component: () => import('@/views/enterprises/info/tax.vue'),
            meta: {
              title: '纳税信息-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'insurance',
            component: () => import('@/views/enterprises/info/insurance.vue'),
            meta: {
              title: '参保信息-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'credit',
            component: () => import('@/views/enterprises/info/credit.vue'),
            meta: {
              title: '参保信息-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'environmental',
            component: () =>
              import('@/views/enterprises/info/environmental.vue'),
            meta: {
              title: '环保信息-企业资料-企业中心',
              requireAuth: true,
            },
          },
          {
            path: 'punish',
            component: () =>
                import('@/views/enterprises/info/punish.vue'),
            meta: {
              title: '行政处罚-企业资料-企业中心',
              requireAuth: true,
            },
          },
        ],
      },
      {
        path: 'recommend',
        component: () => import('@/views/enterprises/recommend/index.vue'),
        meta: {
          title: '推荐申报政策-企业中心',
          requireAuth: true,
        },
      },
      {
        path: 'record',
        component: () => import('@/views/enterprises/record/index.vue'),
        meta: {
          title: '申报记录-企业中心',
          requireAuth: true,
        },
      },
      {
        path: 'message',
        component: () => import('@/views/enterprises/message/index.vue'),
        meta: {
          title: '我的消息-企业中心',
          requireAuth: true,
        },
      },
      {
        path: 'account',
        component: () => import('@/views/enterprises/account/index.vue'),
        meta: {
          title: '企业账号管理-企业中心',
          requireAuth: true,
        },
      },
      {
        path: 'evaluate',
        component: () => import('@/views/enterprises/evaluate/index.vue'),
        meta: {
          title: '评价记录-企业中心',
          requireAuth: true,
        },
      },

    ],
  },
]

export default enterprisesRoute
