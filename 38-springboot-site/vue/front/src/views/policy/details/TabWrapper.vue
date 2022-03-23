<template>
  <div class="policy-details">TabContainer</div>
</template>

<script>
export default {
  name: 'TabContainer',
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
  components: {},
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
