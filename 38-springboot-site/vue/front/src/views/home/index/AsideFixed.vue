<template>
  <div class="aside-fixed">
    <div class="item">
      <i class="iconfont iconfont-scan"></i>
      <div>微信公众号</div>
    </div>
    <div class="item" @click="handleJumpRoute('/home/questionnaire/2')">
      <i class="iconfont iconfont-message"></i>
      <div>企业评价</div>
    </div>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
const router = useRouter()
const route = useRoute()
const handleJumpRoute = (path) => {
  router.push(path)
}
</script>

<style lang="scss" scoped>
.aside-fixed {
  position: fixed;
  right: 0;
  bottom: 92px;
  .item {
    width: 88px;
    height: 88px;
    background-color: rgba(#000b42, 0.6);
    box-shadow: 0px 8px 54px 3px rgba(91, 91, 91, 0.1);
    border-radius: 5px;
    color: #fff;
    font-size: 12px;
    line-height: 1;
    color: #ffffff;
    text-align: center;
    padding-top: 24px;
    cursor: pointer;
    user-select: none;
    .iconfont {
      font-size: 20px;
    }
    div {
      padding-top: 10px;
    }
    position: relative;
    &::after {
      content: '';
      position: absolute;
      left: 50%;
      bottom: 0;
      transform: translateX(-50%);
      width: 72px;
      height: 2px;
      background-color: rgba(0, 108, 255, 0.3);
    }
  }
}
</style>
