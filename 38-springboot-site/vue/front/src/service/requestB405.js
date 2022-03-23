import { request } from './axios'

/**
 * 根据
 * @param {Object} params
 * @returns
 */
const requestB405 = (params) => {
  const data = {
    methodname: 'B405',
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
export { requestB405 }
