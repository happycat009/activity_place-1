<template>
  <div class="real-person-auth">
    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="150px"
      >
        <el-form-item label="负责人姓名：" prop="principal_name">
          <el-input
            type="text"
            v-model="formData.principal_name"
            placeholder="请输入法定代表人/负责人姓名"
          >
            <template #prefix>
              <i class="iconfont iconfont-user_full"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="证件类型：" prop="principal_license_type">
          <el-select
            v-model="formData.principal_license_type"
            placeholder="请选择证件类型"
            class="custom-select"
          >
            <template #prefix>
              <i class="iconfont iconfont-idcard"></i>
            </template>
            <el-option label="居民身份证" value="01"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="负责人证件号：" prop="principal_license_number">
          <el-input
            type="text"
            v-model="formData.principal_license_number"
            placeholder="请输入法定代表人/负责人证件号"
          >
            <template #prefix>
              <i class="iconfont iconfont-idcard"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="证件有效起始日期：" prop="principal_start_date">
          <el-date-picker
            v-model="formData.principal_start_date"
            placeholder="请选择证件有效期开始日期"
            type="date"
            value-format="YYYY-MM-DD"
            :editable="false"
            :clearable="false"
            :disabledDate="disabledBeginDate"
            @change="handleChangeStartData"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="" prop="principal_validity_period">
          <el-radio-group v-model="formData.principal_validity_period">
            <el-radio
              :label="item.aaa102"
              v-for="item in customData.aab159CodeTableList"
              :key="item.aab102"
              >{{ item.aaa103 }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="证件有效截止日期："
          v-if="!timeData.endTimeDisabled"
          prop="principal_end_date"
        >
          <el-date-picker
            v-model="formData.principal_end_date"
            type="date"
            disabled
            :default-value="defaultEndTime"
            placeholder="请选择证件有效期截止日期"
            value-format="YYYY-MM-DD"
            :disabledDate="disabledEndDate"
            :editable="false"
            :clearable="false"
            :shortcuts="customData.shortcuts"
          ></el-date-picker>
        </el-form-item>
      </el-form>
    </div>
    <div class="upload-wrapper">
      <div class="left">
        <div class="name">上传法定代表人证件照 - 头像面：</div>
        <div class="upload">
          <YhUpload
            :exampleUrl="image03"
            :fileList="formData.fileList01"
            @success="handleUploadFileSuccess01"
            @remove="handleUploadFileRemove01"
            :fileType="['image']"
            :fileSize="5"
          ></YhUpload>
        </div>
      </div>
      <div class="right">
        <div class="name">上传法定代表人证件照 - 国徽面：</div>
        <div class="upload">
          <YhUpload
            :exampleUrl="image04"
            :fileList="formData.fileList02"
            @success="handleUploadFileSuccess02"
            @remove="handleUploadFileRemove02"
            :fileType="['image']"
            :fileSize="5"
          ></YhUpload>
        </div>
      </div>
    </div>
    <div class="tip-wrapper">
      <YhTip>
        上传法人注册登记证照原件扫描或照片，照片所有信息需清晰可见，内容真实有效，不得做任何修
        改。照片支持 <em>jpg、jpeg、png、jfif、gif、webp、bmp、svg</em> 格式，
        <em>大小不超过5M</em> 。</YhTip
      >
    </div>
    <div class="button-wrapper">
      <button class="primary reverse" @click="emit('back')">上一步</button>
      <button class="primary" @click="handleSubmit(formRef)">下一步</button>
    </div>
  </div>
</template>

<script setup>
import image03 from '@/assets/images/home/registr/03.png'
import image04 from '@/assets/images/home/registr/04.png'

import YhUpload from '@/components/elementPlusExtend/YhUpload/YhUpload.vue'
import YhTip from '@/components/common/YhTip/YhTip.vue'

import { reactive, ref, onMounted, computed, watch } from 'vue'

import { ElMessage } from 'element-plus'

import { requestGetCollectionData } from '@/service'
import { checkIDCard } from '@/utils/check'

import moment from 'moment'
import state from '@/store/state'

const props = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        principal_name: '',
        principal_license_type: '01',
        principal_license_number: '',
        principal_start_date: '',
        principal_end_date: '',
        principal_validity_period: '',
        fileList01: [],
        fileList02: [],
      }
    },
  },
})

const emit = defineEmits([
  'back',
  'submit',
  'uploadSuccess02',
  'uploadSuccess01',
  'uploadRemove01',
  'uploadRemove02',
])

const defaultEndTime = computed(() => {
  let res = '2023-1-1'
  if (props.formData.principal_start_date) {
    res = moment(props.formData.principal_start_date).add(
      timeData.longerTime,
      'year'
    )
    props.formData.principal_end_date = moment(res).format('YYYY-MM-DD')
    if (timeData.longerTime == 0) {
      props.formData.principal_end_date = ''
    }
  }
  return res
})

const timeData = reactive({
  endTimeDisabled: false,
  // 身份证时长
  longerTime: 5,
})

