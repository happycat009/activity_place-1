import { request } from './axios'

/**
 * 根据 chi037 获取电话号码
 * @returns
 */
const requestC414 = (chi037) => {
    const data = {
        methodname: 'C414',
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
export { requestC414 }
