<template>
  <LayoutsDefault custom>
    <div class="enterprises">
      <div class="container">
        <CompanyInfo @updateRecordTypeIndex="updateRecordTypeIndex"></CompanyInfo>
        <div class="content">
          <div class="tool-tabs-wrapper">
            <ToolTabs></ToolTabs>
          </div>
          <router-view />
        </div>
      </div>
    </div>
  </LayoutsDefault>
</template>


<script>
  import LayoutsDefault from 'layouts/default/index.vue'
  import CompanyInfo from './index/CompanyInfo.vue'
  import ToolTabs from './index/ToolTabs.vue'
  export default {
    name: "EnterprisesIndex",
    components: {LayoutsDefault,CompanyInfo,ToolTabs},
    data() {
      return {
        recordType: null
      }
    },
    provide() {
      return {
        recordTypeProvide: this.getRecordType
      }
    },
    methods: {
      updateRecordTypeIndex(val) {
        this.recordType = val
      },
      getRecordType () {
        return this.recordType
      }
    }
  }
</script>

<style lang="scss" scoped>
.enterprises {
  width: 100%;
  padding: 30px 0;
  .content {
    padding: 30px;
    margin-top: 30px;
    background-color: #fff;
    // overflow-y: scroll;
  }
}
</style>
