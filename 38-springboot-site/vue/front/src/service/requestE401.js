import { request } from './axios'

/**
 * 统计接口
 * @param {String} methodList
 * @returns
 */
const requestE401 = (methodList) => {
  if(typeof methodList != "string"){
    methodList = JSON.stringify(methodList);
  }
  const data = {
    chb004: '02',
    methodname: 'E401',
    methodList,
  }

  return new Promise((resolve, reject) => {
    request('/frontRestService/frontCommonDataRestService/getData', data)
      .then((res) => {
        resolve(res)
      })
      .catch((err) => {
        reject(err)
      })
  })
}
export { requestE401 }
