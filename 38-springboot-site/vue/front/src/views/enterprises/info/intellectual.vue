<template>
  <div class="intellectual-property">
    <YhTitle title="知识产权" type="03"></YhTitle>
    <!--<div class="content">intellectual-property</div>-->
    <div class="content">
      <YhUnderDev></YhUnderDev>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
</script>

<style lang="scss" scoped>
.intellectual-property {
  width: 100%;
  padding: 30px;
  .content {
    padding-top: 30px;
  }
}
</style>
