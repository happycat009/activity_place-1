<template>
  <div class="service-guide-application-conditions">
    <YhTitle title="申请条件" type="03"></YhTitle>
    <div class="custom-table-wrapper">
      <div class="custom-table-cells">

        <div class="custom-table-cell">
          <div style="width: 100%">
            <el-input :rows="15" v-model="conditionInfo" readonly="true" type="textarea"></el-input>

          </div>
<!--          <div class="key">{{(index + 1)}}</div>-->
<!--          <div class="value">-->
<!--            {{item.chr023}}-->
<!--          </div>-->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
export default {
  name: 'ServiceGuideApplicationConditions',
  data() {
    return {}
  },
  props: {
    conditionInfo: {
      type: [Object],
      required: true,
      default: () => {}
    }
  },
  components: { YhTitle },
  mounted() {},
  computed: {},
}
</script>

<style lang="scss" scoped>
.service-guide-application-conditions {
  width: 100%;
  padding-bottom: 40px;
  .custom-table-wrapper {
    padding-top: 22px;
    padding-left: 14px;
  }
}
</style>
