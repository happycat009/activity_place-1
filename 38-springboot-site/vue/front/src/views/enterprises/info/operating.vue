<template>
  <div class="operating-data">
    <YhTitle title="经营数据" type="03"></YhTitle>
    <!--<div class="content">OperatingData</div>-->
    <div class="content">
      <YhUnderDev></YhUnderDev>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
</script>

<style lang="scss" scoped>
.operating-data {
  width: 100%;
  padding: 30px;
  .content {
    padding-top: 30px;
  }
}
</style>
