<template>
  <YhPopup
    :modelValue="show"
    @update:modelValue="emit('update:show', $event)"
    align="center"
    :showCloseBtn="false"
    :maskClose="false"
    popupName="申报结果"
  >
    <div class="apply-result-content">
      <div class="apply-result">
        <i class="iconfont iconfont-success"></i>
        <div>申报成功</div>
      </div>
      <div class="button-wrapper">
        <button class="primary reverse" @click="handleJumpRoute('/home')">
          返回首页
        </button>
        <button class="primary" @click="handleJumpRoute('/enterprises/record')">
          查看申报记录
        </button>
      </div>
    </div>
  </YhPopup>
</template>

<script setup>
import YhPopup from '@/components/common/YhPopup/YhPopup.vue'

import { ref, computed, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { M_UPDATE_CURRENT_MENU_LIST } from '@/store/constants'

const store = useStore()
const router = useRouter()

const props = defineProps({
  show: {
    type: Boolean,
    required: true,
  },
})

const emit = defineEmits(['update:show'])

const handleJumpRoute = (path) => {
  emit('update:show', false)
  if (path.indexOf('enterprises') > -1) {
    store.commit(M_UPDATE_CURRENT_MENU_LIST, [
      {
        path: '/enterprises',
        name: '企业中心',
      },
    ])
  }
  if (path.indexOf('home') > -1) {
    store.commit(M_UPDATE_CURRENT_MENU_LIST, [
      {
        path: '/home',
        name: '首页',
      },
    ])
  }
  router.push(path)
}
</script>

<style lang="scss" scoped>
.apply-result-content {
  width: 100%;
  max-height: 400px;
  overflow: auto;
  padding: 24px;
  .apply-result {
    text-align: center;
    color: #333;
    padding-top: 30px;
    .iconfont {
      font-size: 50px;
      color: var(--color-success);
      font-weight: 700;
    }
    div {
      padding: 20px;
      font-size: 20px;
    }
  }

  .button-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px 102px;
    button {
      width: 140px;
      margin-right: 20px;
      &:last-child {
        margin-right: 0;
      }
    }
  }
}
</style>
