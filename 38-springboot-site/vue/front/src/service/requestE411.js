import { request } from './axios'

/**
 * 重置密码
 * @param {String} userid 用户id 身份证号
 * @param {String} password 新密码
 * @returns
 */
const requestE411 = (userid, password) => {
  const data = {
    methodnamedesc: '重置密码',
    methodname: 'E411',
    password,
    userid,
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
export { requestE411 }
