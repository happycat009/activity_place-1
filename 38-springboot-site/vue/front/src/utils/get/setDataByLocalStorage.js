/**
 * 储存在localStorage中的数据
 * @param {String} key 获取的key
 */
const setDataByLocalStorage = (key, data) => {
  localStorage.setItem(key, data)
}

export { setDataByLocalStorage }
