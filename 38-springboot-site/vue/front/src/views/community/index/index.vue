<template>
  <div class="enterprises-index">
    <div class="container">
      <Bannner :renderData="customData.hm65List"></Bannner>
      <div class="wrapper">
        <WellKnownEnterprises
          :renderData="customData.ab22List"
        ></WellKnownEnterprises>
        <ActivityColumn :renderData="customData.hm65List"></ActivityColumn>
        <CorporateReport :renderData="customData.ab14List"></CorporateReport>
      </div>
    </div>
  </div>
</template>

<script setup>
import Bannner from './Bannner.vue'
import WellKnownEnterprises from './WellKnownEnterprises.vue'

import CorporateReport from './CorporateReport.vue'
import ActivityColumn from './ActivityColumn.vue'

import { reactive, onMounted } from 'vue'
import { requestE405, requestB408, requestB411 } from '@/service'

const customData = reactive({
  ab22List: [], // 获取知名企业目录信息
  hm65List: [], // 获取活动头条（新闻）列表
  ab14List: [], // 获取最新注册企业
})

const getData = async () => {
  const ab22ListData = await requestB408()
  const hm65ListData = await requestE405()
  const ab14ListData = await requestB411()

  let { ab22List } = ab22ListData.data
  let { hm65List } = hm65ListData.data
  let { ab14List } = ab14ListData.data

  customData.ab22List = ab22List
  customData.hm65List = hm65List
  customData.ab14List = ab14List
  console.log('B408 ab22List===',ab22List)
  console.log('E405 hm65List===',hm65List)
  console.log('B411 ab14List===',ab14List)
}
onMounted(() => {
  getData()
})
</script>

<style lang="scss" scoped>
.enterprises-index {
  width: 100%;
  padding: 30px 0;
  background-color: #fff;
  .wrapper {
    padding-top: 60px;
  }
}
</style>
