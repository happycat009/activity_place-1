<template>
  <div class="city-index">
    <WalkIntoCity></WalkIntoCity>
  </div>
</template>

<script>
import WalkIntoCity from './walkIntoCity.vue'
export default {
  name: 'CityIndex',
  data() {
    return {}
  },
  components: {
    WalkIntoCity,
  },
  mounted() {},
  computed: {},
}
</script>

<style lang="scss" scoped>
.city-index {
  width: 100%;
  padding-top: 30px;
}
</style>
