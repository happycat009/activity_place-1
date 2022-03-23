<template>
  <div class="yh-policy-card">
    <div class="bg">
      <img :src="imagesBg" alt="" />
    </div>
    <div class="con">
      <div class="name-wrapper">
        <div class="name" style="cursor: pointer;" title="查看申报指南" @click="toServiceGuide">
          【{{ (getCollectionDesc("aab301","511000"))}}】
          {{renderData.chi032}}
        </div>

        <div class="tags">
          <!--<template v-for="item in tags" :key="item.id">-->
          <!--  <YhTag :label="item.name"></YhTag>-->
          <!--</template>-->
          <YhTag v-if="!applyDuring.overdue" label="申报中..." :tagStyle="{}"></YhTag>
          <YhTag v-else label="申报结束" color="255,0,0"></YhTag>

        </div>

      </div>
      <div class="company-info">
        <YhSeparator
          :renderData="renderData.aka131List"
          collection="aka131"
        ></YhSeparator>
      </div>
      <div class="time">发布日期：{{applyDuring["startDate"]}}<b style="padding: 0 8px;color: black">|</b>截止日期：{{applyDuring["endDate"]}}</div>
      <div class="unit">发布单位：{{getCollectionDesc("chi037",renderData.chi037)}}</div>
      <div class="button-wrapper">
        <button v-if="!applyDuring.overdue" class="primary" @click="toOnlineDeclaration" >在线申报</button>
        <button v-else class="primary" style="color: rgba(0,0,0,0.5);font-weight: bold;background-color: rgba(0,0,0,0.1);border: none">已结束</button>
        <button class="primary reverse" @click="toServiceGuide">
          申报指南
        </button>
        <button class="primary reverse" @click="toMatchReport">
          查看匹配报告
        </button>
      </div>
      <div class="poa-right">
        <div class="item" v-if="!this.userInfo.user_id" @click="this.$router.push('/login')">
          <div class="img">
            <div class="img-text">登录查看</div>
          </div>
          <div class="key">匹配度</div>
        </div>
        <div class="item" v-else>
          <div class="img">
            <div class="img-text" @click="toMatchReport" >{{(renderData.chr081||'查看')}}</div>
          </div>
          <div class="key">匹配度</div>
        </div>
        <div class="item" v-if="!applyDuring.overdue">
          <div class="img">
            <div class="img-text">
              {{applyDuring.limitDays}}天
            </div>
          </div>
          <div class="key">申报结束倒计时</div>
        </div>
        <div class="item" v-else>
          <div class="img">
            <div class="img-text">
              <b style="color: red">0</b>
            </div>
          </div>
          <div class="key">申报已结束</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { USER_INFO, M_UPDATE_USER_INFO } from '@/store/constants'
import imagesBg from './images/bg.png'
import YhTag from '@/components/common/YhTag/YhTag.vue'
import YhSeparator from '@/components/common/YhSeparator/YhSeparator.vue'
import {mapGetters} from "vuex";
import moment from "moment";
export default {
  name: 'YhPolicyCard',
  props: {
    renderData: {
      type: [Object],
      required: true,
    },
  },
  data() {
    return {
      imagesBg
    }
  },
  components: { YhTag, YhSeparator },
  mounted() {
    this.userCodeTableMap("aab301")
    this.userCodeTableMap("chi037")
  },
  computed: {
    cardStyle() {
      let str = ''
      str += `background-image:url(${imagesBg});`
      return str
    },
    applyDuring() {
      if(!this.renderData) {
        return {}
      }
      const {bhi036,bhi037} = this.renderData
      let tempRes = {
        startDate: moment(bhi037).format('yyyy-MM-DD')
      }
      if(bhi036) {
        let a = moment(moment(bhi036).format('yyyy-MM-DD'))
        let b = moment(moment(new Date()).format('yyyy-MM-DD'))
        tempRes["endDate"] = a.format('yyyy-MM-DD')
        let diff = a.diff(b)
        if(diff >= 0) {
          tempRes.limitDays = Math.ceil((diff / (24*60*60*1000)) + 1)
          tempRes.overdue = false
          tempRes.enableDeclare = true
        } else {
          tempRes.overdue = true
          tempRes.enableDeclare = false
        }
      } else {
        tempRes.overdue = true
        tempRes.enableDeclare = false
      }
      return tempRes
    },
    ...mapGetters([USER_INFO]),
  },
  methods: {
    toOnlineDeclaration() {
      const { chi031 } = this.renderData
      this.$router.push(`/apply/${chi031}`)
    },
    toServiceGuide() {
      const { chi031 } = this.renderData
      this.$router.push(`/policy/details/service-guide/${chi031}`)
    },
    toMatchReport() {
      const { chi031 } = this.renderData
      this.$router.push(`/policy/details/match-report/${chi031}`)
    }
  },
  watch: {
    renderData: {
      handler(val) {
      },
      immediate: true,
      deep: true
    }
  }
}
</script>

<style lang="scss" scoped>
.yh-policy-card {
  width: 100%;
  margin-bottom: 30px;
  font-size: 14px;
  line-height: 24px;
  color: #888;
  transition: all 0.3s;
  position: relative;
  height: 225px;
  // background-color: #ffffff;
  // border: solid 2px rgba(216, 232, 255, 0.51);
  // background-position: right bottom;
  // background-repeat: no-repeat;
  // background-size: 1128px 226px;
  .bg {
    width: 100%;
    height: 100%;
    img {
      height: 100%;
    }
  }
  .con {
    position: absolute;
    left: 0;
    width: 100%;
    top: 0;
    padding: 26px 30px 0 0;
  }
  .name-wrapper {
    display: flex;
    align-items: center;
    padding-left: 20px;
    .name {
      overflow: hidden;
      font-size: 18px;
      line-height: 1;
      color: #333;
      font-weight: 700;
    }
    .tags {
      padding-left: 14px;
    }
  }
  .company-info {
    padding-top: 8px;
    padding-left: 30px;
  }
  .time {
    margin-top: 15px;
    padding-left: 30px;
  }
  .unit {
    padding-left: 30px;
  }
  .button-wrapper {
    padding-top: 18px;
    padding-left: 30px;
    button {
      width: 116px;
      padding: 0 10px;
      margin-right: 20px;
    }
  }
  &:last-child {
    margin-bottom: 0;
  }
  &:hover {
    // border-color: $primary;
    // background-color: rgba($primary, 0.1);
  }
}
.poa-right {
  position: absolute;
  bottom: 30px;
  right: 42px;
  display: flex;
  .item {
    text-align: center;
    position: relative;
    padding-bottom: 24px;
    margin-right: 32px;
    .img {
      width: 80px;
      height: 80px;
      border: solid 1px $primary;
      background-color: rgba($primary, 0.1);
      border-radius: 50%;
      cursor: pointer;
    }
    .img-text {
      color: $primary;
      line-height: 80px;
      font-size: 14px;
      font-weight: 700;
    }
    .key {
      position: absolute;
      bottom: 0;
      left: 50%;
      transform: translateX(-50%);
      color: #333333;
      line-height: 1;
      white-space: nowrap;
    }
    &:last-child {
      margin-right: 0;
    }
  }
}
</style>
