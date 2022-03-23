<template>
  <div class="tool-tabs">
    <template v-for="item in customData.list" :key="item.id">
      <div
        class="tab"
        @click="handleJumpRoute(item)"
        :class="[item.checked ? 'checked' : '']"
      >
        {{ item.name }}
      </div>
    </template>
  </div>
</template>

<script setup>
import { reactive, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const customData = reactive({
  list: [
    {
      id: '01',
      path: '/info',
      name: '企业资料',
    },
    {
      id: '02',
      path: '/recommend',
      name: '推荐申报项目',
    },
    {
      id: '03',
      path: '/record',
      name: '申报记录',
    },
    {
      id: '04',
      path: '/message',
      name: '我的消息',
    },
    {
      id: '05',
      path: '/account',
      name: '企业账号管理',
    },
    {
      id:'06',
      path: '/evaluate',
      name: '评价记录',
    }
  ],
})

// 更新tab list
const updateTabDataList = (path) => {
  customData.list.map((item) => {
    item.checked = false
    if (path.indexOf(item.path) != -1) {
      item.checked = true
    }
  })
}

// 路由跳转
const handleJumpRoute = (item) => {
  let routeStr = '/enterprises'
  const { path } = item
  router.push(routeStr + path)
}

watch(
  route,
  (val) => {
    const path = val.path
    updateTabDataList(path)
  },
  {
    immediate: true,
  }
)
</script>

<style lang="scss" scoped>
.tool-tabs {
  width: 100%;
  display: flex;
  align-items: center;
  .tab {
    width: 138px;
    height: 40px;
    border-radius: 8px 8px 0px 0px;
    font-size: 16px;
    line-height: 40px;
    color: var(--color-primary);
    text-align: center;
    cursor: pointer;
    user-select: none;
    margin-right: 12px;
    background-color: #eaf3ff;
    transition: all 0.3s;
    &:last-child {
      margin-right: 0;
    }
    &.checked,
    &:hover {
      color: #fff;
      font-weight: 700;
      background-color: var(--color-primary);
    }
  }
}
</style>
