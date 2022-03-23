<template>
  <div class="company-archives" v-if="renderData && renderData.aab220">
    <YhTitle
      title="企业档案"
      titleEn="ENTERPRISE ARCHIVVES"
      type="01"
    ></YhTitle>
    <div class="main-wrapper" :style="wrapperStyle">
      <div class="desc">
        <p>{{ renderData.aab228 }}</p>
      </div>
      <div class="products-wrapper" v-if="renderData.products && renderData.products.length > 0">
        <div class="products-title">
          <img :src="imageProductTitle" mode="" />
        </div>
        <div class="products-con">
          <!-- <div class="bg">
            <img :src="imageBgProducts" mode="" />
          </div> -->
          <div class="products">
            <template v-for="item in renderData.products" :key="item.id">
              <div class="product">
                <img :src="requestUrl + '/upload/downFileByFileId?fileId=' + item.chi215" alt="" />
                <div class="name">{{ item.aab251 }}</div>
              </div>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import imageProductTitle from '@/assets/images/propaganda/huanglaowu/product-title.png'
import imageBgProducts from '@/assets/images/propaganda/huanglaowu/bg-products.png'
import { requestUrl } from "@/config/city";


import YhTitle from '@/components/project/YhTitle/YhTitle.vue'

const props = defineProps({
  renderData: {
    type: Object,
    required: true,
  },
})
</script>

<style lang="scss" scoped>
.company-archives {
  width: 100%;

  .main-wrapper {
    margin-top: 16px;
    font-size: 16px;
    line-height: 30px;
    color: #333;
    .desc {
      p {
        margin-bottom: 16px;
        text-indent: 2em;
      }
    }
  }
  .products-wrapper {
    padding-top: 20px;
    .products-title {
      img {
        width: 94px;
        margin: 0 auto;
      }
    }
    .products-con {
      position: relative;
      margin-top: 20px;
      .bg {
        width: 100%;
      }
      .products {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        .product {
          position: relative;
          white-space: nowrap;
          margin: 40px 40px 0 0;
          border: 1px solid #bfe8ff;
          width: 248px;
          height: 300px;
          img {
            max-width: 248px;
            max-height: 300px;
            object-fit: cover;
          }
          .name {
            position: absolute;
            left: 50%;
            bottom: 0;
            font-size: 14px;
            line-height: 1;
            transform: translateX(-50%);
            color: #000000;
          }
          &:nth-of-type(4n){
            margin-right: 0;
          }
        }
      }
    }
  }
}
</style>
