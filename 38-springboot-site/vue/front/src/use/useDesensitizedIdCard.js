/**
 * 脱敏身份证号
 * @param {String} idCard 18位身份证号
 * @returns true|false
 */
const useDesensitizedIdCard = (idCard) => {
  if (!idCard) {
    console.error('未传入身份证号')
    return ''
  }
  let len = idCard.length
  let str =
    idCard.substr(0, 1) + '*'.repeat(len - 2) + idCard.substr(len - 1, len)
  return str
}

export { useDesensitizedIdCard }
