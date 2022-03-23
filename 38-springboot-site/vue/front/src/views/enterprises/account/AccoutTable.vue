<template>
  <div class="custom-table-wrapper">
    <el-table ref="tableDom" :data="tableData" style="width: 100%" border>
      <template v-for="(item, index) in tableHead" :key="item.prop">
        <!-- 序号处理 -->
        <template v-if="item.prop == 'index'">
          <el-table-column
            :prop="item.prop"
            align="center"
            type="index"
            header-align="center"
            :label="item.label"
            :width="60"
          >
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 删除 -->
        <template v-else-if="item.prop == 'delete'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            width="100px"
          >
            <template #default="scope">
              <el-popconfirm
                title="您是否确定删除该账号？"
                confirm-button-text="确定"
                cancel-button-text="取消"
                :icon="InfoFilled"
                icon-color="red"
                @confirm="handleConfirmDelete(scope.row)"
              >
                <template #reference>
                  <template v-if="isAdmin">
                    <button :disabled="scope.row.isadmin == '1'">
                      <i class="iconfont iconfont-delete"></i>
                    </button>
                  </template>
                  <template v-else>
                    <button disabled>
                      <i class="iconfont iconfont-delete"></i>
                    </button>
                  </template>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 停用 -->
        <!-- <template v-else-if="item.prop == 'stop'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            width="100px"
          >
            <template #default="scope">
              <el-popconfirm
                title="您是否确定停用该账号？"
                confirm-button-text="确定"
                cancel-button-text="取消"
                :icon="InfoFilled"
                icon-color="red"
                @confirm="handleConfirmStop(scope.row)"
              >
                <template #reference>
                  <button
                    :disabled="
                      scope.row.isadmin == '1' || scope.row.islock == '1'
                    "
                  >
                    {{ scope.row.islock == '0' ? '停用' : '停用中' }}
                  </button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </template> -->
        <!-- 特殊表格显示处理 启用 -->
        <!-- <template v-else-if="item.prop == 'start'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            width="100px"
          >
            <template #default="scope">
              <button
                :disabled="scope.row.isadmin == '1' || scope.row.islock == '0'"
              >
                {{ scope.row.islock == '1' ? '启用' : '启用中' }}
              </button>
            </template>
          </el-table-column>
        </template> -->
        <!-- 特殊表格显示处理 重置密码 -->
        <template v-else-if="item.prop == 'reset'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            width="100px"
          >
            <template #default="scope">
              <template v-if="isAdmin">
                <button @click="handleResetPassword(scope.row)">
                  <i class="iconfont iconfont-reset-password"></i>
                </button>
              </template>
              <template v-else>
                <button
                  @click="handleResetPassword(scope.row)"
                  :disabled="scope.row.loginid != userInfo.loginid"
                >
                  <i class="iconfont iconfont-reset-password"></i>
                </button>
              </template>
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 账号 -->
        <template v-else-if="item.prop == 'loginid'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            width="200px"
          >
            <template #default="scope">
              <div class="loginid-wrapper">
                <span v-if="scope.row.isEncryptLoginid">{{
                  scope.row.encryptLoginid
                }}</span>
                <span v-else>{{ scope.row.loginid }}</span>
                <!-- <i class="iconfont iconfont-preview"></i> -->
              </div>
            </template>
          </el-table-column>
        </template>
        <!-- 特殊表格显示处理 账号类型 -->
        <template v-else-if="item.prop == 'positionname'">
          <el-table-column
            :prop="item.prop"
            align="center"
            header-align="center"
            :label="item.label"
            width="200px"
          >
            <template #default="scope">
              <div class="positionname" v-if="scope.row.isadmin == '1'">
                {{ scope.row.positionname }}
              </div>
              <div class="positionname son" v-else>
                {{ scope.row.positionname }}
              </div>
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
import { InfoFilled } from '@element-plus/icons-vue'
import { ref, reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { USER_INFO } from '@/store/constants'
import { requestB202 } from '@/service'

const store = useStore()

const userInfo = computed(() => {
  return store.state[USER_INFO]
})

const isAdmin = computed(() => {
  let result = false
  const { positionInfo } = userInfo.value
  if (positionInfo.isadmin == '1') {
    result = true
  }
  return result
})

const emit = defineEmits(['resetPassword', 'delete'])

const props = defineProps({
  tableData: {
    type: [Array],
    default: () => {
      return []
    },
  },
})
const tableHead = [
  {
    prop: 'index',
    label: '序号',
  },
  {
    prop: 'positionname',
    label: '账号类型',
  },
  {
    prop: 'loginid',
    label: '账号',
  },
  {
    prop: 'name',
    label: '姓名',
  },
  {
    prop: 'tel',
    label: '联系方式',
  },
  {
    prop: 'delete',
    label: '删除',
  },
  // {
  //   prop: 'stop',
  //   label: '停用',
  // },
  // {
  //   prop: 'start',
  //   label: '启用',
  // },
  {
    prop: 'reset',
    label: '重置密码',
  },
]

const handleConfirmDelete = (row) => {
  emit('delete', row)
}
const handleConfirmStop = (row) => {
  console.log(row, 'handleConfirmStop')
}

const handleResetPassword = (row) => {
  emit('resetPassword', row)
}
</script>

<style lang="scss" scoped>
.custom-table-wrapper {
  .no-operate {
    color: #999;
  }
  button {
    color: var(--color-primary);
    &[disabled] {
      color: #999;
      cursor: not-allowed;
    }
  }
  .positionname {
    color: var(--color-primary);
    &.son {
      color: var(--color-warn);
    }
  }
}
</style>
