<template>
  <div class="environmental-info">
    <YhTitle title="环保信息" type="03"></YhTitle>
    <div class="hint">
      <span>企业环境信用等级属于“不良企业”，可能影响部分惠企政策申报。</span>
    </div>
    <div class="content">
      <el-table
              :data="tableData.tableData1"
      >
        <el-table-column label="环保类型" header-align="center" align="center" width="150">
          <template #default="scope">
            {{scope.row.che351}}
          </template>
        </el-table-column>
        <el-table-column label="环境信用等级" header-align="center" align="center" width="150">
          <template #default="scope">
            {{scope.row.creditLevelCode}}
          </template>
        </el-table-column>
        <el-table-column label="列入日期" header-align="center" align="center" width="150">
          <template #default="scope">
            {{scope.row.startDate}}
          </template>
        </el-table-column>
        <el-table-column label="数据来源" header-align="center" align="center" width="150">
          <template #default="scope">
            {{scope.row.che352}}
          </template>
        </el-table-column>
        <el-table-column label="联系电话" header-align="center" align="center" width="150">
          <template #default="scope">
            {{scope.row.che35d}}
          </template>
        </el-table-column>

      </el-table>
    </div>
    <div class="hint2">
      <i class="iconfont iconfont-hint"></i>
      <span>如无相关行为，请联系数据来源方进行修正。</span>
    </div>
  </div>
</template>

<script setup>
  import {requestB408, requestB409, requestE416} from "@/service";
import {onMounted,reactive,computed} from "vue";
  import { useStore } from 'vuex'
  import {
    CORPORATE_INFO,
  } from '@/store/constants'

let tableData = reactive({
  tableData1:[]
})

  const store = useStore()

  const corporateInfo = computed(()=>{
    return  store.state[CORPORATE_INFO]
  })

// const getData = async (aab01w) => {
//   console.log(12)
//   const res = await requestE416(aab01w)
//   console.log(res)
// }


onMounted(() => {
  var { aab01w } = corporateInfo.value
  requestE416(aab01w).then((res) => {
    const { code, data } = res
    if ('200' == code) {
      tableData.tableData1 = data.ei01List
    }
  })

})

</script>
<style lang="scss" scoped>
.environmental-info {
  width: 100%;
  padding: 30px;
  .content {
    padding-top: 30px;
  }
  .edit-button {
    position: absolute;
    top: 0px;
    right: 22px;
    width: 96px;
  }
  .cancel-button {
    position: absolute;
    top: 0px;
    right: 22px;
    width: 96px;
  }
  .save-button {
    position: absolute;
    top: 0px;
    right: 142px;
    width: 96px;
  }
  .hint{
    position: relative;
    width: auto;
    line-height: 50px;
    top: 22px;
    background-color: #f6fbff;
    span{
      margin-left: 2px;
    }
  }
  .hint2{
    position: relative;
    width: auto;
    color: coral;
    line-height: 50px;
    top: 22px;
    background-color: #f6fbff;
    span{
      margin-left: 2px;
    }
  }
  .notice{
    top: -10px;
  }
  .input-unit{
    line-height: 44px;
  }
}

</style>
