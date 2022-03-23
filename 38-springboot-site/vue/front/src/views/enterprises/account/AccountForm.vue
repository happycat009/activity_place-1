<template>
  <YhPopup
    :modelValue="show"
    :popupName="popupTitle"
    :maskClose="false"
    @update:modelValue="emit('update:show', $event)"
  >
    <div class="form-wrapper">
      <el-form ref="formRef" :model="formData" :rules="formRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="账号：" prop="loginid">
              <el-input
                v-model="formData.loginid"
                placeholder="请输入身份证号"
                type="text"
                :disabled="formData.isEdit"
                maxlength="18"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" class="space">
            <el-form-item label="姓名：" prop="name">
              <el-input
                v-model="formData.name"
                placeholder="请输入姓名"
                :disabled="formData.isEdit"
                type="text"
                maxlength="4"
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系方式：" prop="tel">
              <el-input
                v-model="formData.tel"
                placeholder="请输入手机号码"
                :disabled="formData.isEdit"
                type="text"
                maxlength="11"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" class="space">
            <el-form-item label="密码：" prop="password">
              <el-input
                v-model="formData.password"
                placeholder="请输入密码"
                type="text"
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="button-wrapper">
        <button class="primary" @click="handleCheck(formRef)">确定</button>
      </div>
    </div>
  </YhPopup>
</template>

<script setup>
import YhPopup from '@/components/common/YhPopup/YhPopup.vue'

import { ref, computed, watch, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

import { useCheckIdCard } from '@/use'
import { phoneNumberReg } from '@/utils/regular'
import { requestB103, requestB303, requestA404 } from '@/service'

const formRef = ref(null)
const emit = defineEmits(['update:show', 'addSuccess'])
const props = defineProps({
  show: {
    type: Boolean,
    required: true,
  },
  popupTitle: {
    type: String,
    default: () => '',
  },
  formData: {
    type: Object,
    default: () => {
      return {}
    },
  },
})

// 检测是否有相同的登录id
const checkSameIdCard = () => {}

const formRules = reactive({
  loginid: [
    {
      required: true,
      message: '请输入身份证号',
      trigger: 'blur',
    },
    { validator: useCheckIdCard, trigger: 'blur' },
  ],
  name: [
    {
      required: true,
      message: '请输入姓名',
      trigger: 'blur',
    },
  ],
  tel: [
    {
      required: true,
      message: '请输入手机号',
      trigger: 'blur',
    },
    {
      pattern: phoneNumberReg,
      trigger: 'blur',
      message: '请输入11位正确的手机号',
    },
  ],
  password: [
    {
      required: true,
      message: '请输入密码',
      trigger: 'blur',
    },
    {
      message: '请输入8-16个字符，至少1个大写字母，1个小写字母和1个数字。',
      pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/,
      trigger: 'blur',
    },
  ],
})

// 改变账号信息
const handleChangeAccount = () => {
  const { userid, orgid, password, loginid, name, tel, isadmin, isEdit } =
    props.formData
  if (isEdit) {
    requestB303(userid, loginid, password).then((res) => {
      ElMessage.success('账号重置密码成功')
      emit('update:show', false)
    })
  } else {
    requestA404(loginid, tel).then((res) => {
      requestB103(orgid, password, loginid, name, tel, isadmin).then((res) => {
        ElMessage.success('账号添加成功')
        emit('update:show', false)
        emit('addSuccess')
      })
    })
  }
}

// 检测输入
const handleCheck = (formEl) => {
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      handleChangeAccount()
    } else {
      const formErrPropsArr = []
      for (let item in formErrObj) {
        formErrPropsArr.push(item)
      }
      const firstErrProps = formErrPropsArr[0]
      formEl.scrollToField(firstErrProps)
    }
  })
}
</script>

<style lang="scss" scoped>
.form-wrapper {
  width: 100%;
  padding: 30px;
  .button-wrapper {
    display: flex;
    justify-content: center;
    button {
      width: 160px;
    }
  }
}
</style>
