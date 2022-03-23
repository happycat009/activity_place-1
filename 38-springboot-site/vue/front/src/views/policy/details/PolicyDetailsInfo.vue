<template>
  <div class="policy-details-info" :style="wrapperStyle">
    <!-- <img src="~assets/images/policy/bg-policy-details-info.png" alt="" /> -->
    <div class="name-wrapper">
      <div class="left">
        <div class="city">【{{renderData.aab301 ? getCollectionDesc("aab301",renderData.aab301) : renderData.aab301}}】</div>
        <div class="name">
          {{renderData.chi032}}
        </div>
      </div>
      <div class="right" v-if="true">
        <YhTag label="系统推荐申报" color="112,182,3" borderRadius="4px"
          width="112px" height="40px" border style="cursor:pointer;" title="有消息" v-if="messageInfo.hasHr08 == '1'"
          @click="handleJumpRoute('/enterprises/message')"
        ></YhTag>
<!--        <YhTag label="系统推荐申报" color="0,0,0" borderRadius="4px" style="color: gray"-->
<!--          width="112px" height="40px" v-else-->
<!--        ></YhTag>-->

        <YhTag label="主管部门邀请申报" color="112,182,3" borderRadius="4px"
          width="112px" height="40px" border style="cursor:pointer;" title="有消息" v-if="messageInfo.hasHr09 == '1'"
          @click="handleJumpRoute('/enterprises/message')"
        ></YhTag>
<!--        <YhTag label="业务局邀请申报" color="0,0,0" borderRadius="4px" style="color: gray"-->
<!--          width="112px" height="40px" v-else-->
<!--        ></YhTag>-->
      </div>
    </div>
    <div class="cells">

<!--      <div class="cell">-->
<!--        <div class="key">政策文号：</div>-->
<!--        <div class="value">{{renderData.chi05c}}</div>-->
<!--      </div>-->
      <div class="cell">
        <div class="key">发布日期：</div>
        <div class="value">{{useMoment(renderData.chi052,"YYYY-MM-DD")}}</div>
      </div>

      <div class="cell">
        <div class="key">主管部门：</div>
        <div class="value">{{getCollectionDesc("chi037",renderData.chi037)}}</div>
      </div>

      <div class="cell">
        <div class="key">政策级别：</div>
        <div class="value">{{getCollectionDesc("chi05a",renderData.chi034)}}</div>
      </div>

      <div class="cell">
        <div class="key">联系人：</div>
        <div class="value">{{renderData.chi04p}}</div>
      </div>
      <div class="cell">
        <div class="key">联系电话：</div>
        <div class="value">{{renderData.chi04q}}</div>
      </div>

      <div class="cell">
        <div class="key">浏览量：</div>
        <div class="value">{{renderData.visitCount}}</div>
      </div>

      <!--<div class="cell">-->
      <!--  <div class="key">政策文号：</div>-->
      <!--  <div class="value">{{renderData.chi05c}}</div>-->
      <!--</div>-->

      <div class="cell" style="width: 100%">
        <div class="key">政策依据：</div>
        <div class="value">{{renderData.chi05c + "&nbsp&nbsp&nbsp" +renderData.chi051}}</div>
      </div>




      <!--<div class="cell">-->
      <!--  <div class="key">发布日期：</div>-->
      <!--  <div class="value">{{bhi037Formatted}}</div>-->
      <!--</div>-->


      <!--<div class="cell">-->
      <!--  <div class="key">发布日期：</div>-->
      <!--  <div class="value">2021-10-22</div>-->
      <!--</div>-->
    </div>
    <div class="apply">
      <div class="tip" v-if="!dateInfo.overdue">申报结束倒计时<b style="padding: 0 5px;color: red;">{{dateInfo.limitDays}}</b>天</div>
      <div class="tip" v-else style="font-weight: bold">申报时间已结束</div>
      <el-button @click="toOnlineDeclaration"  type="primary" :disabled="!dateInfo.enableDeclare">立即申报</el-button>
    </div>
  </div>
</template>

<script>
import {requestGetVisitStatistics} from '@/service'
import moment from 'moment'
import imagesBg from '@/assets/images/policy/bg-policy-details-info.png'
import YhTag from '@/components/common/YhTag/YhTag.vue'
import {useMoment} from '@/use'


export default {
  name: 'PolicyDetailsInfo',
  props: {
    renderData: {
      type: [Object],
      required: true,
      default: () => {}
    },
    dateInfo: {
      type: [Object],
      required: true,
      default: () => {}
    },
    messageInfo: {
      type: [Object],
      required: true,
      default: () => {}
    }
  },
  data() {
    return {
    }
  },
  components: {
    YhTag,
  },
  mounted() {
    this.userCodeTableMap("aab301")
    this.userCodeTableMap("chi037")
    this.userCodeTableMap("chi05a")
    this.getVisitStatistics()
  },
  computed: {
    wrapperStyle() {
      let str = ''
      str += `background-image: url(${imagesBg});`
      return str
    },
    bhi037Formatted() {
      if(this.renderData.bhi037) {
        return moment(this.renderData.bhi037).format("yyyy年MM月DD日")
      } else {
        return ''
      }
    }
  },
  methods: {
    toOnlineDeclaration() {
      const chi031 = this.renderData.chi031
      this.$router.push(`/apply/${chi031}`)
    },
    getVisitStatistics() {
      let _this = this
      let {fullPath} = this.$route
      requestGetVisitStatistics({visiturl: fullPath}).then((res) => {
        _this.renderData.visitCount = res.statistics.visitCount
      })
    },
    handleJumpRoute(path) {
      this.$router.push(path)
    },
    useMoment: useMoment
  },
  watch: {
    renderData: {
      handler(val) {

      },
      immediate: true
    }
  }
}
</script>

<style lang="scss" scoped>
.policy-details-info {
  width: 100%;
  // background-color: #fff;
  // border: solid 1px #e4eaf2;
  padding: 30px 0 30px;
  position: relative;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  background-position: center center;
  .name-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 20px;
    padding-right: 30px;
    .right {
      margin-left: 30px;
    }
    .left {
      flex: 1;
      overflow: hidden;
      display: flex;
      .city {
        color: $primary;
      }
      .name {
        flex: 1;
        @include textOverflow();
      }
    }
  }
  .cells {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    padding-left: 30px;
    padding-right: 290px;
    padding-top: 30px;
    .cell {
      width: 33.3333%;
      display: flex;
      align-items: center;
      font-size: 14px;
      line-height: 1;
      color: #9a9a9a;
      margin-bottom: 18px;
      .value {
        color: #333;
      }
    }
  }
  .apply {
    position: absolute;
    bottom: 40px;
    right: 30px;
    display: flex;
    align-items: center;
    font-size: 16px;
    line-height: 34px;
    color: #333333;
    .tip {
      padding-right: 20px;
    }
    span {
      color: #e50000;
      font-weight: 500;
    }
    button {
      width: 106px;
    }
  }
}
</style>
