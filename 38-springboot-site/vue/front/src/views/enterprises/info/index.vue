<template>
  <div class="enterprise-info">
    <div class="info">
      <div class="left" id="fillEchart">
        <!--<div>资料完成度 {{ customData.fillrate }}%</div>-->

      </div>
      <div class="right">
        <div class="hint">
          <i class="iconfont iconfont-hint"></i>
          <span>完善资料系统可更精准的为你匹配扶持政策。</span>
        </div>
        <div class="cells">
          <template v-for="item in customData.tabList" :key="item.aab100">
            <div class="cell">
              <div class="key" v-if="item.aab108 == '1'">
                您的{{ item.aab101 }}填写率：
              </div>
              <div class="key" v-else>您的{{ item.aab101 }}已填写：</div>
              <div class="value" v-if="item.aab108 == '1'">
                {{ item.fillNum }}/{{ item.needFillNum || 0 }}
              </div>
              <div class="value" v-else>{{ item.fillNum }}项</div>
            </div>
          </template>
        </div>
      </div>
    </div>
    <div class="content">
      <div class="tabs">
        <YhCompanyInfoTabs :tabList="customData.tabList"></YhCompanyInfoTabs>
      </div>
      <div class="tab-content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import YhCompanyInfoTabs from '@/components/project/YhCompanyInfoTabs/YhCompanyInfoTabs.vue'

import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'

import { requestB401 } from '@/service'
import * as echarts from "echarts/core";
import { TitleComponent, LegendComponent, PolarComponent } from "echarts/components";
import { BarChart, PieChart } from "echarts/charts";
import { CanvasRenderer } from "echarts/renderers";
echarts.use([TitleComponent, LegendComponent, BarChart, PieChart, CanvasRenderer, PolarComponent ]);

const store = useStore()

const userInfo = computed(() => store.state.userInfo)

const customData = reactive({
  tabList: [],
  fillrate: 0,
})

// 初始化数据
const initData = async () => {
  const { orgid } = userInfo.value
  const B401Data = await requestB401(orgid)
  const { ab10List, fillrate } = B401Data.data
  if (ab10List && ab10List.length > 0) {
    customData.tabList = ab10List
    customData.fillrate = fillrate
    initEchart(fillrate)
  }
}

const initEchart = (fillrate) =>{
  var chartDom = document.getElementById("fillEchart");
  const option = {
    backgroundColor: '#ffffff',
    title: [
      {
        text: '资料完善度',
        x: 'center',
        top: '88%',
        textStyle: {
          color: '#131313',
          fontSize: 15,
          fontWeight: '300',
        },
      },
      {
        text: fillrate + '%',
        x: 'center',
        top: '37%',
        textStyle: {
          fontSize: 30,
          color: '#207cff',
          foontWeight: '500',
        },
      },
    ],
    polar: {
      radius: ['30%', '40%'],
      center: ['50%', '40%'],
    },
    angleAxis: {
      max: 100,
      show: false,
    },
    radiusAxis: {
      type: 'category',
      show: true,
      axisLabel: {
        show: false,
      },
      axisLine: {
        show: false,
      },
      axisTick: {
        show: false,
      },
    },
    series: [
      {
        type: 'pie',
        startAngle: 0,
        top:-10,
        radius: ['60%', '67%'],
        center: ['50%', '50%'],
        data: [
          {
            hoverOffset: 1,
            value: fillrate,
            name: '',
            itemStyle: {
              normal: {
                color: '#207cff',
              },
            },
            label: {
              show: false,
            },
            labelLine: {
              normal: {
                smooth: true,
                lineStyle: {
                  width: 0,
                },
              },
            },
            hoverAnimation: false,
          },
          {
            label: {
              show: false,
            },
            labelLine: {
              normal: {
                smooth: true,
                lineStyle: {
                  width: 0,
                },
              },
            },
            value: 100 - fillrate,
            hoverAnimation: false,
            itemStyle: {
              color: 'rgba(204,204,204,0.2)',
            },
          },
        ],
      },
    ],
  };
  var myChart = echarts.init(chartDom);
  myChart.setOption(option);
}

onMounted(() => {
  initData()
})
</script>

<style lang="scss" scoped>
.enterprise-info {
  width: 100%;
  background-color: #ffffff;
  border: solid 2px rgba(216, 232, 255, 0.51);
  padding: 30px 50px 30px 20px;
  .info {
    display: flex;
    .left {
      width: 152px;
    }
    .right {
      flex: 1;
      overflow: hidden;
    }
    .hint {
      height: 28px;
      background-color: #f6fbff;
      line-height: 28px;
      color: var(--color-primary);
      font-size: 14px;
      padding: 0 10px;
      display: flex;
      align-items: center;
      .iconfont {
        margin-right: 4px;
      }
    }
    .cells {
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      padding-top: 16px;
      padding-bottom: 36px;
      font-size: 14px;
      color: #333;
      line-height: 30px;
      padding-left: 30px;
      .cell {
        width: 33.33%;
        display: flex;
        align-items: center;
        .value {
          color: var(--color-hint);
        }
      }
    }
  }
  .content {
    display: flex;
    .tabs {
      width: 152px;
      background-color: #fff;
      border-radius: 4px 0px 0px 4px;
    }
    .tab-content {
      flex: 1;
      overflow: hidden;
      background-color: #f6fbff;
    }
  }

  #fillEchart{
    height: 160px;
  }
}
</style>
