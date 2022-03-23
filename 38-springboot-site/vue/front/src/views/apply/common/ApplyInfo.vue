<template>
  <div class="apply-info">
    <YhTitle title="业务受理信息" type="04"></YhTitle>
    <slot></slot>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
</script>

<style lang="scss" scoped>
.apply-info {
  width: 100%;
  padding-top: 36px;
}
</style>
