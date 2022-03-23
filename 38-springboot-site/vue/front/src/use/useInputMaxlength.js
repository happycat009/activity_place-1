import { ref, computed, watch } from 'vue'

/**
 * 输入框 最多长度输入限制 解决number类型失效问题
 * @param {Number} len 最大长度
 * @returns true|false
 */
const useInputMaxlength = (value, len) => {
  if (value.length > length) value = value.slice(0, len)
  return value
}

export { useInputMaxlength }
