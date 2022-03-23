<template>
  <div class="calculator">
    <div class="container">
      <YhBreadcrumb :renderData="breadcrumbData"></YhBreadcrumb>
      <DeclaredItems></DeclaredItems>
    </div>
  </div>
</template>

<script setup>
import YhBreadcrumb from '@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue'
import DeclaredItems from './DeclaredItems.vue'

import { reactive } from 'vue'

const breadcrumbData = [
  {
    id: '01',
    path: '/home',
    name: '首页',
  },
  {
    id: '02',
    path: '/calculator',
    name: '政策计算器',
  },
]
</script>

<style lang="scss" scoped>
.calculator {
  width: 100%;
  padding-bottom: 30px;
  min-height: 500px;
}
</style>
