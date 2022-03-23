import { request } from './axios'

/**
 * 获取最新注册企业名单
 * @returns
 */
const requestB411 = () => {
  const data = {
    methodname: 'B411',
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
export { requestB411 }
