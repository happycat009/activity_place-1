<template>
  <div class="service-guide-basic-info">
    <YhTitle title="基本信息" type="03"></YhTitle>
    <div class="custom-table-wrapper">
      <div class="custom-table-cells">
        <div class="custom-table-cell">
          <div class="key">项目名称</div>
          <div class="value">
            {{basicInfo.chi032}}
          </div>
        </div>
        <div class="custom-table-cell">
          <div class="key">所属地市</div>
          <div class="value">{{basicInfo.aab301 ? getCollectionDesc("aab301",basicInfo.aab301) : basicInfo.aab301}}</div>
        </div>
        <div class="custom-table-cell">
          <div class="key">主管部门</div>
          <div class="value">{{getCollectionDesc("chi037",basicInfo.chi037)}}</div>
        </div>

        <div class="custom-table-cell">
          <div class="key">协同审核部门</div>
          <div class="value">{{ getHi01Str(basicInfo.hi01List) }} </div>
        </div>

        <div class="custom-table-cell">
          <div class="key">扶持行业</div>
          <div class="value">{{he24Desc}}</div>
        </div>

        <div class="custom-table-cell">
          <div class="key">扶持方式</div>
          <div class="value">{{getCollectionDesc("bhi031",basicInfo.bhi031)}}</div>
        </div>

        <div class="custom-table-cell">
          <div class="key">扶持标准</div>
          <div class="value">{{basicInfo.chi04x}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'


export default {
  name: 'ServiceGuideBasicInfo',
  data() {
    return {}
  },
  props: {
    basicInfo: {
      type: [Object],
      required: true,
      default: () => {}
    }
  },
  components: { YhTitle },
  mounted() {
    this.userCodeTableMap("aab301")
    this.userCodeTableMap("chi037")
    this.userCodeTableMap("bhi031")
    this.userCodeTableMap("che241")
  },
  methods: {
    getHi01Str(hi01List){

      console.log(hi01List)
      let s = '暂无'
      if (hi01List){
        s = ''
        for (const hi01ListElement of hi01List) {
          s += hi01ListElement.chi011 + "、"
        }
        s = s.substr(0,s.length-1)
      }
      return s
    }
  },
  computed: {
    he24Desc() {
      const {he24List} = this.basicInfo
      let descList = []
      if(he24List && he24List.length) {
        he24List.map((item) => {
          descList.push(this.getCollectionDesc("che241",item["che241"]))
        })
      }
      return descList.join("、")
    }
  },
}
</script>

<style lang="scss" scoped>
.service-guide-basic-info {
  width: 100%;
  padding-top: 8px;
  padding-bottom: 40px;
  .time {
    text-align: right;
    font-size: 14px;
    font-weight: normal;
    letter-spacing: 0px;
    color: #333;
    span {
      color: #ea0000;
    }
  }
  .custom-table-wrapper {
    padding-top: 10px;
    padding-left: 14px;
  }
}
</style>
