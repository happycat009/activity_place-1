import {request} from './axios'


const requestE424 = (aab01w) => {
    const data = {
        methodname: 'E424',
        aab01w
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
export { requestE424 }