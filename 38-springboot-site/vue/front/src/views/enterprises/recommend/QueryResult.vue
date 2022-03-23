<template>
  <div class="query-result">
    <template v-for="(item, index) in renderDataList" :key="index">
      <YhPolicyCard :renderData="item"></YhPolicyCard>
    </template>
  </div>
</template>

<script>
  import YhPolicyCard from '@/components/project/YhPolicyCard/YhPolicyCard.vue'
  export default {
    name: "RecommendQueryResult",
    components: { YhPolicyCard },
    props: {
      renderDataList: {
        type: [Array],
        required: true,
        default: () => []
      }
    },
    data() {
      return {

      }
    }
  }
</script>

<style lang="scss" scoped>
.query-result {
  width: 100%;
  padding-bottom: 30px;
}
</style>