watch(
  () => {
    return props.formData.principal_validity_period
  },
  (state) => {
    timeData.endTimeDisabled = false
    if (state == '01') {
      timeData.longerTime = 5
    }
    if (state == '02') {
      timeData.longerTime = 10
    }
    if (state == '03') {
      timeData.longerTime = 20
    }
    if (state == '04') {
      timeData.longerTime = 0
      timeData.endTimeDisabled = true
    }
  },
  { deep: true }
)

const formRef = ref(null)

const handleCard = (rule, value, callback) => {
  if (props.formData.principal_license_type == '01') {
    let { status, msg } = checkIDCard(value)
    if (status == '0') {
      callback(new Error(msg))
      return
    }
  }
  callback()
}

const handleEndTimeCheck = (rule, value, callback) => {
  if (timeData.longerTime !== 0) {
    if (!value) {
      callback('请选择证件有效期截至日期')
      return
    }
  }
  callback()
}

const formRules = reactive({
  principal_name: [
    {
      required: true,
      message: '请输入法定代表人/负责人姓名',
      trigger: 'blur',
    },
    {
      min: 2,
      message: '法定代表人/负责人姓名必须2个字符以上',
      trigger: 'blur',
    },
  ],
  principal_license_type: [
    {
      required: true,
      message: '请选择证件类型',
      trigger: 'change',
    },
  ],
  principal_license_number: {
    validator: handleCard,
    trigger: 'blur',
    required: true,
  },
  principal_start_date: [
    {
      required: true,
      message: '请选择证件有效期开始日期',
      trigger: 'blur',
    },
  ],
  principal_end_date: {
    validator: handleEndTimeCheck,
    trigger: 'blur',
    required: true,
  },
  principal_validity_period: [
    {
      required: true,
      message: '请选择证件有效期',
      trigger: 'change',
    },
  ],
})

const disabledEndDate = (time) => {
  let res = true
  let beginTime = props.formData.principal_start_date
  let longer = props.formData.principal_validity_period
  // let longerTime = 5;
  // if (!longer) {
  //   longerTime = 5;
  // }
  // if (longer == "01") {
  //   longerTime = 5;
  // }
  // if (longer == "02") {
  //   longerTime = 10;
  // }
  // if (longer == "03") {
  //   longerTime = 15;
  // }
  // if (longer == "04") {
  //   props.formData.principal_end_date = "";
  // }
  if (beginTime) {
    let newDay = moment(beginTime).add(timeData.longerTime, 'year')
    res = moment(newDay).isSame(time, 'day')
    if (!longer) {
      res = moment(newDay).isBefore(time)
    }
  }
  return !res
}

const disabledBeginDate = (time) => {
  let date = moment()
  let res = moment(date).isBefore(time)
  return res
}

const customData = reactive({
  aab159CodeTableList: [],
  shortcuts: [],
})

const handleChangeStartData = (val) => {}

const getData = async () => {
  const aab159List = await requestGetCollectionData('aab159')
  console.log("aab159List",aab159List)
  customData.aab159CodeTableList = aab159List
  props.formData.principal_validity_period=customData.aab159CodeTableList[0].aaa102
}

const handleUploadFileSuccess01 = (data) => {
  emit('uploadSuccess01', data)
}
const handleUploadFileRemove01 = (data) => {
  emit('uploadRemove01', data)
}
const handleUploadFileSuccess02 = (data) => {
  emit('uploadSuccess02', data)
}
const handleUploadFileRemove02 = (data) => {
  emit('uploadRemove02', data)
}

// 校验文件上传
const handleCheckUpload = () => {
  const {
    principal_name,
    principal_license_type,
    principal_license_number,
    principal_start_date,
    principal_end_date,
    principal_validity_period,
    fileList01,
    fileList02,
  } = props.formData
  if (fileList01.length <= 0) {
    ElMessage.warning('法定代表人证件照头像面还未上传，请上传！')
    return
  }
  if (fileList02.length <= 0) {
    ElMessage.warning('法定代表人证件照国徽面还未上传，请上传！')
    return
  }
  const data = {
    principal_name,
    principal_license_type,
    principal_license_number,
    principal_start_date,
    principal_end_date,
    principal_validity_period,
  }
  emit('submit', data)
}

// 点击下一步 先进行表单校验
const handleSubmit = (formEl) => {
  if (!formEl) return
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      handleCheckUpload()
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

onMounted(() => {
  getData()
})
</script>

<style lang="scss" scoped>
.real-person-auth {
  width: 100%;
  padding: 0 230px;
  padding-top: 30px;
}
.form-wrapper {
  .el-form-item-cells {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
  }
}
.upload-wrapper {
  padding-top: 44px;
  display: flex;
  justify-content: space-between;
  .left,
  .right {
    width: 278px;
    .upload {
      width: 100%;
    }
  }

  .name {
    font-size: 16px;
    font-weight: 700;
    line-height: 40px;
    color: #333333;
    line-height: 1;
    padding-bottom: 20px;
  }
}
.tip-wrapper {
  margin-top: 30px;
  em {
    font-style: normal;
    color: var(--color-primary);
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
</style>
