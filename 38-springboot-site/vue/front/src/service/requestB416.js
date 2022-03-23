import { request } from './axios'

/**
 * 获取企业所有登录账号信息
 * @param {String} aab010 企业id
 * @returns
 */
const requestB416 = (aab010) => {
  const data = {
    methodnamedesc: '获取企业所有登录账号信息',
    methodname: 'B416',
    aab010,
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
export { requestB416 }
