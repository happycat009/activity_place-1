<template>
  <div class="match-info">
    <div class="bg">
      <img src="~assets/images/policy/bg-match-report.png" alt="" />
    </div>
    <div class="con">
      <div class="left">
        <MatchInfoEcharts :matchReportItemVoList="matchReportItemVoList"></MatchInfoEcharts>
      </div>
      <div class="right">
        <div class="suitability">
          <div class="key">匹配度:</div>
          <div class="value">{{maxChr081}}%</div>
        </div>
        <div class="suitability-desc">
          <h3>匹配度说明:</h3>
          <p>匹配度90%+，申报的可行性和成功率最高</p>
          <p>匹配度70%+，申报的可行性和成功率较高</p>
          <p>
            匹配度65%+，短期内可能难以满足申报条件，但可以通过提升经营状况达到条件
          </p>
        </div>
        <div class="hint-wrapper">
          <div class="text">
            <i class="iconfont iconfont-hint"></i>
            <span> 当前企业资料完善度</span>
            <span class="val">{{matchStatisticsInfo.fillrate}}%</span>
            <span>，进一步完善企业资料，为你更精准地匹配政策</span>
          </div>
          <button @click="completeCorpoInfo">立即完善</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import MatchInfoEcharts from './MatchInfoEcharts.vue'

export default {
  name: 'ServiceGuideBasicInfo',
  data() {
    return {
    }
  },
  components: { YhTitle,MatchInfoEcharts },
  props:{
    matchReportItemVoList : {
      type: [Array],
      required: true,
      default: () => []
    },
    matchStatisticsInfo : {
      type: [Object],
      required: true,
      default: () => {}
    }
  },
  mounted() {

  },
  methods: {
    completeCorpoInfo() {
      this.$router.push({path: "/enterprises/info/basic",query: {edit: '1'}})
    }
  },
  computed: {
    maxChr081() {

      let list = this.matchReportItemVoList;
      if (list.length != 0 && list[0].chr081){
        return list[0].chr081;
      }else{
        return '暂无';
      }
    }
  },
}
</script>

<style lang="scss" scoped>
.match-info {
  width: 100%;
  padding-bottom: 36px;
  position: relative;
  .con {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    display: flex;
    padding-top: 46px;
  }
  .left {
    width: 364px;
  }
  .right {
    flex: 1;
    overflow: hidden;
    color: var(--color-priamry);
    padding-left: 76px;
    position: relative;
    &::after {
      content: '';
      position: absolute;
      left: 0;
      top: 0;
      width: 1px;
      height: 234px;
      background-color: #c0e2ff;
    }
  }
  .suitability {
    display: flex;
    align-items: center;
    font-size: 24px;
    line-height: 1;
    color: #666666;
    padding-bottom: 8px;
    position: relative;
    .value {
      font-size: 28px;
      color: var(--color-hint);
      padding-left: 12px;
      font-weight: 700;
    }
    &::after {
      content: '';
      position: absolute;
      left: 0;
      bottom: 0;
      width: 174px;
      height: 4px;
      background-image: linear-gradient(
        94deg,
        #e3f3ff 0%,
        rgba(241, 249, 255, 0.74),
        #ffffff 100%
      );
      border-radius: 2px;
    }
  }
  .suitability-desc {
    font-size: 14px;
    line-height: 22px;
    color: #666666;
    h3 {
      font-size: 16px;
      color: #333;
      padding-top: 40px;
      padding-bottom: 10px;
      font-weight: 700;
    }
  }
  .hint-wrapper {
    padding-top: 43px;
    font-size: 14px;
    display: flex;
    align-items: center;
    color: #333;
    .text {
      display: flex;
      align-items: center;
    }
    .iconfont {
      font-size: 14px;
      margin-right: 6px;
      color: var(--color-primary);
    }
    .val {
      color: var(--color-primary);
    }
    button {
      background-color: var(--color-secondary);
      width: 96px;
      height: 36px;
      line-height: 36px;
      border-radius: 4px;
      margin-left: 82px;
      color: #fff;
    }
  }
}
</style>
