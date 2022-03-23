import { request } from './axios'

/**
 * 获取活动头条（新闻）详情
 * @param {String} chm650 新闻主键
 * @returns
 */
const requestE406 = (chm650) => {
    const data = {
        methodname: 'E406',
        chm650
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
export { requestE406 }
