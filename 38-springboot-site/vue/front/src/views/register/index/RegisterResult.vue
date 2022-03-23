<template>
  <div class="register-result">
    <template v-if="isSuccess">
      <img :src="imageSuccess" alt="" />
      <div class="text">提交成功！</div>
    </template>
    <template v-else>
      <img :src="imageFail" alt="" />
      <div class="text fail">提交失败！</div>
    </template>
  </div>
</template>

<script setup>
import imageSuccess from '@/assets/images/home/registr/success.png'
import imageFail from '@/assets/images/home/registr/fail.png'

defineProps({
  isSuccess: {
    type: Boolean,
    default: () => false,
  },
})
</script>

<style lang="scss" scoped>
.register-result {
  width: 100%;
  padding: 120px 230px;
  img {
    display: block;
    width: 120px;
    margin: 0 auto;
  }
  .text {
    text-align: center;
    font-size: 24px;
    font-weight: 700;
    line-height: 1;
    color: #333;
    padding-top: 16px;
    &.fail {
      color: var(--color-fail);
    }
  }
}
</style>
