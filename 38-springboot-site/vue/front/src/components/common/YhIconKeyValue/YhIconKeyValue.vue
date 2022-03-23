<template>
  <div class="yh-icon-key-value" :class="[parseInt(renderData.value)>0 ? 'active' : '']">
    <img :src="renderData.iconSrc" alt="" v-if="renderData.iconSrc" />
    <i class="iconimg" :class="['iconimg-' + renderData.icon]" v-else></i>
    <div class="info">
      <div class="value">
        <div class="val">{{ renderData.value }}</div>
        <div class="unit">{{ renderData.unit }}</div>
      </div>
      <div class="key">{{ renderData.key }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'YhIconKeyValue',
  props: {
    // 渲染数据
    renderData: {
      type: [Object],
      default: {},
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
.yh-icon-key-value {
  display: flex;
  align-items: center;
  cursor: pointer;
  user-select: none;
  &.active {
    position: relative;
    &::after {
      content: '';
      width: 8px;
      height: 8px;
      background-color: #ff0000;
      border: solid 1px #ffffff;
      position: absolute;
      top: 8px;
      left: 24px;
      border-radius: 50%;
    }
  }
  img,
  .iconimg {
    width: 40px;
    height: 40px;
    margin-right: 8px;
  }
  .info {
    font-size: 12px;
    font-weight: 400;
    line-height: 1;
    color: #656565;
  }
  .value {
    display: flex;
    align-items: flex-end;
    .val {
      font-size: 20px;
      font-weight: 700;
      color: #343434;
      line-height: 1;
      padding-right: 2px;
    }
  }
  .key {
    padding-top: 7px;
  }
  .unit {
    color: #656565;
  }
}
</style>
