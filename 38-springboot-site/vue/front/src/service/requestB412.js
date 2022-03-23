import { request } from './axios'

/**
 * 撤销申报
 * @returns
 */
const requestB412 = (chb000) => {
  const data = {
    methodname: 'B412',
    chb000
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
export { requestB412 }
