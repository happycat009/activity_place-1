<template>
  <div class="legal-person-info">
    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="170px"
      >
        <el-form-item label="企业名称：" prop="legal_person_name">
          <el-input
            type="text"
            v-model="formData.legal_person_name"
            placeholder="请输入企业名称"
            maxlength="18"
          >
            <template #prefix>
              <i class="iconfont iconfont-unit"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="企业统一社会信用代码：" prop="legal_person_code">
          <el-input
            type="text"
            v-model="formData.legal_person_code"
            maxlength="18"
            placeholder="请输入企业统一社会信用代码"
          >
            <template #prefix>
              <i class="iconfont iconfont-idcard"></i>
            </template>
          </el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="select-address">
      <YhSelectAddress
        label="企业注册地址："
        label-width="170px"
        required
        v-model="formData.legal_person_address"
      ></YhSelectAddress>
    </div>
    <div class="upload-wrapper">
      <div class="left">
        <div class="name">上传法人证件（如：营业执照）：</div>
        <div class="upload">
          <YhUpload
            :exampleUrl="imageEgUrl"
            :fileList="formData.fileList"
            @success="handleUploadFileSuccess"
            @remove="handleUploadFileRemove"
            :fileType="['image']"
            :fileSize="5"
          ></YhUpload>
        </div>
      </div>
      <div class="right">
        <div class="name">法人证件样例：</div>
        <div class="img-wrapper">
          <img
            src="~/assets/images/home/registr/bg-upload.png"
            alt=""
            class="bg"
          />
          <img class="con" src="~/assets/images/home/registr/01.png" alt="" />
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
      <button class="primary" @click="handleSubmit(formRef)">下一步</button>
    </div>
  </div>
</template>

<script setup>
import imageEgUrl from '@/assets/images/home/registr/02.png'

import YhUpload from '@/components/elementPlusExtend/YhUpload/YhUpload.vue'
import YhSelectAddress from '@/components/elementPlusExtend/YhSelectAddress/YhSelectAddress.vue'
import YhTip from '@/components/common/YhTip/YhTip.vue'

import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'

const formRef = ref(null)
const emit = defineEmits(['submit', 'uploadSuccess', 'uploadRemove'])

const prpos = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        legal_person_name: '',
        legal_person_code: '',
        legal_person_address: '',
        fileList: [],
      }
    },
  },
})

// 表单校验规则
const formRules = reactive({
  legal_person_name: [
    {
      required: true,
      message: '请输入企业名称！',
      trigger: 'blur',
    },
    {
      min: 6,
      message: '企业名称必须6个字符以上！',
      trigger: 'blur',
    },
  ],
  legal_person_code: [
    {
      required: true,
      message: '请输入企业统一社会信用代码！',
      trigger: 'blur',
    },
    {
      message: '请输入正确的18位统一社会信用代码！',
      trigger: 'blur',
      pattern:
        /^([0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}|[1-9]\d{14})$/,
    },
  ],
})

// 校验文件是否上传成功
const handleCheckUpload = () => {
  const {
    legal_person_name,
    legal_person_code,
    legal_person_address,
    fileList,
  } = prpos.formData
  if (!legal_person_address) {
    ElMessage.warning('请选择企业注册地址！')
    return
  }
  if (fileList.length <= 0) {
    ElMessage.warning('营业执照还未上传，请上传！')
    return
  }

  const data = {
    legal_person_name,
    legal_person_code,
    legal_person_address,
  }
  emit('submit', data)
}

// 移除图片
const handleUploadFileRemove = (data) => {
  emit('uploadRemove', data)
}

// 上传成功
const handleUploadFileSuccess = (data) => {
  emit('uploadSuccess', data)
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
</script>

<style lang="scss" scoped>
.legal-person-info {
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
  .left {
    width: 278px;
    .upload {
      width: 100%;
    }
  }
  .right {
    width: 278px;
    .img-wrapper {
      position: relative;
      .con {
        width: 262px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      }
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
  button {
    display: block;
    width: 144px;
    margin: 0 auto;
  }
}
</style>
