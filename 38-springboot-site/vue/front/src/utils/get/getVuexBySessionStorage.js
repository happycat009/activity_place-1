import { LOCAL_STORAGE_KEY_HMHN } from '@/config/constants'
/**
 * 获取本地储存 sessionStorage 中的vuex
 * @param {String} key 获取的key
 */
const getVuexBySessionStorage = (key) => {
  let localData = sessionStorage.getItem(LOCAL_STORAGE_KEY_HMHN)
  if (localData) {
    localData = JSON.parse(localData)
    return localData[key] || {}
  } else {
    return {}
  }
}

export { getVuexBySessionStorage }
