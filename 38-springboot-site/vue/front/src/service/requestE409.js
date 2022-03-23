import { request } from './axios'

/**
 * 根据 che090 获取材料示例
 * @param {String} che090
 * @returns
 */
const requestE409 = (che090) => {
  const data = {
    methodname: 'E409',
    che090,
  }

  return new Promise((resolve, reject) => {
    request(
      '/frontRestService/frontCommonDataRestService/getData',
      data,
      'POST',
      false
    )
      .then((res) => {
        resolve(res)
      })
      .catch((err) => {
        reject(err)
      })
  })
}
export { requestE409 }
