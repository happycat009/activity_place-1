import axios from 'axios'
import qs from 'qs'
import { USER_INFO, USER_LIST } from '@/store/constants'
import { getVuexBySessionStorage, getVuexByLocalStorage } from '@/utils/get'
import { ElMessage, ElLoading } from 'element-plus'
import { showMessage } from './status' // 引入状态码文件
import config from '@/config'
import $ from 'jquery'

let loading = null
// 设置接口超时时间
axios.defaults.timeout = 60000
// 请求地址
axios.defaults.baseURL = config.requestUrl

// http request 拦截器
axios.interceptors.request.use(
  (config) => {
    // 加载中配置
    const { isLoading, loadingText } = config
    if (isLoading) {
      loading = ElLoading.service({
        lock: true,
        text: loadingText,
        background: 'rgba(0, 0, 0, 0.4)',
      })
    }

    const { token } = getVuexByLocalStorage(USER_INFO)

    let header = {
      'Content-Type': 'application/json;charset=UTF-8', // 传参方式json
    }

    if (token) {
      //设置请求头
      config.headers.common = {
        ...header,
        token,
      }
    }

    return config
  },
  (error) => {
    console.log("错错错是我的错axios下的请求拦截的错")
    return Promise.reject(error)
  }
)
// http response 拦截器
axios.interceptors.response.use(
  (response) => {
    loading && loading.close()
    return response
  },
  (error) => {
    loading && loading.close()
    const { response } = error
    if (response) {
      // 传入响应码，匹配响应码对应信息
      ElMessage({
        message: showMessage(response.status),
        grouping: false,
        type: 'error',
      })
      return Promise.reject(response.data)
    } else {
      ElMessage({
        message: '网络连接异常,请稍后再试!',
        grouping: false,
        type: 'error',
      })
    }
  }
)

/**
 * 封装axios GET、POST 请求
 * @param {String} url 请求地址
 * @param {Object} params 请求参数
 * @param {String} type 请求类型 GET POST
 * @param {Boolean} isLoading 是否显示加载中
 * @param {Boolean} isLoading 是否显示加载中
 * @returns
 */
const request = (
  url = '',
  params = {},
  type = 'POST',
  isLoading = true,
  loadingText = '加载中'
) => {
  const userList = getVuexByLocalStorage(USER_LIST)
  params = {
    ...params,
    chb004: '02', // 调用渠道
    userList: JSON.stringify([userList]),
  }
  // 设置 url params type 的默认值
  return new Promise((resolve, reject) => {
    let promise
    if (type.toUpperCase() === 'GET') {
      promise = axios({
        url,
        params,
        isLoading,
        loadingText,
      })
    } else if (type.toUpperCase() === 'POST') {
      promise = axios({
        method: 'POST',
        url,
        data: params,
        isLoading,
        loadingText,
      })
    }
    // 处理返回
    promise
      .then((res) => {
        const { status, data: resData } = res
        // 服务器返回的数据是否正常判断
        if (status === 200 && resData) {
          const { code, data, errors } = resData

          // 错误信息存在 直接抛出第一个错误提示信息
          if (errors && errors.length > 0) {
            const err = errors[0]
            reject(err.msg)
          } else {
            // 服务器返回的数据是否正常判断
            if (code === 200 && data) {
              const { resultData } = data
              if (resultData) {
                const { code, message } = resultData
                if (code == '-1' || code == '500') {
                  ElMessage.error(
                    // `${params.methodname}接口调用失败！错误信息：${message}`
                    `${message}`
                  )
                  reject(resultData)
                } else {
                  resolve(resultData)
                }
              } else {
                reject(data)
              }
            } else {
              reject(resData)
            }
          }
        } else {
          reject(res)
        }
      })
      .catch((err) => {
        reject(err)
      })
  })
}

/**
 * 下载文件
 * @param fileId  文件id
 * @param ops 操作类型 0、预览 1、下载
 */
const downloadFile = function (fileId, ops = '1') {
  let a = $('<a/>')
  a.attr(
    'href',
    config.requestUrl + `/upload/downFileByFileId?fileId=${fileId}&ops=${ops}`
  )
  a.attr('target', '_blank')
  a[0].click()
  a.remove()
}

/**
 * 下载压缩文件
 * @param fileIdListStr  文件id
 */
const downloadZipFile = function (params) {
  let a = $('<a/>')
  a.attr(
    'href',
    config.requestUrl +
      `/upload/downloadZipFile?params=${encodeURIComponent(
        JSON.stringify(params)
      )}`
  )
  a.attr('target', '_blank')
  a[0].click()
  a.remove()
}

export { request, downloadFile, downloadZipFile }
