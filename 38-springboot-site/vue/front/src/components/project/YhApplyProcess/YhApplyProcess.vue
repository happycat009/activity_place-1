<template>
  <div class="yh-apply-process-wrapper">
    <ul>
      <template v-for="(item, index) in dataList" :key="index">
        <li :class="[index == 2 ? 'active' : '']">
          <div class="time">{{getDateFormatted(item.aae036,'YYYY-MM-DD HH:mm')}}</div>
          <div class="icon">

            <i class="iconfont iconfont-hook" v-if="item.handleFinish == '1'"> </i>
            <div class="now" v-else-if="dataList[index - 1].handleFinish == '1' && dataList[index].handleFinish != '1'"></div>
            <div class="ing" v-else></div>
            <!--<i class="iconfont iconfont-close" v-else> </i>-->
          </div>
          <div class="content">
            <div class="name">{{item.che011}}</div>
            <div class="cells">
              <div class="cell">
                <!--受理框架为01-->
                <div class="key">{{item.che063 == '01' ? '申报人' : '经办人'}}：</div>
                <div class="value">{{item.aae011}}</div>
              </div>
              <div class="cell" v-if="item.che063 == '01'">
                <div class="key">申报状态：</div>
                <div class="value">{{getCollectionDesc("yesorno",item.handleFinish)}}</div>
              </div>
              <div class="cell" v-else>
                <div class="key">经办状态：</div>
                <div class="value">{{getCollectionDesc("cpb102",item.cpb102)}}</div>
              </div>
              <!--<div class="cell" v-if="item.che063 != '01'">-->
              <!--  <div class="key">结果：</div>-->
              <!--  <div class="value">{{}}</div>-->
              <!--</div>-->
            </div>
            <div class="cells">
              <div>经办内容：{{item.cpb103}}</div>
            </div>
          </div>
          <i class="iconfont iconfont-arrow-down" v-if="index == 5"></i>
        </li>
      </template>
    </ul>
  </div>
</template>

<script>
import moment from 'moment'
  export default {
    name: "YhApplyProcess",
    props: {
      dataList: {
        type: [Array],
        required: true,
        default: () => []
      }
    },
    data() {
      return {
      }
    },
    mounted() {
      this.userCodeTableMap("yesorno")
      this.userCodeTableMap("cpb102")
    },
    methods: {
      getDateFormatted(oraData,formatter) {
        // console.log(oraData)
        if(!oraData){
          return ''
        }
        return moment(oraData).format(formatter)
      },
    },
    watch: {
      dataList: {
        handler(val) {
          // console.log("val-------",val)
        },
        immediate: true,
        deep: true
      }
    }
  }
</script>

<style lang="scss" scoped>
.yh-apply-process-wrapper {
  width: 100%;
  ul {
    width: 100%;
    padding-bottom: 34px;
    position: relative;
  }
  li {
    padding-left: 230px;
    position: relative;

    .time {
      font-size: 16px;
      line-height: 36px;
      color: #999;
      position: absolute;
      left: 0;
      top: 16px;
    }
    .icon {
      width: 36px;
      height: 36px;

      border-radius: 50%;
      position: absolute;
      top: 16px;
      left: 175px;
      background-color: #eff7ff;
      display: flex;
      div {
        margin: auto;
      }
      .iconfont {
        margin: auto;
        color: var(--color-primary);
        font-size: 20px;
      }
      .now {
        width: 16px;
        height: 16px;
        border-radius: 50%;
        background-color: var(--color-primary);
      }
      .ing {
        width: 10px;
        height: 10px;
        border-radius: 50%;
        background-color: var(--color-primary);
      }
    }
    .iconfont-arrow-down {
      position: absolute;
      bottom: -37px;
      left: 183px;
      font-size: 20px;
      font-weight: 500;
      color: var(--color-primary);
    }

    .content {
      padding: 16px 8px 24px 8px;
      .name {
        font-size: 16px;
        font-weight: 700;
        line-height: 36px;
        color: #333;
      }
      .cells {
        padding-top: 6px;
        font-size: 14px;
        line-height: 2;
        color: #666;
        display: flex;
        align-items: center;
        .cell {
          display: flex;
          align-items: center;
          margin-right: 40px;
        }
      }
    }
    &::before {
      content: '';
      width: 1px;
      height: 100%;
      position: absolute;
      top: 34px;
      left: 192px;
      border-left: 1px dashed var(--color-primary);
    }
    &.active {
      .content {
        background-color: #f2f8ff;
        border-radius: 4px;
      }
    }
    &.ing {
      .icon {
        width: 22px;
        height: 22px;
        left: 182px;
        top: 23px;
      }
    }
  }
}
</style>
