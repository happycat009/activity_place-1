// import { getIsDevEnv } from "@/utils/get";
import {
  M_UPDATE_USER_INFO,
  M_UPDATE_USER_LIST,
  M_UPDATE_CURRENT_MENU_LIST,
  M_CURRENT_CAKE,
  M_UPDATE_CORPORATE_INFO,
} from '@/store/constants/index.js'

// 当前环境是否为开发环境
// const isDev = getIsDevEnv()

const mutations = {
  // 更新用户信息
  [M_UPDATE_USER_INFO](state, data) {
    // 存在数据
    if (data) {
      state.userInfo = { ...state.userInfo, ...data }
    } else {
      // 针对 null 用于清空用户数据
      state.userInfo = {}
    }
  },
  // 更新用户信息
  [M_UPDATE_USER_LIST](state, data) {
    // 存在数据
    if (data) {
      state.userList = { ...state.userList, ...data }
    } else {
      // 针对 null 用于清空用户数据
      state.userList = {}
    }
  },
  [M_UPDATE_CURRENT_MENU_LIST](state, data) {
    state.currentMenuList = [...data]
  },
  [M_CURRENT_CAKE](state, data) {
    state.currentCake = {
      ...state.currentCake,
      ...data,
    }
  },
  [M_UPDATE_CORPORATE_INFO](state, data) {
    state.corporateInfo = {
      ...data,
    }
  },
}

export default mutations
