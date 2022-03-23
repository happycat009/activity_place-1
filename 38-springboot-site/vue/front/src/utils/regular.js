// 手机号校验正则表达式
const phoneNumberReg = /^1[3|4|5|6|7|8|9][0-9]\\d{8}$/

// 电子邮箱校验正则表达式
const emailReg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/

// 18位身份证号校验正则表达式
const idCardReg =
  /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/

export { phoneNumberReg, emailReg, idCardReg }
