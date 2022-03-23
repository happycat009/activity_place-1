<template>
  <div class="recommand-declare">
    <div class="items">
      <div
        class="item"
        @click="isChecked = 0"
        :class="{ checked: isChecked == 0 }"
      >
        推荐申报项目（{{ recommendChi031List.length }}）
      </div>
      <div
        class="item"
        @click="isChecked = 1"
        :class="{ checked: isChecked == 1 }"
      >
        已过期申报项目（{{ overDurChi031List.length }}）
      </div>
    </div>

    <div class="query-results">
      <div v-if="isChecked == 0">
        <template v-if="recommendChi031List && recommendChi031List.length > 0">
          <template v-for="item in recommendChi031List" :key="item.id">
            <YhPolicyCard :renderData="item"></YhPolicyCard>
          </template>
        </template>
        <template v-else>
          <YhNoData></YhNoData>
        </template>
      </div>
      <div v-if="isChecked == 1">
        <template v-if="overDurChi031List && overDurChi031List.length > 0">
          <template v-for="item in overDurChi031List" :key="item.id">
            <YhPolicyCard :renderData="item"></YhPolicyCard>
          </template>
        </template>
        <template v-else>
          <YhNoData></YhNoData>
        </template>
      </div>
    </div>
  </div>
</template>
<script setup>
import YhNoData from '@/components/common/YhNoData/YhNoData.vue'
</script>
<script>
import YhPolicyCard from '@/components/project/YhPolicyCard/YhPolicyCard.vue'

import { requestGetData } from '@/service'
import { mapGetters } from 'vuex'
import { CORPORATE_INFO } from '@/store/constants'
import moment from 'moment'

export default {
  name: 'recommand-declare',
  components: {
    YhPolicyCard,
  },
  data() {
    return {
      queryResultList: [],
      isChecked: 0,
      recommendChi031List: [],
      overDurChi031List: [],
    }
  },
  methods: {
    queryPolicyImpl() {
      let _this = this
      const { aab001 } = this.corporateInfo
      requestGetData('D402', { aab001: aab001 }).then((res) => {
        console.debug(res.data)
        let recommendTempChi031List = res.data.recommendChi031List
        let chi031List = new Array()
        for (const chi031ListElement of recommendTempChi031List) {
          chi031List.push(chi031ListElement.chi031)
        }
        requestGetData('C401', {
          chi031List: JSON.stringify(chi031List),
          pageNo: '1',
          pageSize: '10',
        }).then((res) => {
          console.debug(res.data)

          if (
            res &&
            res.data &&
            res.data.c401List &&
            res.data.c401List.length
          ) {
            let renderList = []
            let overDurTempList = []
            for (let i = 0; i < res.data.c401List.length; i++) {
              let loopItem = res.data.c401List[i]
              for (const recommendItem of recommendTempChi031List) {
                if (recommendItem.chi031 == loopItem.chi031) {
                  loopItem['chr081'] = recommendItem.chr081 + '%'
                }
              }

              // 获取过期项目 bhi036不存在说明数据有问题
              const bhi036 = loopItem['bhi036']
              if (bhi036) {
                let a = moment(moment(bhi036).format('yyyy-MM-DD'))
                let b = moment(moment(new Date()).format('yyyy-MM-DD'))
                let diff = a.diff(b)
                if (diff < 0) {
                  overDurTempList.push(loopItem)
                } else {
                  renderList.push(loopItem)
                }
              }
            }
            this.recommendChi031List = renderList
            this.overDurChi031List = overDurTempList
            // this.queryResultList = renderList
          } else {
            _this.$message.error({
              showClose: false,
              message: '获取政策法规失败！！！',
              center: true,
              type: 'error',
            })
          }
        })
      })
    },
  },
  mounted() {
    this.queryPolicyImpl()
  },
  computed: {
    ...mapGetters([CORPORATE_INFO]),
  },
}
</script>
<style lang="scss" scoped>
.recommand-declare {
  padding-top: 24px;
  .items {
    display: flex;
    .item {
      width: 230px;
      line-height: 48px;
      text-align: center;
      font-size: 20px;
      color: $primary;
      border-radius: 8px 8px 0px 0px;
      background-color: #e8f3fe;
      margin-right: 24px;
      cursor: pointer;
      user-select: none;

      &:hover,
      &.checked {
        color: #fff;
        border-color: $primary;
        background-color: $primary;
      }
    }
  }

  .query-results {
    padding: 36px;
    background-color: #fff;
  }
}

.not-found {
  background-color: #7395ff;
  padding-top: 140px;

  img {
    width: 500px;
    margin: 0 auto;
  }

  .title {
    h2 {
      color: #fff;
      font-size: 36px;
      letter-spacing: 4px;
      text-align: center;
      line-height: 120px;
    }

    h4 {
      color: #fff;
      font-size: 18px;
      line-height: 38px;
      text-align: center;
    }
  }
}
</style>
