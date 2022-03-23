<template>
  <div class="register">
    <div class="container">
      <!-- <YhBreadcrumb :renderData="breadcrumbData"></YhBreadcrumb> -->
      <div class="register-content">
        <TitleInfo></TitleInfo>
        <YhProcess :renderData="customData.processList"></YhProcess>
        <template v-if="currentStep == 1">
          <LegalPersonForm
            @submit="handleSubmitLegalPersonInfo"
            @uploadSuccess="handleUploadSuccessLegalPersonInfo"
            @uploadRemove="handleUploadRemoveLegalPersonInfo"
            :formData="customData.legalPersonInfo"
          ></LegalPersonForm>
        </template>
        <template v-if="currentStep == 2">
          <RealPersonAuthForm
            @submit="handleSubmitRealPersonInfo"
            @back="updateCurrentStep(1)"
            @uploadSuccess01="handleUploadSuccess01RealPersonInfo"
            @uploadRemove01="handleUploadRemove01RealPersonInfo"
            @uploadSuccess02="handleUploadSuccess02RealPersonInfo"
            @uploadRemove02="handleUploadRemove02RealPersonInfo"
            :formData="customData.realPersonInfo"
          ></RealPersonAuthForm>
        </template>
        <template v-if="currentStep == 3">
          <AgentForm
            @submit="handleSubmitAgentInfo"
            @back="updateCurrentStep(2)"
            :formData="customData.agentInfo"
          ></AgentForm>
        </template>
        <template v-if="currentStep == 4">
          <AccountForm
            @submit="handleSubmitAccountInfo"
            @back="updateCurrentStep(3)"
            :formData="customData.accountInfo"
          ></AccountForm>
        </template>
        <template v-if="currentStep == 5">
          <RegisterResult :isSuccess="true"></RegisterResult>
        </template>
      </div>
    </div>
  </div>
</template>
<script setup>
import YhBreadcrumb from '@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue'
import YhProcess from '@/components/common/YhProcess/YhProcess.vue'

import TitleInfo from './TitleInfo.vue'
import LegalPersonForm from './LegalPersonForm.vue'
import RealPersonAuthForm from './RealPersonAuthForm.vue'
import AgentForm from './AgentForm.vue'
import AccountForm from './AccountForm.vue'
import RegisterResult from './RegisterResult.vue'

import { reactive, ref } from 'vue'
import { requestB101 } from '@/service'

// 当前填写步骤 1 2 3 4 5
const currentStep = ref(1)

