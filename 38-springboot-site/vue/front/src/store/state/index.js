const state = {
  // 用户信息

  userInfo: {
    // idCard: "510902199507236534",
    // openId: "oOuBO5cDjEGbdjdKPZt2E-aq8Yuw",
    // sessionId: "B7B9D3A6C8A6C27E321BBEE1DF9B3F0F",
    // user_id: 'sMPW8iZ72RTJzXte/6fhIw==',
    // tel: "18482160080",
    // userName: "曾小明",
    // token: 'oOuBO5cDjEGbdjdKPZt2E-aq8Yuw',
  },

  // 当前主菜单列表 控制高亮状态和面包屑
  currentMenuList: [
    {
      path: '/home',
      name: '首页',
    },
  ],

  userList: {
    cpb10d: '未登录用户身份证号', // 经办用户身份证号码
    aae012: '未登录用户姓名', // 经办用户姓名
    aab301: '510000', // 地市编码
    che015: '1', // 经办层级 0 省 1 地市 2 区县 3 乡镇街道 4 村社区
    chb015: '9999', // 区县编码
    chb017: '9999', // 乡镇编码
    chb018: '9999', // 村编码
    aae018: '未登录用户经办部门', // 经办部门名称
    loginid: '513922199610157466',
  },

  corporateInfo: {}, // 企业信息

  currentCake: {}, // 对用户常用操作的存储
}

export default state
