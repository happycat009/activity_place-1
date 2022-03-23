<template>
  <div class="yh-table">
    <el-table
      ref="tableRef"
      v-loading="showLoading"
      :data="tableData"
      style="width: 100%"
      border
    >
      <template v-for="(item, index) in tableHead" :key="item.prop">
        <!-- 序号处理 -->
        <template v-if="item.prop == 'index'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            :width="60"
          >
            {{ index + 1 }}
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 材料上传 是否必要 -->
        <template v-else-if="item.prop == 'che09a'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            width="100"
          >
            <template #default="scope">
              <span class="color-fail">
                {{ scope.row.che09a == '1' ? '是' : '否' }}</span
              >
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 下载 -->
        <template v-else-if="item.prop == 'download'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            width="100"
          >
            <template #default="scope">
              <span class="download color-primary">
                {{ scope.row.download }}</span
              >
            </template>
          </el-table-column>
        </template>
        <!-- 其他情况 不处理 -->
        <template v-else>
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            show-overflow-tooltip
          ></el-table-column>
        </template>
      </template>
    </el-table>
  </div>
</template>

<script setup>
import YhUploadImagesCard from '@/components/project/YhUploadImagesCard/YhUploadImagesCard.vue'

import { ElMessage } from 'element-plus'
import { getCurrentInstance, ref } from 'vue'

import { requestUploadFile, requestUploadFileByBase64 } from '@/service'

const { proxy, appContext } = getCurrentInstance()

// 文件上传选中列表
const activeName = ref('')
const showUploadPopup = ref(false)

const emit = defineEmits(['view', 'preview', 'upload'])

const props = defineProps({
  // 表格数据
  tableData: {
    type: Array,
    default: () => [],
  },
  // 表头
  tableHead: {
    type: Array,
    default: () => [],
  },

  // 是否显示加载动画
  showLoading: {
    type: Boolean,
    default: () => false,
  },

  // 序号
  index: {
    type: Boolean,
    default: () => false,
  },
  // 单选
  radio: {
    type: Boolean,
    default: () => false,
  },
})

const tableRef = ref(null)

// 点击上传
const handleClickUpload = (data) => {
  showUploadPopup.value = true
  activeName.value = data.che09g
}
// 点击预览
const handleClickView = (data) => {
  const fileList = props.tableData
  let tag = false

  for (let i = 0, len = fileList.length; i < len; i++) {
    const item = fileList[i]
    if (item.che09g === data.che09g) {
      if (data.images.length > 0) {
        tag = true
        break
      }
    }
  }

  if (tag) {
    showUploadPopup.value = true
    activeName.value = data.che09g
  } else {
    ElMessage.error(`${data.che091}还未上传，请上传后再查看！`)
  }
}

// 上传
const handleUploadChange = (data) => {
  emit('upload', data)
}

// 预览示例图片
const handlePreviewImages = () => {
  // appContext.config.globalProperties.$viewerApi({
  //   images: srcList,
  // })
}

const uploadFile = async (filePath, row) => {
  const data = await requestUploadFileByBase64(filePath)
  const { bucketid, objectid, objectkey } = data.data
  row.path = filePath
  row.bucketid = bucketid
  row.objectid = objectid
  row.objectkey = objectkey
  emit('upload', row)
}

// 文件上传
const handleFileChange = (event, row) => {
  const files = event.target.files
  const file = files[0]
  const fileReader = new FileReader()
  fileReader.readAsDataURL(file)
  fileReader.onload = (res) => {
    const base64 = fileReader.result
    uploadFile(base64, row)
  }
  // 读取文件失败回调
  fileReader.onerror = (err) => {}
}
</script>
<style lang="scss">
.yh-table {
  width: 100%;
  .color-primary {
    color: var(--color-primary);
  }
  .color-success {
    color: var(--color-success);
  }
  .color-fail {
    color: var(--color-fail);
  }
  .iconfont {
    color: var(--color-primary);
    cursor: pointer;
  }
  .upload-file-input {
    display: none;
  }
  .download {
    cursor: pointer;
  }
}
.el-table__header-wrapper {
  .el-table__header {
    .el-table__cell {
      background-color: #eff6ff;
      padding: 12px 0;
      .cell {
        color: #333;
        font-size: 16px;
        line-height: 1.4;
      }
    }
  }
}
.el-table__body-wrapper {
  .el-table__cell {
    background-color: #fff;
    padding: 12px 0;
    .cell {
      color: #333;
      font-size: 14px;
      line-height: 1.4;
    }
  }
}
</style>
