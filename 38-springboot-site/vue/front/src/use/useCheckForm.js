import { ref, computed, watch } from 'vue'

/**
 * 校验表单是否填写完成
 * @param {*} formEl formRef
 * @returns true|false
 */
const useCheckForm = (formEl) => {
  if (!formEl) {
    console.error('未传入 formRef')
    return
  }
  let tag = false
  if (!formEl.validate) {
    formEl = formEl.value
  }
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      tag = true
    } else {
      const formErrPropsArr = []
      for (let item in formErrObj) {
        formErrPropsArr.push(item)
      }
      const firstErrProps = formErrPropsArr[0]
      formEl.scrollToField(firstErrProps)
    }
  })
  return tag
}

export { useCheckForm }
