<template>
  <div class="company-honor" v-if="renderData && renderData.length > 0">
    <YhTitle title="企业荣誉" titleEn="ENTERPRISE HONOR" type="05"></YhTitle>

    <div class="con-wrapper">
      <div class="total">
        <div class="item first">
          <img :src="imageHonor" alt="" />
          <div class="key">共获得：</div>
          <div class="value">{{ renderData?  renderData.length : 0 }}</div>
          <div class="unit">项</div>
        </div>
        <div class="line"></div>
        <template v-for="(value, key, index) in honorTotalData" :key="index">
          <div class="item">
            <img :src="honorStateImageArr[index]" alt="" />
            <div class="key">{{ key }}：</div>
            <div class="value">{{ value }}</div>
            <div class="unit">项</div>
          </div>
          <div class="line"></div>
        </template>
      </div>
      <div class="list">
        <template v-for="item in honorData" :key="item.cab230">
          <div class="item">
            <img
              :src="
                requestUrl + '/upload/downFileByFileId?fileId=' + item.chi215
              "
              alt=""
            />
            <div class="honor-data">
              <div class="title">{{item.cab232}}</div>
              <div class="text">{{item.aab235}}</div>
            </div>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import imageHonor from "@/assets/images/propaganda/honor.png";
import imageH01 from "@/assets/images/propaganda/h-01.png";
import imageH02 from "@/assets/images/propaganda/h-02.png";
import imageH03 from "@/assets/images/propaganda/h-03.png";
import imageBgHonor from "@/assets/images/propaganda/bg-honor.png";

import { requestUrl } from "@/config/city";

import YhTitle from "@/components/project/YhTitle/YhTitle.vue";

import { computed, reactive } from "vue";

const backStyle = computed(() => {
  let str = "";
  str += `background-image:url(${imageBgHonor}); `;
  return str;
});

const props = defineProps({
  renderData: {
    type: Array,
    required: true,
  },
});
const honorStateImageArr = [imageH01, imageH02, imageH03];

const honorState = {
  企业获奖: "1",
  企业技术能力: "2",
  企业资质: "3",
};

const honorData = computed(() => {
  const { renderData } = props;
  let arr = [];
  if (renderData && renderData.length > 0) {
    renderData.map((item) => {
      item.state = honorState[item.cab231];
      arr.push(item);
    });
  }

  return arr;
});

const honorTotalData = computed(() => {
  const { renderData } = props;
  let obj = {};
  if (renderData && renderData.length > 0) {
    renderData.map((item) => {
      if (obj[item.cab231]) {
        obj[item.cab231]++;
      } else {
        obj[item.cab231] = 1;
      }
    });
  }
  return obj;
});
</script>

<style lang="scss" scoped>
.company-honor {
  width: 100%;
  padding-top: 46px;
  .con-wrapper {
    width: 100%;
  }
  .total {
    display: flex;
    align-items: center;
    padding: 20px 0;
    justify-content: space-around;
    .line {
      width: 1px;
      height: 32px;
      background-color: #eef5ff;
      border-radius: 1px;
      &:nth-last-of-type(1) {
        display: none;
      }
    }
    .item {
      display: flex;
      align-items: center;
      font-size: 16px;
      line-height: 32px;
      color: #333;
      position: relative;
      img {
        width: 22px;
        margin-right: 10px;
      }
      .value {
        font-weight: 700;
        font-size: 24px;
        line-height: 32px;
        color: #0069ff;
        padding-bottom: 4px;
      }
      .unit {
        font-size: 12px;
        color: #666;
        padding-left: 2px;
        padding-top: 4px;
      }
      // &::after {
      //   content: "";
      //   position: absolute;
      //   top: 50%;
      //   right: 0;
      //   width: 1px;
      //   height: 32px;
      //   background-color: #eef5ff;
      //   border-radius: 1px;
      //   transform: translateY(-50%);
      // }
      &:nth-of-type(2) {
        .value {
          color: #0072ff;
        }
      }
      &:nth-of-type(3) {
        .value {
          color: #00ceff;
        }
      }
      &:nth-of-type(4) {
        // &::after {
        //   width: 0;
        // }
        .value {
          color: #ffb400;
        }
      }
      &.first {
        padding-left: 0;
        img {
          width: 58px;
          margin-right: 14px;
        }
      }
    }
  }
  .list {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    padding: 0 60px;
    .item {
      margin-bottom: 24px;
      margin-right: 84px;
      width: 277px;
      height: 208px;
      border: solid 1px #93bbff;
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      &:nth-of-type(3n){
        margin-right: 0;
      }
      img {
        max-width: 248px;
        max-height: 178px;
        object-fit: cover;
      }
      .honor-data{
        display: none;
        .title{
          max-width: 214px;
          text-align: center;
          margin: 0 auto;
          margin-bottom: 25px;
          margin-top: 29px;
        }
        .text{
          max-width: 214px;
          margin: 0 auto;
          max-height: 87px;
          overflow-y: auto;
        }
      }
      &:hover{
        .honor-data{
          display: block;
          position: absolute;
          top: 0;
          left: 0;
          height: 100%;
          width: 100%;
          background-color: #0032a2b3;
          color: #fff;
          z-index: 1;
        }
      }

    }
  }
}
</style>
