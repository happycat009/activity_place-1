<template>
  <div class="yh-publish-unit">
    <div class="title-wrapper">
      <img
        src="~assets/images/policy/bg-publish-unit-header.png"
        alt=""
        class="bg"
      />
      <div class="con">发布单位</div>
    </div>
    <div class="radio-group">
      <template v-for="item in 8" :key="item.id">
        <div class="radio">商务局</div>
      </template>
    </div>
  </div>
</template>

<script>
export default {
  name: 'YhPublishUnit',
  data() {
    return {}
  },
  components: {},
  mounted() {},
  computed: {},
}
</script>

<style lang="scss" scoped>
.yh-publish-unit {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  .title-wrapper {
    font-size: 16px;
    font-weight: 400;
    line-height: 50px;
    color: #ffffff;
    position: relative;
    .con {
      width: 100%;
      position: absolute;
      top: 0;
      left: 0;
      text-align: center;
    }
  }

  .radio-group {
    background-color: #f7faff;
    border: solid 1px #e4eaf2;
    padding-top: 16px;
    flex: 1;
    .radio {
      width: 149px;
      height: 32px;
      background-color: #ffffff;
      border-radius: 4px;
      border: solid 1px #c8d6e8;
      font-size: 14px;
      font-weight: 400;
      line-height: 30px;
      color: #333333;
      text-align: center;
      margin: auto;
      margin-bottom: 14px;
      cursor: pointer;
      transition: all 0.3s;
      &:hover,
      &.checked {
        color: #fff;
        border-color: $primary;
        background-color: $primary;
      }
    }
  }
}
</style>
