import { request } from './axios'

/**
 * 根据补贴项目编码获取申报数据项
 * @param {String} chi031 补贴项目编码
 * @returns
 */
const requestC415 = (chi031) => {
  const data = {
    methodnamedesc: '根据补贴项目编码获取申报数据项',
    methodname: 'C415',
    chi031,
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
export { requestC415 }
