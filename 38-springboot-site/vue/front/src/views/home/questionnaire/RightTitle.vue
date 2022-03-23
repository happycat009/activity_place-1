<template>
  <div class="right-title">
    <div class="title">违纪投诉电话</div>
    <div class="items">
      <div class="item">
        <div class="name">内江市商务局纪检组</div>
        <div class="cell">投诉电话：0832-8324255</div>
        <div class="cell">咨询电话：0832-8324098</div>
        <div class="cell">联系地址：内江市东兴区太白路49号</div>
      </div>
      <div class="item">
        <div class="name">内江市经济和信息化局纪检组</div>
        <div class="cell">投诉电话：0832-2041934</div>
        <div class="cell">咨询电话：0832-2024554</div>
        <div class="cell">
          <div class="key">联系地址：</div>
          <div class="value">内江市市中区新华路20号</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped>
.right-title {
  width: 100%;
  color: #333333;
  .title {
    font-size: 20px;
    font-weight: 700;
    line-height: 1;
    padding-top: 16px;
    padding-bottom: 20px;
    text-align: center;
  }
  .items {
    background-color: #f3f9ff;
    padding: 0 16px;
    color: #333333;
    .item {
      border-bottom: 2px solid #fff;
      padding: 30px 8px 18px;

      .name {
        font-size: 16px;
        line-height: 1;
        padding-bottom: 12px;
      }
      .cell {
        padding: 3px 0;
        font-size: 14px;
        line-height: 20px;
        display: flex;
        .value {
          flex: 1;
          overflow: hidden;
        }
      }
      &:last-child {
        border-bottom: 0;
      }
    }
  }
}
</style>
