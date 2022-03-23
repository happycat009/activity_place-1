import { request } from './axios'

/**
 * 根据 chb570 获取公示详情
 * @param {String} chb570
 * @returns
 */
const requestE404 = (params) => {
  const data = {
    methodname: 'E404',
    ...params,
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
export { requestE404 }
