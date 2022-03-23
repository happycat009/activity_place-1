import { request } from './axios'

/**
 * 获取不满意评价详情
 * @param
 * @returns
 */
const requestB418 = (aab200) => {
    const data = {
        methodnamedesc: '获取不满意评价详情',
        methodname: 'B418',
        aab200,
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
export { requestB418 }