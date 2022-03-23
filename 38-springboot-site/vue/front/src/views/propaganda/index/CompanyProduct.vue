<template>
  <div class="company-product">
    <YhTitle title="主营产品" titleEn="MAIN PRODUCTS" type="01"></YhTitle>
    <div class="con-wrapper">
      <div class="products-list">
        <el-scrollbar>
          <div class="items">
            <div class="item" v-for="(item, index) in props.renderData" :key="index">
              <div class="img-box">
                <img :src="requestUrl + '/upload/downFileByFileId?fileId=' + item.chi215" alt="" />
              </div>
              <div class="name">{{item.aab251}}</div>
            </div>
          </div>
        </el-scrollbar>
      </div>
    </div>
  </div>
</template>
<script setup>
import { requestUrl } from "@/config/city";

const props = defineProps({
  renderData: {
    type: Array,
    required: true,
  },
});
</script>
<style lang="scss" scoped>
.company-product {
  width: 100%;
  padding-top: 46px;
  .con-wrapper {
    width: 100%;
    margin-top: 64px;
  }
  .products-list {
    width: 100%;
    white-space: nowrap;
    .items {
      display: flex;
      flex-wrap: nowrap;
      width: max-content;
      .item {
        margin-right: 26px;
        .img-box {
          height: 171px;
          display: flex;
          align-items: center;
          margin-bottom: 10px;
          img {
            max-height: 171px;
            max-width: 300px;
          }
        }
        .name {
          text-align: center;
          color: #333333;
          font-size: 16px;
        }
      }
    }
  }
}
</style>