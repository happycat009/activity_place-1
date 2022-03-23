<template>
  <ApplyCommon :formData="formData" chi031="100081">
    <div class="form-wrapper">
      <el-form ref="formRef" :model="formData" :rules="formRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="单位类型：" prop="c00003">
              <el-select v-model="formData.c00003" placeholder="请选择单位类型">
                <el-option
                        :label="item.aaa103"
                        :value="item.aaa102"
                        v-for="item in customData.c00003CodeTableList"
                        :key="item.aaa102"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col class="space" :span="12">
            <el-form-item label="从业人员期末人数：" prop="chb00f">
              <el-input
                v-model="formData.chb00f"
                placeholder="请输入从业人员期末人数"
                type="number"
                maxlength="20"
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </ApplyCommon>
</template>

<script setup>
import ApplyCommon from '../common/index.vue' // 不要删除
import { ref, reactive, provide, onMounted } from 'vue' // 不要删除
import { useCheckForm, useResetForm, useInputMaxlength } from '@/use' // 不要删除

import { requestGetCollectionData } from '@/service'
import { emailReg } from '@/utils/regular'

const customData = reactive({
  c00003CodeTableList: [],
  aae008CodeTableList: [],
})

// form ref
const formRef = ref(null)

// 表单数据
const formData = reactive({
  // aab001: '', // 证件号码/企业ID 已存在字段无需再次加入
  // aab012: '', // 姓名/企业名称 已存在字段无需再次加入
  // chb00l: '', // 成立时间 已存在字段无需再次加入
  // chb00u: '', // 企业类型 已存在字段无需再次加入
  // chb00z: '', // 注册地址 已存在字段无需再次加入
  // chb00p: '', // 企业法人姓名 已存在字段无需再次加入
  // chb00y: '', // 所属行业 已存在字段无需再次加入
  // chb00s: '', // 联系人姓名 已存在字段无需再次加入
  // aae005: '', // 联系电话 已存在字段无需再次加入
  // chb015: '', // 区县 已存在字段无需再次加入
  // chb017: '', // 乡镇 已存在字段无需再次加入
  // chb018: '', // 村 已存在字段无需再次加入

  c00003: '', // 单位类型
  chb00f: '', // 从业人员期末人数
})


// 表单校验规则
const formRules = reactive({
  chb00f: [
    {
      required: true,
      message: '请输入从业人员期末人数',
      trigger: 'blur',
    },
  ],
  c00003: [
    {
      required: true,
      message: '请选择单位类型',
      trigger: 'blur',
    },
  ]
})

// 获取所有使用的码表
const getCodeTableData = async () => {
  const c00003Res = await requestGetCollectionData('c00003')
  const aae008Res = await requestGetCollectionData('aae008')
  customData.c00003CodeTableList = c00003Res
  customData.aae008CodeTableList = aae008Res
}

onMounted(() => {
  getCodeTableData()
})

// 导出方法供子组件访问 不要修改
provide('checkForm', useCheckForm)
provide('formRef', formRef)
provide('resetForm', useResetForm)
</script>

<style lang="scss" scoped>
.form-wrapper {
  padding-top: 36px;
}
</style>
