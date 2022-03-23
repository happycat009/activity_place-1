<template>
  <div class="personnel-info">
    <YhTitle title="人事信息" type="03"></YhTitle>
    <!--<div class="content">personnel-info</div>-->
    <div class="content">
      <YhUnderDev></YhUnderDev>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
</script>

<style lang="scss" scoped>
.personnel-info {
  width: 100%;
  padding: 30px;
  .content {
    padding-top: 30px;
  }
}
</style>
