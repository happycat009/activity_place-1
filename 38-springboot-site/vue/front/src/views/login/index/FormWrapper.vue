<template>
  <div class="form-wrapper">
    <div class="left">
      <img src="~assets/images/login/form-bg.png" alt="" />
    </div>
    <div class="right">
      <div class="form">
        <div class="title">{{ loginTypeText }}</div>
        <div class="form-content">
          <!-- 动态码登录 -->
          <template v-if="isLoginTypePhone">
            <div class="input-wrapper">
              <input
                type="text"
                placeholder="请输入手机号"
                maxlength="11"
                v-model="accountInfo.phoneNumber"
                @keyup="handleKeyUpPhoneNumber"
                ref="phoneNumberRef"
              />
              <div class="iconfont iconfont-user"></div>
            </div>
            <div class="input-wrapper">
              <input
                type="text"
                placeholder="请输入验证码"
                v-model="accountInfo.verificationCode"
                @keyup="handleKeyUpVerificationCode"
                maxlength="6"
                ref="verificationCodeRef"
              />
              <div class="iconfont iconfont-password"></div>
              <button
                class="primary"
                type="button"
                :disabled="sendBtnDisabled"
                @click="getSmsCode"
              >
                {{ sendBtnText }}
              </button>
            </div>
          </template>
          <!-- 账号登录 -->
          <template v-else>
            <div class="input-wrapper">
              <input
                type="text"
                placeholder="请输入经办用户身份证号码或手机号"
                v-model="accountInfo.loginid"
                @keyup="handleKeyUpLoginid"
                ref="loginidRef"
              />
              <div class="iconfont iconfont-user"></div>
            </div>
            <div class="input-wrapper">
              <input
                type="password"
                placeholder="请输入用户密码"
                v-model="accountInfo.password"
                @keyup="handleKeyUpPassword"
                ref="passwordRef"
              />
              <div class="iconfont iconfont-password"></div>
            </div>
          </template>
        </div>
        <div class="tool-wrapper">
          <button @click="handleChangeLogin">{{ loginChangeTypeText }}</button>
          <button @click="handleJumpRoute">忘记密码？</button>
        </div>
        <div class="button-wrapper">
          <button @click="handleCheck" class="button-login">登录</button>
        </div>
      </div>
      <div class="image-wrapper">
        <img class="qr" :src="imageQr" alt="" @click="emit('change', true)" />
      </div>
    </div>
  </div>
</template>

<script setup>
import imageQr from '@/assets/images/login/qr.png'

