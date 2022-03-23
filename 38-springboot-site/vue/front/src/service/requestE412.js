import { request } from './axios'

/**
 * 根据企业id获取 该企业申报的补贴
 * @param {String} aab001 企业id
 * @returns
 */
const requestE412 = (aab001) => {
  const data = {
    methodname: 'E412',
    aab001
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
export { requestE412 }
