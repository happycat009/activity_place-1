<template>
  <div class="apply-type" v-if="renderData && renderData.length > 0">
    <YhTitle title="企业申报类别" type="04"></YhTitle>
    <div class="type-group">
      <el-radio-group
        :modelValue="modelValue"
        @update:modelValue="emit('update:modelValue', $event)"
      >
        <template v-for="item in renderData" :key="item.id">
          <el-radio :label="item.aka131" size="large" style="display: inline-block;">{{
            item.che142
          }}</el-radio>
        </template>
      </el-radio-group>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import YhTip from '@/components/common/YhTip/YhTip.vue'

const emit = defineEmits(['update:modelValue'])
const props = defineProps({
  modelValue: {
    type: [String],
    required: true,
  },
  renderData: {
    type: [Array],
    required: true,
  },
})
</script>

<style lang="scss" scoped>
.apply-type {
  width: 100%;
  padding-top: 70px - 24px;
  .type-group {
    padding-top: 30px;
  }
  .el-radio-group {
    display: block;
    .el-radio {
      display: block;
    }
  }
}
</style>
