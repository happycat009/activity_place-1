import { request } from './axios'

/**
 * 底层调用的时request
 * 获取登录人岗位信息
 * @param {String} userid 用户id
 * @returns
 */
const requestA402 = (userid) => {
  const data = {
    chb004: '02',
    methodname: 'A402',
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
export { requestA402 }
