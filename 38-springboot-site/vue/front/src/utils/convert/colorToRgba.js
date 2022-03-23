/**
 * 颜色16进制转RGB方法
 * @param {String} color 16进制颜色
 * @param {String|Number} opacity 默为1 透明度0-1
 */
const colorToRgba = (color, opacity = 1) => {
  // 十六进制颜色值的正则表达式
  const reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/
  // 如果是16进制颜色
  if (color) {
    let sColor = color.toLowerCase()
    if (reg.test(sColor)) {
      if (sColor.length === 4) {
        let sColorNew = '#'
        for (var i = 1; i < 4; i += 1) {
          sColorNew += sColor.slice(i, i + 1).concat(sColor.slice(i, i + 1))
        }
        sColor = sColorNew
      }
      // 处理六位的颜色值
      let sColorChange = []
      for (var i = 1; i < 7; i += 2) {
        sColorChange.push(parseInt('0x' + sColor.slice(i, i + 2)))
      }

      const rgba = `${sColorChange.join(',')},${opacity}`
      return rgba
    }
  }
  return ''
}

export { colorToRgba }
