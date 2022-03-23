<template>
  <div class="policy-details-tabs">
    <div class="left">
      <div class="tab-item" @click="handleTabChange(0)" :class="{checked: isChecked == 0}" >
        <i class="iconfont iconfont-pipeibaogao"></i>
        <div>匹配报告</div>
      </div>
      <div class="tab-item" @click="handleTabChange(1)" :class="{checked: isChecked == 1}" >
        <i class="iconfont iconfont-banshizhinan"></i>
        <div>申报指南</div>
      </div>
    </div>
    <div class="right">
      <!--与上面基础信息部分重复-->
      <!--<button class="primary">立即申报</button>-->
      <el-button @click="downloadOriginalPolicy" class="primary" v-if="policyInfo && policyInfo.oraFile">政策原文</el-button>
      <el-button type="primary" v-else disabled="true">政策原文</el-button>
    </div>
  </div>
</template>

<script>
import {requestGetData} from "@/service";
import { downloadFile } from '@/service/axios'

export default {
  name: 'PolicyDetailsTabs',
  props: {
    policyInfo: {
      type: [Object],
      required: true,
      default: () => {}
    }
  },
  data() {
    return {
      isChecked: 0
    }
  },
  components: {},
  mounted() {
    this.handleChecked()
  },
  methods: {
    // 判断进入页面时候选中谁
    handleChecked() {
      let {fullPath} = this.$route
      console.log(fullPath)
      let match = fullPath.indexOf('match-report')
      let service = fullPath.indexOf('service-guide')
      this.$nextTick(() => {
        if (match !== -1) {
          this.isChecked = 0
        }
        if (service !== -1) {
          this.isChecked = 1
        }
      })
    },
    // 判断tab跳转
    handleTabChange(index) {
      let _this = this
      if (this.isChecked == index) {
        return
      }
      if (index == 0) {
        this.$router.push("/policy/details/match-report/" + _this.$route.params.id)
      }
      if (index == 1) {
        this.$router.push("/policy/details/service-guide/" + _this.$route.params.id)
      }
      this.isChecked = index
    },
    downloadOriginalPolicy(){
      downloadFile(this.policyInfo.oraFile,'0')
    },
  },

  computed: {},
}
</script>

<style lang="scss" scoped>
.policy-details-tabs {
  width: 100%;
  background-color: #fff;
  height: 91px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #e4eaf2;
  margin-bottom: 30px;
  padding: 0 40px;
  .left {
    display: flex;
    align-items: center;
    font-size: 22px;
    line-height: 1.4;

    flex: 1;
    overflow: hidden;
    .tab-item {
      cursor: pointer;
      line-height: 90px;
      position: relative;
      margin-right: 84px;
      transition: all 0.3s;
      display: flex;
      align-items: center;
      color: #333333;
      .iconfont {
        font-size: 24px;
        line-height: 1;
        font-weight: 500;
      }
      &::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        height: 4px;
        transition: all 0.3s;
      }

      &:hover,
      &.checked {
        color: $primary;
        font-weight: 700;
        &::after {
          width: 100%;
          background-color: $primary;
        }
        .iconfont {
          color: $primary;
        }
      }
    }
  }
  .right {
    display: flex;
    align-items: center;
    button {
      width: 106px;
      line-height: 38px;
      height: 40px;
      margin-right: 30px;
      &:last-child {
        margin-right: 0;
      }
    }
  }
}
</style>
