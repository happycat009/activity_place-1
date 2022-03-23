<template>
  <div>
    <YhTitle title="查询结果" style="margin-top:20px;margin-bottom: 10px" type="03"></YhTitle>
    <evaluate-table @showModel="showPage">
    </evaluate-table>
<!--    <el-dialog v-model="isShow" width="800px" @close="handleChange" top="8vh">-->
<!--    <dissatisfied-page  @closeModel="hidePage">-->
<!--    </dissatisfied-page>-->
<!--    </el-dialog>-->
    <YhPopup
        :modelValue="isShow"
        @update:modelValue="hidePage"
        align="center"
        :showCloseBtn="true"
        :maskClose="false"
        popupName="不满意事项详情"
    >
      <dissatisfied-page :ab20="ab20" :ab21List="ab21List"></dissatisfied-page>
    </YhPopup>
  </div>
</template>

<script>
import EvaluateTable from "./evaluateTable.vue";
import DissatisfiedPage from "./dissatisfiedPage.vue";
import {request} from "@/service/axios";
import {requestB418} from "@/service";
export default {
  name: "index",
  components: {DissatisfiedPage, EvaluateTable },
  data() {
    return {
      isShow: false,
      ab21List:[],
      ab20:{},
    }
  },
  methods:{
     showPage(val,ab20) {
       requestB418(ab20.aab200).then((res) => {
         const { code, data } = res
         this.ab21List = [...res.data.ab21List]
         this.ab20={...ab20}
         console.log("this.ab20",this.ab20)
         console.log("ab21list",this.ab21List)
       })
       this.isShow = val
    },
    hidePage(){
      this.isShow=false;
    },
  }
}
</script>

<style scoped>

</style>