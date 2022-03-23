import { request } from './axios'

/**
 * 补贴项目申报
 * @param {Object} param 参数
 * @returns
 */
const requestC409 = (param) => {
  const data = {
    methodname: 'C409',
    ...param,
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
export { requestC409 }
