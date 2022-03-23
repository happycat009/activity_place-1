<template>
  <div class="evaluate-table-wrapper">
    <div class="table-wrapper">
      <el-table
          height="500px"
          ref="multipleTableRef"
          :data="tableData"
          style="width: 100%"
          @selection-change="handleSelectionChange"
      >
        <el-table-column label="查看不满意事项" header-align="center" align="center" width="150">
          <template #default="scope">
            <el-button type="primary"  @click="readMessage(scope.row)">查看</el-button>
          </template>
        </el-table-column>

        <el-table-column label="经办人" header-align="center" align="center" width="100">
          <template #default="scope" >{{ scope.row.aae012 }}</template>
        </el-table-column>

        <el-table-column label="评价时间" header-align="center" align="center" width="160" >
          <template #default="scope">
            {{getDateFormatted(scope.row.aae036,"yyyy-MM-DD HH:mm:ss")}}
          </template>
        </el-table-column>
        <el-table-column label="所属政策项目" header-align="center" align="center" width="360">
          <template #default="scope" >{{ scope.row.chi031Desc }}</template>
        </el-table-column>
        <el-table-column  label="所属主管部门" header-align="center" align="center"  width="200">
          <template #default="scope" >{{ scope.row.chi037Desc }}</template>
        </el-table-column>
        <el-table-column  label="服务态度"   width="100">
          <template #default="scope" header-align="center" align="center">{{ scope.row.aab201Desc }}</template>
        </el-table-column>
        <el-table-column  label="服务质量"   width="100">
          <template #default="scope" header-align="center" align="center">{{ scope.row.aab202Desc }}</template>
        </el-table-column>
        <el-table-column  label="服务效率"   width="100">
          <template #default="scope" header-align="center" align="center" >{{ scope.row.aab203Desc }}</template>
        </el-table-column>
        <el-table-column  label="服务环境"   width="100">
          <template #default="scope" header-align="center" align="center">{{ scope.row.aab204Desc }}</template>
        </el-table-column>


      </el-table>
      <div class="demo-pagination-block">
        <el-pagination
            v-model:currentPage="pageInfo.pageNo"
            v-model:page-size="pageInfo.pageSize"
            :page-sizes="[5, 10, 20, 30]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="parseInt(pageInfo.total)"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </div>
<!--      <YhPagination :total="Number(pageInfo.total)" @pagination="pageInfoChange" :page-sizes="['1','10', '20', '30']"/>-->
    </div>
  </div>
</template>

<script>
import {requestB302, requestB417, requestB413, requestB414, requestGetData} from "@/service";
import YhTable from '@/components/elementPlusExtend/YhTable/YhTable.vue'
import YhPagination from '@/components/elementPlusExtend/YhPagination/YhPagination.vue'
import {mapGetters} from "vuex";
import {CORPORATE_INFO} from "@/store/constants";
import moment from "moment";

export default {
  name: "evaluateTable",
  components: {YhTable,YhPagination},
  props: {
    toolbarParam: {
      type: [Object],
      required: true,
      default: () => {}
    }
  },
  data() {
    return {
      tableData: [],
      tablePageInfo: {
        pageNo: 1,
        pageSize: 10
      },
      pageInfo: {
        pageNo: 1,
        pageSize: 10,
        total: 0
      },
      multipleTableRef: null,
      messageDetail: {
        title: "",
        content: "",
        dialogVisible: false
      },
      messageUnreadCount: 0
    }
  },

  methods: {

    getDateFormatted(oraData,formatter) {
      return moment(oraData).format(formatter)
    },
    loadTableData() {
      let _this = this
      let { pageNo , pageSize , aab001 ,issee } = _this.queryParam
      if(!pageNo || !pageSize || !aab001) {
        return
      }
      var param = {aab001,"start":this.pageInfo.pageNo,"limit":this.pageInfo.pageSize}
      requestB417(param).then(res =>{
        const {data} = res
        const {ab20ListPage} = data
        _this.tableData = ab20ListPage.ab20List
        this.pageInfo.total=ab20ListPage.total
        console.log("b417",res)
      })
    },
    // 读取信息
    readMessage(data) {
      console.log("data",data);
      this.$emit("showModel",true,data);
    },
    handleSizeChange (val){
      this.pageInfo.pageSize=val
      this.loadTableData()
      console.log("handleSizeChangeval",val);
    },
    handleCurrentChange(val){
      this.pageInfo.pageNo=val
      this.loadTableData()
      console.log("handleCurrentChange",val);
    }
  },
  computed: {
    ...mapGetters([CORPORATE_INFO]),
    queryParam() {
      // 预备兼容本页面之后可能新增的条件
      let otherParam = {
        ...this.toolbarParam
      }
      return {
        ...otherParam,
        ...this.tablePageInfo,
        aab001: this.corporateInfo.aab001
      }
    }
  },
  watch: {
    queryParam: {
      handler(val) {
        this.loadTableData()
      },
      immediate: true,
      deep: true
    },
    messageUnreadCount: {
      handler (val) {
        this.$emit("updateMessageUnreadCount",val)
      },
      immediate: true
    }
  }
}
</script>

<style lang="scss">
.demo-pagination-block {
  margin-top: 10px;
}

.evaluate-table-wrapper {
  padding-bottom: 30px;
}
</style>