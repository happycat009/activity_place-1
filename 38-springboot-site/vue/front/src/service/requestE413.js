import { request } from './axios'

/**
 * 根据补贴项目编号获取 业务局
 * @param {String} chi031 补贴编码
 * @returns
 */
const requestE413 = (chi031) => {
  const data = {
    methodname: 'E413',
    chi031
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
export { requestE413 }
