<template>
  <div class="scan-wrapper">
    <div class="title">扫码登录</div>
    <div class="scan-img">
      <img class="scan-content" :src="imageScan" alt="" />
       <img class="scan-bg" :src="imageBg" alt="" />
    </div>
    <div class="tip-wrapper">
      <div>打开<em>甜成办微信公众号</em></div>
      <div>扫一扫登录</div>
    </div>
    <div class="agree-wrapper">
       <span class="agreement" @click="showAgreement">《用户服务协议》 《隐私政策》</span>
      <el-dialog v-model="isShowAgreement" width="800px" @close="handleChange" top="8vh">
        <AgreementContent @closeModel="hideAgreement"></AgreementContent>
      </el-dialog>
    </div>
    <div class="image-wrapper">
        <img class="qr" :src="imageMac" alt="" @click="emit('change', false)" />
    </div>
  </div>
</template>

<script setup>
import imageMac from '@/assets/images/login/mac.png'
import imageScan from '@/assets/images/login/scanExamples.png'
import imageBg from '@/assets/images/login/scan-bg.png'
import AgreementContent from '@/components/common/YhAgreement/AgreementContent.vue'
import {ref } from 'vue'

const emit = defineEmits(['change'])
const isShowAgreement = ref(false)
const hideAgreement = (val) => {
  isShowAgreement.value = val
}
const showAgreement = () => {
  isShowAgreement.value = true
}
</script> 

<style lang="scss" scoped>
.scan-wrapper {
  text-align: center;
  width: 680px;
  // width: 65.625%;
  background-color: rgba(#fff, 0.93);
  box-shadow: 0px 30px 28px 4px rgba(0, 15, 217, 0.17);
  border-radius: 8px;
  display: block;
  align-items: center;
  justify-content: space-between;
  padding: 2%;
  // padding-left: 5.729167%;
  // padding-right: 7.8125%;
  margin: auto;
  position: relative;
  .left {
    width: 494px;
    width: 49.4%;
    // margin-right: 120px;
    // margin-left: 110px - 24px;
  }
  .right {
    width: 376px;
    width: 37.6%;
    // overflow: hidden;
  }
  @media screen and (max-width: 1500px) {
    padding: 4%;
    .right {
      width: 44%;
    }
  }
}
.title{
    color: var(--color-primary);
    font-weight: 600;
    font-size: 18px;
    padding-bottom: 5%;
    text-align: left;
}
.image-wrapper {
  position: absolute;
  width: 7.936508%;
  top: 24px;
  right: 24px;
  img {
    width: 100%;
    cursor: pointer;
  }
  @media screen and (max-width: 1500px) {
    top: 12px;
    right: 12px;
  }
}
 .agreement {
    color: var(--color-primary);
    cursor: pointer;
    user-select: none;
    transition: all 0.3s;
    &:hover {
      text-decoration: underline;
    }
  }
  .scan-img{
    width: 500px;
    display: flex;
  }
  .tip-wrapper{
    padding: 20px;
  }
  .scan-content{
    width: 300px;
    margin-right: 10px;
  }
</style>
