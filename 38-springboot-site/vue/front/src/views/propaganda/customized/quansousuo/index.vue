<template>
  <div class="propaganda">
    <div class="container">
      <CompanyInfo :renderData="customData.companyInfo"></CompanyInfo>
      <div class="con">
        <CompanyHonor
          :renderData="customData.companyInfo.honors"
        ></CompanyHonor>
      </div>
    </div>
  </div>
</template>

<script setup>
import CompanyInfo from './CompanyInfo.vue'
import CompanyHonor from './CompanyHonor.vue'

import { ref, reactive } from 'vue'
import { useRoute } from 'vue-router'
import { requestB409 } from '@/service'

const route = useRoute()

const customData = reactive({
  companyInfo: {}, // 企业基本信息
  ab23List: {},
  newHoronList:[]
})

// 调取api获取数据
const getData = async (aab220) => {
  const res = await requestB409(aab220)
  const { code, data } = res
  if (code != '-1') {
    let { ab22Domain, ab23List,ab25List } = data

    if (ab22Domain && ab22Domain.aab220) {
      if (ab22Domain.aab222) {
        ab22Domain.tags = ab22Domain.aab222.split('|')
      }
    }
    if(ab25List && ab25List.length > 0){
      ab22Domain.products = [...ab25List]
    }
    if (ab23List && ab23List.length > 0) {
      ab22Domain.honors = [...ab23List]
    }
    customData.companyInfo = { ...ab22Domain }
  }
}



getData(route.params.id)
</script>

<style lang="scss" scoped>
.propaganda {
  width: 100%;
  padding: 30px 0;
  .con {
    margin-top: 30px;
    padding: 40px;
    background-color: #fff;
  }
}
</style>
