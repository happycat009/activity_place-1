import { request } from './axios'

/**
 * 删除账号
 * @param {String} positionid 岗位id
 * @param {String} userid 用户id
 * @param {String} orgid 组织机构id
 * @returns
 */
const requestB202 = (positionid, userid, orgid) => {
  const data = {
    methodnamedesc: '删除账号',
    methodname: 'B202',
    positionid,
    userid,
    orgid,
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
export { requestB202 }
