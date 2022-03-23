<template>
  <YhPopup
    :modelValue="modelValue"
    @update:modelValue="emit('update:modelValue', $event)"
    popupName="材料上传"
    :maskClose="false"
  >
    <div class="popup-content">
      <div class="content">
        <el-collapse v-model="activeName" accordion>
          <template v-for="item in fileList" :key="item.che092">
            <el-collapse-item
              :title="
                item.che091 + '(' + item.images.length + '/' + item.num + ')'
              "
              :name="item.che092"
            >
              <ul>
                <template
                  v-for="(itemImg, indexImg) in item.images"
                  :key="itemImg.objectid"
                >
                  <li>
                    <img
                      :data-attr="itemImg.objectid"
                      :src="getFileImage(itemImg.chi214,itemImg.path)"
                      alt=""
                      @click="handlePreviewImage(indexImg)"
                    />
                    <i
                      class="iconfont iconfont-close-fill"
                      @click.stop="handleDelete(item, itemImg)"
                    ></i>
                  </li>
                </template>

                <li class="add" v-if="item.images.length < item.num">
                  <label :for="'upload_file_' + item.che092">
                    <el-icon class="upload-icon"><plus /></el-icon>
                    <input
                      type="file"
                      @change="handleFileChange($event, item)"
                      class="upload-file-input"
                      :id="'upload_file_' + item.che092"
                      name="upload_file"
                      accept
                    />
                  </label>
                </li>
              </ul>
            </el-collapse-item>
          </template>
        </el-collapse>
      </div>
    </div>
  </YhPopup>
</template>

<script setup>
import YhPopup from '@/components/common/YhPopup/YhPopup.vue'
import imageZip from './images/zip.png'
import imageWord from './images/word.png'
import imageExcel from './images/excel.png'
import imagePdf from './images/pdf.png'


import { ref, getCurrentInstance } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

import { requestUploadFileByBase64 } from '@/service'
import config from '@/config'
import { request,downloadFile } from '@/service/axios'

const { appContext } = getCurrentInstance()

const props = defineProps({
  activeName: {
    type: String,
    default: () => '',
  },
  modelValue: {
    type: Boolean,
    default: () => false,
  },
  fileList: {
    type: Array,
    default: () => [],
  },
})

// 图片下载地址
const downloadUrl = config.requestUrl + '/upload/downloadFile/'

const emit = defineEmits(['update:modelValue', 'change'])

// 预览图片
const handlePreviewImage = (index) => {
  const data = props.fileList
  const activeName = props.activeName

  let targetObj = null

  data.map((item) => {
    if (item.che092 == activeName) {
      targetObj = { ...item }
    }
  })

  let srcList = []
  targetObj.images.map((item) => {
    if (item.path && (
        item.chi214 == '.png' ||
        item.chi214 == '.gif' ||
        item.chi214 == '.jpeg' ||
        item.chi214 == '.jpg'
    )) {
      srcList.push(item.path)
    }else{
       downloadFile(item.objectid,'0')
      
    }
  })
  if (srcList.length > 0) {
    appContext.config.globalProperties.$viewerApi({
      images: srcList,
    })
  }
}

// 文件上传至服务器
const uploadFileToService = async (filePath, fileData,fileName) => {
  const data = await requestUploadFileByBase64(filePath,fileName)
  const { bucketid, objectid, objectkey } = data.data
  const { images } = fileData
  images.push({
    path: downloadUrl + objectid,
    chi214: fileData.chi214,
    bucketid,
    objectid,
    objectkey,
  })
  emit('change', fileData)
}

// 文件选择完成
const handleFileChange = (event, data) => {
  let fileTypeListStr = data.che09d
  if(!fileTypeListStr) {
    ElMessage.error('没有明确的文件类型！')
    return
  }
  let fileTypeList = fileTypeListStr.toLowerCase().split(",")
  const files = event.target.files
  const file = files[0]
  const type = file.name.split('.')[1]
  // 上传文件的数据格式在规定的格式中
  if (fileTypeList.indexOf(type.toLowerCase()) >= 0 ) {
    data.chi214 = '.' + type
    const fileReader = new FileReader()
    fileReader.readAsDataURL(file)
    fileReader.onload = () => {
      const base64 = fileReader.result
      uploadFileToService(base64, data,file.name)
    }
    // 读取文件失败回调
    fileReader.onerror = (err) => {}
  } else {
    ElMessage.error('请上传正确的图片格式，支持' + fileTypeList.join("、") + '格式的图片！')
  }
}

const handleDelete = (item, itemImg) => {
  let newImages = []
  const { images } = item
  images.map((e) => {
    if (e.objectid != itemImg.objectid) {
      newImages.push(e)
    }
  })
  let obj = {
    ...item,
    images: newImages,
  }
  emit('change', obj)
}

const getFileImage = (type,src) => {
  let typeFormatted = type.toLowerCase()
  let imgRes

  switch(typeFormatted) {
    case ".zip":
      imgRes = imageZip
      break
    case ".doc":
    case ".docx":
      imgRes = imageWord
      break
    case ".pdf":
      imgRes = imagePdf
      break
    case ".excel":
      imgRes = imageExcel
      break
    default:
      imgRes = src
  }
  return imgRes
}
</script>

<style lang="scss" scoped>
.content {
  width: 100%;
  padding: 20px;
  max-height: 400px;
  overflow: auto;
  ul {
    display: flex;
  }
  li {
    width: 150px;
    height: 150px;
    margin-right: 12px;
    margin-bottom: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    img {
      width: 100%;
      cursor: pointer;
    }
    .iconfont {
      display: none;
      position: absolute;
      top: 4px;
      right: 4px;
      font-size: 20px;
      cursor: pointer;
      color: #f00;
      background-color: #fff;
      border-radius: 50%;
    }
    &:hover {
      .iconfont {
        display: block;
      }
    }
  }
}
.el-collapse {
  width: 100%;
}

.upload-icon {
  width: 150px;
  height: 150px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #8c939d;
  text-align: center;
  &:hover {
    border-color: #409eff;
  }
}
input {
  display: none;
}
</style>
