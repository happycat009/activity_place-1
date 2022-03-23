/**
 * 企业宣传页路由
 */
const enterprisesPropagandaRoute = {
  path: '/propaganda',
  component: () => import('@/views/propaganda/index.vue'),
  children: [
    {
      path: 'common/:id',
      component: () => import('@/views/propaganda/index/index.vue'),
      meta: {
        title: '企业详情',
        requireAuth: false,
      },
    },
    {
      path: 'jiuyuan/:id',
      component: () =>
        import('@/views/propaganda/customized/jiuyuanyinhai/index.vue'),
      meta: {
        title: '久远银海软件股份有限公司',
        requireAuth: false,
      },
    },
    {
      path: 'huanglaowu/:id',
      component: () =>
        import('@/views/propaganda/customized/huanglaowu/index.vue'),
      meta: {
        title: '黄老五食品股份有限公司',
        requireAuth: false,
      },
    },
    {
      path: 'quansousuo/:id',
      component: () =>
        import('@/views/propaganda/customized/quansousuo/index.vue'),
      meta: {
        title: '内江全搜索网络科技有限公司',
        requireAuth: false,
      },
    },
    {
      path: 'feideli/:id',
      component: () =>
        import('@/views/propaganda/customized/feideli/index.vue'),
      meta: {
        title: '四川菲德力制药有限公司',
        requireAuth: false,
      },
    },
  ],
}

export default enterprisesPropagandaRoute
