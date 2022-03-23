<template>
  <div class="yh-table">
    <el-table ref="tableDom" :data="tableData" style="width: 100%" border>
      <template v-for="(item, index) in tableHead" :key="item.prop">
        <!-- 序号处理 -->
        <template v-if="item.prop == 'index'">
          <el-table-column
            align="center"
            header-align="center"
            type="index"
            :label="item.label"
            :width="60"
          >
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
              <span class="color-primary">
                {{ scope.row.che09a == '1' ? '是' : '否' }}</span
              >
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 材料上传 上传页数/最大上传页数 -->
        <template v-else-if="item.prop == 'num'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            :width="80"
          >
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 材料上传 已上传材料 -->
        <template v-else-if="item.prop == 'uploaded'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            :width="120"
          >
            <template #default="scope">
              <span class="color-success" v-if="scope.row.images.length===scope.row.num"
                >已上传({{ scope.row.images.length }}/{{ scope.row.num }})</span
              >
              <span class="color-fail" v-else-if="scope.row.images.length>=1 && scope.row.images.length<scope.row.num"
              >已上传({{ scope.row.images.length }}/{{ scope.row.num}})</span
              >
              <span class="color-fail" v-else>未上传</span>
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 材料上传 预览示例文件 -->
        <template v-else-if="item.prop == 'operation_preview'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            :width="100"
          >
            <template #default="scope">
              <i
                class="iconfont iconfont-operation-preview"
                @click="handlePreviewImages(scope.row)"
              >
              </i>
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 材料上传 查看已上传文件 -->
        <template v-else-if="item.prop == 'operation_see'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            :width="100"
          >
            <template #default="scope">
              <i
                class="iconfont iconfont-operation-see"
                @click="handleClickView(scope.row)"
                v-if="scope.row.che102 != '02'"
              ></i>
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 材料上传 上传文件 -->
        <template v-else-if="item.prop == 'operation_upload'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            :width="100"
          >
            <template #default="scope">
              <i
                class="iconfont iconfont-operation-upload"
                @click="handleClickUpload(scope.row)"
                v-if="scope.row.che102 != '02'"
              ></i>
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

    <YhUploadImagesCard
      v-model="showUploadPopup"
      :activeName="activeName"
      :fileList="tableData"
      @change="emit('change', $event)"
    ></YhUploadImagesCard>
  </div>
</template>

<script setup>
import YhUploadImagesCard from '@/components/project/YhUploadImagesCard/YhUploadImagesCard.vue'

import { ref, getCurrentInstance } from 'vue'
import { ElMessage } from 'element-plus'
import { requestE409 } from '@/service'
import config from '@/config'
import { downloadFile } from '@/service/axios'

const emit = defineEmits(['change'])
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
})

const { appContext } = getCurrentInstance()

// 文件上传选中列表
const activeName = ref('')
const showUploadPopup = ref(false)

// 点击上传
const handleClickUpload = (data) => {
  showUploadPopup.value = true
  activeName.value = data.che092
}

// 预览已上传图片
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
    activeName.value = data.che092
  } else {
    ElMessage.error(`${data.che091}还未上传，请上传后再查看！`)
  }
}

// 预览示例图片
const handlePreviewImages = async (row) => {
  const imagePerfix = config.requestUrl + '/upload/downFileByFileId?fileId='
  const urls = []
  const { che090, che091 } = row
  let res = await requestE409(che090)
  const { data } = res
  if (data) {
    const { resultList } = data
    if (resultList && resultList.length > 0) {
      for (let i = 0, len = resultList.length; i < len; i++) {
        const item = resultList[i]
        urls.push(imagePerfix + item.chi215)
      }
      appContext.config.globalProperties.$viewerApi({
        images: urls,
      })
    } else {
      ElMessage.error(`后台暂未上传${che091}示例文件`)
    }
  }
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
