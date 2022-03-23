<template>
  <div class="policy-details">
    <div class="container">
      <YhBreadcrumb :renderData="breadcrumbData"></YhBreadcrumb>
      <PolicyDetailsInfo></PolicyDetailsInfo>
    </div>
  </div>
</template>

<script>
import YhBreadcrumb from '@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue'
import PolicyDetailsInfo from './PolicyDetailsInfo.vue'
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
    }
  },
  components: {
    YhBreadcrumb,
    PolicyDetailsInfo,
  },
  mounted() {},
  computed: {},
}
</script>

<style lang="scss" scoped>
.policy-details {
  width: 100%;
  background-color: #f7faff;
  padding-bottom: 30px;
  min-height: 500px;
}
</style>
