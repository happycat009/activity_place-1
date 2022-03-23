<template>
  <div class="bannner-wrapper">
    <div class="item">
<!--      <img :src="imageBanner01" alt="" @click="handleJumpRoute" />-->
<!--      <img :src="requestUrl+'/upload/downFileByFileId?fileId='+renderData[0].chi215" alt="" @click="handleJumpRoute(renderData[0])" />-->
      <img style="height: 159px;" :src="getSrc()" alt="" @click="handleJumpRoute(renderData[0])" />
    </div>
  </div>
</template>

<script setup>
import imageBanner01 from '@/assets/images/community/banner/01.png'

import { useRouter } from 'vue-router'
import {requestUrl} from "@/config/city";

const router = useRouter()

// 跳转路由
const handleJumpRoute = (item) => {
  const { chm650 } = item
  router.push(`/news/${chm650}`)
}
const getSrc = () => {
  for (const renderDatum of props.renderData) {
    console.log("item",renderDatum)
    if (renderDatum.chi215 && renderDatum.chm655 == "1"){
      return requestUrl+'/upload/downFileByFileId?fileId='+ renderDatum.chi215;
    }
  }
  return imageBanner01;
}
const props = defineProps({
  renderData: {
    type: Array,
    default: () => [],
  }
})
</script>

<style lang="scss" scoped>
.bannner-wrapper {
  width: 100%;
  .item {
    cursor: pointer;
  }
}
</style>
