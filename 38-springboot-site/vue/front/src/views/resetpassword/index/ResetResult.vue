<template>
  <div class="reset-result">
    <template v-if="isSuccess">
      <img :src="imageSuccess" alt="" />
      <div class="text">密码重置成功</div>
      <div class="jump-link">
        <span class="primary"> {{ timeNum }}</span>
        <span>秒后自动跳转至登录页面，您可以点击</span>
        <span class="link" @click="handleJumpRoute">此处立即跳转</span>
      </div>
    </template>
    <template v-else>
      <img :src="imageFail" alt="" />
      <div class="text fail">密码重置失败</div>
    </template>
  </div>
</template>

<script setup>
import imageSuccess from '@/assets/images/home/registr/success.png'
import imageFail from '@/assets/images/home/registr/fail.png'

import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const timeNum = ref(15)
const timer = ref(null)

const props = defineProps({
  // 是否成功
  isSuccess: {
    type: Boolean,
    default: () => false,
  },
})

const handleJumpRoute = () => {
  router.push('/login')
}
onMounted(() => {
  timer.value = setInterval(() => {
    timeNum.value--
    if (timeNum.value == 0) {
      clearInterval(timer.value)
      handleJumpRoute()
    }
  }, 1000)
})
</script>

<style lang="scss" scoped>
.reset-result {
  width: 100%;
  padding: 60px 230px;
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
  .jump-link {
    font-size: 16px;
    padding-top: 40px;
    display: flex;
    align-items: center;
    color: #666;
    justify-content: center;
    .primary {
      color: var(--color-primary);
    }
    .link {
      cursor: pointer;
      color: var(--color-primary);
    }
  }
}
</style>
