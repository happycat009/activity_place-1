import { request } from './axios'

/**
 * 根据获取 业务局 补贴项目编号 (非必传) 企业信息满意度调查成果
 * @param chi031
 * @param chi037
 * @returns
 */
const requestB406 = (chi031,chi037) => {
  const data = {
    methodname: 'B406',
    chi031,
    chi037
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
export { requestB406 }
