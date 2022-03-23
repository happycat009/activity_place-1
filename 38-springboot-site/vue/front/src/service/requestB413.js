import { request } from './axios'

/**
 * 企业消息查询
 * @returns
 */
const requestB413 = (pageNo,pageSize,aab001,issee) => {
  const data = {
    methodname: 'B413',
    pageNo,
    pageSize,
    aab001,
    issee
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
export { requestB413 }
