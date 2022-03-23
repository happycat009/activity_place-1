<template>
  <component :is="isRouteHome ? 'HomeIndex' : 'LayoutsDefault'"></component>
</template>

<script>
import LayoutsDefault from 'layouts/default/index.vue'
import HomeIndex from './index/index.vue'
export default {
  name: 'Home',
  data() {
    return {
      isRouteHome: true, // 当前路由是否为一级路由
    }
  },
  components: {
    LayoutsDefault,
    HomeIndex,
  },
  mounted() {},
  computed: {},
  watch: {
    $route: {
      handler(val) {
        if (val) {
          const { fullPath } = val
          let fullPathArr = fullPath.split('/')
          if (fullPathArr.length == 2 && fullPathArr[1] == 'home') {
            this.isRouteHome = true
          } else {
            this.isRouteHome = false
          }
        }
      },
      immediate: true,
    },
  },
}
</script>

<style lang="scss" scoped></style>
