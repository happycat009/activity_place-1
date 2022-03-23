import { request } from './axios'

/**
 * 根据企业id获取信息菜单
 * @param {String} orgid 企业id
 * @returns
 */
const requestB401 = (orgid) => {
  const data = {
    methodname: 'B401',
    orgid,
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
export { requestB401 }
