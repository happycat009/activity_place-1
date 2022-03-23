<template>
  <div class="apply-project-info" v-if="renderData && renderData.chi030">
    <h3>{{ renderData.chi032 }}</h3>
    <div class="cells">
      <div class="cell" v-if="endTime">
        <div class="key">申报截止日期：</div>
        <div class="value">{{ endTime }}</div>
      </div>
      <div class="cell" v-if="chi037Desc">
        <div class="key">主管部门：</div>
        <div class="value">{{ chi037Desc }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import { requestGetCollectionData } from '@/service'
import { useMoment } from '@/use'

const chi037Desc = ref('')
const props = defineProps({
  renderData: {
    type: Object,
    default: () => {
      return {
        chi030: '',
        chi032: '',
      }
    },
  },
})

// 申报开始日期
// const startTime = computed(() => {
//   let str = ''
//   const { bhi036 } = props.renderData
//   if (bhi036) {
//     str = useMoment(bhi036, 'YYYY年MM月DD日')
//   }
//   return str
// })

// 申报结束日期
const endTime = computed(() => {
  let str = ''
  const { bhi036 } = props.renderData
  if (bhi036) {
    str = useMoment(bhi036, 'YYYY年MM月DD日')
  }
  return str
})

// get
const getData = async (val) => {
  const res = await requestGetCollectionData('chi037', val)
  chi037Desc.value = res
}

watch(
  props,
  (val) => {
    const { renderData } = val
    if (renderData && renderData.chi037) {
      getData(renderData.chi037)
    }
  },
  { immediate: true }
)
</script>

<style lang="scss" scoped>
.apply-project-info {
  width: 100%;
  padding-bottom: 30px;

  h3 {
    text-align: center;
    font-size: 24px;
    font-weight: 700;
    line-height: 1;
    color: #333333;
  }
  .cells {
    display: flex;
    justify-content: center;
    align-items: center;
    line-height: 1;
    color: #999;
    padding-top: 16px;
    .cell {
      display: flex;
      align-items: center;
      margin-right: 30px;
      &:last-child {
        margin-right: 0;
      }
    }
  }
}
</style>
