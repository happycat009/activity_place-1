<template>
  <div class="apply-files-info">
    <YhTitle title="材料信息" type="04"></YhTitle>
    <div class="tip-wrapper">
      <YhTip
        color="#ed0000"
        msg="温馨提示：是否必传，为“是”的材料必须上传。"
      ></YhTip>
    </div>
    <div class="table-wrapper">
      <YhTableUploadFile
        :table-head="tableHead"
        :table-data="renderData"
        @change="emit('change', $event)"
      ></YhTableUploadFile>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import YhTip from '@/components/common/YhTip/YhTip.vue'
import YhTableUploadFile from '@/components/elementPlusExtend/YhTableUploadFile/YhTableUploadFile.vue'

const tableHead = [
  {
    prop: 'index',
    label: '序号',
  },
  {
    prop: 'che091',
    label: '材料名称',
  },
  {
    prop: 'che09b',
    label: '材料说明',
  },
  {
    prop: 'che09d',
    label: '文件格式',
  },
  {
    prop: 'che09a',
    label: '是否必要',
  },
  {
    prop: 'uploaded',
    label: '已上传材料',
  },
  {
    prop: 'num',
    label: '页数',
  },
  {
    prop: 'operation_preview',
    label: '材料示例',
  },
  {
    prop: 'operation_see',
    label: '查看文件',
  },
  {
    prop: 'operation_upload',
    label: '上传文件',
  },
]

const props = defineProps({
  // 渲染数据
  renderData: {
    type: Array,
    default: () => [],
  },
})

const emit = defineEmits(['change'])
</script>

<style lang="scss" scoped>
.apply-files-info {
  width: 100%;
  padding-top: 70px - 24px;
  .tip-wrapper {
    padding-top: 15px;
    padding-bottom: 23px;
  }
}
</style>
