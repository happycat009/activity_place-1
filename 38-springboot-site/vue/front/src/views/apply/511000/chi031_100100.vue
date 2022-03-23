<template>
  <ApplyCommon :formData="formData" chi031="100100">
    <div class="form-wrapper">
      <el-form ref="formRef" :model="formData" :rules="formRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="企业主要产品：" prop="chban3">
              <el-input
                      v-model="formData.chban3"
                      placeholder="请输入企业主要产品"
                      type="text"
                      maxlength="30"
              /> </el-form-item>
          </el-col>
          <el-col class="space" :span="12">
            <el-form-item label="生产规模及应用行业或领域：" prop="chban4">
              <el-input
                      v-model="formData.chban4"
                      placeholder="请输入生产规模及应用行业或领域"
                      type="text"
                      maxlength="30"
              /> </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="2021年1-3月工业总产值（亿元）：" prop="chban5">
              <el-input
                      v-model="formData.chban5"
                      placeholder="请输入2021年1-3月工业总产值（亿元）"
                      type="number"
                      maxlength="20"
              /> </el-form-item
            ></el-col>
          <el-col :span="12" class="space">
            <el-form-item label="工业总产值增速：" prop="chban6">
              <el-input
                      v-model="formData.chban6"
                      placeholder="请输入工业总产值增速"
                      type="number"
                      maxlength="10"
              /> </el-form-item
            ></el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="2020年入库税金（万元）：" prop="chban7">
              <el-input
                      v-model="formData.chban7"
                      placeholder="请输入2020年入库税金（万元）"
                      type="number"
                      maxlength="20"
              /> </el-form-item
            ></el-col>
          <el-col :span="12" class="space">
            <el-form-item label="入库税金增速（%）：" prop="chban8">
              <el-input
                      v-model="formData.chban8"
                      placeholder="请输入入库税金增速（%）"
                      type="number"
                      maxlength="10"
              /> </el-form-item
            ></el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="流动资金贷款总额(万元)：" prop="chban1">
              <el-input
                      v-model="formData.chban1"
                      placeholder="请输入流动资金贷款总额(万元)"
                      type="number"
                      maxlength="20"
              /> </el-form-item
            ></el-col>
          <el-col :span="12" class="space">
            <el-form-item label="贷款银行机构：" prop="chban9">
              <el-input
                      v-model="formData.chban9"
                      placeholder="请输入贷款银行机构"
                      type="text"
                      maxlength="10"
              /> </el-form-item
            ></el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="申报补助金额（万元）：" prop="chbana">
              <el-input
                      v-model="formData.chbana"
                      placeholder="请输入申报补助金额（万元）"
                      type="number"
                      maxlength="20"
              /> </el-form-item
            ></el-col>
        </el-row>
      </el-form>
    </div>
  </ApplyCommon>
</template>

<script setup>
import ApplyCommon from '../common/index.vue' // 不要删除
import { ref, reactive, provide } from 'vue' // 不要删除
import { useCheckForm, useResetForm, useInputMaxlength } from '@/use' // 不要删除

import { emailReg } from '@/utils/regular'
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

  chban3: '', // 企业主要产品
  chban4: '', // 生产规模及应用行业或领域
  chban5: '', // 2021年1-3月工业总产值（亿元）
  chban6: '', // 工业总产值增速
  chban7: '', // 2020年入库税金（万元）
  chban8: '', // 入库税金增速
  chban1: '', // 流动资金贷款总额(单位：万元)
  chban9: '', // 贷款银行机构
  chbana: '', // 申报补助金额（万元）
})

// 表单校验规则
const formRules = reactive({
  chban3: [
    {
      required: true,
      message: '请输入企业主要产品',
      trigger: 'blur',
    },
  ],
  chban4: [
    {
      required: true,
      message: '请输入生产规模及应用行业或领域',
      trigger: 'blur',
    },
  ],
  chban5: [
    {
      required: true,
      message: '请输入2021年1-3月工业总产值（亿元）',
      trigger: 'blur',
    },
  ],
  chban6: [
    {
      required: true,
      message: '请输入工业总产值增速',
      trigger: 'blur',
    },
  ],
  chban7: [
    {
      required: true,
      message: '请输入2020年入库税金（万元）',
      trigger: 'blur',
    },
  ],
  chban8: [
    {
      required: true,
      message: '请输入入库税金增速（%）',
      trigger: 'blur',
    },
  ],
  chban1: [
    {
      required: true,
      message: '请输入流动资金贷款总额(万元)',
      trigger: 'blur',
    },
  ],
  chban9: [
    {
      required: true,
      message: '请输入贷款银行机构',
      trigger: 'blur',
    },
  ],
  chbana: [
    {
      required: true,
      message: '请输入申报补助金额（万元）',
      trigger: 'blur',
    },
  ],
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
