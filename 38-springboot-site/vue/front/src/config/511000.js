/* 
  配置文件
  文件中保存的是一些常用配置 请谨慎更改 更改前请确认是否存在使用
*/

import { requestUrl, areaCode } from '@/config/city'

const config = {
  // api 请求地址
  requestUrl,
  areaCode, // 行政区域编码
  areaName_zh: '内江', // 行政区域中文名称/简称 内江 511000
}

export default config
