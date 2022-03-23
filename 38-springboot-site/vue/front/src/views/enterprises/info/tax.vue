<template>
  <div class="tax-info">
    <YhTitle title="纳税信息" type="03"></YhTitle>
    <!--<div class="content">tax-info-info</div>-->
    <div class="content">
      <YhUnderDev></YhUnderDev>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
</script>

<style lang="scss" scoped>
.tax-info {
  width: 100%;
  padding: 30px;
  .content {
    padding-top: 30px;
  }
}
</style>
