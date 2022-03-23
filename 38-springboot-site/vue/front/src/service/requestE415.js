import { request } from './axios'

/**
 * 根据统一社会信用代码查询市场监管局的企业基本信息
 * @param {String} aab01w 统一社会信用代码
 * @returns
 */
const requestE415 = (aab01w) => {
  const data = {
    methodname: 'E415',
    aab01w
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
export { requestE415 }
