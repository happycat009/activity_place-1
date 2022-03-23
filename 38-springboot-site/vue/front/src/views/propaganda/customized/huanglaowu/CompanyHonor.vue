<template>
  <div class="company-honor" v-if="renderData && renderData.length > 0">
    <YhTitle title="企业荣誉" titleEn="ENTERPRISE HONOR" type="01"></YhTitle>

    <div class="con-wrapper">
      <div class="total">
        <div class="item first">
          <img :src="imageHonor" alt="" />
          <div class="key">共获得：</div>
          <div class="value">{{ renderData.length }}</div>
          <div class="unit">项</div>
        </div>
        <template v-for="(value, key, index) in honorTotalData" :key="index">
          <div class="item">
            <img :src="honorStateImageArr[index]" alt="" />
            <div class="key">{{ key }}：</div>
            <div class="value">{{ value }}</div>
            <div class="unit">项</div>
          </div>
        </template>
      </div>
      <div class="list">
        <template v-for="item in honorData" :key="item.cab230">
          <div class="item">
            <div class="name">
              {{ item.cab232 }}
            </div>
            <div class="time">获奖日期：{{ item.cab233.substr(0, 10) }}</div>
            <div class="tag" :class="['tag-' + item.state]">
              {{ item.cab231 }}
            </div>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import imageHonor from '@/assets/images/propaganda/honor.png'
import imageH01 from '@/assets/images/propaganda/h-01.png'
import imageH02 from '@/assets/images/propaganda/h-02.png'
import imageH03 from '@/assets/images/propaganda/h-03.png'

import YhTitle from '@/components/project/YhTitle/YhTitle.vue'

import { computed } from 'vue'

const props = defineProps({
  renderData: {
    type: Array,
    required: true,
  },
})

const honorStateImageArr = [imageH01, imageH02, imageH03]

const honorState = {
  企业获奖: '1',
  企业技术能力: '2',
  企业资质: '3',
}

const honorData = computed(() => {
  const { renderData } = props
  let arr = []
  if (renderData && renderData.length > 0) {
    renderData.map((item) => {
      item.state = honorState[item.cab231]
      arr.push(item)
    })
  }

  return arr
})

const honorTotalData = computed(() => {
  const { renderData } = props
  let obj = {}
  if (renderData && renderData.length > 0) {
    renderData.map((item) => {
      if (obj[item.cab231]) {
        obj[item.cab231]++
      } else {
        obj[item.cab231] = 1
      }
    })
  }
  return obj
})
</script>

<style lang="scss" scoped>
.company-honor {
  width: 100%;
  padding-top: 46px;
  .con-wrapper {
    width: 100%;
  }
  .total {
    display: flex;
    align-items: center;
    padding: 20px 0;

    .item {
      display: flex;
      align-items: center;
      font-size: 16px;
      line-height: 32px;
      color: #333;
      padding: 0 36px;
      position: relative;
      img {
        width: 22px;
        margin-right: 10px;
      }
      .value {
        font-weight: 700;
        font-size: 24px;
        line-height: 32px;
        color: #0069ff;
        padding-bottom: 4px;
      }
      .unit {
        font-size: 12px;
        color: #666;
        padding-left: 2px;
        padding-top: 4px;
      }
      &::after {
        content: '';
        position: absolute;
        top: 50%;
        right: 0;
        width: 1px;
        height: 32px;
        background-color: #eef5ff;
        border-radius: 1px;
        transform: translateY(-50%);
      }
      &:nth-of-type(2) {
        .value {
          color: #0072ff;
        }
      }
      &:nth-of-type(3) {
        .value {
          color: #00ceff;
        }
      }
      &:nth-of-type(4) {
        &::after {
          width: 0;
        }
        .value {
          color: #ffb400;
        }
      }
      &.first {
        padding-left: 0;
        img {
          width: 58px;
          margin-right: 14px;
        }
      }
    }
  }
  .list {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    .item {
      width: 384px;
      width: 357px;
      margin-bottom: 24px;
      margin-right: 24px;
      border-radius: 4px;
      padding: 22px 122px 22px 20px;
      border-radius: 4px;
      transition: all 0.3s;
      border: solid 2px #e0ecff;
      cursor: pointer;
      position: relative;

      .name {
        font-size: 16px;
        font-weight: 700;
        line-height: 1;
        color: #333;
      }
      .time {
        font-size: 14px;
        line-height: 1;
        color: #999;
        padding-top: 14px;
      }
      .tag {
        position: absolute;
        top: 14px;
        right: 0;
        font-size: 14px;
        line-height: 1;
        color: #f00;
        padding: 5px 0;
        border: solid 1px #ffb5b5;
        border-right: 0;
        background-color: #fff1f1;
        width: 96px;
        text-align: center;
        &.tag-2 {
          color: #0060ff;
          background-color: #f1fcff;
          border-color: #7ccfff;
        }
        &.tag-3 {
          color: #008457;
          background-color: #e8fff2;
          border-color: #00e772;
        }
      }
      &:nth-of-type(3n) {
        margin-right: 0;
      }
      &:hover {
        transform: scale(1.02);
      }
    }
  }
}
</style>
