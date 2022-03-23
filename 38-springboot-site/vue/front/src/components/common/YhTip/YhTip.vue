<template>
  <div class="yh-tip" :style="tipStyle">
    <i class="iconfont iconfont-hint" :style="iconfontStyle"></i>
    <div class="tip">
      <slot>{{ msg }}</slot>
    </div>
  </div>
</template>

<script setup>
import { colorToRgba } from '@/utils/convert'
import { computed } from 'vue'

const props = defineProps({
  msg: String,
  color: {
    type: [String],
    default: '#207cff',
  },
})

// 背景颜色控制
const tipStyle = computed(() => {
  let str = ''
  const color = props.color
  const rgbaColor = colorToRgba(color, 0.1)

  if (rgbaColor) {
    str += `background-color: rgba(${rgbaColor});`
  }
  return str
})
// iconfont颜色控制
const iconfontStyle = computed(() => {
  let str = ''
  const color = props.color
  const rgbaColor = colorToRgba(color)

  if (rgbaColor) {
    str += `color: rgba(${rgbaColor});`
  }

  return str
})
</script>

<style lang="scss" scoped>
.yh-tip {
  font-size: 14px;
  font-weight: 400;
  line-height: 1.4;
  color: #333;
  padding: 12px;
  display: flex;
  align-items: center;
  .iconfont {
    margin-right: 4px;
  }
}
</style>
