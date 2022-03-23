import { request } from './axios'

/**
 * 上传图片
 * @param {String} filePath 图片路径
 * @param {String} fileName 文件名
 * @returns
 */
const requestUploadFileByBase64 = (filePath,fileName) => {
  const data = {
    fileContent: filePath,
    fileName,
  }

  return new Promise((resolve, reject) => {
    request('/upload/uploadFileByBase64', data, 'POST', true, '上传中')
      .then((res) => {
        resolve(res)
      })
      .catch((err) => {
        reject(err)
      })
  })
}
export { requestUploadFileByBase64 }
