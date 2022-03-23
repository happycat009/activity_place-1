import { request } from './axios'

/**
 * 标记消息已读
 * @param {String} aab001 企业主键
 * @param {String} mgid 消息主键
 * @returns
 */
const requestB302 = (
    aab001,
    mgid
) => {
  const data = {
    methodname: 'B302',
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
export { requestB302 }
