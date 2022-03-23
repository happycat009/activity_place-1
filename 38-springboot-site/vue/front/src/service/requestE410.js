import { request } from './axios'

/**
 * 验证用户是否存在
 * @param {String} loginid 用户id 身份证号
 * @param {String} tel 改id绑定的电话号码
 * @returns
 */
const requestE410 = (loginid, tel) => {
  const data = {
    methodnamedesc: '验证用户是否存在',
    methodname: 'E410',
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
export { requestE410 }
