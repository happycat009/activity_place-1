import { request } from './axios'

/**
 * 根据 获取公示列表
 * @param {String} params
 * @returns
 */
const requestE403 = (params) => {
  const data = {
    methodname: 'E403',
    ...params,
    chb57a: '1',
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
export { requestE403 }
