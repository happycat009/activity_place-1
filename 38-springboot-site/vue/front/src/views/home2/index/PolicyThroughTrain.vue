<template>
  <div class="home-policy-through-train-wrapper">
    <div class="container">
      <YhTitle
        title="政策直通车"
        titleEn="Policy through train"
        type="01"
      ></YhTitle>
      <div class="content-wrapper">
        <div class="map-wrapper">
          <PolicyThroughTrainMap></PolicyThroughTrainMap>
        </div>
        <div class="policy-wrapper">
          <YhTitle title="政策" type="02" rightText="更多>>"></YhTitle>
          <div class="items">
            <template v-for="item in lista" :key="item.id">
              <div class="item">
                <HqPolicy01 :renderData="item"></HqPolicy01>
              </div>
            </template>
          </div>
        </div>
      </div>
      <div class="content-wrapper">
        <div class="payment-wrapper">
          <YhTitle title="阳光公示" type="02" rightText="更多>>"></YhTitle>
          <div class="items">
            <template v-for="item in listb" :key="item.id">
              <div class="item">
                <HqPolicy02 :renderData="item"></HqPolicy02>
              </div>
            </template>
          </div>
        </div>
        <div class="calculator-wrapper">
          <YhTitle title="政策计算器" type="02" rightText="更多>>"></YhTitle>
          <div class="item">
            <HqPolicy03></HqPolicy03>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import PolicyThroughTrainMap from './PolicyThroughTrainMap.vue'
import HqPolicy01 from '@/components/hq-policy-01/hq-policy-01.vue'
import HqPolicy02 from '@/components/hq-policy-02/hq-policy-02.vue'
import HqPolicy03 from '@/components/hq-policy-03/hq-policy-03.vue'
export default {
  name: 'HomePolicyThroughTrain',
  props: {},
  setup() {},
  data() {
    return {
      lista: [
        {
          id: '01',
          cityName: '内江市',
          name: '中小企业成长资金支持',
          endTime: '2021-10-09',
          rate: '80%',
          time: '1',
        },
        {
          id: '02',
          cityName: '内江市',
          name: '中小企业成长资金支持',
          endTime: '2021-10-09',
          rate: '80%',
          time: '1',
        },
        {
          id: '03',
          cityName: '内江市',
          name: '中小企业成长资金支持',
          endTime: '2021-10-09',
          rate: '80%',
          time: '1',
        },
        {
          id: '04',
          cityName: '内江市',
          name: '中小企业成长资金支持',
          endTime: '2021-10-09',
          rate: '80%',
          time: '1',
        },
      ],
      listb: [
        {
          id: '01',
          cityName: '内江市',
          name: '2021年度第二批市级跨境电子商务示范企业拟入选名单公示哈哈2021年度第二批市级跨境电子商务示范企业拟入选名单公示哈哈',
          time: '2021-10-09',
        },
        {
          id: '02',
          cityName: '内江市',
          name: '2021年度第二批市级跨境电子商务示范企业拟入选名单公示哈哈',
          time: '2021-10-09',
        },
        {
          id: '03',
          cityName: '内江市',
          name: '2021年度第二批市级跨境电子商务示范企业拟入选名单公示哈哈',
          time: '2021-10-09',
        },
        {
          id: '04',
          cityName: '内江市',
          name: '2021年度第二批市级跨境电子商务示范企业拟入选名单公示哈哈',
          time: '2021-10-09',
        },
      ],
    }
  },
  created() {},
  mounted() {},
  components: {
    YhTitle,
    PolicyThroughTrainMap,
    HqPolicy01,
    HqPolicy02,
    HqPolicy03,
  },
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.home-policy-through-train-wrapper {
  width: 100%;
  padding-bottom: $spacing * 2;
  .content-wrapper {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
    padding-top: $spacing-title * 2;
    .map-wrapper {
      width: 728px;
      height: 558px;
      // height: 100%;
      background-color: #f3f9ff;
    }
    .policy-wrapper {
      width: 430px;
      .items {
        padding-top: $spacing-title;
      }

      .item {
        margin-bottom: 30px;
        &:last-child {
          margin-bottom: 0;
        }
      }
    }
    .payment-wrapper {
      width: 638px;

      .items {
        margin-top: $spacing-title;
        background-image: linear-gradient(#f7faff, #f7faff),
          linear-gradient(#ffffff, #ffffff);
        padding: $spacing - 14px $spacing;
      }
    }
    .calculator-wrapper {
      width: 518px;
      .item {
        margin-top: $spacing-title;
      }
    }
  }
}
</style>
