<template>
  <div class="auth-form-phone">
    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="账号名：" prop="auth_loginid">
          <el-input
            type="text"
            v-model="formData.auth_loginid"
            placeholder="请输入账号"
            maxlength="18"
          >
            <template #prefix>
              <i class="iconfont iconfont-user"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="手机号码：" prop="auth_phone">
          <el-input
            type="text"
            v-model="formData.auth_phone"
            placeholder="请输入手机号码"
            maxlength="11"
          >
            <template #prefix>
              <i class="iconfont iconfont-phone"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item
          label="短信验证码："
          prop="auth_verification_code"
          class="account-verification-code"
        >
          <el-input
            type="text"
            v-model="formData.auth_verification_code"
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

    <div class="button-wrapper">
      <button class="primary" type="button" @click="handleSubmit(formRef)">
        下一步
      </button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { requestGetSmsCode, requestCheckSmsCode, requestE410 } from '@/service'
import { ElMessage } from 'element-plus'
import { phoneNumberReg } from '@/utils/regular'

const sendButtonDisabled = ref(false)
const buttonText = ref('发送验证码')

const formRef = ref(null)
const timer = ref(null)

// 短信验证码是否正确
const isOkVerificationCode = ref(false)
// 验证码校验正确时使用的手机号
const verificationCodePhone = ref('')

const emit = defineEmits(['submit'])

const props = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        auth_loginid: '',
        auth_phone: '',
        auth_verification_code: '',
      }
    },
  },
})

// 手机号改变
const handleChangePhoneNumber = (e) => {
  let value = e.target.value
  value = value.trim()
  if (value != verificationCodePhone.value) {
    isOkVerificationCode.value = false
    emit('submit', { auth_verification_code: '', isSubmit: false })
  }
}

// 校验验证码
const validateSmsCode = () => {
  let value = props.formData.auth_verification_code
  let { auth_phone } = props.formData
  value = value.trim()
  auth_phone = auth_phone.trim()

  if (auth_phone && auth_phone.length == 11 && value && value.length == 6) {
    requestCheckSmsCode(auth_phone, value)
      .then((res) => {
        verificationCodePhone.value = auth_phone
        isOkVerificationCode.value = true
      })
      .catch((err) => {
        ElMessage.error(err)
      })
  }
}

// 校验规则
const formRules = reactive({
  auth_phone: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    {
      pattern: phoneNumberReg,
      message: '手机号码格式不对，请重新输入',
      trigger: 'blur',
    },
  ],
  auth_loginid: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  auth_verification_code: [
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
    buttonText.value = `重新获取（${times}s）`
    if (times == 1) {
      sendButtonDisabled.value = false
      buttonText.value = `重新获取验证码`
      clearInterval(timer.value)
    }
  }, 1000)
}

// 点击发送验证码
const handleSendCode = (formEl) => {
  if (!formEl) return
  formEl.validateField('auth_phone', async (errorMessage) => {
    if (errorMessage) {
      formEl.scrollToField('auth_phone')
      return false
    } else {
      const { auth_phone } = props.formData

      const data = await requestGetSmsCode(auth_phone)
      ElMessage.success(data.message + '注意查收！')
      updataSendButtonState()
    }
  })
}
// 校验用户
const handleCheckUser = async () => {
  const { auth_loginid, auth_phone } = props.formData
  const res = await requestE410(auth_loginid, auth_phone)
  const { userid } = res.data
  emit('submit', userid)
}
// 点击下一步 先进行表单校验
const handleSubmit = (formEl) => {
  if (!formEl) return
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      if (isOkVerificationCode.value) {
        handleCheckUser()
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
</script>

<style lang="scss" scoped>
.auth-form-phone {
  width: 100%;
  padding: 0 230px;
  padding-top: 30px;
  .send-btn {
    width: 160px;
  }
}

.button-wrapper {
  padding: 60px 0;
  display: flex;
  justify-content: center;
  button {
    display: block;
    width: 160px;
    margin-right: 30px;
    &:last-child {
      margin-right: 0;
    }
  }
}

.account-verification-code {
  position: relative;
  .el-input {
    padding-right: 170px;
  }
  button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 0;
  }
}
</style>
