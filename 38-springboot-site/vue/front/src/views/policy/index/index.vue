-
<template>
  <div class="policy-index">
    <div class="container">
      <Banner></Banner>
      <div class="search-wrapper">
        <div class="left">
          <PublishUnit @departSelected="departSelected"></PublishUnit>
        </div>
        <div class="right">
          <router-view :key="$route.fullPath"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './Banner.vue'
import PublishUnit from './PublishUnit.vue'
export default {
  name: 'PolicyIndex',
  data() {
    return {
      // 查询结果数据
      dimensionList: []
    }
  },
  components: { Banner, PublishUnit },
  mounted() {
  },
  methods: {
    departSelected(data) {
      console.log("depart",data.chi037)
    }
  },
  computed: {},
}
</script>

<style lang="scss" scoped>
.policy-index {
  width: 100%;
  padding-top: 20px;
  padding-bottom: 20px;
  .container {
    background-color: #fff;
    padding: 30px 24px;
  }
  .search-wrapper {
    padding-top: 30px;
    display: flex;
    .left {
      width: 184px;
      margin-right: 30px;
    }
    .right {
      flex: 1;
      overflow: hidden;
    }
  }
}
</style>
