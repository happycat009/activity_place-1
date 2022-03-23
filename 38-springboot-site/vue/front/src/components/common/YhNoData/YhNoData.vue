<template>
  <div class="yh-no-data">
    <el-empty :image-size="200" :description="description"></el-empty>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  description: {
    type: [String],
    default: '暂无数据',
  },
})
</script>

<style lang="scss" scoped>
.yh-no-data {
  width: 100%;
  .image-wrapper {
    width: 200px;
    margin: 0 auto;
    svg {
      width: 100%;
      height: 100%;
      vertical-align: top;
      fill: rgb(220, 221, 224);
    }
  }
}
</style>
