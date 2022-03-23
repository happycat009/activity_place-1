<template>
  <div class="company-honor-2">
    <div class="inner-image">
      <img src="~/assets/images/propaganda/inner2.png" alt="" />
    </div>
    <YhTitle title="企业荣誉" titleEn="ENTERPRISE HONOR" type="01"></YhTitle>
    <div class="con-wrapper">
      <div class="total">
        <div class="item first">
          <img :src="imageHonor" alt="" />
          <div class="key">共获得：</div>
          <div class="value">{{ renderData ? renderData.length : 0 }}</div>
          <div class="unit">项</div>
        </div>
        <template v-for="(value, key, index) in honorTotalData" :key="index">
          <div class="item">
            <img :src="honorStateImageArr[index]" alt="" />
            <div class="key">{{ key }}：</div>
            <div class="value">{{ value }}</div>
            <div class="unit">项</div>
          </div>
        </template>
      </div>
      <div class="horon-list">
        <template v-for="(item, index) in newHoronData" :key="index">
          <li v-for="one in item" :key="one.cab230">
            <div class="left">
              <div class="time">
                <div class="left-icon">
                  <img src="~/assets/images/propaganda/time-icon.png" alt="" />
                </div>
                {{ one.month }}
              </div>
              <div class="name">{{ one.cab232 }}</div>
              <div class="from">颁发单位：{{one.aab236 || '暂无'}}</div>
            </div>
            <div class="right">
              <div
                class="honor-box"
                :style="{
                  background:
                    'url(' + imageBgHonor2 + ')' + '50% 50% no-repeat',
                }"
              >
                <img
                  :src="
                    requestUrl + '/upload/downFileByFileId?fileId=' + one.chi215
                  "
                  alt=""
                />
              </div>
            </div>
          </li>
          <div class="year-list">
            <div class="year">{{ item[0].year }}</div>
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
import imageBgHonor2 from "@/assets/images/propaganda/bg-honor2.png";

import { requestUrl } from "@/config/city";

import YhTitle from "@/components/project/YhTitle/YhTitle.vue";

import { computed, reactive } from "vue";
import moment from 'moment'


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

const newHoronData = computed(() => {
  const { renderData } = props;
  console.log('aaaaaaaaa',renderData)
  let newArr = [];
  if (renderData && renderData.length > 0) {
    let arr = [];
    let array = [...renderData];
    array.forEach((item) => {
      let time = moment(item.cab233).format("YYYY");
      let month = moment(item.cab233).format("YYYY-MM");
      let res = arr.findIndex((one) => one == time);
      let newItem = { ...item, year: time, month };
      if (res == -1) {
        arr.push(time);
        newArr.push([newItem]);
      } else {
        newArr[res].push(newItem);
      }
    });
  }
  console.log(newArr,'aaaa')
  return [...newArr];
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
.company-honor-2 {
  .inner-image {
    margin: 0 0 47px 0;
  }
  .con-wrapper {
    width: 100%;
  }
  .total {
    display: flex;
    align-items: center;
    padding: 20px 0;

    .item {
      display: flex;
      align-items: center;
      font-size: 16px;
      line-height: 32px;
      color: #333;
      padding: 0 36px;
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
      &::after {
        content: "";
        position: absolute;
        top: 50%;
        right: 0;
        width: 1px;
        height: 32px;
        background-color: #eef5ff;
        border-radius: 1px;
        transform: translateY(-50%);
      }
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
        &::after {
          width: 0;
        }
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
  .horon-list {
    li {
      padding-left: 209px;
      position: relative;
      list-style: none;
      display: flex;
      justify-content: space-between;
      align-items: center;
      overflow: hidden;
      .left {
        .time {
          color: #ff4300;
          font-size: 24px;
          margin-bottom: 10px;
          position: relative;
          .left-icon {
            position: absolute;
            top: 50%;
            left: -43px;
            transform: translate(0, -50%);
            z-index: 10;
          }
        }
        .name {
          font-size: 18px;
          color: #333333;
          margin-bottom: 10px;
          display: inline-block;
          position: relative;
          max-width: 180px;
          &::after {
            content: "";
            position: absolute;
            top: 50%;
            transform: translate(0, -50%);
            left: 100%;
            width: 500px;
            border-bottom: dashed 2px #ffa396;
          }
        }
        .from {
          font-size: 14px;
          color: #333333;
        }
      }
      .right {
        background: #fff;
        z-index: 3;
        position: relative;
        .honor-box {
          width: 272px;
          height: 220px;
          display: flex;
          justify-content: center;
          align-items: center;
          img {
            max-width: 235px;
            max-height: 157px;
            object-fit: cover;
            margin-top: 10px;
          }
        }
        &::after {
          content: "";
          position: absolute;
          width: 12px;
          height: 12px;
          background-color: #ff6934;
          top: 50%;
          left: -15px;
          border-radius: 50%;
        }
        &::before {
          content: "";
          position: absolute;
          width: 12px;
          height: 12px;
          background: #fff;
          top: 50%;
          left: -12px;
        }
      }
      &::after {
        content: "";
        width: 2px;
        background-color: #ffc8b5;
        height: 100%;
        position: absolute;
        top: 0;
        left: 177px;
        display: block;
        z-index: 1;
      }
      &:nth-of-type(2n) {
        .right {
          padding-right: 314px;
        }
      }
    }
    .year-list {
      width: 128px;
      border-right: 2px solid #ffc8b5;
      margin-left: 51px;
      position: relative;
      .year {
        width: 104px;
        height: 40px;
        background-color: #fff2ee;
        border-radius: 4px;
        border: solid 1px #ffd3c3;
        color: #ff4300;
        font-size: 18px;
        line-height: 40px;
        text-align: center;
        position: relative;
        &::after {
          content: "";
          position: absolute;
          border-bottom: 2px dashed #ff6934;
          top: 50%;
          left: 100%;
          width: 18px;
          transform: translate(0, -50%);
        }
      }
      &::after {
        content: "";
        position: absolute;
        width: 16px;
        height: 16px;
        background-color: #ff6934;
        border-radius: 50%;
        top: 50%;
        right: 0;
        transform: translate(50%, -50%);
      }
    }
  }
}
</style>
