import { LOCAL_STORAGE_KEY_HMHN } from '@/config/constants'
/**
 * 提交表单
 * @param {String} formEl ref Dom
 */
const submitForm = (formEl) => {
  return new Promise((resolve, reject) => {
    if (!formEl) {
      reject(false)
    }
    formEl.validate((valid, formErrObj) => {
      if (valid) {
        resolve()
      } else {
        const formErrPropsArr = []
        for (let item in formErrObj) {
          formErrPropsArr.push(item)
        }
        const firstErrProps = formErrPropsArr[0]
        formEl.scrollToField(firstErrProps)
        reject(formErrObj)
      }
    })
  })
}

export { submitForm }
