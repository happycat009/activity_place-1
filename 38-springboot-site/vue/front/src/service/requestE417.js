import {request} from './axios'


const requestE417 = (aab01w) => {
    const data = {
        methodname: 'E417',
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
export { requestE417 }