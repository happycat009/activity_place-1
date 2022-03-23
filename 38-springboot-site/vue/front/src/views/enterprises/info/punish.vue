
  <template>
    <div class="environmental-info">
      <YhTitle title="行政处罚信息" type="03"></YhTitle>
      <div style="margin-top: 30px;margin-left: 20px;font-size:20px">含有行政处罚信息，可能影响部分惠企政策申报。</div>
      <div class="content">
        <el-table
          border="true"
          :data="punishTable"

        >
          <el-table-column prop="punishType"  label="处罚类型" header-align="center" align="center"></el-table-column>
          <el-table-column prop="hasViolation" label="有无违规行为" header-align="center" align="center"></el-table-column>
          <el-table-column prop="includedDate" label="列入日期" header-align="center" align="center"></el-table-column>
          <el-table-column prop="dataSources" label="数据来源" header-align="center" align="center"></el-table-column>
          <el-table-column prop="tel" label="联系电话" header-align="center" align="center"></el-table-column>

        </el-table>
        <div class="hint">
          <i class="iconfont iconfont-hint"></i>
          <span> 如无相关行为，请联系数据来源方进行修正。</span>
        </div>
      </div>
    </div>
  </template>

  <script>
  import {requestE423 , requestE424} from '@/service'
  import moment from "moment";
  import store from '@/store'
    export default {
      name:'punish',
      data(){
        return{
          punishTable:[
          ]
        }
      },
      computed:{
        corporateInfo(){
          return store.state.corporateInfo
        }
      },

      mounted() {
        const {aab01w} = this.corporateInfo
        //行政处罚当事人信息 aab01w 统一社会信用代码 91511025206762369T
        requestE423(aab01w).then( res =>{
            console.log("E423=========",res)
          const {data} = res
          const {ei10List} = data
          if(ei10List.length >0){
            for (var i=0;i<ei10List.length;i++){
              this.punishTable.push({
                punishType:ei10List[i].che351,
                hasViolation:"1",
                includedDate:moment(ei10List[i].sExtDatatime).format("yyyy-MM-DD"),
                dataSources:ei10List[i].che352,
                tel:ei10List[i].che35d
              })
            }

          }
        })
        //企业法人公共信用综合评价
        // requestE424(aab01w).then( res =>{
        //   console.log("E424=========",res)
        //   const {data} = res
        //   const {ei07List} = data
        //   if(ei07List.length >0){
        //     for (var i=0;i<ei07List.length;i++){
        //       this.punishTable.push({
        //         punishType:ei07List[i].che351,
        //         hasViolation:"1",
        //         includedDate:moment(ei07List[i].addtime).format("yyyy-MM-DD"),
        //         dataSources:ei07List[i].che352,
        //         tel:ei07List[i].che35d
        //       })
        //     }
        //
        //   }
        // })

      }
    }
  </script>

  <style lang="scss" scoped>
    .environmental-info {
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