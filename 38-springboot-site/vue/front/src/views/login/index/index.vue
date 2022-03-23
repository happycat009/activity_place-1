<template>
  <div class="login" :style="loginStyle">
    <div class="content">
      <div class="logo-wrapper">
        <YhLogo @click="handleJumpRoute('/home')"></YhLogo>
      </div>
      <div class="main">
        <template v-if="isScanLogin">
          <ScanWrapper @change="handleChangeLoginType"></ScanWrapper>
        </template>
        <template v-else>
          <FormWrapper @change="handleChangeLoginType"></FormWrapper>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import imageBg from '@/assets/images/login/bg.png'

import YhLogo from '@/components/project/YhLogo/YhLogo.vue'
import FormWrapper from './FormWrapper.vue'
import ScanWrapper from './ScanWrapper.vue'

import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

// 登录类型 false 账号登录|手机验证码登录 true 扫码登录
const isScanLogin = ref(false)
const loginStyle = computed(() => {
  let str = ''
  str += `background-image: url(${imageBg})`
  return str
})
const router = useRouter()

const handleJumpRoute = (path) => {
  router.push(path)
}
const handleChangeLoginType = (val) => {
  return isScanLogin.value = val
}
</script>

<style lang="scss" scoped>
.login {
  width: 100%;
  min-width: 1000px;
  min-height: 100%;
  min-height: 100vh;
  padding-bottom: 100px;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: top left;
}
</style>
