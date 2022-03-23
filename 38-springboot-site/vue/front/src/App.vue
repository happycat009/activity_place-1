<template>
<!--Config Provider  被用来提供全局的配置选项 customData.locale全局被访问-->
  <el-config-provider :locale="customData.locale">
    <router-view></router-view>
  </el-config-provider>
</template>

<script setup>
import { reactive } from 'vue'
import { useRoute } from 'vue-router'
import { ElConfigProvider } from 'element-plus'

import zhCn from 'element-plus/lib/locale/lang/zh-cn'
const route = useRoute()

// console.log(route.meta.keepAlive, 9988776655)

const customData = reactive({
  locale: zhCn,
})
</script>

<style lang="scss">
#app {
  width: 100%;
  overflow: auto;
}
</style>
