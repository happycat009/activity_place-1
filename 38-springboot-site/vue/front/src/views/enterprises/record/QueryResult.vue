<template>
  <div class="record-query-result">
    <el-scrollbar height="800px">
      <el-collapse accordion class="record-collapse" style="max-height: 800px;min-height: 600px;" v-model="collapseItem">
        <template v-for="(item, index) in hb00List" :key="index" v-show="listVisiable">
          <el-collapse-item :name="index + 1" class="record-collapse-item">
            <template #title>
              <div class="record-collapse-title">
                <div class="record-collapse-title-project-name">
                  <div>【{{getCollectionDesc("aab301",item.aab301)}}】{{item.chi031Desc}}</div>
                  <b :style="tagStyle(item)">{{getCollectionDesc("chb00k",item.chb00k)}}</b>
                </div>
                <div class="record-collapse-title-project-info">
                  <div>申报时间：{{getDateFormatted(item.aae036,"yyyy-MM-DD")}}</div>
                  <div>已核定扶持金额：{{item.aae019Formatted}}</div>
                  <div>当前环节：{{getCollectionDesc("cpb070",item.cse240)}}</div>
                </div>
                <div class="record-collapse-title-button-wrapper">
                  <!--<button class="primary" @click="showToReApply(item)" :disabled="!('true' == ('' + item.isCanCancel))">重新申报</button>-->
                  <!--<button class="primary" @click="showToReApply(item,true)" :disabled="item.isCanCancel == 'false'">重新申报</button>-->
                  <button class="primary" @click="showToReApply(item,false)" :disabled="item.isCanCancel == 'false'">撤销申报</button>
                  <button class="primary" @click="toJudge(item.chi031)" >评价</button>
                  <!--<button class="primary reverse" disabled>催进度</button>-->
                </div>
              </div>
            </template>
            <div class="record-collapse-item-con">
              <YhApplyProcess :dataList="item['process'] ? item['process']['row'] : []"></YhApplyProcess>
            </div>
          </el-collapse-item>
        </template>
      </el-collapse>
    </el-scrollbar>

    <el-pagination
        v-model:current-page="pageInfo.pageNo"
        v-model:page-size="pageInfo.pageSize"
        background
        layout="sizes, prev, pager, next, jumper"
        :total="Number(pageInfo.total)"
        :hide-on-single-page="false"
        @size-change="pageSizeChange"
        @current-change="pageNoChange"
        :page-sizes="['5','10', '20', '30']"
    />

    <el-dialog
        v-model="dialogVisible"
        title="提示"
        width="30%"
        @close="closeToReApply"
    >
      <b style="font-size: 20px" @click="test">{{reapplyFlag ? "确认重新申报？":""}}<br/>当前申报数据将会被撤销！！！</b>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="danger" @click="toReApply">{{reapplyFlag ? "重新申报":"撤销"}}</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>


