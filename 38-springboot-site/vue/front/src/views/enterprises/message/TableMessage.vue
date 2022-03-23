<template>
  <div class="message-table-wrapper">
    <div class="table-wrapper">
      <el-table
          height="500px"

          ref="multipleTableRef"
          :data="tableData"
          style="width: 100%"
          @selection-change="handleSelectionChange"
      >
        <!--<el-table-column type="selection" width="55" />-->
        <el-table-column label="操作" header-align="center" align="center" width="120">
          <template #default="scope">
            <!--<el-button v-if="scope.row.issee == '1'" disabled="true">已读</el-button>-->
            <!--<el-button @click="readMessage(scope.row)" v-else>读取</el-button>-->
            <el-button type="primary" :disabled="scope.row.issee==1?true:false" @click="readMessage(scope.row)">标记为已读</el-button>
<!--        <el-button type="primary" @click="showMessageDetail(scope.row)">查看详情</el-button>-->
          </template>
        </el-table-column>

        <el-table-column label="状态" header-align="center" align="center" width="100">
<!--          <template #default="scope">{{ getCollectionDesc("yesorno",scope.row.issee) }}</template>-->
          <template #default="scope">{{ scope.row.issee==1?"已读":"未读" }}</template>
        </el-table-column>

        <el-table-column  label="消息主题"   width="200">
          <template #default="scope" >{{ scope.row.title }}</template>
        </el-table-column>

        <el-table-column label="消息内容" width="360">

          <template #default="scope">
            <el-button type="primary" @click="showMessageDetail(scope.row.content)">查看内容</el-button>
          </template>

<!--          <template #default="scope" :cell-style="cellStyle">-->
<!--            <div v-html="scope.row.content"></div>-->
<!--          </template>-->
        </el-table-column>

        <!--<el-table-column-->
        <!--    property="address"-->
        <!--    label="消息种类"-->
        <!--    show-overflow-tooltip-->
        <!--/>-->
        <el-table-column label="接收时间" header-align="center" align="center" width="160" >
          <template #default="scope">
            {{getDateFormatted(scope.row.mgdate,"yyyy-MM-DD HH:mm:ss")}}
          </template>
        </el-table-column>

        <!--<el-table-column property="issee" label="状态" width="120" />-->
      </el-table>
      <YhPagination :total="Number(pageInfo.total)" @pagination="pageInfoChange" :page-sizes="['1','10', '20', '30']"/>
    </div>

<!--    <el-dialog
        v-model="messageDetail.dialogVisible"
        :title="messageDetail.title"
        width="30%"
        top="20vh"
        modal="true"
    >
      <span :title="messageDetail.content">{{messageDetail.content}}</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="messageDetail.dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>-->
  </div>
</template>


<script>
  import {requestB302,requestB413,requestB414} from "@/service";
  import YhTable from '@/components/elementPlusExtend/YhTable/YhTable.vue'
  import YhPagination from '@/components/elementPlusExtend/YhPagination/YhPagination.vue'
  import {mapGetters} from "vuex";
  import {CORPORATE_INFO} from "@/store/constants";
  import moment from "moment";
  import { ElMessageBox } from 'element-plus'


  export default {
    name: "MessageTableMessage",
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
    mounted() {
      this.userCodeTableMap("yesorno")
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
        requestB413(pageNo,pageSize,aab001,issee).then((res) => {
          const {code,data} = res
          if( "200" == code) {
            const { messageList,messagePageInfo,unMarkMessageCount } = data
            console.log("messageList",messageList)
            console.log("messagePageInfo",messagePageInfo)
            console.log("unMarkMessageCount",unMarkMessageCount)
            _this.tableData           = messageList
            _this.pageInfo            = messagePageInfo
            _this.messageUnreadCount  = unMarkMessageCount
          }
        })
      },
      pageInfoChange(pageParam) {
        const { currentPage, pageSize} = pageParam
        this.pageInfo.pageNo        = currentPage
        this.pageInfo.pageSize      = pageSize
        this.tablePageInfo.pageNo   = currentPage
        this.tablePageInfo.pageSize = pageSize
      },
      // 读取信息
      readMessage(data) {
        const { aab001 } = this.corporateInfo
        const { mgid } = data
        requestB302(aab001,mgid).then((res) => {
          this.loadTableData()
        })
      },
      showMessageDetail (data) {

        ElMessageBox.alert(
            data,
            '消息内容',
            {
              dangerouslyUseHTMLString: true,
            }
        )
        //
        // this.messageDetail = {
        //   ...data,
        //   dialogVisible: true
        // }
        // this.loadMessageDetail()
        // // this.readMessage(data)
      },
      loadMessageDetail() {
        const { aab001 } = this.corporateInfo
        const { mgid } = this.messageDetail
        requestB414(aab001,mgid).then((res) => {
          const { code, data } = res
          if('200' == code) {
            this.messageDetail = {
              ...this.messageDetail,
              ...data
            }
            // 读取且未读状态，设置为已读
            if(this.messageDetail.issee != '1') {
              this.readMessage(this.messageDetail)
            }
          }
        })
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
.message-table-wrapper {
  padding-bottom: 30px;
}
</style>
