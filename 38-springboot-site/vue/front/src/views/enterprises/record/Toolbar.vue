<template>
  <div class="toolbar">
    <el-radio-group v-model="radio">
      <template v-for="item in customData.filter">
        <el-radio :label="item.value">{{ item.name }}</el-radio>
      </template>
    </el-radio-group>
  </div>
</template>


<script>
  import { requestE401 } from '@/service'
  import {mapGetters} from "vuex";
  import {CORPORATE_INFO, CURRENT_MENU_LIST, USER_INFO} from "@/store/constants";
  export default {
    name: "EnterprisesRecordToolbar",
    props: {
      staRefresh: {
        type: [Number],
        required: true,
        default: () => 0
      }
    },
    data() {
      return {
        staData: {},
        radio: null
      }
    },
    inject: ['recordTypeProvide'],
    mounted() {
      const { recordType } = this.$route.query
      this.radio = (recordType == null) ? 0 : Number(recordType)
      this.loadStaImpl()
    },
    methods: {
      loadStaImpl() {
        requestE401([
          {
            method: "enterprisesStatisticsCount",
            param: {
              aab001: this.corporateInfo.aab001,
              orgid: this.corporateInfo.aab001,
            }
          }
        ]).then((res) => {
          const {code ,data} = res
          if('200' == code) {
            const {enterprisesStatisticsCountMap} = data
            this.staData = enterprisesStatisticsCountMap
          }
        })
      }
    },
    watch: {
      radio: {
        handler(val) {
          if(val != null) {
            this.$router.push("/enterprises/record?recordType=" + val)
            this.$emit('updateRecordType', val)
          }
        },
        immediate: true,
        deep: true
      },
      recordTypeProvideComputed: {
        handler(val) {
          if(null != val) {
            this.radio = val
          }
        }
      },
      staRefresh: {
        handler() {
          // 重新统计
          this.loadStaImpl()
        }
      }
    },
    computed: {
      ...mapGetters([USER_INFO, CURRENT_MENU_LIST, CORPORATE_INFO]),
      customData() {
        let _this = this
        return {
          filter: [
            {
              value: -1,
              name: '全部申报记录（' + (Number(_this.staData.ingbusnum) + Number(_this.staData.backbusnum) + Number(_this.staData.finishbusnum) + Number(_this.staData.cancelbusnum) ) + '项）',
            },
            {
              value: 0,
              name: '仅展示进行中项目（' + (_this.staData.ingbusnum || 0) + '项）',
            },
            {
              value: 1,
              name: '仅展示被驳回项目（' + (_this.staData.backbusnum || 0) + '项）',
            },
            {
              value: 2,
              name: '仅展示已享受项目（' + (_this.staData.finishbusnum || 0) + '项）',
            },
            // {
            //   value: 3,
            //   name: '仅展示已撤销项目（' + (_this.staData.cancelbusnum || 0) + '项）',
            // },
          ],
        }
      },
      recordTypeProvideComputed() {
        return this.recordTypeProvide()
      }
    }
  }
</script>

<style lang="scss" scoped>
.toolbar {
  width: 100%;
  padding: 30px 0;
}
</style>
