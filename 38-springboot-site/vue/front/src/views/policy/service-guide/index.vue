<template>
  <div class="policy-details-service-guide" id="a">
    <div class="toolbar">
      <Toolbar @handlerTabChange="handlerTabChange"></Toolbar>
    </div>
    <div class="content">
      <BasicInfo :basicInfo="basicInfo" class="scroll-type" ></BasicInfo>
      <ApplicationConditions :conditionInfo="conditionInfo" class="scroll-type" ></ApplicationConditions>
      <ApplicationMaterials :materialsInfo="materialsInfo" class="scroll-type" ></ApplicationMaterials>
      <Process class="scroll-type" ></Process>
      <Attachment :downloadFiles="attachmentInfo" class="scroll-type"></Attachment>
    </div>
  </div>
</template>

<script>
import Toolbar from './Toolbar.vue'
import BasicInfo from './BasicInfo.vue'
import ApplicationConditions from './ApplicationConditions.vue'
import ApplicationMaterials from './ApplicationMaterials.vue'
import Process from './Process.vue'
import Attachment from './Attachment.vue'

export default {
  name: 'PolicyDetailsServiceGuide',
  data() {
    return {
      basicInfo     : {},
      conditionInfo : {},
      materialsInfo : {},
      attachmentInfo: {}
    }
  },
  inject: ['serviceGuideProvide'],
  components: {
    Toolbar,
    BasicInfo,
    ApplicationConditions,
    ApplicationMaterials,
    Process,
    Attachment,
  },
  mounted() {
    let _this = this
    this.$nextTick(()=>{
      _this.handleGetOffset()
    })
  },
  computed: {
    serviceGuideProvideComputed() {
      return this.serviceGuideProvide()
    }
  },
  watch: {
    serviceGuideProvideComputed: {
      handler(val){
        if(val) {
          this.basicInfo = val.basicInfo
          this.conditionInfo = val.basicInfo.chi035
          this.materialsInfo = val.materialsInfo
          this.attachmentInfo = val.attachmentInfo
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods:{
    handleGetOffset(){
      let domArr = document.getElementsByClassName('scroll-type')
      let offsetArr = []
      for(let i=0;i<domArr.length;i++) {
        offsetArr.push({offsetTop:domArr[i].offsetTop,offsetHeight:domArr[i].offsetHeight})
      }
      this.$bus.emit('getOffsetTop',offsetArr)
    },
    // tab切换 进行滚动
    handlerTabChange(value){
      var anchorTop = document.getElementsByClassName('scroll-type')[value].offsetTop
      var app = window.document.querySelector('#app')
      let sum = anchorTop - app.scrollTop
      let index = 0
      var timer = setInterval(()=>{
        app.scrollTop = app.scrollTop + sum/30
        index++
        if(sum > 0 ) {
          if (app.scrollTop == anchorTop || app.scrollTop> anchorTop) {
            app.scrollTop = anchorTop
            clearInterval(timer)
          }
        } else {
          if (app.scrollTop == anchorTop || app.scrollTop< anchorTop) {
            app.scrollTop = anchorTop
            clearInterval(timer)
          }
        }
        if(index>30){
          clearInterval(timer)
        }
      },10)
    },
  }
}
</script>

<style lang="scss" scoped>
.policy-details-service-guide {
  width: 100%;
  display: flex;
  padding-bottom: 78px;
  .content {
    flex: 1;
    overflow: hidden;
    padding-left: 20px;
    padding-right: 40px;
  }
}
</style>
