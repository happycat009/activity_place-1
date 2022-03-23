<template>
  <div class="agent-info">
    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="124px"
      >
        <el-form-item label="经办人姓名：" prop="agent_name">
          <el-input
            type="text"
            v-model="formData.agent_name"
            placeholder="请输入经办人姓名"
          >
            <template #prefix>
              <i class="iconfont iconfont-user"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="经办人身份证号：" prop="agent_code">
          <el-input
            type="text"
            v-model="formData.agent_code"
            placeholder="请输入经办人身份证号"
          >
            <template #prefix>
              <i class="iconfont iconfont-user"></i>
            </template>
          </el-input>
        </el-form-item>
      </el-form>
    </div>

    <div class="button-wrapper">
      <button class="primary reverse" @click="emit('back')">上一步</button>
      <button class="primary" @click="handleSubmit(formRef)">下一步</button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'

const formRef = ref(null)

const emit = defineEmits(['submit', 'back'])
const prpos = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        agent_name: '',
        agent_code: '',
      }
    },
  },
})

const formRules = reactive({
  agent_name: [
    {
      required: true,
      message: '请输入请输入经办人姓名',
      trigger: 'blur',
    },
  ],
  agent_code: [
    {
      required: true,
      message: '请输入经办人身份证号',
      trigger: 'blur',
    },
    {
      message: '请输入经办人正确的身份证号',
      trigger: 'blur',
      pattern:
        /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/,
    },
  ],
})

// 检测
const handleCheck = () => {
  const { agent_name, agent_code } = prpos.formData
  const data = {
    agent_name,
    agent_code,
  }
  emit('submit', data)
}

// 点击下一步 先进行表单校验
const handleSubmit = (formEl) => {
  if (!formEl) return
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      handleCheck()
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
.agent-info {
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
    width: 144px;
    margin-right: 30px;
    &:last-child {
      margin-right: 0;
    }
  }
}
</style>
