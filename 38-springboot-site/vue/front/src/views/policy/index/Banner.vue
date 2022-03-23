<template>
  <div class="policy-banner">
    <div class="left">
      <img src="~assets/images/policy/calc.png" alt="" />
      <div class="con">
        <div class="name YouSheBiaoTiHei">政策计算器</div>
        <div class="desc">精准、快捷生成匹配报告</div>
        <button @click="$router.push('/calculator')">立即使用</button>
      </div>
    </div>
    <div class="center">
      <p class="YouSheBiaoTiHei">
        已为<span style="color: #ffc600">{{ enterprisesCount.num || 0 }}</span
        >{{ enterprisesCount.suffix + enterprisesCount.unit }}企业服务
      </p>
      <p class="indent YouSheBiaoTiHei">
        共计兑付资金<span style="color: #ffc600">{{
          digitSegmentation(moneyCount.num, 3)
        }}</span
        >{{ moneyCount.suffix + moneyCount.unit }}
      </p>
    </div>
    <div class="right">
      <button @click="$router.push('/home/publicity')">兑付详情</button>
    </div>
  </div>
</template>

<script>
import { requestGetData, requestGetVisitStatistics } from '@/service'
export default {
  name: 'PolicyBanner',
  data() {
    return {
      enterprisesCount: { num: '', suffix: '', unit: '家' },
      moneyCount: { num: '', suffix: '', unit: '元' },
    }
  },
  components: {},
  mounted() {
    requestGetData('E401', {
      methodList: JSON.stringify([
        { method: 'enterprisesUnder' },
        { method: 'hb00MoneyCount' },
      ]),
    }).then((res) => {
      let _this = this
      const { code, data } = res
      if (code == '200') {
        const { enterprisesUnderMap, hb00MoneyCountMap } = data
        // 申报中的企业数量
        let enterprisesUnderMapRes = _this.unitConvert(
          Math.floor(Number(enterprisesUnderMap.num) || 0)
        )
        // 申报的资金
        let hb00MoneyCountMapRes = _this.unitConvert(
          Math.floor(Number(hb00MoneyCountMap.num) || 0)
        )
        this.enterprisesCount = {
          num: enterprisesUnderMapRes.num,
          suffix: enterprisesUnderMapRes.unit,
          unit: '家',
        }
        this.moneyCount = {
          num: hb00MoneyCountMapRes.num,
          suffix: enterprisesUnderMapRes.unit,
          unit: '元',
        }
      }
    })
  },
  computed: {},
}
</script>

<style lang="scss" scoped>
.policy-banner {
  width: 100%;
  height: 150px;
  background-image: linear-gradient(#4285ff, #4285ff);
  color: #fff;
  padding-left: 40px;
  padding-left: 30px;
  display: flex;
  button {
    transition: all 0.3s;
    cursor: pointer;
    &:hover {
      opacity: 0.8;
    }
  }
  .left {
    display: flex;
    line-height: 1;
    padding-top: 24px;
    img {
      width: 104px;
      margin-right: 20px;
    }
    .name {
      font-size: 44px;
      font-weight: 500;
    }
    .desc {
      font-size: 14px;
      padding-top: 6px;
      color: #d8e3ff;
    }
    button {
      width: 92px;
      height: 32px;
      background-image: linear-gradient(2deg, #ffae00 0%, #ffcc00 100%);
      border-radius: 16px;
      font-size: 16px;
      line-height: 32px;
      margin-top: 16px;
    }
  }
  .center {
    font-size: 36px;
    line-height: 1;
    position: relative;
    margin-left: 48px;
    margin-left: 40px;
    padding-left: 48px;
    padding-left: 40px;
    padding-top: 38px;
    flex: 1;
    overflow: hidden;
    .indent {
      text-indent: 2em;
    }
    &::before {
      content: '';
      position: absolute;
      top: 50%;
      left: 0;
      transform: translateY(-50%);
      width: 2px;
      height: 118px;
      background-color: rgba(#fff, 0.2);
      border-radius: 1px;
    }
  }
  .right {
    padding-top: 58px;
    padding-right: 42px;
    padding-right: 30px;
    padding-left: 20px;
    button {
      width: 96px;
      height: 36px;
      background-image: linear-gradient(2deg, #ffae00 0%, #ffcc00 100%);
      border-radius: 4px;
    }
  }
}
</style>
