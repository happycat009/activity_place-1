<template>
  <div class="yh-no-data">
    <el-empty :description="description"></el-empty>
  </div>
</template>

<script>
export default {
  name: 'YhNoData',
  props: {
    description: {
      type: [String],
      default: 'No Data',
    },
  },
  setup() {
    return {}
  },
  data() {
    return {}
  },
  created() {},
  mounted() {},
  components: {},
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.yh-no-data {
  width: 100%;
}
</style>