<script>
  import YhApplyProcess from '@/components/project/YhApplyProcess/YhApplyProcess.vue'
  import YhPagination from '@/components/elementPlusExtend/YhPagination/YhPagination.vue'

  import {mapGetters} from "vuex";
  import {CORPORATE_INFO, CURRENT_MENU_LIST, USER_INFO} from "@/store/constants";
  import {requestB407,requestB412} from '@/service'
  import moment from 'moment'

  export default {
    name: "EnterprisesRecordQueryResult",
    components: {
      YhApplyProcess,YhPagination
    },
    props: {
      type: {
        type: Number,
        required: true,
      }
    },
    data() {
      return {
        hb00List: [],
        listVisiable: false,
        dialogVisible: false,
        selectedData: null,
        tagType: 0,
        tablePageInfo: {
          pageNo: 1,
          pageSize: 5,
          total: 0
        },
        pageInfo: {
          pageNo: 1,
          pageSize: 5,
          total: 0
        },
        reapplyFlag: false,
        collapseItem: -1
      }
    },
    methods: {
      test() {
        console.log("dialogVisible",this.dialogVisible)
      },
      toJudge(chi031) {
        this.$router.push("/home/questionnaire/1&"+chi031)
      },
      getDateFormatted(oraData,formatter) {
        return moment(oraData).format(formatter)
      },
      showToReApply(data,flag) {
        this.reapplyFlag = flag
        this.selectedData = data
        this.dialogVisible = true
        // this.$router.push(`/apply/${chi031}`)
      },
      closeToReApply() {
        this.selectedData = null
      },
      toReApply() {
        let _this = this
        const {chb000,chi031} = _this.selectedData
        requestB412(chb000).then((res) => {
          _this.dialogVisible = false
          const {data} = res
          if('200' == data.code) {
            if(_this.reapplyFlag) {
              _this.$message.success("撤销成功，请重新填写申报数据！")
              _this.$router.push(`/apply/${chi031}`)
            } else {
              _this.$message.success("撤销成功！")
              _this.$nextTick(() => {
                _this.$emit("updateStaRefresh")
                _this.loadTableData()
              })
            }
          }
        })
      },
      pageNoChange(pageNo) {
        this.pageInfo.pageNo        = pageNo
        this.tablePageInfo.pageNo   = pageNo
      },
      pageSizeChange(pageSize) {
        this.pageInfo.pageSize      = pageSize
        this.tablePageInfo.pageSize = pageSize
      },
      loadTableData() {
        let val = this.param
        if(!val.queryParam) {
          return
        }
        requestB407(val.aab001,val.queryParam,val.pageNo,val.pageSize,val.aae100).then((res) => {
          const { code , data } = res
          if( '200' == code ) {
            const {hb00List,hb00ListPageInfo} = data
            let tempList = []
            hb00List.map(item => {
              if (item.aae019){
                let tempAae019 = this.unitConvert(item.aae019 || 0)
                tempList.push({
                  ...item,
                  aae019Formatted: tempAae019.num + tempAae019.unit + '元'
                })
              }else{
                tempList.push({
                  ...item,
                  aae019Formatted: '暂无'
                })
              }

            })
            this.hb00List = tempList
            this.pageInfo.total = hb00ListPageInfo.total
            this.listVisiable = true
            // 加载新数据时收起面板
            this.collapseItem = -1
            // 请求返回之后才会修改标签的样式
            this.tagType = this.type
          }
        })
      },
      tagStyle(item) {
        let style = {}
        switch(item.chb00k) {
          case "1":
            style = {
              "font-size": "14px",
              "color": "white",
              "padding": "6px 18px",
              "line-height": "1",
              "background-color": "#43a4ae",
              "border-radius": "50px",
            }
            break
          case "2":
            style = {
              "font-size": "14px",
              "color": "#245319",
              "padding": "6px 18px",
              "line-height": "1",
              "background-color": "#99cd87",
              "border-radius": "50px",
            }
            break
          case "3":
            style = {
              "font-size": "14px",
              "color": "#f90000",
              "padding": "6px 18px",
              "line-height": "1",
              "background-color": "#ffebeb",
              "border-radius": "50px",
            }
            break
          case "4":
            style = {
              "font-size": "14px",
              "color": "#f90000",
              "padding": "6px 18px",
              "line-height": "1",
              "background-color": "#ffebeb",
              "border-radius": "50px",
            }
            break
          case "5":
            style = {
              "font-size": "14px",
              "color": "#f90000",
              "padding": "6px 18px",
              "line-height": "1",
              "background-color": "#ffebeb",
              "border-radius": "50px",
            }
            break
          case "6":
            style = {
              "font-size": "14px",
              "color": "#f90000",
              "padding": "6px 18px",
              "line-height": "1",
              "background-color": "#ffebeb",
              "border-radius": "50px",
            }
            break
          case "7":
            style = {
              "font-size": "14px",
              "color": "#f90000",
              "padding": "6px 18px",
              "line-height": "1",
              "background-color": "#ffebeb",
              "border-radius": "50px",
            }
            break
        }
        return style
      }
    },
    mounted() {
      this.userCodeTableMap("aab301")
      this.userCodeTableMap("cpb070")
      this.userCodeTableMap("chb00k")
    },
    watch: {
      type: {
        handler(val) {
          this.listVisiable = false
          // 查询类型变化 重置查询分页
          this.tablePageInfo = {
            pageNo: 1,
            pageSize: 5
          }
          this.pageInfo = {
            pageNo: 1,
            pageSize: 5
          }
          this.loadTableData()
        }
      },
      param: {
        handler() {
          this.loadTableData()
        },
        immediate: true,
        deep: true
      }
    },
    computed: {
      ...mapGetters([USER_INFO, CURRENT_MENU_LIST, CORPORATE_INFO]),
      param() {
        let _this = this
        let tempQueryParam = ""
        let aae100 = "1"
        switch(_this.type) {
          case -1:
            tempQueryParam = 'all'
            break
          case 0:
            tempQueryParam = 'ing'
            break
          case 1:
            tempQueryParam = 'back'
            aae100 = "0"
            break
          case 2:
            tempQueryParam = 'finish'
            break
          case 3:
            tempQueryParam = 'cancel'
            break
        }
        return {
          aab001: this.corporateInfo.aab001,
          ...this.tablePageInfo,
          queryParam: tempQueryParam,
          aae100
        }
      }
    }
  }
</script>

<style lang="scss">
// 此处样式没有scoped 注意变量命名
.record-query-result {
  width: 100%;
  padding-bottom: 30px;
  .record-collapse {
    border: 0;
    .record-collapse-item {
      border: solid 2px rgba(216, 232, 255, 0.51);
      padding: 30px;
      margin-bottom: 26px;

      .el-collapse-item__header {
        height: auto;
        border: 0;
        position: relative;
      }
      .el-collapse-item__wrap {
        border-top: solid 2px rgba(216, 232, 255, 0.51);
        border-bottom: 0;
        margin-top: 30px;
      }
      .el-collapse-item__content {
      }

      .el-collapse-item__arrow {
        position: absolute;
        margin: 0;
        width: 80px;
        height: 80px;
        background-color: #eff7ff;
        border-radius: 50%;
        right: 20px;
        cursor: pointer;
        transform: rotate(90deg);
        .icon {
          width: 22px;
          height: 22px;
          color: var(--color-primary);
        }
        &.is-active {
          transform: rotate(-90deg);
        }
      }
      &:last-child {
        margin-bottom: 0;
      }
    }
  }
  .record-collapse-title {
    width: 100%;
    padding-right: 120px;
  }
  .record-collapse-title-project-name {
    display: flex;
    align-items: center;

    div {
      font-size: 18px;
      font-weight: 700;
      color: #333;
      margin-right: 10px;
      line-height: 1;
      margin-left: -8px;
    }
    span {
      font-size: 14px;
      color: #f90000;
      padding: 6px 18px;
      line-height: 1;
      background-color: #ffebeb;
      border-radius: 50px;
    }
  }
  .record-collapse-title-project-info {
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #666;
    padding-top: 18px;
    padding-bottom: 28px;
    line-height: 1;
    div {
      margin-right: 50px;
      &:last-child {
        margin-right: 0;
      }
    }
  }
  .record-collapse-title-button-wrapper {
    display: flex;
    button {
      width: 100px;
      margin-right: 16px;
      height: 36px;
      line-height: 34px;
    }
    button:disabled {
      color: white;
      background-color: gray;
    }
  }
}
.record-collapse-item-con {
  padding-top: 30px;
}
</style>
