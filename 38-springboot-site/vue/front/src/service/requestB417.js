import { request } from './axios'

/**
 * 获取企业评价
 * @param
 * @returns
 */
const requestB417 = (param) => {
    const data = {
        methodnamedesc: '获取企业评价',
        methodname: 'B417',
        ...param
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
export { requestB417 }
