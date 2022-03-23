import { request } from './axios'

/**
 * 获取某知名企业信息
 * @param {String} aab220 企业id
 * @returns
 */
const requestB409 = (aab220) => {
  const data = {
    methodname: 'B409',
    aab220,
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
export { requestB409 }
