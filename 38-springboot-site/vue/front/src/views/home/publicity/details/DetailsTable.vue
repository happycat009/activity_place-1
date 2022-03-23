<template>
  <div class="publicity-details-table-wrapper">
    <YhTitle :title="titleData.chb57e" type="03"></YhTitle>
    <div class="information-belongs">
      <div class="top">
        <span>主管部门：{{ titleData.chi037Desc }}</span
        ><span>公示开始日期：{{ titleData.chb578 }}</span>
        <span>公示结束日期：{{ titleData.chb579 }}</span>
        <span>涉及企业（单位）：{{ titleData.ab01Total + "个" }}</span
        ><span>浏览次数：{{ titleData.chb57f }}</span>
      </div>
      <div class="under">
        <span>政策依据：{{ titleData.chi051 }}</span>
        <span class="tel">投诉电话：{{ titleData.che273 }}</span>
      </div>
    </div>
    <div class="table-wrapper">
      <el-table
        :data="tableData"
        :span-method="arraySpanMethod"
        border
        sum-text="合计"
        style="width: 100%"
      >
        <el-table-column type="index" align="center" label="序号" width="80" />
        <el-table-column
          prop="aab012"
          width="210"
          align="center"
          show-overflow-tooltip
          label="企业名称"
        />
        <el-table-column
          prop="aab01d"
          label="企业规模"
          width="248"
          align="center"
          show-overflow-tooltip
        >
          <template #default="scope">
            <div>
              {{
                scope.row.aab01d
                  ? getCollectionDesc("aab01d", scope.row.aab01d)
                  : "规上服务业"
              }}
            </div>
          </template>
        </el-table-column>
        <!-- <el-table-column
          prop="aab017"
          align="center"
          show-overflow-tooltip
          label="所属行业"
          width="114"
        >
          <template #default="scope">
            <div>
              {{ getCollectionDesc("aab017", scope.row.aab017) }}
            </div>
          </template>
        </el-table-column> -->
        <el-table-column
            prop="aae019"
            label="扶持金额"
            align="center"
            width="196"
            show-overflow-tooltip
        >
          <template #default="scope">
            <div style="color: #006bfd">
              {{ unitConvert(scope.row.aae019||0).num + unitConvert(scope.row.aae019||0).unit +"元" }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="chb00k"
          label="申报结果"
          align="center"
          width="196"
          show-overflow-tooltip
        >
          <template #default="scope">
            <div style="color: #006bfd">
              {{ getCollectionDesc("chb00k", scope.row.chb00k) }}
            </div>
          </template>
        </el-table-column>

<!--        <el-table-column label="扶持资金" align="center" header-align="center">-->

<!--          <template #default="scope">-->
<!--            <div>-->
<!--              {{scope.row.aae019Formatted}}-->
<!--            </div>-->
<!--          </template>-->

<!--        </el-table-column>-->

        <el-table-column label="投诉" align="center" header-align="center">
          <!-- <template #header>
            <div class="complaint">
              投诉
              <span>1</span>
            </div>
          </template> -->
          <template #default="scope">
            <el-button
              size="small"
              color="#ffae00"
              style="color: #fff"
              @click="handleComplaint(scope.$index, scope.row)"
              >投诉
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="chb00q" label="备注" width="124" />
      </el-table>
    </div>
    <div>
      <div class="pagination-wrapper">
        <el-pagination
          background
          layout="prev, pager, next,sizes,jumper,total,slot"
          :total="total"
          v-model:page-size="pageSize"
          @current-change="handlePagesChange"
          @size-change="handlePageSizeChange"
          :page-sizes="['10', '20', '30']"
        >
          <!-- 共7页 -->
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import YhTitle from "@/components/project/YhTitle/YhTitle.vue";
import { reactive } from "vue";
import { requestE403, requestE404, requestC414 } from "@/service";
import moment from "moment";
export default {
  name: "PublicityDetailsTable",
  setup() {
    return {};
  },
  data() {
    return {
      value:['key1','key2'],
      // 表格数据
      tableData: [],
      id: "",
      // 头部数据
      titleData: {},
      chb00kChange: [
        { value: "1", name: "业务进行中" },
        { value: "2", name: "业务通过" },
        { value: "3", name: "业务不通过4" },
        { value: "4", name: "打回指定节点" },
        { value: "5", name: "回退到受理节点" },
        { value: "6", name: "业务作废" },
        { value: "7", name: "申报撤销" },
      ],

      // 分页数据
      pageNo: 1,
      pageSize: 10,
      total: 0,
    };
  },
  components: {
    YhTitle,
  },
  async mounted() {
    // requestE403
    let { id } = this.$route.params;
    this.id = id;
    let type = "";
    if (window.name == "") {
      type = "1";
      window.name = "isRefresh";
    } else if (window.name == "isRefresh") {
      console.log("页面被刷新");
    }
    this.hanleGetData(type);
    this.handleGetHeadData();
    this.userCodeTableMap("aab01d");
    this.userCodeTableMap("aab017");
    this.userCodeTableMap("chb00k");
    document.getElementsByClassName(
      "el-pagination__jump"
    )[0].childNodes[0].nodeValue = "跳转";
  },
  unmounted() {
    window.name = "";
  },
  computed: {},
  methods: {
    // 合并列
    arraySpanMethod({ row, column, rowIndex, columnIndex }) {},
    handleComplaint(index, value) {
      let { aab001, cpb100, chb000, aab012, chi037,aab01w,chb015,chb570,chi031 } = value;
      console.log(value,'value')
      let data = { aab001, cpb100, chb000, aab012, chi037,aab01w,chb015,chb570,chi031 };
      this.$router.push({
        path: "/home/publicity/complaint",
        query: { companyData: JSON.stringify(data) },
      });
    },
    hanleGetData(type) {
      let params = {
        pageNo: this.pageNo + "",
        pageSize: this.pageSize + "",
        chb570: this.id,
      };
      // 判断浏览次数
      if (type && type == "1") {
        params = {
          ...params,
          type,
        };
      }
      requestE404(params).then((res) => {
        let list = res.data.hb49List ? res.data.hb49List : [];
        let renderList = []
        list.map(item => {
          // let tempAae019 = this.unitConvert(item.aae019 || 0)
          // let content = (!tempAae019.num ? "资金未定" : tempAae019.num + tempAae019.unit + '元')
          renderList.push({
            ...item,
            // aae019Formatted: content
          })
        })
        this.tableData = [...renderList];
        this.total = Number(res?.data?.hb49ListPageInfo?.total);
      });
    },

    async handleGetHeadData() {
      let params = {
        pageNo: this.pageNo + "",
        pageSize: this.pageSize + "",
        chb570: this.id,
      };
      let res = await requestE403(params);
      let headData = res?.data?.hb57List[0];
      let chb578 = moment(headData.chb578).format("YYYY-MM-DD");
      let chb579 = moment(headData.chb579).format("YYYY-MM-DD");
      let chb57e = headData.chb57e.replace(/\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}/g,"");
      this.titleData = { ...headData, chb578, chb579, chb57e };
      let result = await requestC414(headData.chi037)
      let {che273} = result.data.he27List[0]
      this.titleData = {...this.titleData,che273}
    },

    // 分页函数
    handlePagesChange(value) {
      this.pageNo = value;
      this.hanleGetData();
    },
    handlePageSizeChange(value) {
      this.hanleGetData();
    },
  },
};
</script>

<style lang="scss" scoped>
.publicity-details-table-wrapper {
  width: 100%;
  background-color: #fff;
  padding: 24px;
  .information-belongs {
    span {
      font-size: 12px;
      color: #666666;
      margin-right: 40px;
    }
    .top {
      margin-top: 10px;
    }
    .under {
      display: flex;
      justify-content: space-between;
      margin-top: 10px;
      .tel {
        font-size: 14px;
        color: #d9001b;
      }
    }
  }
  .table-wrapper {
    padding-top: 24px;
  }
}
.complaint {
  position: relative;
  span {
    position: absolute;
    top: 0;
    right: 0;
    display: block;
    white-space: nowrap;
    padding-top: 1px;
    background-color: #fff849;
    font-size: 10px;
    font-weight: bold;
    line-height: 14px;
    margin-right: 3px;
    padding: 0px 4px;
    color: #000;
    -moz-box-shadow: 1px 1px 3px #aaa;
    -webkit-box-shadow: 1px 1px 3px #aaa;
    box-shadow: 1px 1px 3px #aaa;
  }
}
.pagination-wrapper {
  margin-top: 10px;
}
</style>
