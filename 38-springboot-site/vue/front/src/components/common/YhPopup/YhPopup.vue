<template>
  <div class="yh-popup" v-if="modelValue">
    <div class="yh-popup-content">
      <div class="title-wrapper" :style="titleWrapperStyle">
        <span>{{ popupName }}</span>
        <i
          v-if="showCloseBtn"
          class="iconfont iconfont-close"
          @click="emit('update:modelValue', false)"
        ></i>
      </div>
      <el-scrollbar :max-height="scrollWrapperMaxHeight" class="scroll-wrapper">
        <slot></slot>
      </el-scrollbar>
    </div>
    <div class="yh-popup-mask" @click="handleClickMask"></div>
  </div>
</template>

<script setup>
import imageBgHeader from './images/bg-header.png'
import { computed, onMounted, ref } from 'vue'

const emit = defineEmits(['update:modelValue'])
const props = defineProps({
  // 弹窗标题
  popupName: {
    type: String,
    default: () => '标题',
  },
  // 控制弹窗显示与关闭
  modelValue: {
    type: Boolean,
    required: true,
  },
  // 蒙层关闭
  maskClose: {
    type: Boolean,
    default: () => true,
  },
  // 是否显示关闭按钮
  showCloseBtn: {
    type: Boolean,
    default: () => true,
  },
  // 控制弹窗标题字体排版
  align: {
    type: String,
    default: () => 'left',
  },
})

const windowInnerHeight = ref(0)

// 标题样式
const titleWrapperStyle = computed(() => {
  let str = ''
  const align = props.align
  const showCloseBtn = props.showCloseBtn

  if (imageBgHeader) {
    str += `background-image:url(${imageBgHeader}); `
  }
  if (align) {
    str += `text-align:${align}; `
  }
  if (showCloseBtn) {
    str += `padding-right: 70px; `
  } else {
    str += `padding-right: 0; `
  }

  return str
})

// 遮罩层点击
const handleClickMask = () => {
  if (props.maskClose) {
    emit('update:modelValue', false)
  }
}

// 滚动容器最大高度
const scrollWrapperMaxHeight = computed(() => {
  let num = 0
  let rate = 0.8
  num = windowInnerHeight.value * rate - 56
  return num
})

onMounted(() => {
  windowInnerHeight.value = window.innerHeight
  window.onresize = () => {
    windowInnerHeight.value = window.innerHeight
  }
})
</script>

<style lang="scss" scoped>
.yh-popup {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: 99;
}
.yh-popup-content {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 80%;
  max-width: 1100px;
  background-color: #fff;
  transform: translate(-50%, -50%);
  z-index: 2;
}
.yh-popup-mask {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1;
}
.title-wrapper {
  width: 100%;
  height: 56px;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: relative;
  color: #fff;
  padding-right: 70px;
  span {
    padding-left: 26px;
    display: inline-block;
    font-weight: 700;
    font-size: 18px;
    line-height: 56px;
  }
  .iconfont {
    font-size: 20px;
    position: absolute;
    top: 50%;
    right: 16px;
    transform: translateY(-50%);
    z-index: 2;
    padding: 10px;
    cursor: pointer;
    color: #fff;
  }
}
.scroll-wrapper {
  width: 100%;
}
</style>
