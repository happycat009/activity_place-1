<template>
  <div class="key-projects">
    <YhTitle title="重点项目" type="03"></YhTitle>
    <!--<div class="content">key-key-projects</div>-->
    <div class="content">
      <YhUnderDev></YhUnderDev>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
</script>

<style lang="scss" scoped>
.key-projects {
  width: 100%;
  padding: 30px;
  .content {
    padding-top: 30px;
  }
}
</style>
