<template>
  <div class="match-details">
    <el-collapse v-model="activeName" class="el-collapse-table">
      <template v-for="(matchReportItemVo, index) in matchReportItemVoList" :key="index">
        <el-collapse-item :name="'item_' + index">
          <template #title>
            <div class="collapse-header">
              <div class="name">{{matchReportItemVo.aka131Desc}}</div>
              <div class="right">
                <div class="key">匹配度：</div>
                <div class="val">{{matchReportItemVo.chr081||'暂无'}}%</div>
              </div>
            </div>
          </template>
          <div class="cells">
            <template v-for="(reportItemDetailVo, reportItemDetailVoIndex) in matchReportItemVo.reportItemDetailVoList" :key="reportItemDetailVoIndex">
              <div class="cell">
                <div class="left">
                  <div class="key">{{reportItemDetailVo.chr022Desc}}</div>
                  <div class="value">{{reportItemDetailVo.chr023}}</div>
                </div>
                <div class="right">
                  <div class="result">
<!--                    <i class="iconfont iconfont-fail iconfont-warn iconfont-success"></i>-->
                    <i :class="iconfont(reportItemDetailVo)"></i>
                    <span>{{reportItemDetailVo.chr032Desc}}</span>
                  </div>
                  <div class="handle">
                    <button @click="completeCorpoInfo">编辑</button>
                  </div>
                </div>
              </div>
            </template>

<!--            <div class="cell">-->
<!--              <div class="left">-->
<!--                <div class="key">材料要求</div>-->
<!--                <div class="value">企业注册地在内江市</div>-->
<!--              </div>-->
<!--              <div class="right">-->
<!--                <div class="result">-->
<!--                  <i class="iconfont iconfont-fail"></i>-->
<!--                  <span>不匹配</span>-->
<!--                </div>-->
<!--                <div class="handle">-->
<!--                  <button>编辑</button>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
<!--            <div class="cell">-->
<!--              <div class="left">-->
<!--                <div class="key">材料要求</div>-->
<!--                <div class="value">企业注册地在内江市</div>-->
<!--              </div>-->
<!--              <div class="right">-->
<!--                <div class="result">-->
<!--                  <i class="iconfont iconfont-warn"></i>-->
<!--                  <span>未填写</span>-->
<!--                </div>-->
<!--                <div class="handle">-->
<!--                  <button>编辑</button>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
          </div>
        </el-collapse-item>
      </template>
    </el-collapse>
  </div>
</template>

<script>
import {requestGetData} from '@/service'
import {
  USER_INFO,
  CURRENT_MENU_LIST,
  M_UPDATE_CURRENT_MENU_LIST,
} from '@/store/constants'
import { mapGetters } from 'vuex'

export default {
  name: 'MatchDetails',
  data() {
    return { activeName: 'item_0' }
  },
  props:{
    matchReportItemVoList : {
      type: [Array],
      required: true,
      default: () => []
    }
  },
  components: {},
  mounted() {

  },
  methods: {
    iconfont(data) {
      let style = ''
      switch (data.chr032) {
        case "1":
          style = 'iconfont-success'
          break
        case "3":
          style = 'iconfont-warn'
          break
        case "2":
          style = 'iconfont-fail'
          break
      }
      return style
    },
    completeCorpoInfo() {
        this.$router.push({path: "/enterprises/info/basic",query: {edit: '1'}})
    }
  },
  computed: {
    ...mapGetters([USER_INFO, CURRENT_MENU_LIST]),
  },
}
</script>

<style lang="scss" scoped>
.match-details {
  width: 100%;
}

.collapse-header {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-right: 46px;
  background-color: #f7faff;
  .name {
    flex: 1;
    overflow: hidden;
    font-size: 16px;
    font-weight: 700;
    line-height: 40px;
    color: #323232;
    padding-left: 36px;
    line-height: 46px;
  }
  .right {
    display: flex;
    align-items: center;
    .val {
      color: var(--color-hint);
    }
  }
}

.cells {
  font-size: 14px;
  line-height: 1.4;
  color: #323232;
  .cell {
    display: flex;
    align-items: center;
    padding: 15px 0;
    border-bottom: 1px solid #f9fbff;
    border-left: 1px solid #f9fbff;
    border-right: 1px solid #f9fbff;
  }
  .left {
    display: flex;
    align-items: center;
    flex: 1;
    overflow: hidden;
    .key {
      width: 178px;
      padding-left: 36px;
      padding-right: 12px;
    }
    .value {
      flex: 1;
      overflow: hidden;
    }
  }
  .right {
    display: flex;
    align-items: center;
    padding-right: 46px;
    .result {
      width: 114px;
      display: flex;
      align-items: center;
      color: #333;
      .iconfont {
        margin-right: 8px;
      }
      .iconfont-success {
        color: var(--color-success);
      }
      .iconfont-fail {
        color: var(--color-fail);
      }
      .iconfont-warn {
        color: var(--color-warn);
      }
    }
    .handle {
      button {
        color: var(--color-primary);
        line-height: 1.4;
      }
    }
  }
}

.el-collapse-item {
  margin-bottom: 30px;
  &:last-child {
    margin-bottom: 0;
  }
}
</style>
