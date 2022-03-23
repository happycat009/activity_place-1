<template>
  <div class="hq-policy-01-wrapper">
    <div class="bg">
      <img :src="imageBg" alt="" />
    </div>
    <div class="content">
      <div class="name">【{{ renderData.cityName }}】{{ renderData.name }}</div>
      <div class="end-time">申报截止时间：{{ renderData.endTime }}</div>
      <div class="rate">匹配度：{{ renderData.rate }}</div>
      <div class="time">
        <span>剩余 </span>
        <span class="val"> {{ renderData.time }} </span>
        <span> 天</span>
      </div>
    </div>
  </div>
</template>

<script>
import imageBg from './images/bg.png'
export default {
  name: 'hqPolicy-01',
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
    return {
      imageBg,
    }
  },
  created() {},
  mounted() {},
  components: {},
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.hq-policy-01-wrapper {
  width: 100%;
  position: relative;
  color: #fff;
  font-size: 14px;
  line-height: 1;
  cursor: pointer;
  transition: all 0.3s;
  .content {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    padding: 16px;
  }
  .name {
    font-size: 16px;
    font-weight: 700;
  }
  .end-time {
    padding-top: 16px;
  }
  .rate {
    padding-top: 12px;
  }
  .time {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    right: 42px;
    font-weight: 700;
    .val {
      color: #ffde00;
    }
  }
  &:hover {
    transform: scale(1.1);
  }
}
</style>
