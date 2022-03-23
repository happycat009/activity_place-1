import { request } from './axios'

/**
 * 根据补贴项目编码获取申报类别
 * @param {String} chi031 补贴项目编码
 * @returns
 */
const requestC416 = (chi031) => {
  const data = {
    methodnamedesc: '根据补贴项目编码获取申报数据项',
    methodname: 'C416',
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
export { requestC416 }
