<template>
  <div class="hq-policy-02-wrapper">
    <div class="index"></div>
    <div class="name">【{{ renderData.cityName }}】{{ renderData.name }}</div>
    <div class="time">{{ renderData.time }}</div>
  </div>
</template>

<script>
export default {
  name: 'hqPolicy-02',
  props: {
    renderData: {
      type: [Object],
      required: true,
    },
  },
  setup() {
    return {}
  },
  data() {
    return {}
  },
  created() {},
  mounted() {},
  components: {},
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.hq-policy-02-wrapper {
  width: 100%;
  position: relative;
  color: $color;
  font-size: $font-size-sm;
  line-height: 1;
  cursor: pointer;
  transition: all 0.3s;
  padding: 14px 0;
  display: flex;
  align-items: center;
  padding-left: 12px;
  .index {
    width: 4px;
    height: 4px;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    left: 0;
    background-color: $color;
    transition: all 0.3s;
  }

  .name {
    flex: 1;
    overflow: hidden;
    @include textOverflow();
  }

  .time {
    padding-left: 30px;
  }
  &:hover {
    color: $primary;
    .index {
      background-color: $primary;
    }
  }
}
</style>
