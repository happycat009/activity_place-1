import { createApp } from 'vue'
import App from './App.vue'

import router from './router'
import store from './store'
import mitt from 'mitt'

import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'

// import '@/assets/styles/scss/themes/element-variables.scss'
import { ElMessage } from 'element-plus'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import 'normalize.css/normalize.css'
import 'element-plus/theme-chalk/index.css'
import '@/assets/fonts/PangMenZhengDao/PangMenZhengDao.scss'
import '@/assets/iconfont/index.scss'
import '@/assets/fonts/YouSheBiaoTiHei/YouSheBiaoTiHei.scss'

import '@/assets/styles/scss/index.scss'

import SliderVerify from 'slider-verify-v3'
import 'slider-verify-v3/lib/SliderVerify.css'
import global_ from '@/common' //引用文件

const app = createApp(App)
app.config.productionTip = false
app.config.globalProperties.$message = ElMessage
app.config.globalProperties.$api = global_
const vueEvent = mitt()
app.config.globalProperties.$bus = vueEvent
// 屏蔽警告
app.config.warnHandler = () => null

app.mixin({
  data() {
    return {
      codeTableMap: {},
      tempxx: {},
    }
  },
  methods: {
    userCodeTableMap(collection, val) {
      if (this.codeTableMap[collection] && this.codeTableMap[collection][val]) {
        return
      }
      this.$api
        .getCollectionData(collection, val)
        .then((res) => {
          if (res) {
            let tempCollectionMap = {}
            res.map((item) => {
              tempCollectionMap[item['aaa102']] = item['aaa103']
            })
            this.codeTableMap[collection] = {
              ...tempCollectionMap,
              ...this.codeTableMap[collection],
            }
          } else {
            console.error(`码值${collection}获取失败！！！`)
          }
        })
        .catch((err) => {
          console.error(`码值${collection}获取失败！！！`)
        })
    },
    getCollectionDesc(collection, val) {
      if (!this.tempyy[collection]) {
        return ''
      } else {
        return this.tempyy[collection][val]
      }
    },
    getCollectionList(collection) {
      if (!this.tempyy[collection]) {
        return null
      } else {
        return this.tempyy[collection]
      }
    },
    unitConvert(num, begin, precision) {
      if (!begin) {
        begin = 0
      }
      if (!precision) {
        precision = 2
      }
      var moneyUnitsBase = ['', '万', '亿', '万亿']
      var moneyUnits = moneyUnitsBase.slice(begin, moneyUnitsBase.length)
      var dividend = 10000
      var currentNum = num
      //转换数字
      var currentUnit = ''
      //转换单位
      for (var i = 0; i < 4; i++) {
        currentUnit = moneyUnits[i]
        var stringNum = currentNum.toString()
        var dotIndex = stringNum.indexOf('.')

        var stringInt =
          dotIndex !== -1 ? stringNum.substring(0, dotIndex) : stringNum
        if (stringInt.length < 5) {
          break
        }
        currentNum = currentNum / dividend
      }
      var m = { num: 0, unit: '' }
      m.num = !currentUnit ? currentNum : parseFloat(currentNum).toFixed(2)
      m.unit = currentUnit
      return m
    },
    /**
     * 数字{digit}位分节法
     * @param target 分割目标
     * @param digit 分节长度
     */
    digitSegmentation(target, digit) {
      if (!target || !Number(target)) {
        return '0'
      }
      let numList = (target + '').split('.')
      let integer = numList[0]
      let decimal = numList[1]
      let integerList = integer.split('').reverse()
      let tempIntegerList = []
      for (let pos = 0; pos < integerList.length; pos++) {
        tempIntegerList.push(integerList[pos])
        if ((pos + 1) % digit == 0 && pos + 1 < integerList.length) {
          tempIntegerList.push(',')
        }
      }
      return (
        tempIntegerList.reverse().join('').trim(',') +
        (decimal ? '.' + decimal : '')
      )
    },
  },
  computed: {
    tempyy() {
      return this.tempxx
    },
  },
  watch: {
    codeTableMap: {
      handler(val) {
        this.tempxx = val
      },
      deep: true,
      immediate: true,
    },
  },
})

// ElementPlus使用中文
app.use(ElementPlus, {
  locale: zhCn,
})
app.use(VueViewer)
app.use(router)
app.use(store)
app.use(SliderVerify)
app.mount('#app')
