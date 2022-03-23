import { request } from './axios'

/**
 * 根据补贴项目编码获取补贴项目信息
 * @returns
 */
const requestD403 = (chb000) => {
    const data = {
        methodname: 'D403',
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
export { requestD403 }
