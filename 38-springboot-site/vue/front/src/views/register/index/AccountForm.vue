<template>
  <div class="agent-info">
    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="124px"
      >
        <el-form-item label="手机号：" prop="account_phone">
          <el-input
            type="text"
            v-model="formData.account_phone"
            maxlength="11"
            placeholder="请输入手机号"
            @blur="handleChangePhoneNumber"
          >
            <template #prefix>
              <i class="iconfont iconfont-phone"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="account_password">
          <el-input
            type="password"
            v-model="formData.account_password"
            placeholder="请输入8-16位数字、字母或符号"
            maxlength="16"
          >
            <template #prefix>
              <i class="iconfont iconfont-password_full"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="确认密码：" prop="account_password_again">
          <el-input
            type="password"
            v-model="formData.account_password_again"
            placeholder="请再次输入密码"
            maxlength="16"
          >
            <template #prefix>
              <i class="iconfont iconfont-password_full"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item
          label="短信验证码："
          prop="account_verification_code"
          class="account-verification-code"
        >
          <el-input
            type="text"
            v-model="formData.account_verification_code"
            placeholder="请输入验证码"
            maxlength="6"
            :disabled="isOkVerificationCode"
            @blur="validateSmsCode"
          >
            <template #prefix>
              <i class="iconfont iconfont-verification-code"></i>
            </template>
          </el-input>
          <button
            class="primary send-btn"
            type="button"
            @click.stop="handleSendCode(formRef)"
            :disabled="sendButtonDisabled"
          >
            {{ buttonText }}
          </button>
        </el-form-item>
      </el-form>
    </div>
    <div class="agreement-wrapper">
      <YhAgreement
        :modelValue="isAgree"
        agreement="《用户服务协议》和《隐私政策》"
        @handlerAgree="handlerAgree"
      ></YhAgreement>
    </div>

    <div class="button-wrapper">
      <button class="primary reverse" type="button" @click="emit('back')">
        上一步
      </button>
      <button
        class="primary"
        type="button"
        :disabled="!isCanSubmit"
        @click="handleSubmit(formRef)"
      >
        下一步
      </button>
    </div>
  </div>
</template>

<script setup>
import YhAgreement from '@/components/common/YhAgreement/YhAgreement.vue'
import YhTip from '@/components/common/YhTip/YhTip.vue'

import { reactive, ref, computed } from 'vue'
import { requestGetSmsCode, requestCheckSmsCode } from '@/service'
import { ElMessage } from 'element-plus'

import { phoneNumberReg } from '@/utils/regular'

const sendButtonDisabled = ref(false)
const buttonText = ref('发送验证码')

const formRef = ref(null)
const timer = ref(null)
const isAgree = ref(false)
// 短信验证码是否正确
const isOkVerificationCode = ref(false)
// 验证码校验正确时使用的手机号
const verificationCodePhone = ref('')

const emit = defineEmits(['submit', 'back'])

const props = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        account_phone: '',
        account_password: '',
        account_password_again: '',
        account_verification_code: '',
      }
    },
  },
})

// 校验是否输入了相同密码
const validatePassword = (rule, value, callback) => {
  const { account_password } = props.formData
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== account_password) {
    callback(new Error('两次输入密码不一致!'))
  } else {
    callback()
  }
}

// 手机号改变
const handleChangePhoneNumber = (e) => {
  let value = e.target.value
  value = value.trim()
  if (value != verificationCodePhone.value) {
    isOkVerificationCode.value = false
    emit('submit', { account_verification_code: '', isSubmit: false })
  }
}

// 校验验证码
const validateSmsCode = () => {
  let value = props.formData.account_verification_code
  let { account_phone } = props.formData
  value = value.trim()
  account_phone = account_phone.trim()

  if (
    account_phone &&
    account_phone.length == 11 &&
    value &&
    value.length == 6
  ) {
    requestCheckSmsCode(account_phone, value)
      .then((res) => {
        // console.log(res, 'validateSmsCode====')
        verificationCodePhone.value = account_phone
        isOkVerificationCode.value = true
      })
      .catch((err) => {
        ElMessage.error(err)
      })
  }
}

// 校验规则
const formRules = reactive({
  account_phone: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    {
      pattern: phoneNumberReg,
      message: '手机号码格式不对，请重新输入',
      trigger: 'blur',
    },
  ],
  account_password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      message: '请输入8-16个字符，至少1个大写字母，1个小写字母和1个数字。',
      pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/,
      trigger: 'blur',
    },
  ],
  account_password_again: [
    { required: true, validator: validatePassword, trigger: 'blur' },
  ],
  account_verification_code: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 6, message: '请输入6位数字验证码', trigger: 'blur' },
  ],
})

// 更新发生按钮状态
const updataSendButtonState = () => {
  sendButtonDisabled.value = true
  let times = 60
  timer.value = setInterval(() => {
    times--
    buttonText.value = `${times}s后点击重新发送`
    if (times == 1) {
      sendButtonDisabled.value = false
      buttonText.value = `重新发送验证码`
      clearInterval(timer.value)
    }
  }, 1000)
}

// 传递数据给父组件
const emitData = () => {
  const {
    account_phone,
    account_passwor,
    account_password_again,
    account_verification_code,
  } = props.formData
  const data = {
    account_phone,
    account_passwor,
    account_password_again,
    account_verification_code,
    isSubmit: true,
  }
  emit('submit', data)
}

// 是否可以点击下一步提交
const isCanSubmit = computed(() => {
  let result = true
  if (!isAgree.value) {
    result = false
  }
  return result
})

// 点击发送验证码
const handleSendCode = (formEl) => {
  if (!formEl) return
  formEl.validateField('account_phone', async (errorMessage) => {
    if (errorMessage) {
      formEl.scrollToField('account_phone')
      return false
    } else {
      const { account_phone } = props.formData

      const data = await requestGetSmsCode(account_phone)
      ElMessage.success(data.message + '注意查收！')
      updataSendButtonState()
    }
  })
}

// 点击下一步 先进行表单校验
const handleSubmit = (formEl) => {
  if (!formEl) return
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      if (isOkVerificationCode.value) {
        emitData()
      } else {
        ElMessage.error('短信验证码不正确，请检查！')
      }
    } else {
      const formErrPropsArr = []
      for (let item in formErrObj) {
        formErrPropsArr.push(item)
      }
      const firstErrProps = formErrPropsArr[0]
      formEl.scrollToField(firstErrProps)
      return false
    }
  })
}

const handlerAgree = (val) => {
  isAgree.value = val
}

</script>

<style lang="scss" scoped>
.agent-info {
  width: 100%;
  padding: 0 230px;
  padding-top: 30px;
  .send-btn {
    width: 170px;
  }
}

.button-wrapper {
  padding: 60px 0;
  display: flex;
  justify-content: center;
  button {
    display: block;
    width: 144px;
    margin-right: 30px;
    &:last-child {
      margin-right: 0;
    }
  }
}

.account-verification-code {
  position: relative;
  .el-input {
    padding-right: 180px;
  }
  button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);

    right: 0;
  }
}

.agreement-wrapper {
  padding: 90px 124px 0;
  display: flex;
  align-content: center;
}
</style>
