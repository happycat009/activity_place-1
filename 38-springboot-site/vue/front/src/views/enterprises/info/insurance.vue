<template>
  <div class="insurance-info">
    <YhTitle title="参保信息" type="03"></YhTitle>
    <!--<div class="content">insurance-info</div>-->
    <div class="content">
      <YhUnderDev></YhUnderDev>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
</script>

<style lang="scss" scoped>
.insurance-info {
  width: 100%;
  padding: 30px;
  .content {
    padding-top: 30px;
  }
}
</style>
