import { request } from './axios'
import config from '@/config'

/**
 * 新增投诉处理 (无业务)
 *
 * @returns
 */
const requestE102 = (params) => {
  const data = {
    chb004: '02',
    aab301: config.areaCode,
    methodname: 'E102',
    chb09g: 2, //投诉渠道
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
export { requestE102 }
