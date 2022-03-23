<template>
  <div class="custom-file-upload-one yh-upload">
    <el-upload
      :action="uploadUrl"
      list-type="picture-card"
      :class="[isUploadComplete ? 'upload-complete' : '']"
      :on-preview="handleUploadPreview"
      :on-remove="handleUploadRemove"
      :on-success="handleUploadSuccess"
      :on-change="handleUploadChange"
      :before-upload="handleUploadBefore"
      :file-list="fileList"
      :limit="1"
    >
      <div class="img-wrapper">
        <img
          src="~/assets/images/home/registr/bg-upload.png"
          alt=""
          class="bg"
        />
        <img :src="exampleUrl" alt="" class="con" />
        <i class="iconfont iconfont-camera"></i>
      </div>
    </el-upload>
    <el-dialog v-model="showDialog">
      <img width="100%" :src="imageUrl" alt="" />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import config from '@/config'

const props = defineProps({
  // 图片上传示例背景
  exampleUrl: {
    type: String,
    default: () => '',
  },
  fileList: {
    type: Array,
    default: () => [],
  },
  // 限制文件上传类型 如 image application video
  fileType: {
    type: Array,
    default: () => [],
  },
  fileSize: {
    type: Number,
    default: 0,
  },
})
const emit = defineEmits(['success', 'remove'])

// 是否上传完成
const isUploadComplete = ref(false)
// 是否显示预览弹窗
const showDialog = ref(false)
// 图片地址
const imageUrl = ref('')
// 图片上传地址
const uploadUrl = ref(config.requestUrl + '/upload/uploadFile')
// 图片下载地址
const downloadUrl = ref(config.requestUrl + '/upload/downloadFile/')
// 移除上传
const handleUploadRemove = (file, fileList) => {
  isUploadComplete.value = false
  emit('remove', file)
}

// 文件上传前判断文件
const handleUploadBefore = (file, fileList) => {
  let [fileType] = file.type.split('/')
  if (props.fileType.length !== 0) {   
    let res = props.fileType.find((item) => item == fileType)
    
    if (!res) {
      ElMessage.warning(`文件类型不为${props.fileType.join('、')},无法上传`)
      return false
    }
  }
  if (props.fileSize > 0 && props.fileSize < file.size / 1048576) {
    ElMessage.warning(`文件大于${props.fileSize}M,无法上传`)
    return false
  }
}

// 上传成功
const handleUploadSuccess = (response, file, fileList) => {
  console.log(response, '===responseresponse=====')
  const { url, raw } = file
  const { type, size, name } = raw
  const { resultData } = response.data
  const { code, data, message } = resultData
  if (code == '-1') {
    ElMessage.error(message)
    isUploadComplete.value = false
  } else {
    const arr = name.split('.')
    const subfix = '.' + arr[arr.length - 1]
    let fileObj = {
      ...data,
      url: downloadUrl.value + data.objectid,
      type,
      size,
      name,
      chi214: subfix,
    }
    isUploadComplete.value = true
    emit('success', fileObj)
  }
}

// 上传change
const handleUploadChange = (file, fileList) => {}

// 预览
const handleUploadPreview = (file) => {
  const { url } = file
  imageUrl.value = url
  showDialog.value = true
}
</script>

<style lang="scss">
.yh-upload {
  .img-wrapper {
    width: 278px;
    position: relative;
    .con {
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      position: absolute;
      z-index: 1;
      width: 262px;
    }
    .iconfont {
      width: 64px;
      height: 64px;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      position: absolute;
      z-index: 2;
      background-color: var(--color-primary);
      border-radius: 50%;
      overflow: hidden;
      line-height: 64px;
      text-align: center;
      font-size: 40px;
      color: #fff;
      margin: 0;
    }
  }
}
</style>
