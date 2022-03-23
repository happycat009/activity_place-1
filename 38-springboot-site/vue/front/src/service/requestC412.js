import { request } from './axios'

/**
 * 根据补贴项目编码获取补贴项目信息
 * @param {String} chi031 补贴项目编码
 * @param {String} aab001 公司主键
 * @returns
 */
const requestC412 = (chi031,aab001) => {
    const data = {
        methodname: 'C412',
        chi031,
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
export { requestC412 }
