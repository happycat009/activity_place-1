<template>
  <div
    class="well-konwn-enterprises-wrapper"
    v-if="renderData && renderData.length > 0"
  >
    <div class="container">
      <YhTitle
        title="知名企业"
        titleEn="WELL-KNOWN ENTERPRISES"
        type="01"
      ></YhTitle>
      <div class="content-wrapper">
        <div class="items">
          <template v-for="(item, index) in renderData" :key="item.aab220">
            <div class="item" @click="handleJumpRoute(item)">
              <img class="img" v-if="!item.chi215" :src="imageBg01" alt="" />
              <img class="img" v-if="item.chi215" :src="requestUrl+'/upload/downFileByFileId?fileId='+ item.chi215" alt="" />
              <div class="card">
                <div class="icon-wrapper">
                  <img class="icon" v-if="item.aab225" :src="'data:image/jpg;base64,'+item.aab225" alt="" />
                  <img class="icon" v-if="!item.aab225" :src="imageBg01" alt="" />
                </div>
                <div class="name">{{ item.aab012 }}</div>
                <div class="desc">{{ item.aab229 }}</div>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import imageBg01 from '@/assets/images/community/bg-01.png'

import YhNoData from '@/components/common/YhNoData/YhNoData.vue'
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import {requestUrl} from "@/config/city";

import { useRouter } from 'vue-router'

const router = useRouter()
const props = defineProps({
  renderData: {
    type: Array,
    default: () => [],
  },
})

// 路由跳转
const handleJumpRoute = (item) => {

  console.log("知名企业",item)
  const { aab22b, aab220, aab22a } = item
  // 是否是定制化企业 1 是
  if (aab22a == '1') {
    router.push(`/propaganda/${aab22b}/${aab220}`)
  } else {
    router.push(`/propaganda/common/${aab220}`)
  }
}
</script>

<style lang="scss" scoped>
.well-konwn-enterprises-wrapper {
  width: 100%;
  padding-bottom: $spacing-title * 2;
  .items {
    display: flex;
    flex-wrap: wrap;
    padding-top: $spacing-title;
    .item {
      margin-bottom: 36px;
      margin-right: 40px;
      width: 270px;
      position: relative;
      transition: all 0.3s;
      .img {
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 1;
      }
      .card {
        width: 100%;
        height: 263px;
        border-radius: 4px;
        border: solid 2px #e0ecff;
        cursor: pointer;
        font-size: 14px;
        line-height: 1;
        color: $color-title;
        overflow: hidden;

        text-align: center;
        position: relative;
        padding-top: 184px;
      }
      .icon-wrapper {
        position: absolute;
        top: 92px;
        left: 0;
        z-index: 2;
        display: flex;
        width: 80px;
        height: 80px;
        background-color: #ffffff;
        box-shadow: 0px 3px 0px 0px rgba(190, 205, 227, 0.35);
        border-radius: 50%;
        left: 50%;
        transform: translateX(-50%);
        .icon {
          width: 60px;
          height: 60px;
          margin: auto;
          border-radius: 50%;
        }
      }
      .desc {
        padding-top: 8px;
      }
      .name {
        font-size: $font-size-c;
        font-weight: 700;
        line-height: 1;
        color: $color-title;
      }
      &:hover {
        transform: scale(1.1);
      }
      &:nth-of-type(4n) {
        margin-right: 0;
      }
    }
  }
}
</style>
