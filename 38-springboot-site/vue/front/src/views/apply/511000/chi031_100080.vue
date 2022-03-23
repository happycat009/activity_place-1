<template>
  <ApplyCommon :formData="formData" chi031="100080">
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

        <el-row>
          <el-col :span="12">
            <el-form-item label="企业统计报表人员姓名：" prop="c00005">
              <el-input
                v-model="formData.c00005"
                placeholder="请输入企业统计报表人员姓名"
                type="text"
                maxlength="4"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col class="space" :span="12">
            <el-form-item label="企业统计报表人员开户行名称：" prop="c00006">
              <el-input
                v-model="formData.c00006"
                placeholder="企业统计报表人员开户行名称"
                type="text"
                maxlength="10"
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="企业统计报表人员开户行账号：" prop="c00007">
              <el-input
                v-model="formData.c00007"
                placeholder="请输入统计报表人员开户行账号"
                type="number"
                maxlength="10"
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
import {ref, reactive, provide, onMounted} from 'vue' // 不要删除
import { useCheckForm, useResetForm, useInputMaxlength } from '@/use' // 不要删除
import { requestGetCollectionData } from '@/service'
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

  chb00f: '', // 从业人员期末人数
  c00003: '', // 单位类型
  c00005: '', // 企业统计报表人员姓名
  c00006: '', // 统计报表人员开户行类别
  c00007: '', // 统计报表人员开户行账号
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
      message: '请输入单位类型',
      trigger: 'blur',
    },
  ],
  c00005: [
    {
      required: true,
      message: '请输入企业统计报表人员姓名',
      trigger: 'blur',
    },
  ],
  c00006: [
    {
      required: true,
      message: '请输入统计报表人员开户行名称',
      trigger: 'blur',
    },
  ],
  c00007: [
    {
      required: true,
      message: '请输入统计报表人员开户行账号',
      trigger: 'blur',
    },
    {
      pattern: /^([1-9]{1})(\d{14}|\d{18})$/,
      message: "请输入正确的开户行账号",
      trigger: "blur",
    },
  ],
})

const customData = reactive({
  c00003CodeTableList: [],
})

// 获取所有使用的码表
const getCodeTableData = async () => {
  const c00003Res = await requestGetCollectionData('c00003')
  customData.c00003CodeTableList = c00003Res
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
