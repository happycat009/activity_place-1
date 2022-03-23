import { request } from './axios'

/**
 * 统计数据查询
 * @returns
 */
const requestGetVisitStatistics = (params = {}) => {
  const data = {
    systemtype: '1',
    ...params
  }
  return new Promise((resolve, reject) => {
    request(
      '/frontRestService/behaviorService/getVisitStatistics',
      data,
      'POST'
    )
      .then((res) => {
        resolve(res)
      })
      .catch((err) => {
        if (reject) {
          reject(err)
        } else {
          console.error(err)
        }
      })
  })
}
export { requestGetVisitStatistics }
