<template>
  <div class="walk-into-city-wrapper">
    <div class="container">
      <YhTitle
        title="走进内江"
        titleEn="Walk into Neijiang"
        type="01"
      ></YhTitle>
      <div class="content-wrapper">
        <HqAdCity></HqAdCity>
      </div>
    </div>
  </div>
</template>

<script>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import HqAdCity from '@/components/hq-ad-city/hq-ad-city.vue'
export default {
  name: 'WalkIntoCity',
  props: {},
  setup() {
    return {}
  },
  data() {
    return {}
  },
  created() {},
  mounted() {},
  components: { YhTitle, HqAdCity },
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.walk-into-city-wrapper {
  width: 100%;
  padding-bottom: $spacing-title * 2;
  .content-wrapper {
    padding-top: $spacing-title;
  }
}
</style>
