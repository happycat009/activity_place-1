<template>
  <div class="credit-info">
    <YhTitle title="信用信息" type="03"></YhTitle>
<!--    <div style="margin-top: 10px;font-size:20px">经系统计算得出，企业信用等级为“B”，可能影响部分惠企政策申报</div>-->
    <div class="content">
      <div style="margin-bottom: 5px">企业信用数据</div>
      <el-table
          border="true"
          ref="multipleTableRef"
          :data="enterpriseTable"
          style="width: 100%"
      >
        <el-table-column label="信用类型" header-align="center" align="center" width="233">
          <template #default="scope">
          {{scope.row.creditType}}
          </template>
        </el-table-column>

        <el-table-column label="有无失信行为" header-align="center" align="center" width="120">
          <template #default="scope" >{{ scope.row.dishonest=='1'? '有':'无'  }}</template>
        </el-table-column>

        <el-table-column label="列入日期" header-align="center" align="center" width="100" >
          <template #default="scope">
            {{getDateFormatted(scope.row.time,"yyyy-MM-DD")}}
          </template>
        </el-table-column>
        <el-table-column label="数据来源" header-align="center" align="center" width="200">
          <template #default="scope" >{{ scope.row.dataSources }}</template>
        </el-table-column>
        <el-table-column  label="联系电话" header-align="center" align="center"  width="200">
          <template #default="scope" >{{ scope.row.tel }}</template>
        </el-table-column>

      </el-table>

      <div style="margin-top: 30px;margin-bottom: 5px">企业法人公共信用综合评价</div>
      <el-table
          border="true"
          ref="multipleTableRef"
          :data="legalPerson"
          style="width: 100%"
      >
        <el-table-column label="信用类型" header-align="center" align="center" width="233">
          <template #default="scope">
            {{scope.row.creditType}}
          </template>
        </el-table-column>

        <el-table-column  label="评价结果" header-align="center" align="center" width="120">
          <template #default="scope" >{{ scope.row.results }}</template>
        </el-table-column>

        <el-table-column label="列入日期" header-align="center" align="center" width="100" >
          <template #default="scope">
            {{getDateFormatted(scope.row.time,"yyyy-MM-DD")}}
          </template>
        </el-table-column>
        <el-table-column label="数据来源" header-align="center" align="center" width="200">
          <template #default="scope" >{{ scope.row.dataSources }}</template>
        </el-table-column>
        <el-table-column  label="联系电话" header-align="center" align="center"  width="200">
          <template #default="scope" >{{ scope.row.tel }}</template>
        </el-table-column>

      </el-table>
      <div class="hint">
        <i class="iconfont iconfont-hint"></i>
        <span> 如无相关行为，请联系数据来源方进行修正。</span>
      </div>
    </div>
  </div>
</template>

<script>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import {requestB404,requestE418,requestE419,requestE420,requestE421,requestE422,requestE417} from '@/service'
import moment from "moment";
import store from '@/store'
export default {
  name: 'credit',
  data(){
    return{
      enterpriseTable:[

      ],
      legalPerson :[
      ]
    }
  },
  methods:{
    // cellStyle(column){
    //   console.log(column)
    //   if(column.columnIndex === 1 || column.columnIndex === 1) {
    //     return "font-size: 20px"
    //   }
    // },
    getDateFormatted(oraData,formatter) {
      if(oraData!="---" && oraData!=undefined){
        return moment(oraData).format(formatter)
      }else {
        return "---"
      }
    },
  },
  computed:{
    corporateInfo(){
      return store.state.corporateInfo
    }
  },

  mounted() {
    const {aab01w,aac002} = this.corporateInfo
    //企业法人公共信用综合评价  aab01w 统一社会信用代码91511523060333798L
    requestE417(aab01w).then(res=>{
      console.log("E417==========",res)
      const {data} = res
      const {ei04List} = data
      if(ei04List.length >0){
        for (var i=0;i<ei04List.length;i++){
          this.legalPerson.push({
            creditType:ei04List[i].che351,
            results:ei04List[i].cei044,
            dataSources:ei04List[i].che352,
            tel:ei04List[i].che35d
          })
        }

      }
    })
    // 严重违法失信企业 aab01w 91511000MA6CJTR62E 统一社会信用代码  aac002 511021195301011754 法人身份证号码
    requestE418(aab01w,aac002).then(res=>{
      console.log("E418==========",res)
      const {data} = res
      const {ei08List} = data
      if(ei08List.length >0){
        for(var i =0;i<ei08List.length;i++){
          this.enterpriseTable.push({
            creditType:ei08List[i].che351,
            dishonest:"1",
            time:ei08List[i].abntime,
            dataSources:ei08List[i].che352,
            tel:ei08List[i].che35d
          })
        }

      }
    })
    // 失信被执行人名单（法人或其他组织） aab01w  55352669-4统一社会信用代码
    requestE419(aab01w).then(res=>{
      console.log("E419==========",res)
      const {data} = res
      const {ei13List} = data
      if(ei13List.length >0){
        for(var i =0;i<ei13List.length;i++) {
          this.enterpriseTable.push({
            creditType:ei13List[i].che351,
            dishonest:"1",
            time:ei13List[i].abntime,
            dataSources:ei13List[i].che352,
            tel:ei13List[i].che35d
          })
        }
      }
    })
    // 失信被执行人名单（自然人） aac002 512530196111272266 身份证号码
    requestE420(aac002).then(res=>{
      const {data} = res
      const {ei12List} = data
      if(ei12List.length >0){
        for(var i =0;i<ei12List.length;i++) {
          this.enterpriseTable.push({
            creditType:ei12List[i].che351,
            dishonest:"1",
            time:ei12List[i].fbsj,
            dataSources:ei12List[i].che352,
            tel:ei12List[i].che35d
          })
        }

      }
      console.log("E420==========",res)
    })
    // 四川省失信被执行人名单 aab01w 554972469 统一社会信用代码
    requestE421(aab01w).then(res=>{
      console.log("E421==========",res)
      const {data} = res
      const {ei06List} = data
      if(ei06List.length >0){
        for(var i =0;i<ei06List.length;i++) {
          this.enterpriseTable.push({
            creditType:ei06List[i].che351,
            dishonest:"1",
            time:ei06List[i].bmtabletime,
            dataSources:ei06List[i].che352,
            tel:ei06List[i].che35d
          })
        }
      }
    })
    // 企业安全生产不良记录 aab01w  91360781731963054P 统一社会信用代码
    requestE422(aab01w).then(res=>{
      console.log("E422==========",res)
      const {data} = res
      const {ei03List} = data
      if(ei03List.length >0){
        for(var i =0;i<ei03List.length;i++) {
          this.enterpriseTable.push({
            creditType:ei03List[i].che351,
            dishonest:"1",
            time:ei03List[i].bmtabletime,
            dataSources:ei03List[i].che352,
            tel:ei03List[i].che35d
          })
        }

      }
    })


  }
}
</script>

<style lang="scss" scoped>
.credit-info {
  width: 100%;
  padding: 30px;
  .content {
    padding-top: 30px;
  }
}
.hint{
  position: relative;
  width: fit-content;
  color: coral;
  line-height: 30px;
  top: 22px;
  span{
    margin-left: 2px;
  }
}
</style>
