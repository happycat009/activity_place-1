<template>
  <div class="company-archives" v-if="renderData && renderData.aab220">
    <YhTitle
      title="企业档案"
      titleEn="ENTERPRISE ARCHIVVES"
      type="05"
    ></YhTitle>
    <div class="main-wrapper" :style="wrapperStyle">
      <div class="desc">
        <p>{{ renderData.aab228 }}</p>
      </div>
    </div>
    <div class="inner-image">
      <img src="~/assets/images/propaganda/inner.png" alt="">
    </div>
    <!-- <div class="company-achievement">
      <div class="box" :style="{'background':'url('+img.imageBoxBg+')' + 'no-repeat 0 0','background-size':'233px 104px'}" v-for="{item,index} in 4" :key="index">
        <div class="box-inner">
          <div class="number"><span>40</span>项</div>
          <div class="text">30年的专业技术</div>
        </div>
      </div>
    </div> -->
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'

import imageBoxBg from '@/assets/images/propaganda/box-bg.png'

import { reactive } from 'vue'

const props = defineProps({
  renderData: {
    type: Object,
    required: true,
  },
})

const img = reactive({
  imageBoxBg
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

  .inner-image{
    margin: 30px 0 47px 0;
  }

  .company-achievement{
    display: flex;
    justify-content: space-between;
    .box{
      width: 233px;
	    height: 104px;
      display: flex;
      align-items: center;
      // border-radius: 4px;
	    // border: solid 2px rgba(208, 230, 255, 0.51);
      .box-inner{
        margin-left: 92px;
        .number{
          font-size: 12px;
          color: #98a4c0;
          span{
            font-size: 30px;
            color: #3e86ff;
            font-weight: 600;
          }
        }
        .text{
          color: #38578b;
          font-size: 16px;
          max-width: 120px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
      }
    }
  }
}
</style>
