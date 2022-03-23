import { request } from './axios'

/**
 * 根据用户信息获取申报记录
 * @param
 * @returns
 */
const requestB407 = (aab001,queryParam,pageNo,pageSize,aae100) => {
  const data = {
    methodname: 'B407',
    aab001,
    queryParam,
    pageNo,
    pageSize,
    aae100
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
export { requestB407 }
