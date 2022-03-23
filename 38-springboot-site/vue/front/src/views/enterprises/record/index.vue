<template>
  <div class="enterprise-record">
    <Toolbar @updateRecordType="updateRecordType" :staRefresh="staRefresh"></Toolbar>
    <QueryResult :type="type" @updateStaRefresh="updateStaRefresh"></QueryResult>
  </div>
</template>


<script>
  import Toolbar from './Toolbar.vue'
  import QueryResult from './QueryResult.vue'

  export default {
    name: "EnterprisesRecordIndex",
    components: {Toolbar,QueryResult},
    data() {
      return {
        type: null,
        staRefresh: 0
      }
    },
    methods: {
      updateRecordType(radioVal) {
        this.type = radioVal
      },
      updateStaRefresh() {
        this.staRefresh++
      }
    }
  }
</script>

<style lang="scss" scoped>
.enterprise-record {
  width: 100%;
  background-color: #ffffff;
}
</style>
