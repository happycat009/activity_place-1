<template>
  <div class="home-statistics-wrapper">
    <ul class="container">
      <template v-for="item in list" :key="item.id">
        <li>
          <YhIconKeyValue :renderData="item"></YhIconKeyValue>
        </li>
      </template>
    </ul>
  </div>
</template>

<script>
import { requestGetData } from '@/service'
import image01 from '@/assets/images/home/statistics/01.png'
import image02 from '@/assets/images/home/statistics/02.png'
import image03 from '@/assets/images/home/statistics/03.png'
import image04 from '@/assets/images/home/statistics/04.png'
import image05 from '@/assets/images/home/statistics/05.png'
import image06 from '@/assets/images/home/statistics/06.png'

import YhIconKeyValue from '@/components/common/YhIconKeyValue/YhIconKeyValue.vue'
export default {
  name: 'HomeStatistics',
  props: {},
  setup() {
    return {}
  },
  data() {
    return {
      list: [
        {
          id: 'zzsb',
          iconSrc: image01,
          value: 123,
          unit: '家',
          key: '正在申报',
        },
        {
          id: 'fbhqzc',
          iconSrc: image02,
          value: 123,
          unit: '项',
          key: '发布惠企政策',
        },
        {
          id: 'dffczj',
          iconSrc: image03,
          value: 123,
          unit: '万元',
          key: '兑付扶持资金',
        },
        {
          id: 'jrsb',
          iconSrc: image04,
          value: 123,
          unit: '笔',
          key: '今日申报',
        },
        {
          id: 'jrfwl',
          iconSrc: image05,
          value: 123,
          unit: '次',
          key: '今日访问量',
        },
        {
          id: 'ljfwl',
          iconSrc: image06,
          value: 123,
          unit: '次',
          key: '累计访问量',
        },
      ],
    }
  },
  created() {},
  mounted() {
    // debugger
    requestGetData("E401",null).then((res) => {
      console.log("统计结果",res)
    })
  },
  components: { YhIconKeyValue },
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.home-statistics-wrapper {
  width: 100%;
  padding-top: 70px;
  padding-bottom: 62px;
  ul {
    display: flex;
    align-items: center;
    li {
      width: 200px;
    }
  }
}
</style>
