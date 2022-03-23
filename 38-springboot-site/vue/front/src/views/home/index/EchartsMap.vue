<template>
  <div class="echarts-map" id="map"></div>
</template>

<script>
// 引入 echarts 核心模块，核心模块提供了 echarts 使用必须要的接口
import * as echarts from 'echarts/core'
import $ from 'jquery'

import {
  TooltipComponent,
  VisualMapComponent,
  GeoComponent,
} from 'echarts/components'

import { MapChart } from 'echarts/charts'
import { CanvasRenderer } from 'echarts/renderers'

echarts.use([
  TooltipComponent,
  VisualMapComponent,
  GeoComponent,
  MapChart,
  CanvasRenderer,
])

import { requestE401 } from '@/service'
import mapJson from '@/assets/json/511000.json'

//  { id: 'r_03', name: '已申报的企业', value: 95 },
//  { name: '正在申报的企业', value: 82 },
//  { name: '已申报政策数', value: 42 },

export default {
  name: 'HomeEchartsMap',
  data() {
    return {
      mapName: 'neijiang',
      // 数据
      mockData: [
        {
          "declared": "0",
          "chb015": "165003",
          "chb015desc": "东兴区",
          "chi031Declared": "0",
          "undeDeclaration": "0"
        },
        {
          "declared": "0",
          "chb015": "165006",
          "chb015desc": "高新区",
          "chi031Declared": "0",
          "undeDeclaration": "0"
        },
        {
          "declared": "0",
          "chb015": "165009",
          "chb015desc": "经开区",
          "chi031Declared": "0",
          "undeDeclaration": "0"
        },
        {
          "declared": "0",
          "chb015": "165012",
          "chb015desc": "隆昌市",
          "chi031Declared": "0",
          "undeDeclaration": "0"
        },
        {
          "declared": "0",
          "chb015": "165015",
          "chb015desc": "市本级",
          "chi031Declared": "0",
          "undeDeclaration": "0"
        },
        {
          "declared": "0",
          "chb015": "165018",
          "chb015desc": "市中区",
          "chi031Declared": "0",
          "undeDeclaration": "0"
        },
        {
          "declared": "0",
          "chb015": "165021",
          "chb015desc": "威远县",
          "chi031Declared": "0",
          "undeDeclaration": "0"
        },
        {
          "declared": "0",
          "chb015": "165024",
          "chb015desc": "资中县",
          "chi031Declared": "0",
          "undeDeclaration": "0"
        }
      ],
      // 渲染数据
      renderData: [],
      pieces: this.pieces,
      echart: null, //
      currentIndex: -1,
      loopInterval: null// 循环高亮标记
    }
  },
  components: {},
  mounted() {
    this.getRenderData()
    this.mouseEvents()
  },
  methods: {
    // 获取数据
    async getRenderData() {
      let _this = this
      const res = await requestE401([{ method: 'countyStatisticsCount' }])
      const { data,code } = res
      if (code == '200') {
        const { statisticsList } = data.countyStatisticsCountMap
        const renderData = (statisticsList && statisticsList.length) ? statisticsList : _this.mockData
        let arr = []
        renderData.map((item) => {
          arr.push({
            name: item.chb015desc,
            tooltip: [
              item.chi031Declared,
              item.declared,
              item.undeDeclaration,
            ],
            value: item.declared,
          })
        })
        this.renderData = [...arr]
        this.pieces = this.setAveragePieces(renderData)
      }
      this.renderMap()
    },
    renderMap() {
      echarts.registerMap(this.mapName, mapJson)
      this.echart = echarts.init(document.getElementById('map'))

      var geoCoordMap = {}
      let mapFeatures = echarts.getMap(this.mapName).geoJson.features
      mapFeatures.forEach(function (v) {
        // 地区名称
        var name = v.properties.name
        // 地区经纬度
        geoCoordMap[name] = v.properties.center
      })
      let mapOptions = {
        tooltip: {
          trigger: 'item',
          backgroundColor: '#001a9d',
          padding: 0,
          textStyle: {
            color: '#fff',
            fontSize: 14,
          },
          borderColor: 'rgba(0, 168, 255, 0.46)',
          formatter: function (params) {
            const meta = params.data.tooltip
            return `
            <div >
              <div style="font-size:16px;  padding: 12px 12px 0; font-weight:500;">${
                params.name
              }</div>
              <div style="display:flex; align-items:center; line-height:38px; padding: 0 12px;">
                <div style="width:10px; height:10px; border-radius:50%; border:solid 1px #f6faff; margin-right:8px; background-color:#ffc000;"></div>
                <div class="key">已申报的企业：</div>
                <div style="flex:1; overflow: hidden; text-align:right;">${
                  meta[0] || 0
                } 家</div>
              </div>
              <div style="display:flex; align-items:center; line-height:38px; padding: 0 12px;">
              <div style="width:10px; height:10px; border-radius:50%; border:solid 1px #f6faff; margin-right:8px; background-color:#004eff;"></div>
                <div class="key">正在申报的企业：</div>
              <div style="flex:1; overflow: hidden; text-align:right;">${
                meta[1] || 0
              } 家</div>
              </div>
              <div style="display:flex; align-items:center; line-height:38px; padding: 0 12px;">
              <div style="width:10px; height:10px; border-radius:50%; border:solid 1px #f6faff; margin-right:8px; background-color:#00fffc;"></div>
                <div class="key">已申报政策数：</div>
                <div style="flex:1; overflow: hidden; text-align:right;">${
                  meta[2] || 0
                } 项</div>
              </div>
            </div>
            `
          },
          position: function (point, params, dom, rect, size) {
            return point;
          }
        },
        visualMap: {
          show: true,
          type: 'piecewise',
          left: '2%',
          bottom: '0',
          splitNumber: 5,
          itemWidth: 30,
          itemHeight: 15,
          itemSymbol: 'rect',
          text: ['高', '低'],
          calculable: true,
          itemGap: 2,
          textGap: 100,
          borderColor: '#02f4f0',
          textStyle: {
            color: '#fff',
          },
          pieces: this.pieces,
          inRange: {
            color: ['#5bcbff', '#008aff', '#0337fa', '#052edd', '#011fa1'],
          },
        },
        series: [
          {
            type: 'map',
            map: this.mapName,
            layoutCenter: ['50%', '50%'],
            label: {
              show: true,
              color: '#fff',
              fontSize: 16,
            },
            itemStyle: {
              areaColor: '#5bcbff',
              borderColor: '#6fbdff',
              borderWidth: 2,
            },
            emphasis: {
              label: {
                color: '#fff',
                fontSize: 16,
              },
              itemStyle: {
                borderColor: '#8affb9',
                areaColor: '#ffc000',
              },
            },
            data: this.renderData,
          },
        ],
      }

      // console.log(this.echart, 'this.echart')

      this.echart.setOption(mapOptions)
      window.addEventListener('resize', () => {
        this.echart.resize()
      })
      this.$nextTick(() => {
        this.loopShow()
      })
    },
    renderMap2() {},
    setAveragePieces(list) {
      var min = list[0].declared;
      var max = list[0].declared;
      list.forEach(function(item) {
        min = Math.min(item.declared,min)
        max = Math.max(item.declared,max)
      })
      var gap = (max - min) ? (max - min)/3 : 0
      return [
        {gt: max - 1},
        {gt: max-gap, lte: max - 1},
        {gt: max-2*gap, lte: max-gap},
        {gt: max-3*gap+1, lte: max-2*gap},
        {gt: -1,lte: min + 1}
      ]
    },
    mouseEvents () {
      let _this = this
      let indexedList = [0,3,5,6,7]
      // 鼠标划入
      $("#map").on('mouseover', () => {
        // 停止定时器，清除之前的高亮
        this.loopDestroy()
        // 取消之前高亮的图形
        _this.echart.dispatchAction({
          type: 'downplay',
          seriesIndex: 0,
          dataIndex: indexedList[_this.currentIndex]
        });
      });
      // 鼠标划出重新定时器开始
      $("#map").on('mouseout', () => {
        this.loopShow()
      });
    },
    loopShow() {
      let indexedList = [0,3,5,6,7]
      let _this = this

      this.loopInterval = setInterval(() => {
        let dataLen = indexedList.length;
        // 取消之前高亮的图形
        _this.echart.dispatchAction({
          type: 'downplay',
          seriesIndex: 0,
          dataIndex: indexedList[_this.currentIndex]
        });
        _this.currentIndex = (_this.currentIndex + 1) % dataLen;
        // 高亮当前图形
        _this.echart.dispatchAction({
          type: 'highlight',
          seriesIndex: 0,
          dataIndex: indexedList[_this.currentIndex]
        });
        // 显示 tooltip
        _this.echart.dispatchAction({
          type: 'showTip',
          seriesIndex: 0,
          dataIndex: indexedList[_this.currentIndex]
        });
      }, 3000);
    },
    loopDestroy() {
      clearInterval(this.loopInterval)
    }
  },
  computed: {},
  destroyed() {
    this.loopDestroy()
  }
}
</script>

<style lang="scss" scoped>
.echarts-map {
  flex: 1;
  max-height: 100%;
  overflow: hidden;
}
</style>
