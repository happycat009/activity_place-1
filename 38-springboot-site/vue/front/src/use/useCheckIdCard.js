import { idCardReg } from '@/utils/regular'

/**
 * 校验18位数字身份证号码是否填写正确
 * @param {Object} rule
 * @param {String} value
 * @param {Function} callback
 * @returns
 */
const useCheckIdCard = (rule, value, callback) => {
  const factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
  const parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2]
  const code = value.substring(17)
  if (idCardReg.test(value)) {
    let sum = 0
    for (var i = 0; i < 17; i++) {
      sum += value[i] * factor[i]
    }
    if (parity[sum % 11] != code.toUpperCase()) {
      callback(new Error('请输入正确的身份证号码'))
    }
    callback()
  } else {
    callback(new Error('请输入正确的身份证号码'))
  }
}

export { useCheckIdCard }
