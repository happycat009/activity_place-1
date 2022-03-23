<template>
  <div class="apply-submit">
    <!-- <div class="agreement-wrapper">
      <YhAgreement
        agreement="《内江企业扶持政策申报》协议"
        v-model="arggen"
      ></YhAgreement>
    </div> -->
    <div class="button-wrapper">
      <!-- <el-button type="primary" disabled>测试</el-button> -->
      <button class="primary reverse" @click="emit('reset')">重置</button>
      <button class="primary" @click="emit('submit')">
        提交
      </button>
    </div>
  </div>
</template>

<script setup>
import YhAgreement from '@/components/common/YhAgreement/YhAgreement.vue'

import { ref } from 'vue'

// const arggen = ref(false)
const emit = defineEmits(['reset', 'submit'])
</script>

<style lang="scss" scoped>
.apply-submit {
  width: 100%;
  padding-top: 34px;
  margin-top: 66px;
  border-top: solid 1px #c9e0ff;

  user-select: none;
  .agreement-wrapper {
    text-align: center;
  }

  .button-wrapper {
    display: flex;
    justify-content: center;
    padding-top: 30px;
    button {
      width: 144px;
      line-height: 40px;
      font-weight: 700;
    }
    .reverse {
      margin-right: 30px;
    }
  }
}
</style>
