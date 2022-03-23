import {request} from './axios'


const requestE418 = (aab01w,aac002) => {
    const data = {
        methodname: 'E418',
        aab01w,
        aac002
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
export { requestE418 }