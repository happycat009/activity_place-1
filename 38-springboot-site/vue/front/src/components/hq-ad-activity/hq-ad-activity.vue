<template>
  <div class="hq-ad-activity-wrapper" @click="handleClick">
    <img :src="imageBg" alt="" />
  </div>
</template>

<script>
import imageBg from './images/bg.png'
export default {
  name: 'HqAdActivity',
  props: {},
  setup() {
    return {}
  },
  data() {
    return {
      imageBg,
    }
  },
  created() {},
  mounted() {},
  components: {},
  computed: {},
  methods: {
    handleClick() {
      this.$router.push('/home/news/234')
    },
  },
}
</script>

<style lang="scss" scoped>
.hq-ad-activity-wrapper {
  width: 100%;
  cursor: pointer;
  .bg {
    width: 100%;
  }
}
</style>
