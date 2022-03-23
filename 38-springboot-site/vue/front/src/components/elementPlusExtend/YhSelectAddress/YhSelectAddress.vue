<template>
  <div class="yh-select-wrapper">
    <div
      class="label"
      :class="[required ? 'required' : '']"
      :style="labelStyle"
    >
      {{ label }}
    </div>
    <el-cascader
      :props="cascaderProps"
      :model-value="modelValue"
      placeholder="请选择地址"
      class="custom-cascader"
      :disabled="disabled"
      @change="handleChange"
    ></el-cascader>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { requestE407 } from '@/service'

const emit = defineEmits(['update:modelValue'])
const props = defineProps({
  modelValue: {
    type: String,
    required: true,
  },
  label: {
    type: String,
    required: true,
  },
  labelWidth: {
    type: String,
    default: '',
  },
  required: {
    type: Boolean,
    default: () => false,
  },
  disabled: {
    type: Boolean,
    defalut: () => false,
  }
})

// 获取数据
const getData = async (orgid = '110386') => {
  return new Promise((resolve, reject) => {
    requestE407(orgid)
      .then((res) => {
        resolve(res)
      })
      .catch((err) => {
        reject(err)
      })
  })
}

const labelStyle = computed(() => {
  let str = ''
  const { labelWidth } = props
  if (labelWidth) {
    str += `width:${labelWidth}; `
  }
  return str
})

const cascaderProps = {
  lazy: true,
  lazyLoad(node, resolve) {
    const { value } = node.data
    getData(value).then((res) => {
      const { orgList } = res.data
      if (orgList && orgList.length > 0) {
        const nodes = []
        let obj = { value: '', label: '', leaf: false }
        orgList.map((item) => {
          obj = {
            ...obj,
            value: item.orgid,
            label: item.orgname,
            leaf: item.isleaf == '1' ? false : true,
          }
          nodes.push(obj)
        })
        resolve(nodes)
      } else {
        resolve([])
      }
    })
  },
}

const handleChange = (val) => {
  const value = val.join('/')
  emit('update:modelValue', value)
}
</script>
<style lang="scss" scoped>
.yh-select-wrapper {
  display: flex;
  align-items: center;
  .label {
    position: relative;
    font-size: 14px;
    color: #333;
    line-height: 42px;
    text-align: right;
    &.required {
      &::before {
        content: '*';
        color: #f00;
        padding-right: 2px;
      }
    }
  }
  .el-cascader {
    overflow: hidden;
    flex: 1;
  }
}
</style>
