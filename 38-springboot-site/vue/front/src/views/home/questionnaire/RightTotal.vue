<template>
  <div class="right-total" v-if="renderData.length > 0">
    <!-- <div class="title">企业满意度调查结果统计</div> -->
    <div class="search">
      <el-form
        :model="basicForm"
        class="basic-form"
      >
        <el-form-item label="主管部门：">
          <el-select
            v-model="basicForm.chi037"
            placeholder="请选择主管部门"
            @change="handleChange"
          >
            <template v-for="(item, index) in codeMap.chi037" :key="index">
              <el-option
                :label="item.aaa103"
                :value="item.aaa102"
              ></el-option>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="项目：">
          <el-select
            v-model="basicForm.chi031"
            placeholder="请选择项目"
            @change="handleChange"
          >
            <template v-for="(item, index) in codeMap.chi031" :key="index">
              <el-option             
              :label="item.aaa103"
              :value="item.aaa102"
              ></el-option>
            </template>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <div class="items">
      <template v-for="item in renderData" :key="item.id">
        <div class="item">
          <div class="name">{{ item.name }}</div>
          <div class="list">
            <template
              v-for="(itemList, indexList) in item.list"
              :key="itemList.id"
            >
              <div class="list-item" :class="['list-item-' + indexList]">
                <div class="key">{{ itemList.name }}</div>
                <div class="rate">
                  <div
                    class="bg"
                    :style="{ width: itemList.value + '%' }"
                  ></div>
                </div>
                <div class="value">{{ itemList.value }}%</div>
              </div>
            </template>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import {onMounted, reactive} from 'vue'
import { ElMessage } from "element-plus";
import {
  requestGetCollectionData,
} from "@/service";
defineProps({
  renderData: {
    type: Array,
    default: () => [],
  },
});
const basicForm = reactive({
  chi031:'',
  chi037:''
})
const codeMap = reactive({
  chi037: [],
  chi031: [],
});
const getCodeMap = async (value) => {
  let res = await requestGetCollectionData(value);
  if (res) {
    return res;
  }
  ElMessage(`码表${value}加载失败`);
  return [];
};
const emit = defineEmits(['getTotal'])
const handleChange = () => {
  console.log(basicForm,'数据变化')
  emit('getTotal',basicForm)
}
onMounted(async ()=>{
  codeMap.chi037 = await getCodeMap("chi037");
  codeMap.chi031 = await getCodeMap("chi031");
})
</script>

<style lang="scss" scoped>
.right-total {
  width: 100%;
  padding-top: 68px;
  .title {
    text-align: center;
    font-weight: 700;
    font-size: 20px;
    line-height: 1;
    color: #333;
  }
  .items {
    // background-color: #f3f9ff;
    margin-top: 26px;
    padding: 0 20px;
  }
  .item {
    padding: 0 12px;
    font-size: 12px;
    color: #333;
    border-bottom: 1px solid #e9f1ff;
    padding-top: 26px;
    padding-bottom: 10px;
    .name {
      font-size: 16px;
      font-weight: 700;
      margin-bottom: 20px;
      position: relative;
      &::before {
        content: "";
        position: absolute;
        left: -7px;
        top: 50%;
        transform: translateY(-50%);
        width: 4px;
        height: 4px;
        background-color: #006bfd;
        border-radius: 2px;
      }
    }
    .list {
      .list-item {
        display: flex;
        align-items: center;
        margin-bottom: 12px;
        .key {
          width: 70px;
        }
        .rate {
          flex: 1;
          overflow: hidden;
          height: 8px;
          background-color: #fff;
          border-radius: 4px;
          position: relative;
          .bg {
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            border-radius: 4px;
            background-image: linear-gradient(90deg, #00ccff 0%, #002aff 100%);
          }
        }
        .value {
          width: 36px;
          color: #666;
          text-align: right;
        }
        &.list-item-2 {
          .rate {
            .bg {
              background-image: linear-gradient(
                90deg,
                #ffd800 0%,
                #ff8400 100%
              );
            }
          }
        }
      }
    }
    &:last-child {
      border: 0;
    }
  }
}
.basic-form {
  padding: 0 10px;
  display: flex;
  align-items: center;
  .el-form-item {
    flex: 1;
    overflow: hidden;
    .el-form-item__content {
      flex: 1;
      overflow: hidden;
    }
  }
}
</style>
