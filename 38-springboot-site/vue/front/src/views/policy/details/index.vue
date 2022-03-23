<template>
  <div class="policy-details">
    <div class="container">
      <YhBreadcrumb :renderData="breadcrumbData"></YhBreadcrumb>
      <PolicyDetailsInfo :renderData="renderData" :dateInfo="dateInfo" :messageInfo="messageInfo"></PolicyDetailsInfo>
      <div class="tab-wrapper">
        <Tabs :policyInfo="policyInfo"></Tabs>
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import YhBreadcrumb from '@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue'
import PolicyDetailsInfo from './PolicyDetailsInfo.vue'

import Tabs from './Tabs.vue'
import {requestC412} from "@/service";
import moment from "moment";
import {mapGetters} from "vuex";
import {CORPORATE_INFO} from "@/store/constants";

export default {
  name: 'PolicyDetails',
  data() {
    return {
      breadcrumbData: [
        {
          id: '01',
          path: '/policy',
          name: '政策直通车',
        },
        {
          id: '02',
          path: '',
          name: '政策详情',
        },
      ],
      renderData: {},
      dateInfo: {
        limitDays: "",
        overdue: false,
        enableDeclare: false
      },
      policyInfo: {},
      messageInfo: {},
      indexServiceGuideInfo: {
        basicInfo     : {},
        conditionInfo : {},
        materialsInfo : {},
        attachmentInfo: {}
      }
    }
  },
  provide() {
    return {
      serviceGuideProvide: this.getServiceGuideInfo
    }
  },
  mounted() {
    this.getPolicyDetails()
  },
  methods: {
    getPolicyDetails() {
      let _this = this
      if(_this.$route.params.id) {
        const { aab001 } = this.corporateInfo
        requestC412(_this.$route.params.id,aab001).then((res) => {
          if(res.code == "200") {
            /**
             * 基础信息
             */
            let policyTypeArr = []
            res.data.he26List.map((item) => {
              policyTypeArr.push(item.che262)
            })
            _this.renderData = {
              ..._this.renderData,
              chi031: res.data.hi03Map.chi031,
              aab301: res.data.hi03Map.aab301,
              chi032: res.data.hi03Map.chi032,
              chi05c: res.data.hi05Map.chi05c,
              chi034: res.data.hi03Map.chi034,
              chi052: res.data.hi05Map.chi052,
              chi037: res.data.hi03Map.chi037,
              chi051: res.data.hi05Map.chi051,
              // chi05a: res.data.hi03Map.chi05a,
              // bhi036: res.data.hi03Map.bhi036,
              bhi037: res.data.hi03Map.bhi037,
              chi04p: res.data.hi03Map.chi04p, // 联系人
              chi04q: res.data.hi03Map.chi04q, // 联系方式
              policyType: policyTypeArr.join("、")
            }
            /**
             * 超期时间
             */
            if(res.data.hi03Map.bhi036) {
              let a = moment(moment(res.data.hi03Map.bhi036).format('yyyy-MM-DD'))
              let b = moment(moment(new Date()).format('yyyy-MM-DD'))
              let diff = a.diff(b)
              if(diff >= 0) {
                _this.dateInfo.limitDays = Math.ceil((diff / (24*60*60*1000)) + 1)
                _this.dateInfo.overdue = false
                _this.dateInfo.enableDeclare = true
              } else {
                _this.dateInfo.overdue = true
                _this.dateInfo.enableDeclare = false
              }
            } else {
              _this.dateInfo.overdue = true
              _this.dateInfo.enableDeclare = false
            }

            // 政策信息
            const { hi23List } = res.data
            let oraFile
            for(var key in hi23List ){
              if(hi23List[key].chi238 == 4){
                oraFile= hi23List[key].chi215;
                break
              }
            }
            _this.policyInfo = {oraFile}

            const { hasHr08, hasHr09 } = res.data
            _this.messageInfo = {
              hasHr08,
              hasHr09
            }

            /**
             * 申报指南信息
             */
            // 项目信息
            _this.indexServiceGuideInfo.basicInfo = {
              ...res.data.hi03Map,
              he24List: res.data.he24List,
              hi01List: res.data.hi01List,
            }
            // 申请要求
            _this.indexServiceGuideInfo.conditionInfo = res.data.hr02List
            // 申请材料
            let he10List = res.data.he10List
            let he09List = res.data.he09List
            let materialList = []
            let materialMap = {}
            he10List.map((he10) => {
              he09List.map((he09) => {
                if(he10.che092 == he09.che092) {
                  materialMap[he10.che092] = {
                    che091: he09.che091, // 材料名称
                    che093: he09.che093, // 材料类型
                    che094: he09.che094, // 材料页数
                    che09a: he10.che09a, // 必要性
                    che09b: he09.che09b, // 材料说明
                    che099: he09.che099, // 材料说明
                  }
                }
              })
            })
            _this.indexServiceGuideInfo.materialsInfo = Object.values(materialMap)
            // 附件列表
            let attachmentList = []
            res.data.hi23List.map((item) => {
              attachmentList.push({
                chi230: item.chi230,// 主键
                chi231: item.chi231,// 名称
                chi233: item.chi233,// 文件类型（带.）
                chi215: item.chi215,//文件存储id
              })
            })
            _this.indexServiceGuideInfo.attachmentInfo = attachmentList

          } else {
            _this.$message.warning("获取政策信息失败！")
          }
        })
      }
    },
    getServiceGuideInfo() {
      return this.indexServiceGuideInfo
    }
  },
  components: {
    YhBreadcrumb,
    PolicyDetailsInfo,
    Tabs,
  },
  computed: {
    ...mapGetters([CORPORATE_INFO]),
  },
}
</script>

<style lang="scss" scoped>
.policy-details {
  width: 100%;
  background-color: #f7faff;
  padding-bottom: 30px;
  min-height: 500px;
  .tab-wrapper {
    background-color: #ffffff;
    border: solid 1px #e4eaf2;
    margin-top: 30px;
  }
}
</style>
