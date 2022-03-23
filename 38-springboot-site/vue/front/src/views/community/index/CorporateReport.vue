<template>
  <div
    class="corporate-report-wrapper"
    v-if="renderData && renderData.length > 0"
  >
    <div class="container">
      <YhTitle title="企业报道" titleEn="CORPORATE REPORT" type="01"></YhTitle>
      <div class="content-wrapper">
        <div class="items">
          <template v-for="(item, index) in renderData" :key="item.aab150">
            <div class="item">
              <div class="card">
                <div class="left">
                  <img :src="imagebg03" alt="" />
                  <span>企业注册</span>
                </div>
                <div class="rigth">
                  <div class="name">{{ item.aac003 }}</div>
<!--                  <div class="tag">-->
<!--                    <span>中小型企业</span>-->
<!--                    <span>制造业</span>-->
<!--                    <span>政府办公用品提供商</span>-->
<!--                  </div>-->
                  <div class="time">时间：{{ formattDate(item.aae036) }}</div>
                  <div class="address">
                    所在区县：
                     {{ item.chb015Desc }}
                  </div>
                </div>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import imagebg03 from '@/assets/images/community/bg-03.png'

import YhNoData from '@/components/common/YhNoData/YhNoData.vue'
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import {onMounted} from 'vue'
import { useRouter } from 'vue-router'
import moment from 'moment'

import { useGetCollectionData, useGetCollectionDesc } from '@/use'

const router = useRouter()
const props = defineProps({
  renderData: {
    type: Array,
    default: () => [],
  },
})

const formattDate = (aae036) => {
  if (aae036) {
    return moment(aae036).format('yyyy年MM月DD日')
  }
  return ''
}

// 路由跳转
const handleJumpRoute = (item) => {
  const { chm650 } = item
  router.push(`/new/${chm650}`)
}
</script>

<style lang="scss" scoped>
.corporate-report-wrapper {
  width: 100%;
  padding-top: $spacing;
  padding-bottom: $spacing-title * 2;
  .items {
    display: flex;
    flex-wrap: wrap;
    padding-top: $spacing-title;
    .item {
      margin-bottom: 20px;
      margin-right: 27px;

      .card {
        width: 382px;
        height: 130px;
        background-image: linear-gradient(#f7faff, #f7faff),
          linear-gradient(#ffffff, #ffffff);

        border-radius: 4px;
        border: solid 2px rgba(216, 232, 255, 0.51);
        cursor: pointer;
        padding: 26px 20px;
        display: flex;
        align-items: center;
        font-size: 12px;
        line-height: 1;
        color: #666666;
        overflow: hidden;
        transition: all 0.3s;
        &:hover {
          transform: scale(1.1);
        }
      }
      .left {
        width: 66px;
        font-size: 14px;
        font-weight: 400;
        line-height: 1;
        color: #ffffff;
        position: relative;
        white-space: nowrap;
        margin-right: 16px;
        span {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
        }
      }
      .right {
        flex: 1;
        overflow: hidden;
      }
      .name {
        font-size: $font-size-c;
        font-weight: 700;
        line-height: 1;
        color: $color-title;
      }
      .tag {
        display: flex;
        align-items: center;
        font-size: 14px;
        color: $primary;
        padding-top: 14px;
        white-space: nowrap;
        span {
          position: relative;
          padding-right: 10px;
          &::after {
            position: absolute;
            content: '';
            height: 100%;
            width: 1px;
            top: 0%;
            right: 5px;
            background-color: $primary;
          }
          &:last-child {
            padding-right: 0;
            &::after {
              width: 0;
            }
          }
        }
      }
      .time {
        line-height: 14px;
        padding-top: 6px;
      }
      .address {
        line-height: 14px;
      }
      &:nth-of-type(3n) {
        margin-right: 0;
      }
    }
  }
}
</style>
