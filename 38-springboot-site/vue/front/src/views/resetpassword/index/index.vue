<template>
  <div class="reset-password">
    <div class="container">
      <div class="reset-password-content">
        <TitleInfo></TitleInfo>
        <YhProcess :renderData="customData.processList"></YhProcess>
        <template v-if="currentStep == 1">
          <AuthForm
            :formData="customData.authInfo"
            @submit="handleSubmitAuthForm"
          ></AuthForm>
        </template>
        <template v-if="currentStep == 2">
          <PasswordForm
            :formData="customData.passwordInfo"
            @back="updateCurrentStep(1)"
            @submit="handleSubmitPasswordForm"
          ></PasswordForm>
        </template>
        <template v-if="currentStep == 3">
          <ResetResult :isSuccess="true"></ResetResult>
        </template>
      </div>
    </div>
  </div>
</template>
<script setup>
import YhProcess from '@/components/common/YhProcess/YhProcess.vue'

import TitleInfo from './TitleInfo.vue'
import AuthForm from './AuthForm.vue'
import PasswordForm from './PasswordForm.vue'
import ResetResult from './ResetResult.vue'

import { reactive, ref } from 'vue'
import { requestE411 } from '@/service'

// 当前填写步骤 1 2 3
const currentStep = ref(1)

const customData = reactive({
  processList: [
    {
      id: '01',
      name: '验证身份',
      desc: '',
      state: 1, // 0未进行 1进行中 2已完成
    },
    {
      id: '02',
      name: '重置密码',
      desc: '',
      state: 0, // 0未进行 1进行中 2已完成
    },
    {
      id: '03',
      name: '重置完成',
      state: 0, // 0未进行 1进行中 2已完成
    },
  ],
  authInfo: {
    auth_loginid: '',
    auth_phone: '',
    auth_verification_code: '',
  },
  passwordInfo: {
    password: '',
    password_again: '',
  },
  userid: '', // 用户id
})

// 更新当前步骤
const updateCurrentStep = (num) => {
  const data = customData.processList
  currentStep.value = num
  data.map((item, index) => {
    if (index == num - 1) {
      item.state = 1
    }
    if (index > num - 1) {
      item.state = 0
    } else {
      item.state = 2
    }
  })
}

// 身份证验证下一步
const handleSubmitAuthForm = (data) => {
  customData.userid = data
  updateCurrentStep(2)
}

const handleSubmitPasswordForm = (data) => {
  const { userid } = customData
  const { password } = data
  requestE411(userid, password).then((res) => {
    updateCurrentStep(3)
  })
}
</script>

<style lang="scss" scoped>
.reset-password {
  width: 100%;
  background-color: #f7faff;
  padding: 30px 0;

  .reset-password-content {
    background-color: #fff;
    padding: 30px;
  }
}
</style>
