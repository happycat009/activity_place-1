import { LOCAL_STORAGE_KEY_HMHN } from '@/config/constants'
/**
 * 获取本地储存 localStorage 中的 vuex
 * @param {String} key 获取的key 为 userInfo
 */
const getVuexByLocalStorage = (key) => {
  let localData = localStorage.getItem(LOCAL_STORAGE_KEY_HMHN)
  console.log("localStorage.getItem=======",localData)
  if (localData) {
    localData = JSON.parse(localData)
    return localData[key] || {}
  } else {
    return {}
  }
}

export { getVuexByLocalStorage }
