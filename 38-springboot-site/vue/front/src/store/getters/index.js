import {
  USER_INFO,
  USER_LIST,
  CURRENT_MENU_LIST,
  CORPORATE_INFO,
} from '@/store/constants/index.js'

const getters = {
  // 获取用户信息
  [USER_INFO](state) {
    return state.userInfo
  },
  // 获取用户信息
  [USER_LIST](state) {
    return state.userList
  },
  [CURRENT_MENU_LIST](state) {
    return state.currentMenuList
  },
  [CORPORATE_INFO](state) {
    return state.corporateInfo
  },
}

export default getters
