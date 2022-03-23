<template>
  <div class="honor">
    <YhTitle title="荣誉资质" type="03"></YhTitle>
    <!--<div class="content">honor-info</div>-->
    <div class="content">
      <YhUnderDev></YhUnderDev>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
</script>

<style lang="scss" scoped>
.honor {
  width: 100%;
  padding: 30px;
  .content {
    padding-top: 30px;
  }
}
</style>
