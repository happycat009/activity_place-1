<template>
  <div class="match-info-echarts">
    <div id="myChart" :style="{ width: '364px', height: '253px' }"></div>
  </div>
</template>
<script>
import * as echarts from "echarts/core";
import { TitleComponent, LegendComponent } from "echarts/components";
import { RadarChart } from "echarts/charts";
import { CanvasRenderer } from "echarts/renderers";

echarts.use([TitleComponent, LegendComponent, RadarChart, CanvasRenderer]);
export default {
  name: "MatchInfoEcharts",
  data() {
    return {
      msg: "Welcome to Your Vue.js App",
      index: 0,
    };
  },
  props:{
    matchReportItemVoList : {
      type: [Array],
      required: true,
      default: () => []
    }
  },
  mounted() {

    this.$nextTick(()=>{
      this.drawRadar(this.matchReportItemVoList);
    })

  },
  watch:{
    matchReportItemVoList(val){
      this.drawRadar(val);
    }
  },
  methods: {
    drawRadar(item) {
      //构造顶点
      let indicator =
        { "地点": 60 ,
          "信用": 60 ,
          "资质": 60 ,
          "经营": 60 ,
          "行业": 60 ,
          "规模": 60 };

      console.log("this.matchReportItemVoList",item)
      for (let matchReportItemVoListElement of item) {
        for (const matchReportItemVoListElementElement of matchReportItemVoListElement.reportItemDetailVoList) {
          //违法失信
          if (matchReportItemVoListElementElement.chr022 == '03'
              && matchReportItemVoListElementElement.chr032 != '1'
              && indicator["信用"] > 0
          ){

            indicator["信用"] = indicator["信用"] - 10;
          }
          //环保失信
          if (matchReportItemVoListElementElement.chr022 == '05'
              && matchReportItemVoListElementElement.chr032 != '1'
              && indicator["信用"] > 0
          ){
            indicator["信用"] = indicator["信用"] - 10;
          }
          //安全失信
          if (matchReportItemVoListElementElement.chr022 == '02'
              && matchReportItemVoListElementElement.chr032 != '1'
              && indicator["信用"] > 0
          ){
            indicator["信用"] = indicator["信用"] - 10;
          }

          //基本信息
          if (matchReportItemVoListElementElement.chr022 == '01'
              && matchReportItemVoListElementElement.chr032 != '1'
              && indicator["地点"] > 0
          ){
            indicator["地点"] = indicator["地点"] - 10;
          }
          //行政处罚
          if (matchReportItemVoListElementElement.chr022 == '06'
              && matchReportItemVoListElementElement.chr032 != '1'
              && indicator["经营"] > 0
          ){
            indicator["经营"] = indicator["经营"] - 10;
          }
          //扶持行业
          if (matchReportItemVoListElementElement.chr022 == '04'
              && matchReportItemVoListElementElement.chr032 != '1'
              && indicator["行业"] > 0
          ){
            indicator["行业"] = indicator["行业"] - 10;
          }


        }
      }

      let indicatorData = new Array();
      for (const indicatorKey in indicator) {
        indicatorData.push(indicator[indicatorKey]);
      }
      console.log("indicatorData",indicatorData)


      var chartDom = document.getElementById("myChart");
      var myChart = echarts.init(chartDom);
      var option;
      let _this = this;

      option = {
        //   标题
        title: {
          text: "",
        },
        // 线条颜色
        color: ["#3a92fc"],
        // 数据名称
        legend: {
          data: ["匹配程度"],
        },
        radar: {
          // shape: 'circle', 每个顶点的代表以及最大值 逆时针方向
          indicator: [
            { name: "地点", max: 60 },
            { name: "信用", max: 60 },
            { name: "资质", max: 60 },
            { name: "经营", max: 60 },
            { name: "行业", max: 60 },
            { name: "规模", max: 60 },
          ],
          axisName: {
            fontSize: 16,
            fontFamily: "Microsoft YaHei",
            color: "#000",
          },
          // 显示刻度
          axisLabel: {
            show: true,
            // 处理刻度函数
            formatter: function (value, index) {
              // 格式化成月/日，只在第一个刻度显示年份
              let showValue = "";
              if (index === 5) {
                showValue = value;
              }
              // console.log(index, value, "value");
              return showValue;
            },
            textStyle: {
              color: function (value, index) {
                let color = [
                  "#866aff",
                  "#44d3c3",
                  "#fe3131",
                  "#2486fc",
                  "#2486fc",
                  "#feb539",
                ];
                let numColor = "";
                if (index === 5) {
                  numColor = color[_this.index];
                  _this.index++;
                }
                return numColor;
              },
            },
            align: "center"
          },
        },
        // 数据
        series: [
          {
            name: "Budget vs spending",
            type: "radar",
            colorBy: "data",
            data: [
              {
                value: indicatorData,
                // name: "Allocated Budget",
                // 区域颜色
                areaStyle: {
                  color: new echarts.graphic.RadialGradient(0.1, 0.6, 1, [
                    {
                      color: "rgba(124, 183, 253, 0.1)",
                      offset: 0,
                    },
                    {
                      color: "rgba(124, 183, 253, 0.9)",
                      offset: 1,
                    },
                  ]),
                },
                // 展示数据数
                label: {
                  show: true,
                  formatter: function (params) {
                    return params.value;
                  },
                  position: "insideLeft",
                },
                // itemStyle:{
                //     color: ['#3f94fc','#e91e63','#2196f3','#3f94fc','#e91e63','#2196f3']
                // }
              },
            ],
          },
        ],
      };

      option && myChart.setOption(option);
    },
  },
};
</script>
