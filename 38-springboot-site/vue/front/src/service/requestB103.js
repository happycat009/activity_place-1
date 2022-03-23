import { request } from './axios'

/**
 * 添加账号
 * @param {String} orgid 组织机构id
 * @param {String} password 密码
 * @param {String} loginid 身份证号
 * @param {String} name 姓名
 * @param {String} tel 电话号码
 * @param {String} isadmin 是否为账号 1 为是
 * @returns
 */
const requestB103 = (orgid, password, loginid, name, tel, isadmin = '0') => {
  const data = {
    methodnamedesc: '添加账号',
    methodname: 'B103',
    password,
    orgid,
    loginid,
    name,
    tel,
    isadmin,
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
export { requestB103 }
