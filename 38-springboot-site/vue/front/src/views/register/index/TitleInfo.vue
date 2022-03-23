<template>
  <div class="title-info">
    <h3>企业注册</h3>
    <!-- <div class="cells">
      <div class="cell">
        <div class="key">申报日期：</div>
        <div class="value">2022年1月1日 至 2022年3月22日</div>
      </div>
      <div class="cell">
        <div class="key">主管部门：</div>
        <div class="value">商务局</div>
      </div>
    </div> -->
  </div>
</template>

<script>
export default {
  name: 'TitleInfo',
  data() {
    return {}
  },
  components: {},
  mounted() {},
  computed: {},
}
</script>

<style lang="scss" scoped>
.title-info {
  width: 100%;
  padding-bottom: 30px;

  h3 {
    text-align: center;
    font-size: 24px;
    font-weight: 700;
    line-height: 1;
    color: #333333;
  }
  .cells {
    display: flex;
    justify-content: center;
    align-items: center;
    line-height: 1;
    color: #999;
    padding-top: 16px;
    .cell {
      display: flex;
      align-items: center;
      margin-right: 30px;
      &:last-child {
        margin-right: 0;
      }
    }
  }
}
</style>
