<template>
  <div class="publicity-query-result">
    <div class="container">
      <YhTitle title="公示列表" type="03"></YhTitle>
      <div class="items">
        <el-scrollbar height="800px">
          <template v-for="item in publicityData" :key="item.chb570">
            <div class="item">
              <div class="item-title-wrapper">
                <QueryResultItemTitle :titleData="item"></QueryResultItemTitle>
              </div>
              <div class="item-card-wrapper">
                <template
                    v-for="(itemCard, itemIndex) in item.ab01List"
                    :key="itemIndex"
                >
                  <QueryResultItemCard
                      :index="itemIndex + 1"
                      :companyList="itemCard"
                  ></QueryResultItemCard>
                </template>
              </div>
            </div>
          </template>
        </el-scrollbar>
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
    </div>
  </div>
</template>

<script>
import YhTitle from "@/components/project/YhTitle/YhTitle.vue";
import QueryResultItemTitle from "./QueryResultItemTitle.vue";
import QueryResultItemCard from "./QueryResultItemCard.vue";

import { requestE403 } from "@/service";
import moment from "moment";

export default {
  name: "PublicityQueryResult",
  props: {
    form: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {
      pageNo: 1,
      pageSize: 10,
      total: 0,
      // 展示公示数据
      publicityData: [],
      queryForm: {}
    };
  },
  components: {
    YhTitle,
    QueryResultItemTitle,
    QueryResultItemCard,
  },
  mounted() {
    this.handleGetData();
  },
  computed: {},
  methods: {
    reloadForm(){
      this.queryForm = this.form
      this.handleGetData()
    },
    handleGetData() {
      let params = {
        pageNo: this.pageNo + "",
        pageSize: this.pageSize + "",
        ...this.queryForm,
      };
      requestE403(params).then((res) => {
        let list = res?.data?.hb57List;
        list = list.map((item) => {
          let chb578 = moment(item.chb578).format("YYYY-MM-DD");
          let chb579 = moment(item.chb579).format("YYYY-MM-DD");
          return { ...item, chb578, chb579 };
        });
        this.total = Number(res?.data?.hb57ListPageInfo?.total)
        this.publicityData = [...list];
        console.log("this.publicityData",this.publicityData);
      });
    },
        // 分页函数
    handlePagesChange(value){
      this.pageNo = value
      this.handleGetData()
    },
    handlePageSizeChange(value){
      this.handleGetData()
    }
  },
};
</script>

<style lang="scss" scoped>
.publicity-query-result {
  width: 100%;
  margin-top: 30px;
  .container {
    background-color: #fff;
    padding: 26px 24px;
  }
  .items {
    width: 100%;
    padding-top: 30px;
  }
  .item {
    margin-bottom: 68px;
    .item-more {
      text-align: right;

      button {
        color: $primary;
        font-size: 16px;
        padding: 12px 0;
        cursor: pointer;
        transition: all 0.3s;
        &:hover {
          opacity: 0.8;
        }
      }
    }
    .item-card-wrapper {
      display: flex;
      align-items: center;
      flex-wrap: wrap;
    }
    &:last-child {
      margin-bottom: 0;
    }
  }
}
.pagination-wrapper{
  margin-top: 10px;
}
</style>
