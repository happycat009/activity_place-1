<template>
  <div class="query-result-item-title-wrapper">
    <img
      src="~assets/images/publicity/index/query-result/bg-title.png"
      alt=""
      class="bg"
    />
    <div class="content">
      <div class="title">
        {{"【" + titleData.chb57e.split(" ")[0] + "】" + titleData.chb57e.split(" ")[1]}}
      </div>
      <div class="cells">
        <div class="cell">
          <div class="key">主管部门：</div>
          <div class="value">{{titleData.chi037Desc}}</div>
        </div>
        <div class="cell">
          <div class="key">公示开始日期：</div>
          <div class="value">{{titleData.chb578}}</div>
        </div>
        <div class="cell">
          <div class="key">公示结束日期：</div>
          <div class="value">{{titleData.chb579}}</div>
        </div>
        <div class="cell">
          <div class="key">涉及企业（单位）：</div>
          <div class="value">{{titleData.ab01Total}}</div>
        </div>
        <div class="cell">
          <div class="key">浏览次数：</div>
          <div class="value">{{titleData.chb57f}}次</div>
        </div>
      </div>
      <div class="item-more">
        <button @click="handleViewMore(titleData.chb570)">更多>></button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QueryResultItemTitle',
  props:{
    titleData:{
      type: Object,
      defalut: () => {
        return {}
      }
    }
  },
  data() {
    return {}
  },
  components: {},
  mounted() {
    this.userCodeTableMap("chi037")
  },
  computed: {},
  methods:{
    handleViewMore(id) {
      // this.$router.push(`/home/publicity/details/${id}}`)
      this.$router.push("/home/publicity/details/" + id)
    },
  }
}
</script>

<style lang="scss" scoped>
.query-result-item-title-wrapper {
  position: relative;
  .content {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1;
    padding: 20px 0;
    .title {
      font-size: 18px;
      font-weight: 700;
      line-height: 1;
      color: #333333;
      padding: 0 16px;
      background: url(~assets/images/publicity/index/query-result/bg-title.png)
        no-repeat center center;
      background-size: 100% 100%;
    }
    .cells {
      font-size: 12px;
      line-height: 1;
      color: #999999;
      display: flex;
      align-items: center;
      padding: 0 24px;
      .cell {
        display: flex;
        align-items: center;
        padding-top: 10px;
        margin-right: 36px;
      }
    }
    .item-more {
      text-align: right;
      position: absolute;
      top: 50%;
      transform: translate(0,-50%);
      right: 10px;

      button {
        color: $primary;
        font-size: 16px;
        padding: 12px 0;
        cursor: pointer;
        transition: all 0.3s;
        &:hover {
          opacity: 0.8;
        }
      }
    }
  }
}
</style>
