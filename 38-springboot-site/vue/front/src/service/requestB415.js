import { request } from './axios'

/**
 * 保存知名企业联系我们信息
 * @param
 * @returns
 */
const requestB415 = ({
                       aab220='ab20主键',
                       aab01w='统一信用代码',
                       aab012='企业名称',
                       aab241='用户类型',
                       aab242='姓名',
                       aab243='机构',
                       aab244='邮箱',
                       aab245='电话',
                       aab246='主题',
                       aab247='内容'
                     }) => {
    // console.log(arguments)

    const data = {
    methodname: 'B415',
        aab220:aab220,
        aab01w:aab01w,
        aab012:aab012,
        aab241:aab241,
        aab242:aab242,
        aab243:aab243,
        aab244:aab244,
        aab245:aab245,
        aab246:aab246,
        aab247:aab247
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
export { requestB415 }