import { ref, reactive, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

import config from '@/config'
import { phoneNumberReg } from '@/utils/regular'
import {
  M_UPDATE_USER_INFO,
  M_UPDATE_CORPORATE_INFO,
  M_UPDATE_USER_LIST,
} from '@/store/constants'
import {
  requestB404,
  requestLogin,
  requestA402,
  requestGetSmsCode,
  requestCheckSmsCode,
} from '@/service'

const store = useStore()
const router = useRouter()
const route = useRoute()

const emit = defineEmits(['change'])
const props = defineProps({
  isScanLogin: {
    type: Boolean,
    default: () => false,
  },
})

const passwordRef = ref(null)
const verificationCodeRef = ref(null)
const phoneNumberRef = ref(null)
const loginidRef = ref(null)

const sendBtnText = ref('发送验证码')
const sendBtnDisabled = ref(false)

// 登录类型 是否手机号登录
const isLoginTypePhone = ref(false)
// 账号信息
const accountInfo = reactive({
  loginid: '',
  password: '',
  phoneNumber: '',
  verificationCode: '',
})

// 数据
const customData = reactive({
  // 账号信息
})

// 登录类型文本
const loginTypeText = computed(() => {
  let str = ''
  if (isLoginTypePhone.value) {
    str = '手机登录'
  } else {
    str = '账号登录'
  }
  return str
})

// 切换登录类型文本
const loginChangeTypeText = computed(() => {
  let str = ''
  if (isLoginTypePhone.value) {
    str = '账号密码登录'
  } else {
    str = '手机动态码登录'
  }
  return str
})

// 更新发放验证码按钮状态
const updateSendBtnState = () => {
  let num = 60
  sendBtnDisabled.value = true
  sendBtnText.value = `重新获取（${num}s）`
  let timer = setInterval(() => {
    num--
    sendBtnText.value = `重新获取（${num}s）`
    if (num <= 0) {
      sendBtnDisabled.value = true
      sendBtnText.value = `重新获取验证码`
      clearInterval(timer)
    }
  }, 1000)
}

// 校验手机号是否正确
const checkPhoneNumber = () => {
  let result = true
  const { phoneNumber } = accountInfo
  if (!phoneNumber) {
    ElMessage.warning('请输入手机号！')
    phoneNumberRef.value.focus()
    result = false
  } else {
    if (!phoneNumberReg.test(phoneNumber)) {
      ElMessage.warning('请输入正确的11位手机号！')
      phoneNumberRef.value.focus()
      result = false
    }
  }
  return result
}

// 校验验证码是否正确
const checkVerificationCode = () => {
  let result = true
  const { verificationCode } = accountInfo
  if (!verificationCode) {
    ElMessage.warning('请输验证码！')
    verificationCodeRef.value.focus()
    result = false
  } else {
    if (verificationCode.length != 6) {
      ElMessage.warning('请输入6位数字的验证码！')
      verificationCodeRef.value.focus()
      result = false
    }
  }
  return result
}

// 发送验证码
const getSmsCode = () => {
  const isOkPhoneNumber = checkPhoneNumber()
  if (isOkPhoneNumber) {
    const { phoneNumber } = accountInfo
    requestGetSmsCode(phoneNumber)
      .then((res) => {
        ElMessage.success('验证码获取成功！')
        verificationCodeRef.value.focus()
        updateSendBtnState()
      })
      .catch((err) => {
        // ElMessage.error('验证码获取失败！')
      })
  }
}

// 服务器校验验证码是否正确
const checkSmsCode = () => {
  const isOk = checkVerificationCode()
  if (isOk) {
    const { phoneNumber, verificationCode } = accountInfo
    requestCheckSmsCode(phoneNumber, verificationCode)
      .then((res) => {
        console.log(res, 123)
        if (res && res.flag == '200') {
          const { data, token } = res.userInfo
          if (data) {
            handleLoginSuccess({
              loginid: data.loginid,
              name: data.name,
              sex: data.sex,
              tel: data.tel,
              token: token,
              user_id: data.userid,
            })
          } else {
            ElMessage.error('登录用户暂无数据！')
          }
        } else {
          ElMessage.error(res.message)
        }
      })
      .catch((err) => {
        // ElMessage.error('登录失败！')
      })
  }
}

// 登录成功
const handleLoginSuccess = async (res) => {
  const { redirect } = route.query
  ElMessage.success('登录成功，正在获取与您相关的企业信息！')

  const A402Data = await requestA402(res.user_id)
  const { positionList } = A402Data.data
  let positionInfo = null
  let corporateInfo = null
  if (positionList.length > 0) {
    positionInfo = positionList[0]
    const B404Data = await requestB404(positionInfo.orgid)
    const { row } = B404Data.data.ab01Map.ab01Domains
    corporateInfo = row
    res = {
      ...res,
      positionInfo,
      positionList,
      orgid: positionInfo.orgid,
    }
  }

  store.commit(M_UPDATE_USER_INFO, res)
  store.commit(M_UPDATE_CORPORATE_INFO, corporateInfo)
  store.commit(M_UPDATE_USER_LIST, {
    cpb10d: res.loginid, // 经办用户身份证号码
    aae012: res.name, // 经办用户姓名
    aab301: config.areaCode, // 地市编码
    loginid: res.loginid,
  })
  // 登录后返回原页面
  if (redirect) {
    router.push(redirect)
  } else {
    // 用于判断登录后展示提示框
    sessionStorage.setItem('showLoginReminder', 'true')
    router.push('/home')
  }
}

// 登录
const handleLogin = async () => {
  const { loginid, password, phoneNumber, verificationCode } = accountInfo
  const data = await requestLogin(loginid, password)
  const resultData = data.data
  if (resultData.code == '1') {
    handleLoginSuccess({
      loginid: resultData.loginid,
      name: resultData.name,
      sex: resultData.sex,
      tel: resultData.tel,
      token: data.token,
      user_id: resultData.userid,
    })
  } else {
    ElMessage.error('账号或密码错误！！！')
  }
}

// 校验后登录
const handleCheck = () => {
  const { loginid, password, phoneNumber, verificationCode } = accountInfo
  // 手机号登录
  if (isLoginTypePhone.value) {
    const isOk = checkPhoneNumber()
    isOk && checkSmsCode()
  } else {
    // 账号登录
    if (!loginid) {
      ElMessage.warning('请输入账号！')
      loginidRef.value.focus()
      return
    }
    if (!password) {
      ElMessage.warning('请输入密码！')
      passwordRef.value.focus()
      return
    }
    handleLogin()
  }
}

// 切换登录模式
const handleChangeLogin = () => {
  isLoginTypePhone.value = !isLoginTypePhone.value
}

// 账号输入完成 enter 键操作
const handleKeyUpLoginid = (e) => {
  if (e.keyCode === 13) {
    passwordRef.value.focus()
  }
}

// 密码输入完成 enter 键操作
const handleKeyUpPassword = (e) => {
  if (e.keyCode === 13) {
    handleCheck()
  }
}

// 手机号输入完成 enter 键操作
const handleKeyUpPhoneNumber = (e) => {
  if (e.keyCode === 13) {
    getSmsCode()
  }
}

// 验证码输入完成 enter 键操作
const handleKeyUpVerificationCode = (e) => {
  if (e.keyCode === 13) {
    const isOk = checkPhoneNumber()
    isOk && checkSmsCode()
  }
}

const handleJumpRoute = () => {
  router.push('/resetpassword')
}
</script>

<style lang="scss" scoped>
.form-wrapper {
  width: 1260px;
  width: 65.625%;
  background-color: rgba(#fff, 0.93);
  box-shadow: 0px 30px 28px 4px rgba(0, 15, 217, 0.17);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 7.8125% 0;
  padding-left: 5.729167%;
  padding-right: 7.8125%;
  margin: auto;
  position: relative;
  .left {
    width: 494px;
    width: 49.4%;
    // background-color: #f00;
  }
  .right {
    width: 376px;
    width: 37.6%;
    // background-color: #f0f;
  }
  @media screen and (max-width: 1500px) {
    padding: 4%;
    .right {
      width: 44%;
    }
  }
}
.image-wrapper {
  position: absolute;
  width: 7.936508%;
  top: 24px;
  right: 24px;
  img {
    width: 100%;
    cursor: pointer;
  }
  @media screen and (max-width: 1500px) {
    top: 12px;
    right: 12px;
  }
}

.form {
  width: 100%;
  position: relative;

  .title {
    background-image: linear-gradient(180deg, #008aff, #0042ff);
    background-clip: text;
    -webkit-background-clip: text;
    color: transparent;
    font-weight: 700;
    font-size: 36px;
    line-height: 1;
    text-align: center;
  }

  .form-content {
    width: 100%;
    padding-top: 50px;

    .input-wrapper {
      position: relative;
      margin-bottom: 20px;
      display: flex;
      align-items: center;
      .iconfont {
        position: absolute;
        font-size: 22px;
        color: #333333;
        line-height: 1;
        top: 50%;
        left: 16px;
        transform: translateY(-50%);
        transition: all 0.3s;
      }
      input {
        flex: 1;
        overflow: hidden;
        height: 48px;
        background-color: #fff;
        border-radius: 4px;
        border: 0;
        border: 2px solid #fff;
        padding-left: 44px;
        font-size: 16px;
        color: #333;
        outline: 0;
        transition: all 0.3s;
        &:focus {
          border-color: $primary;
          ~ .iconfont {
            color: $primary;
          }
        }
      }
      button {
        width: 120px;
        height: 48px;
        line-height: 46px;
        margin-left: 10px;
      }
    }
  }
  .tool-wrapper {
    display: flex;
    align-items: center;
    justify-content: space-between;
    button {
      color: $primary;
      font-size: 16px;
      line-height: 1;
      padding: 15px 0;
      cursor: pointer;
      transition: all 0.3s;
      &:hover {
        opacity: 0.8;
      }
    }
  }
  .button-login {
    width: 100%;
    height: 50px;
    background-color: $primary;
    border-radius: 6px;
    border: solid 1px $primary;
    color: #fff;
    margin-top: 45px;
  }
  @media screen and (max-width: 1500px) {
    .title {
      font-size: 32px;
    }
    .form-content {
      padding-top: 30px;
    }
    .button-login {
      margin-top: 20px;
    }
  }
}
</style>
