import { request } from './axios'

/**
 * 校验账号中是否存在相同账号
 * @param {String} loginid 用户id
 * @param {String} tel 手机号
 * @returns
 */
const requestA404 = (loginid, tel) => {
  const data = {
    methodname: 'A404',
    loginid,
    tel,
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
export { requestA404 }
