<template>
  <div class="yh-breadcrumb">
    <div class="left">当前位置：</div>
    <div class="right">
      <el-breadcrumb :separator-icon="ArrowRight">
        <template v-for="item in renderData" :key="item.id">
          <el-breadcrumb-item :to="{ path: item.path }" v-if="item.path">{{
            item.name
          }}</el-breadcrumb-item>
          <el-breadcrumb-item v-else>{{ item.name }}</el-breadcrumb-item>
        </template>
      </el-breadcrumb>
    </div>
  </div>
</template>

<script>
import { ArrowRight } from '@element-plus/icons-vue'
export default {
  name: 'YhBreadcrumb',
  props: {
    renderData: {
      type: [Array],
      required: true,
    },
  },
  setup() {
    return {
      ArrowRight,
    }
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
.yh-breadcrumb {
  width: 100%;
  display: flex;
  align-items: center;
  font-size: 14px;
  line-height: 34px;
  color: #888888;
  padding: 10px 0;
}
</style>
