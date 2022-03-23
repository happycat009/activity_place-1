<template>
  <div class="password-info">
    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="新密码：" prop="password">
          <el-input
            type="password"
            v-model="formData.password"
            placeholder="请输入8-16位数字、字母或符号"
            maxlength="16"
          >
            <template #prefix>
              <i class="iconfont iconfont-password_full"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="确认密码：" prop="password_again">
          <el-input
            type="password"
            v-model="formData.password_again"
            placeholder="请再次输入密码"
            maxlength="16"
          >
            <template #prefix>
              <i class="iconfont iconfont-password_full"></i>
            </template>
          </el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="button-wrapper">
      <button class="primary reverse" type="button" @click="emit('back')">
        上一步
      </button>
      <button class="primary" type="button" @click="handleSubmit(formRef)">
        下一步
      </button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { requestGetSmsCode } from '@/service'
import { ElMessage } from 'element-plus'

const formRef = ref(null)

const emit = defineEmits(['submit', 'back'])

const props = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        password: '',
        password_again: '',
      }
    },
  },
})

// 校验是否输入了相同密码
const validatePassword = (rule, value, callback) => {
  const { password } = props.formData
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== password) {
    callback(new Error('两次输入密码不一致!'))
  } else {
    callback()
  }
}

// 校验规则
const formRules = reactive({
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      message: '请输入8-16个字符，至少1个大写字母，1个小写字母和1个数字。',
      pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/,
      trigger: 'blur',
    },
  ],
  password_again: [
    { required: true, validator: validatePassword, trigger: 'blur' },
  ],
})

// 点击下一步 先进行表单校验
const handleSubmit = (formEl) => {
  if (!formEl) return
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      emit('submit', props.formData)
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
.password-info {
  width: 100%;
  padding: 0 230px;
  padding-top: 30px;
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
</style>
