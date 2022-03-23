<template>
  <div class="yh-table">
    <el-table
      ref="tableDom"
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
          >
            <template #default="scope">
              <span class="color-primary">
                {{ scope.row.che09a == '1' ? '是' : '否' }}</span
              >
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 材料上传 已上传材料 -->
        <template v-else-if="item.prop == 'uploaded'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
          >
            <template #default="scope">
              <span class="color-success" v-if="scope.row.uploaded"
                >已上传</span
              >
              <span class="color-fail" v-else>未上传</span>
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 材料上传 查看文件 -->
        <template v-else-if="item.prop == 'operation_preview'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            :width="140"
          >
            <template #default="scope">
              <i
                class="iconfont iconfont-operation-preview"
                @click="emit('preview', scope.row)"
              ></i>
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 材料上传 查看文件 -->
        <template v-else-if="item.prop == 'operation_see'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
          >
            <template #default="scope">
              <i
                class="iconfont iconfont-operation-see"
                @click="emit('see', scope.row)"
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
          >
            <template #default="scope">
              <i
                class="iconfont iconfont-operation-upload"
                @click="emit('upload', scope.row)"
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
    <template v-if="showPagination">
      <YhPagination :total="total" @pagination="pagination" />
    </template>
  </div>
</template>
<script setup>
import { getCurrentInstance, ref } from 'vue'
import YhPagination from '@/components/elementPlusExtend/YhPagination/YhPagination.vue'

const { proxy } = getCurrentInstance()

const emit = defineEmits(['see', 'preview', 'upload'])

defineProps({
  // 是否显示分页
  showPagination: {
    type: Boolean,
    default: () => false,
  },
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
  // 表格高度
  tableHeight: {
    type: String,
    default: () => '100%',
  },
  // 翻页条件
  total: {
    type: Number,
    default: () => 0,
  },
  // 是否显示加载动画
  showLoading: {
    type: Boolean,
    default: () => false,
  },
  // 需要哪些操作按钮
  operation: {
    type: Array,
    default: () => [],
  },
  // 全选
  selection: {
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
  // 表头配置
  setting: {
    type: Boolean,
    default: () => false,
  },
  // 表头配置
  selectAll: {
    type: Boolean,
    default: () => false,
  },
  tableRowClassName: {
    type: Function,
    default: () => null,
  },
  // 是否需要点击高亮当行
  isHighlight: {
    type: Boolean,
    default: () => false,
  },
})

const tableDom = ref()

// 选择内容变化
const handleSelectionChange = (val) => {
  // 如果radio为true，则当前表格为单选表格
  if (proxy.radio === true && val.length > 1) {
    tableDom.value.clearSelection()
    tableDom.value.toggleRowSelection(val.pop())
  } else {
    emit('handleSelectionChange', val)
  }
}
// 全选
const onSelectAll = () => {
  if (proxy.radio === true) {
    tableDom.value.clearSelection()
  } else {
    emit('onSelectAll')
  }
}
const openDialog = () => {
  console.log('设置')
}

const pagination = (data) => {
  emit('paginationChange', data)
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
