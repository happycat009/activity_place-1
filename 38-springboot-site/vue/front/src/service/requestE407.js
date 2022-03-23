import { request } from './axios'

/**
 * 根据 orgid 获取地址
 * @param {String} orgid
 * @returns
 */
const requestE407 = (orgid) => {
    const data = {
        methodname: 'E407',
        orgid,
    }

    return new Promise((resolve, reject) => {
        request(
            '/frontRestService/frontCommonDataRestService/getData',
            data,
            'POST',
            false
        )
            .then((res) => {
                resolve(res)
            })
            .catch((err) => {
                reject(err)
            })
    })
}
export { requestE407 }
