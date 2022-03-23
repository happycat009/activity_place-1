<template>
  <div class="yh-title">
    <template v-if="type == '01'">
      <div class="title">
        <img class="img" :src="image01" />
        <span class="zh">{{ title }}</span
        >/<span class="en">{{ titleEn }}</span>
      </div>
    </template>
    <template v-if="type == '02'">
      <div class="title-02">
        <div class="left">
          {{ title }}
        </div>
        <div class="right">
          {{ rightText }}
        </div>
      </div>
    </template>
    <template v-if="type == '03'">
      <div class="title-03">
        <div class="left">{{ title }}</div>
        <div class="right"></div>
      </div>
    </template>
    <template v-if="type == '04'">
      <div class="title-04">
        <div class="left">{{ title }}</div>
        <div class="right"></div>
      </div>
    </template>
    <template v-if="type == '05'" >
      <div class="title-05" :data-en="titleEn" :style="{'background':'url('+image02+')' + 'no-repeat 50% 50%'}">
        <div class="text">{{ title }}</div>
      </div>
    </template>
  </div>
</template>

<script>
import image01 from './images/01.png'
import image02 from './images/02.png'
export default {
  name: 'YhTitle',
  props: {
    type: {
      type: [String],
      required: true,
    },
    title: {
      type: [String],
      required: true,
    },
    titleEn: {
      type: [String],
      default: '',
    },
    rightText: {
      type: [String],
      default: '',
    },
  },
  setup() {
    return {}
  },
  data() {
    return {
      image01,
      image02,
    }
  },
  created() {},
  mounted() {},
  components: {},
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.yh-title {
  width: 100%;
  .title {
    display: flex;
    align-items: center;
    font-size: 19px;
    font-weight: 400;
    line-height: 1;
    color: #999999;
    padding-left: 44px;
    padding-bottom: 12px;
    position: relative;
    padding-top: 17px;
    &::after {
      content: '';
      position: absolute;
      left: 0;
      bottom: 0;
      width: 506px;
      height: 8px;
      background-image: linear-gradient(to right, #edf5ff, #fff);
    }
    .img {
      width: 48px;
      height: 38px;
      position: absolute;
      top: 0;
      left: 0;
    }
    .zh {
      font-size: 22px;
      font-weight: 700;
      line-height: 1;
      color: #323232;
      padding-right: 15px;
    }
    .en {
      padding-left: 15px;
    }
  }
  .title-02 {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;
    &::after {
      content: '';
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      height: 4px;
      background-color: #f7f9fb;
    }
    .left {
      font-size: 18px;
      font-weight: 700;
      line-height: 1;
      color: #333333;
      padding-bottom: 14px;
      position: relative;
      &::after {
        content: '';
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 4px;
        background-color: var(--color-primary);
        z-index: 1;
      }
    }
    .right {
      color: var(--color-primary);
      font-size: 16px;
      line-height: 1;
      padding-bottom: 14px;
      cursor: pointer;
    }
  }
  .title-03 {
    display: flex;
    align-items: center;
    justify-content: space-between;
    .left {
      position: relative;
      padding-left: 16px;
      font-size: 16px;
      font-weight: 700;
      line-height: 1;
      color: #333333;
      &::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 4px;
        height: 16px;
        background-color: var(--color-primary);
        border-radius: 2px;
      }
    }
  }
  .title-04 {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-bottom: 17px;
    position: relative;
    &::after {
      content: '';
      position: absolute;
      bottom: 0;
      left: 0;
      width: 506px;
      height: 8px;
      background-image: linear-gradient(to right, #edf5ff, #fff);
    }
    .left {
      position: relative;
      padding-left: 16px;
      font-size: 18px;
      font-weight: 700;
      line-height: 1;
      color: #333333;
      &::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 4px;
        height: 100%;
        background-color: var(--color-primary);
        border-radius: 2px;
      }
    }
  }
  .title-05{
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    margin-bottom: 12px;
    .text{
      text-align: center;
      font-size: 28px;
      color:#333333;
    }
    &::after{
      content: attr(data-en);
      position: absolute;
      left: 50%;
      top: 100%;
      transform: translate(-50%,0);
      display: block;
      font-size: 12px;
      color: #999999;
    }
  }
}
</style>
