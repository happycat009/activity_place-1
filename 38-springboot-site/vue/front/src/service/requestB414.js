import { request } from './axios'

/**
 * 企业消息查询
 * @returns
 */
const requestB414 = (aab001,mgid) => {
  const data = {
    methodname: 'B414',
    aab001,
    mgid
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
export { requestB414 }
