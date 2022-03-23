<template>
  <div>
    <el-table
        height="500px"

        ref="multipleTableRef"
        :data="ab21List"
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >
      <el-table-column label="公司名称" header-align="center" align="center" width="200">
        <template #default="scope" >{{ ab20.aab012 }}</template>
      </el-table-column>

      <el-table-column label="所属政策" header-align="center" align="center" width="300">
        <template #default="scope" >{{ ab20.chi031Desc }}</template>
      </el-table-column>

      <el-table-column label="所属业务局" header-align="center" align="center" width="200" >
        <template #default="scope" >{{ ab20.chi037Desc }}</template>
      </el-table-column>
      <el-table-column label="服务事项" header-align="center" align="center" width="200">
        <template #default="scope" >{{ scope.row.aab211Desc }}</template>
      </el-table-column>
      <el-table-column  label="不满意服务详情"  header-align="center" align="center" width="200">
        <template #default="scope" >{{ scope.row.aab212Desc }}</template>
      </el-table-column>


    </el-table>
  </div>
</template>

<script>
export default {
  name: "dissatisfiedPage",
  props: {
    ab21List: {
      type: [Array],
      required: true,
      default: () => []
    },
    ab20:{
      type:Object,
      required:true,
      default: {}
    },
  },
}
</script>

<style scoped>

</style>