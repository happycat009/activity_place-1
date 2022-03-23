<template>
  <div class="query-result-item-card-wrapper" :class="['item-card-' + index]">
    <div class="company-info-wrapper">
      <div class="left">
        <img
          src="https://www.yinhai.com/psp/resource/yhsite/YHSITE/site-v2/image/home/QRCodeNew.jpg"
          alt=""
        />
      </div>
      <div class="right">
        <div class="name">{{companyList.aab012}}</div>
        <div class="tag">{{getCollectionDesc("aka131",companyList.aka131)}}</div>
      </div>
    </div>
     <div class="cells">
      <div class="cell">
        <div class="key">项目类型：</div>
        <div class="value">资金扶持</div>
      </div>
      <div class="cell">
        <div class="key">扶持金额：</div>
        <div class="value primary">{{unitConvert(companyList.aae019||0).num + unitConvert(companyList.aae019||0).unit }}</div>
        <div class="unit primary">元</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QueryResultItemCard',
  props: {
    index: {
      type: [Number],
      default: 0,
    },
    companyList: {
      type: [Array],
      default: () => {
        return []
      }
    }
  },
  data() {
    return {}
  },
  components: {},
  mounted() {
    this.userCodeTableMap("aka131")
    console.log("99999999999",this.companyList)
  },
  computed: {},
}
</script>

<style lang="scss" scoped>
.query-result-item-card-wrapper {
  width: 264px;
  // height: 144px;
  background-color: #fff;
  border: solid 2px #e6f1ff;
  margin-right: 32px;
  margin-top: 15px;
  padding: 20px 12px;
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.3s;

  .company-info-wrapper {
    display: flex;
    align-items: center;
    // padding-bottom: 20px;

    .left {
      width: 48px;
      height: 48px;
      background-image: linear-gradient(0deg, #3258e6 0%, #26abff 100%);
      border-radius: 50%;
      overflow: hidden;
      display: flex;
      img {
        width: 60%;
        margin: auto;
      }
    }
    .right {
      flex: 1;
      overflow: hidden;
      font-size: 14px;
      line-height: 1;
      color: #333333;
      padding-left: 12px;
      .name {
        font-weight: 700;
        padding-top: 8px;
        @include textOverflow();
      }
      .tag {
        font-size: 12px;
        padding-top: 6px;
        color: $primary;
      }
    }
  }
  .cells {
    border-top: 1px solid #e6f1ff;
    font-size: 14px;
    font-weight: 400;
    line-height: 18px;
    color: #333333;
    padding-top: 8px;
    .cell {
      display: flex;
    }
    .primary {
      color: #ffa200;
    }
  }
  &:nth-of-type(4n) {
    margin-right: 0;
  }
  &.item-card-2 {
    .company-info-wrapper {
      .left {
        background-image: linear-gradient(
          0deg,
          #00aeff 1%,
          #00d7fb 50%,
          #00fff6 100%
        );
      }
    }
  }
  &.item-card-3 {
    .company-info-wrapper {
      .left {
        background-image: linear-gradient(0deg, #ff4200 0%, #ffb400 100%);
      }
    }
  }
  &.item-card-4 {
    .company-info-wrapper {
      .left {
        background-image: linear-gradient(
          0deg,
          #3c00ff 0%,
          #883dff 50%,
          #d178ff 100%
        );
      }
    }
  }
  &:hover {
    transform: scale(1.1);
  }
}
</style>
