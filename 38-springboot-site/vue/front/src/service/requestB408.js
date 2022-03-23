import { request } from './axios'

/**
 * 获取知名企业目录信息
 * @returns
 */
const requestB408 = () => {
  const data = {
    methodname: 'B408',
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
export { requestB408 }
