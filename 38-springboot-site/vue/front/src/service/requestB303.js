import { request } from './axios'

/**
 * 修改账号
 * @param {String} userid 用户id
 * @param {String} loginid 身份证号
 * @param {String} password 密码
 * @returns
 */
const requestB303 = (userid, loginid, password) => {
  const data = {
    methodnamedesc: 'B303',
    methodname: 'B303',
    userid,
    loginid,
    password,
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
export { requestB303 }
