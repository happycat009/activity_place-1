<template>
  <div v-if="new Date(hi05.chi059)>new Date()" class="policy-item information-title" @click="jumptopdf">
    <div class="left">
      <img
        :src="image_default"
        alt=""
      />
    </div>
    <div class="right">
      <div class="title">{{ hi05.chi05c }}{{ hi05.chi051 }}</div>
      <div class="source">
        <span class="unit"> 发文单位:{{hi05.chi037_desc}}</span>
        <span class="time">
          发布时间:
          {{ hi05.chi052.substring(0, 10) }}
        </span>
        <span class="time">
          有效时间:
          {{ hi05.chi058.substring(0, 10) }} 至 {{ hi05.chi059.substring(0, 10) }}
        </span>
      </div>
      <div class="content">
        {{ hi05.chi054 }}
      </div>
    </div>
  </div>
</template>
<script>
import { request,downloadFile } from '@/service/axios'
import {requestUrl} from "@/config/city";
import image_default from './images/default.png'

export default {
  name: 'PolicyItem',
  data() {
    return {
      hi05list: [],
      requestUrl:requestUrl,
      image_default
    }
  },
  props: {
    hi05: {
      type: Object,
      default: () => {
        return {}
      },
    },
  },
  methods: {
    jumptopdf() {
      if (!!this.hi05.chi215) {
        downloadFile(this.hi05.chi215,'0')
      }else {
        this.$message.error("该政策导读不是政策原文")
      }
    },
  },
  mounted() {},
}
</script>
<style lang="scss" scoped>
.information-title {
  cursor: pointer; /*鼠标悬停变小手*/
}
.policy-item {
  display: flex;
  height: 144px;
  justify-content: space-between;
  padding: 10px;
  margin-bottom: 40px;
  .left {
    height: 124px;
    width: 124px;
    margin-right: 10px;
    img {
      width: 124px;
      height: 124px;
      display: block;
      object-fit: cover;
      background-clip: padding-box;
      box-sizing: border-box;
    }
  }
  .right {
    width: 784px;
    .title {
      width: 784px;
      white-space: nowrap;
      text-overflow: ellipsis;
      overflow: hidden;
      font-size: 18px;
      font-weight: 700;
    }
    .source {
      font-size: 16px;
      color: #666;
      margin: 10px 0 5px 0;
      span {
        margin-right: 30px;
      }
    }
    .content {
      overflow: hidden;
    }
  }
}
</style>
