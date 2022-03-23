<template>
  <div class="whistleblower">
    <YhTitle title="举报人信息" type="03"></YhTitle>
    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <div class="el-form-item-cells">
          <div class="el-form-item-cell">
            <el-form-item label="姓名：" prop="aac003">
              <el-input
                v-model="formData.aac003"
                placeholder="请输入姓名"
                style="width: 270px"
              />
            </el-form-item>
          </div>

          <div class="el-form-item-cell">
            <el-form-item label="身份证号：" prop="aac002">
              <el-input
                v-model="formData.aac002"
                placeholder="请输入身份证号"
                style="width: 270px"
                maxlength="18"
              />
            </el-form-item>
          </div>

          <div class="el-form-item-cell">
            <el-form-item label="联系电话：" prop="aae005">
              <el-input
                v-model="formData.aae005"
                placeholder="请输入联系电话"
                style="width: 270px"
                maxlength="11"
              />
            </el-form-item>
          </div>

          <!-- <div class="el-form-item-cell">
            <el-form-item label="户籍地址：" prop="aae006">
              <el-input
                v-model="formData.aae006"
                placeholder="请输入户籍地址"
                style="width: 270px"
              />
            </el-form-item>
          </div> -->

          <!-- <div class="el-form-item-cell">
            <el-form-item label="所在区县：" prop="chb015">
              <el-select
                v-model="formData.aae006"
                placeholder="请所在区县"
                style="width: 270px"
              >
                <template
                  v-for="(item, index) in props.codeMap.chb015"
                  :key="index"
                >
                  <el-option
                    :label="item.aaa103"
                    :value="item.aaa102"
                  ></el-option>
                </template>
              </el-select>
            </el-form-item>
          </div> -->
        </div>
      </el-form>
    </div>
  </div>
</template>
<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'

import { ref, reactive, onMounted } from 'vue'
import { phoneNumberReg } from '@/utils/regular'
import { useCheckIdCard } from '@/use'

const formRef = ref(null)

const props = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        aac002: '',
        aac003: '',
        aae005: '',
        // chb015: "",
        aae006: '',
      }
    },
  },
  codeMap: {
    type: [Object],
    default: () => {},
  },
})

const formRules = reactive({
  aac002: [
    { trigger: 'blur', required: true, message: '请输入身份证号' },
    { validator: useCheckIdCard, trigger: 'blur' },
  ],
  aac003: {
    required: true,
    message: '请输入姓名',
    trigger: 'blur',
  },
  aae005: [
    {
      required: true,
      trigger: 'blur',
      message: '请输入手机号码',
    },
    {
      pattern: phoneNumberReg,
      trigger: 'blur',
      message: '请输入正确的电话号码',
    },
  ],
  aae006: {
    required: true,
    message: '请选择所在区县',
    trigger: 'change',
  },
})

// 点击下一步 先进行表单校验
const handleSubmit = (formEl) => {
  if (!formEl) {
    formEl = formRef.value
  }
  let tag = false
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      tag = true
    } else {
      const formErrPropsArr = []
      for (let item in formErrObj) {
        formErrPropsArr.push(item)
      }
      const firstErrProps = formErrPropsArr[0]
      formEl.scrollToField(firstErrProps)
    }
  })
  return tag
}
onMounted(() => {})
defineExpose({
  handleSubmit,
})
</script>
<style lang="scss" scoped>
.form-wrapper {
  margin-top: 10px;
  .el-form-item-cells {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
  }
}
</style>
