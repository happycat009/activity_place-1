import { ref, computed, watch } from 'vue'
import { requestGetCollectionData } from '@/service'

/**
 * 对整个表单进行重置，将所有字段值重置为初始值并移除校验结果
 * @param {*} formEl formRef
 * @returns
 */
const useResetForm = (formEl) => {
  if (!formEl) {
    console.error('未传入 formRef')
    return
  }
  if (!formEl.validate) {
    formEl = formEl.value
  }
  formEl.resetFields()
}

export { useResetForm }
