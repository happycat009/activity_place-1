<template>
  <div class="page layout-default">
    <YhHeader></YhHeader>
    <template v-if="custom">
      <slot></slot>
    </template>
    <template v-else>
      <router-view />
    </template>
    <YhFooter></YhFooter>
    <YhBacktop></YhBacktop>
  </div>
</template>

<script setup>
import YhHeader from '@/components/project/YhHeader/YhHeader.vue'
import YhFooter from '@/components/project/YhFooter/YhFooter.vue'
import YhBacktop from '@/components/elementPlusExtend/YhBacktop/YhBacktop.vue'

defineProps({
  custom: {
    type: Boolean,
    default: () => false,
  },
})
</script>

<style lang="scss" scoped>
.layout-default {
  width: 100%;
}
</style>
