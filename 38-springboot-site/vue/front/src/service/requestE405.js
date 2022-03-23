import { request } from './axios'

/**
 * 获取活动头条（新闻）列表
 * @returns
 */
const requestE405 = () => {
  const data = {
    methodname: 'E405',
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
export { requestE405 }
