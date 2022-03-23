<template>
  <div class="home-index">
    <HomeStatistics></HomeStatistics>
    <HomePolicyThroughTrain></HomePolicyThroughTrain>
    <HomeCloudCommunity></HomeCloudCommunity>
    <HomeWalkIntoCity></HomeWalkIntoCity>
  </div>
</template>

<script>
import HomeStatistics from './statistics.vue'
import HomePolicyThroughTrain from './PolicyThroughTrain.vue'
import HomeCloudCommunity from './cloudCommunity.vue'
import HomeWalkIntoCity from './walkIntoCity.vue'
export default {
  name: 'HomeIndex',
  data() {
    return {}
  },
  components: {
    HomeStatistics,
    HomePolicyThroughTrain,
    HomeCloudCommunity,
    HomeWalkIntoCity,
  },
  mounted() {},
  computed: {},
}
</script>

<style lang="scss" scoped>
.home-index {
  width: 100%;
  background-color: #fff;
}
</style>
