import { request } from './axios'

/**
 * 根据补贴项目编码获取补贴项目申报需要的材料信息
 * @param {String} chi031 补贴项目编码
 * @returns
 */
const requestC406 = (chi031) => {
  const data = {
    methodnamedesc: '根据补贴项目编码获取补贴项目申报需要的材料信息',
    methodname: 'C406',
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
export { requestC406 }
