import {request} from './axios'

//aab01w 统一社会信用代码
const requestE421 = (aab01w) => {
    const data = {
        methodname: 'E421',
        aab01w,
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
export { requestE421 }