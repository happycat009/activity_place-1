import { request } from './axios'

/**
 * 根据公司主键与补贴项目编码获取匹配信息
 * @returns
 */
const requestD401 = (aab001,chi031) => {
    const data = {
        methodname: 'D401',
        aab001,
        chi031
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
export { requestD401 }
