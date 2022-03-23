import { request } from './axios'

/**
 * 根据orgid或aab001获取企业信息
 * @param {String} orgid 岗位id
 * @returns
 */
const requestB404 = (orgid,aab001) => {
  const data = {
    chb004: '02',
    methodname: 'B404',
    orgid,
    aab001
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
export { requestB404 }
