/**
 * 获取本地储存在localStorage中的数据
 * @param {String} key 获取的key
 */
const getDataByLocalStorage = (key) => {
  let localData = localStorage.getItem(key)
  if (localData) {
    localData = JSON.parse(localData)
    return localData
  } else {
    return null
  }
}

export { getDataByLocalStorage }
