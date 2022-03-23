<template>
  <div class="yh-company-info-tabs">
    <template
      v-for="(item, index) in customData.tabDataList"
      :key="item.aab100"
    >
      <div
        class="tab"
        :class="[item.checked ? 'checked' : '']"
        @click="handleJumpRoute(item)"
      >
        <img :src="item.imgSrc" alt="" />
        <span>{{ item.aab101 }}</span>
      </div>
    </template>
  </div>
</template>

<script setup>
import imageTabCBXX from './images/tab-cbxx.png'
import imageTabHBXX from './images/tab-hbxx.png'
import imageTabJBXX from './images/tab-jbxx.png'
import imageTabJYSJ from './images/tab-jysj.png'
import imageTabNSXX from './images/tab-nsxx.png'
import imageTabRSXX from './images/tab-rsxx.png'
import imageTabRYZZ from './images/tab-ryzz.png'
import imageTabXYXX from './images/tab-xyxx.png'
import imageTabZDXM from './images/tab-zdxm.png'
import imageTabZSCQ from './images/tab-zscq.png'

import { watch, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const props = defineProps({
  tabList: {
    type: Array,
    default: () => [],
  },
})

const route = useRoute()
const router = useRouter()

const customData = reactive({
  tabDataList: [],
})

const tabsImageData = {
  1000020: imageTabJBXX,
  1000021: imageTabRSXX,
  1000040: imageTabJYSJ,
  1000041: imageTabRYZZ,
  1000042: imageTabZSCQ,
  1000043: imageTabZDXM,
  1000044: imageTabNSXX,
  1000045: imageTabCBXX,
  1000046: imageTabXYXX,
  1000047: imageTabHBXX,
  1000080: imageTabZSCQ,
}

// 根据 props 初始化数据
const initTabDataList = (data) => {
  const { path } = route
  const pathArr = path.split('/')
  const pathName = pathArr[pathArr.length - 1]

  const arr = []
  data.map((item) => {
    let obj = {
      ...item,
      imgSrc: tabsImageData[item.aab100],
      checked: false,
    }
    if (item.aab107 == pathName) {
      obj.checked = true
    }
    arr.push(obj)
  })
  customData.tabDataList = arr
}

// 更新 tabDataList 数据
const updateTabDataList = (path) => {
  const data = customData.tabDataList
  if (data.length > 0) {
    const pathArr = path.split('/')
    const pathName = pathArr[pathArr.length - 1]
    data.map((item) => {
      item.checked = false
      if (item.aab107 == pathName) {
        item.checked = true
      }
    })
  }
}

// 路由跳转
const handleJumpRoute = (item) => {
  let routeStr = '/enterprises/info/'
  const { aab107 } = item
  router.push(routeStr + aab107)
}

watch(
  props,
  (val) => {
    const { tabList } = val
    if (tabList && tabList.length > 0) {
      initTabDataList(tabList)
    }
  },
  { immediate: true }
)

watch(
  route,
  (val) => {
    const { path } = val
    updateTabDataList(path)
  },
  { immediate: true }
)

// initData()
</script>

<style lang="scss" scoped>
.yh-company-info-tabs {
  width: 100%;
  .tab {
    padding: 0 16px;
    border-radius: 4px 0px 0px 4px;
    cursor: pointer;
    user-select: none;
    display: flex;
    align-items: center;
    font-size: 18px;
    line-height: 30px;
    color: #333333;
    transition: all 0.3s;
    height: 66px;
    &.checked,
    &:hover {
      color: var(--color-primary);
      font-weight: 700;
      background-color: #f3faff;
    }
    img {
      width: 30px;
      margin-right: 12px;
    }
  }
}
</style>
