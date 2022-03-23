<template>
  <div class="policy-publish-unit">
    <div class="title-wrapper">
      <img
        src="~assets/images/policy/bg-publish-unit-header.png"
        alt=""
        class="bg"
      />
      <div class="con">政策直通车</div>
    </div>
    <div class="radio-group">
      <template v-for="(item, index) in data.tabList" :key="index">
        <div
          class="radio"
          @click="handleCheckTab(item, index)"
          :class="{ checked: index == data.isChecked }"
        >
          {{ item.name }}
        </div>
      </template>
      <!-- <template v-for="(item,index) in data.hi01List" :key="item.chi037">
        <div class="radio"  @click="emit('departSelected',item)">{{item.chi011}} <el-tag type="success">{{item.num}}项</el-tag></div>
      </template> -->
    </div>
  </div>
</template>

<script setup>
import { requestGetData } from '@/service'
import { getCurrentInstance, ref, onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const { proxy, appContext } = getCurrentInstance()
const refDom = ref()
const emit = defineEmits(['departSelected'])
const data = reactive({
  hi01List: [],
  tabList: [
    {
      name: '扶持项目',
      path: '/policy',
    },
    {
      name: '惠企政策导读',
      path: '/policy/index/guide',
    },
    {
      name: '政策计算器',
      path: '/calculator',
    },
    {
      name: '申报结果公示',
      path: '/home/publicity',
    },
  ],
  // 选中政策
  isChecked: '0',
})
const mounted = onMounted(() => {
  getHi01List()
  handleRouteCheckTab()
})

const getHi01List = () => {
  requestGetData('C402').then((res) => {
    if (res && res.code == '200' && res.data && res.data.c402List) {
      data.hi01List = res.data.c402List
    } else {
      appContext.config.globalProperties.$message.error({
        showClose: false,
        message: '获取业务局信息失败！！！',
        center: true,
        type: 'error',
      })
    }
  })
}

const handleCheckTab = (item, index) => {
  data.isChecked = index
  router.push(item.path)
}

const handleRouteCheckTab = () => {
  const path = router.currentRoute.value.path
  // console.log(path,'path')
  let guide = path.indexOf('guide')
  let projects = path.indexOf('supporting-projects')
  if (guide > 0) {
    data.isChecked = 1
  }
  if (projects > 0) {
    data.isChecked = 0
  }
}
</script>

<style lang="scss" scoped>
.policy-publish-unit {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  .title-wrapper {
    font-size: 16px;
    font-weight: 400;
    line-height: 50px;
    color: #ffffff;
    position: relative;
    .con {
      width: 100%;
      position: absolute;
      top: 0;
      left: 0;
      text-align: center;
    }
  }

  .radio-group {
    background-color: #f7faff;
    border: solid 1px #e4eaf2;
    padding-top: 16px;
    flex: 1;
    .radio {
      width: 149px;
      height: 32px;
      background-color: #ffffff;
      border-radius: 4px;
      border: solid 1px #c8d6e8;
      font-size: 14px;
      font-weight: 400;
      line-height: 30px;
      color: #333333;
      text-align: center;
      margin: auto;
      margin-bottom: 14px;
      cursor: pointer;
      transition: all 0.3s;
      &:hover,
      &.checked {
        color: #fff;
        border-color: $primary;
        background-color: $primary;
      }
    }
  }
}
</style>
