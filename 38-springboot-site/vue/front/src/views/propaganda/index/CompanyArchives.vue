<template>
  <div class="company-archives" v-if="renderData && renderData.aab220">
    <YhTitle
      title="企业档案"
      titleEn="ENTERPRISE ARCHIVVES"
      type="01"
    ></YhTitle>
    <div class="main-wrapper" :style="wrapperStyle">
      <div class="desc">
        <p>{{ renderData.aab228 }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'

const props = defineProps({
  renderData: {
    type: Object,
    required: true,
  },
})
</script>

<style lang="scss" scoped>
.company-archives {
  width: 100%;

  .main-wrapper {
    margin-top: 16px;
    font-size: 16px;
    line-height: 30px;
    color: #333;
    .desc {
      p {
        margin-bottom: 16px;
        text-indent: 2em;
      }
    }
  }
}
</style>
