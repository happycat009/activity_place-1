<template>
  <div
    class="yh-logo"
    :class="[noPadding ? 'no-padding' : '']"
    @click.stop="emit('click')"
  >
    <img class="logo-img" :src="imageLogo" alt="" />
    <div class="logo-text PangMenZhengDao">甜成办</div>
  </div>
</template>

<script setup>
import imageLogo from './images/logo.png'

const emit = defineEmits(['click'])
const props = defineProps({
  noPadding: {
    type: [Boolean],
    default: false,
  },
})
</script>

<style lang="scss" scoped>
.yh-logo {
  display: inline-flex;
  align-items: center;
  white-space: nowrap;
  padding: 50px 60px;
  padding: 2.604167% 3.125%;
  cursor: pointer;
  &.no-padding {
    padding: 0;
  }
  .logo-img {
    width: 78px;
    margin-right: 24px;
  }
  .logo-text {
    font-size: 50px;
    font-weight: 500;
    line-height: 1;
    letter-spacing: 8px;
    color: #fff;
  }
  @media screen and (max-width: 1500px) {
    .logo-img {
      width: 60px;
      margin-right: 12px;
    }
    .logo-text {
      font-size: 40px;
      letter-spacing: 8px;
    }
  }
}
</style>
