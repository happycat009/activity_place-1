<template>
  <div class="hq-ad-city-wrapper">
    <div class="items">
      <template v-for="(item, index) in cityData" :key="item.id">
        <div class="item" :class="index % 2 == 0 ? 'odd' : ''">
          <div class="left">
            <div class="wrapper">
              <div class="index">
                {{ index < 10 ? '0' + (index + 1) : index + 1 }}
              </div>
              <div class="tag">重点产业</div>
              <div class="desc-img">
                <img :src="item.descSrc" alt="" />
              </div>
            </div>
            <div class="desc">{{ item.desc }}</div>
          </div>
          <div class="right">
            <img :src="item.imgSrc" alt="" />
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import config from '@/config'

import image_511000_img_01 from './images/img-01.png'
import image_511000_img_02 from './images/img-02.png'
import image_511000_img_03 from './images/img-03.png'
import image_511000_desc_01 from './images/desc-01.png'
import image_511000_desc_02 from './images/desc-02.png'
import image_511000_desc_03 from './images/desc-03.png'

export default {
  name: 'HqAdCity',
  props: {},
  setup() {
    return {}
  },
  data() {
    return {
      images: {
        desc: {
          511000: [
            image_511000_desc_01,
            image_511000_desc_02,
            image_511000_desc_03,
          ],
        },
        img: {
          511000: [
            image_511000_img_01,
            image_511000_img_02,
            image_511000_img_03,
          ],
        },
      },
      cityData: [],
    }
  },
  created() {},
  mounted() {
    this.initData()
  },
  components: {},
  computed: {},
  methods: {
    initData() {
      const { areaCode } = config
      const images = this.images
      const data = [
        {
          id: '01',
          desc: '新材料、新装备与机械汽配、新医药、新能源与节能环保、大数据与电子信息、特色农业、电子商务和现代化物流、文旅康养和现代商贸等9大重点产业。',
          descSrc: images.desc[areaCode][0],
          imgSrc: images.img[areaCode][0],
        },
        {
          id: '02',
          desc: '国家级内江经济技术开发区、国家级内江高新技术开发区、内江国家农业科技园区、隆昌经济开发区、资中经济开发区、威远经济开发区、内江东兴经济开发区、市中区工业经济开发区等8大重点园区。',
          descSrc: images.desc[areaCode][1],
          imgSrc: images.img[areaCode][1],
        },
        {
          id: '03',
          desc: '人才优势.创新力、资源优势.聚集力、人居优势.新引力、配套优势.保障力、服务优势.支持力、成本优势.优惠力、政策优势.护航力等7大投资优势。',
          descSrc: images.desc[areaCode][2],
          imgSrc: images.img[areaCode][2],
        },
      ]
      this.cityData = [...data]
    },
  },
}
</script>

<style lang="scss" scoped>
.hq-ad-city-wrapper {
  width: 100%;
  .item {
    display: flex;
    align-items: center;
    border: solid 1px #e4e4e4;
    margin-bottom: $spacing;
    cursor: pointer;

    .left {
      width: 50%;
      font-size: $font-size-sm;
      color: $color;
      padding: 22px $spacing 0;
      order: 2;
      .wrapper {
        justify-content: flex-end;
      }
    }
    .right {
      order: 1;
      width: 50%;
      overflow: hidden;
    }
    &:last-child {
      margin-bottom: 0;
    }
    &.odd {
      .left {
        order: 1;
        .wrapper {
          justify-content: flex-start;
        }
      }
      .right {
        order: 2;
      }
    }
  }
  .wrapper {
    display: flex;
    align-items: center;
    .index {
      font-size: 70px;
      line-height: 1;
      background-image: linear-gradient(to bottom, #00d2ff, #001eff);
      background-clip: text;
      -webkit-background-clip: text;
      color: transparent;
      margin-right: 15px;
    }
    .tag {
      width: 72px;
      font-size: 36px;
      font-weight: 400;
      line-height: 1;
      color: #323232;
      margin-right: 20px;
    }
    .desc-img {
      width: 132px;
    }
  }
  .desc {
    padding-top: 20px;
    line-height: 24px;
  }
}
</style>
