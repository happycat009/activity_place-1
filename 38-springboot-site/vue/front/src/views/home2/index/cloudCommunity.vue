<template>
  <div class="home-community-wrapper">
    <div class="container">
      <YhTitle
        title="甜企云社区"
        titleEn="Sweet Enterprise Cloud Community"
        type="01"
      ></YhTitle>
      <div class="content-wrapper">
        <div class="corporate-report-wrapper">
          <YhTitle title="企业报道" type="02" rightText="更多>>"></YhTitle>
          <div class="items">
            <template v-for="item in listb" :key="item.id">
              <div class="item">
                <HqPolicy02 :renderData="item"></HqPolicy02>
              </div>
            </template>
          </div>
        </div>
        <div class="well-known-enterprises-wrapper">
          <YhTitle title="知名企业" type="02" rightText="更多>>"></YhTitle>
          <div class="items">
            <template v-for="item in lista" :key="item.id">
              <div class="item"></div>
            </template>
          </div>
        </div>
      </div>
      <div class="content-wrapper">
        <HqAdActivity></HqAdActivity>
      </div>
    </div>
  </div>
</template>

<script>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import HqPolicy02 from '@/components/hq-policy-02/hq-policy-02.vue'
import HqAdActivity from '@/components/hq-ad-activity/hq-ad-activity.vue'

export default {
  name: 'HomeCloudCommunity',
  props: {},
  setup() {
    return {}
  },
  data() {
    return {
      lista: [
        {
          id: '01',
        },
        {
          id: '02',
        },
        {
          id: '03',
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
      ],
    }
  },
  created() {},
  mounted() {},
  components: { YhTitle, HqPolicy02, HqAdActivity },
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.home-community-wrapper {
  width: 100%;
  padding-bottom: $spacing-title * 2;
  .content-wrapper {
    display: flex;
    justify-content: space-between;
    padding-top: $spacing-title;
  }
  .corporate-report-wrapper {
    width: 638px;
    .items {
      margin-top: $spacing-title;
      background-image: linear-gradient(#f7faff, #f7faff),
        linear-gradient(#ffffff, #ffffff);
      padding: $spacing - 14px $spacing;
    }
  }
  .well-known-enterprises-wrapper {
    width: 518px;
    .items {
      display: flex;
      align-items: center;
      padding-top: $spacing-title;
    }
    .item {
      width: 158px;
      height: 158px;
      border: solid 1px #e4e4e4;
      margin-right: 20px;
      &:last-child {
        margin-right: 0;
      }
    }
  }
}
</style>