const customData = reactive({
  processList: [
    {
      id: '01',
      name: '法人信息',
      desc: '',
      state: 1, // 0未进行 1进行中 2已完成
    },
    {
      id: '02',
      name: '法人代表人实人认证',
      desc: '',
      state: 0, // 0未进行 1进行中 2已完成
    },
    {
      id: '03',
      name: '经办人信息',
      state: 0, // 0未进行 1进行中 2已完成
    },
    {
      id: '04',
      name: '账号信息',
      state: 0, // 0未进行 1进行中 2已完成
    },
  ],
  // 法人信息
  legalPersonInfo: {
    legal_person_name: '',
    legal_person_code: '',
    legal_person_address: '',
    fileList: [],
  },
  // 负责人认证信息
  realPersonInfo: {
    principal_name: '',
    principal_license_type: '01',
    principal_license_number: '',
    principal_start_date: '',
    principal_end_date: '',
    principal_validity_period: '',
    fileList01: [],
    fileList02: [],
  },
  // 经办人信息
  agentInfo: {
    agent_name: '',
    agent_code: '',
  },
  accountInfo: {
    account_phone: '',
    account_password: '',
    account_password_again: '',
    account_verification_code: '',
  },
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
// 法人信息 文件上传成功
const handleUploadSuccessLegalPersonInfo = (data) => {
  customData.legalPersonInfo = {
    ...customData.legalPersonInfo,
    fileList: [data],
  }
}
// 法人信息 文件上传移除
const handleUploadRemoveLegalPersonInfo = () => {
  customData.legalPersonInfo = {
    ...customData.legalPersonInfo,
    fileList: [],
  }
}
// 法人信息 表单提交成功
const handleSubmitLegalPersonInfo = (data) => {
  customData.legalPersonInfo = {
    ...customData.legalPersonInfo,
    ...data,
  }
  updateCurrentStep(2)
}

// 法人代表人 身份证正面 文件上传成功
const handleUploadSuccess01RealPersonInfo = (data) => {
  customData.realPersonInfo = {
    ...customData.realPersonInfo,
    fileList01: [data],
  }
}
// 法人代表人 身份证正面 文件上传移除
const handleUploadRemove01RealPersonInfo = (data) => {
  customData.realPersonInfo = {
    ...customData.realPersonInfo,
    fileList01: [],
  }
}
// 法人代表人 身份证反面 文件上传成功
const handleUploadSuccess02RealPersonInfo = (data) => {
  customData.realPersonInfo = {
    ...customData.realPersonInfo,
    fileList02: [data],
  }
}
// 法人代表人 身份证反面 文件上传移除
const handleUploadRemove02RealPersonInfo = (data) => {
  customData.realPersonInfo = {
    ...customData.realPersonInfo,
    fileList02: [],
  }
}

// 法人代表人 表单提交成功
const handleSubmitRealPersonInfo = (data) => {
  customData.realPersonInfo = {
    ...customData.realPersonInfo,
    ...data,
  }
  updateCurrentStep(3)
}

// 经办人 表单提交成功
const handleSubmitAgentInfo = (data) => {
  customData.agentInfo = {
    ...customData.agentInfo,
    ...data,
  }
  updateCurrentStep(4)
}

// 提交注册
const handleSubmit = async () => {
  let { accountInfo, agentInfo, legalPersonInfo, realPersonInfo } = customData
  const {
    legal_person_name,
    legal_person_code,
    legal_person_address,
    fileList,
  } = legalPersonInfo
  const {
    principal_name,
    principal_license_type,
    principal_license_number,
    principal_start_date,
    principal_end_date,
    principal_validity_period,
    fileList01,
    fileList02,
  } = realPersonInfo
  const { agent_name, agent_code } = agentInfo
  const { account_phone, account_password } = accountInfo
  let arr = [] // 储存上传文件
  fileList.map((item) => {
    arr.push({
      chi212: '企业证件', // 文件名
      chi214: item.chi214, // 文件类别
      chi217: '', // 文件备注
      chi215: item.objectid, // 统一存储id
      che110: '007', // 材料id
    })
  })
  fileList01.map((item) => {
    arr.push({
      chi212: '法人证件', // 文件名
      chi214: item.chi214, // 文件类别
      chi217: '', // 文件备注
      chi215: item.objectid, // 统一存储id
      che110: '008', // 材料id
    })
  })
  fileList02.map((item) => {
    arr.push({
      chi212: '法人证件', // 文件名
      chi214: item.chi214, // 文件类别
      chi217: '', // 文件备注
      chi215: item.objectid, // 统一存储id
      che110: '008', // 材料id
    })
  })
  const data = await requestB101(
    legal_person_name,
    legal_person_code,
    principal_name,
    principal_license_number,
    principal_license_type,
    principal_start_date,
    principal_end_date,
    agent_name,
    agent_code,
    account_password,
    principal_validity_period,
    account_phone,
    legal_person_address,
    '1',
    '1',
    JSON.stringify(arr)
  )
  console.log(data, 987654321)
  updateCurrentStep(5)
}
// 账号信息 表单提交成功
const handleSubmitAccountInfo = (data) => {
  customData.accountInfo = {
    ...customData.accountInfo,
    ...data,
  }
  if (data.isSubmit) {
    handleSubmit()
  }
}
</script>

<style lang="scss" scoped>
.register {
  width: 100%;
  background-color: #f7faff;
  padding: 30px 0;

  .register-content {
    background-color: #fff;
    padding: 30px;
  }
}
</style>
