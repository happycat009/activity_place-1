<template>
  <div class="statistics">
    <ul>
      <template v-for="item in renderList" :key="item.id">
        <li>
          <img :src="item.iconSrc" alt="" />
          <div class="key">{{ item.key }}：</div>
          <div class="value">{{ item.value || 0 }}</div>
          <div class="unit">{{ item.unit }}</div>
        </li>
      </template>
    </ul>
  </div>
</template>

<script>
import { requestGetData, requestGetVisitStatistics } from '@/service'
import image01 from '@/assets/images/home/01.png'
import image02 from '@/assets/images/home/02.png'
import image03 from '@/assets/images/home/03.png'
import image04 from '@/assets/images/home/04.png'
import image05 from '@/assets/images/home/05.png'
import image06 from '@/assets/images/home/06.png'

export default {
  name: 'HomeStatistics',
  props: {},
  setup() {
    return {}
  },
  data() {
    return {
      businessList: [],
      visitList: [],
    }
  },
  created() {},
  mounted() {
    let _this = this
    // enterprisesUnder 申报中的企业数量
    // hb00MoneyCount   申报的资金
    // policyCount      政策数量
    // todayPolicyCount 今日申报的政策
    requestGetData('E401', {
      methodList: JSON.stringify([
        { method: 'enterprisesUnder' },
        { method: 'hb00MoneyCount' },
        { method: 'policyCount' },
        { method: 'todayPolicyCount' },
      ]),
    })
      .then((res) => {
        let tempList = []
        const { code, data } = res
        if (code == '200') {
          const {
            enterprisesUnderMap,
            policyCountMap,
            hb00MoneyCountMap,
            todayPolicyCountMap,
          } = data

          // 申报中的企业数量
          let enterprisesUnderMapRes = _this.unitConvert(
            Math.floor(Number(enterprisesUnderMap.num) || 0)
          )
          // 政策数量
          let policyCountMapRes = _this.unitConvert(
            Math.floor(Number(policyCountMap.num) || 0)
          )
          // 申报的资金
          let hb00MoneyCountMapRes = _this.unitConvert(
            Math.floor(Number(hb00MoneyCountMap.num) || 0)
          )
          // 今日申报的政策
          let todayPolicyCountMapRes = _this.unitConvert(
            Math.floor(Number(todayPolicyCountMap.num) || 0)
          )
          tempList.push({
            id: 'zzsb',
            iconSrc: image01,
            value: enterprisesUnderMapRes.num + enterprisesUnderMapRes.unit,
            unit: '家',
            key: '正在申报的企业',
          })
          tempList.push({
            id: 'dffczj',
            iconSrc: image03,
            value: hb00MoneyCountMapRes.num + hb00MoneyCountMapRes.unit,
            unit: '万元',
            key: '累计完成申报资金',
          })
          tempList.push({
            id: 'fbhqzc',
            iconSrc: image02,
            value: policyCountMapRes.num + policyCountMapRes.unit,
            unit: '项',
            key: '惠企政策',
          })

          tempList.push({
            id: 'jrsb',
            iconSrc: image04,
            value: todayPolicyCountMapRes.num + todayPolicyCountMapRes.unit,
            unit: '项',
            key: '今日申报政策',
          })
        }
        _this.businessList = tempList
      })
      .catch((res) => {})

    requestGetVisitStatistics({})
      .then((res) => {
        let tempList = []
        if (res && res.statistics) {
          for (let key in res.statistics) {
            switch (key) {
              //case 'visitCountToday':
              case 'visitCount':
                let visitCountTodayRes = _this.unitConvert(
                  Math.floor(Number(res.statistics.visitCountToday) || 0)
                )
                tempList.push({
                  id: 'visitCountToday',
                  iconSrc: image06,
                  value: visitCountTodayRes.num + visitCountTodayRes.unit,
                  unit: '人次',
                  key: '今日访问量',
                })
                break
              //case 'visitCount':
              case 'visitCountToday':
                let visitCountRes = _this.unitConvert(
                  Math.floor(Number(res.statistics.visitCount) || 0)
                )
                tempList.push({
                  id: 'visitCount',
                  iconSrc: image05,
                  value: visitCountRes.num + visitCountRes.unit,
                  unit: '人次',
                  key: '累计访问量',
                })
                break
            }
          }
        }
        this.visitList = tempList
      })
      .catch((err) => {
        console.error('err', err)
      })
  },
  components: {},
  computed: {
    renderList: function () {
      return this.businessList.concat(this.visitList)
    },
  },
  methods: {},
}
</script>

<style lang="scss" scoped>
.statistics {
  width: 100%;
  position: absolute;
  left: 0;
  bottom: 0;
  ul {
    display: flex;
    align-items: center;
    background-color: #0066ff;
    padding: 0 130px;
    padding: 0 6.770833%;
    line-height: 76px;
    color: #fff;
    white-space: nowrap;
    font-size: 14px;

    li {
      display: flex;
      align-items: center;
      margin-right: 20px;
      img {
        width: 40px;
        margin-right: 10px;
      }
      .value {
        font-size: 20px;
        font-weight: 700;
        padding-right: 4px;
      }
    }
  }
  @media screen and (max-width: 1500px) {
    ul {
      padding: 0 40px;
      line-height: 40px;
      li {
        font-size: 14px;
        img {
          width: 24px;
          margin-right: 8px;
        }
        .value {
          font-size: 18px;
        }
      }
    }
  }
}
</style>
