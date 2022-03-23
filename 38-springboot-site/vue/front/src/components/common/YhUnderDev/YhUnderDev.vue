<template>
  <div class="yh-under-dev">
    <img :src="image01" alt="" />
    <img :src="imageText" class="text" alt="" />
  </div>
</template>

<script setup>
import image01 from './images/01.png'
import imageText from './images/text.png'
</script>

<style lang="scss" scoped>
.yh-under-dev {
  width: 100%;
  padding: 60px 30px;
  img {
    width: 320px;
    display: block;
    margin: 0 auto;
    &.text {
      width: 200px;
      margin-top: 20px;
    }
  }
}
</style>
