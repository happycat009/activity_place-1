import {request} from './axios'

//aac002 身份证号码
const requestE420 = (aac002) => {

    const data = {
        methodname: 'E420',
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
export { requestE420 }