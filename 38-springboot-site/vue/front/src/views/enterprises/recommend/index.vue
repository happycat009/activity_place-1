<template>
  <div class="enterprise-recommend">
    <Toolbar :radio="radio" @updateRadio="updateRadio"
             :recommendNum="recommendChi031List.length"
             :overDurNum="overDurChi031List.length"
    >
    </Toolbar>
    <QueryResult :renderDataList="renderDataList"></QueryResult>
  </div>
</template>

<script>
import Toolbar from './Toolbar.vue'
import QueryResult from './QueryResult.vue'
import {requestGetData} from "@/service";
import moment from 'moment'

import {CORPORATE_INFO} from "@/store/constants";
import {mapGetters} from "vuex";

export default {
  name: 'RecommendIndex',
  data() {
    return {
      radio: 0,
      recommendChi031List: [],
      overDurChi031List: []
    }
  },
  mounted() {
    this.queryPolicyImpl()
  },
  components: {Toolbar,QueryResult},
  computed: {
    ...mapGetters([CORPORATE_INFO]),
    renderDataList() {
      if(this.radio == 0) {
        return this.recommendChi031List
      } else {
        return this.overDurChi031List
      }
    }
  },
  methods: {
    queryPolicyImpl(param,departData) {
      let _this = this
      const {aab001} = this.corporateInfo
      requestGetData("D402",{'aab001':aab001}).then((res) => {
        let recommendChi031TempList = res.data.recommendChi031List
        let chi031List = new Array();
        for (const chi031ListElement of recommendChi031TempList) {
          chi031List.push(chi031ListElement.chi031);
        }
        requestGetData("C401",{'chi031List':JSON.stringify(chi031List),pageNo: '1', pageSize: '10'}).then((res) => {
          if(res && res.data && res.data.c401List && res.data.c401List.length) {
            let renderList = []
            let overDurTempList = []
            for (let i = 0;i < res.data.c401List.length;i++) {
              let loopItem = res.data.c401List[i]

              // 筛选推荐项目
              for (const recommendItem of recommendChi031TempList) {
                if (recommendItem.chi031 == loopItem.chi031){
                  loopItem['chr081'] = recommendItem.chr081+'%'
                }
              }

              // 获取过期项目 bhi036不存在说明数据有问题
              const bhi036 = loopItem["bhi036"]
              if(bhi036) {
                let a = moment(moment(bhi036).format('yyyy-MM-DD'))
                let b = moment(moment(new Date()).format('yyyy-MM-DD'))
                let diff = a.diff(b)
                if(diff < 0) {
                  overDurTempList.push(loopItem)
                } else {
                  renderList.push(loopItem)
                }
              }
            }
            _this.overDurChi031List = overDurTempList
            _this.recommendChi031List = renderList
          } else {
            _this.$message.error({
              showClose: false,
              message: '获取政策法规失败！！！',
              center: true,
              type: "error"
            })
          }

        });
      });
    },
    updateRadio(radioVal) {
      this.radio = radioVal
    }
  }
}
</script>

<style lang="scss" scoped>
.enterprise-recommend {
  width: 100%;
  border: solid 2px rgba(216, 232, 255, 0.51);
  padding: 0 30px;
}
</style>
