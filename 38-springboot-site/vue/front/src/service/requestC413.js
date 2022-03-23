import { request } from './axios'

/**
 * 根据补贴项目编码获取补贴项目信息
 * @returns
 */
const requestC413 = () => {
    const data = {
        methodname: 'C413',
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
export { requestC413 }
