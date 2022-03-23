<template>
  <div class="service-guide-process">
    <YhTitle title="办理流程" type="03"></YhTitle>
    <div class="process-wrapper">
      <ul>
        <template
          v-for="(item, index) in customData.processList"
          :key="item.che014"
        >
          <li>
            <div class="index">{{ index + 1 }}</div>
            <div class="process-name">{{ item.che011 }}</div>
            <div class="hanlde-info">
              <div class="cell">
                <div class="key">办理人：</div>
                <div class="value">{{ item.agent }}</div>
              </div>
              <div class="cell">
                <div class="key">办理时限：</div>
                <div class="value">{{ item.time || '暂无' }}</div>
              </div>
            </div>
            <div class="desc">
              {{ item.cpb07a }}
            </div>
          </li>
        </template>
      </ul>
    </div>
  </div>
</template>

<script setup>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'

import { reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { requestC408 } from '@/service'

const route = useRoute()

const customData = reactive({
  processList: [],
})

const getData = async () => {
  const { id } = route.params
  const res = await requestC408(id)

  const { resultList } = res.data
  resultList.map((item, index) => {
    let arr = []
    item.positionnameList.map((e) => arr.push(e.positionname))

    item.agent = arr.join('、')
    item.time = item.cpb072
    // if (index == 0) {
    //   item.time = item.cpb072.split(';').join('、')
    // } else {
    //   item.time = item.cpb072
    // }
  })
  console.log(resultList, 1124)
  customData.processList = resultList
}

onMounted(() => {
  getData()
})
</script>

<style lang="scss" scoped>
.service-guide-process {
  width: 100%;
  padding-bottom: 50px;
  .process-wrapper {
    padding-top: 36px;
    padding-left: 30px;
  }
}
ul {
  li {
    padding-left: 30px;
    border-left: dashed 2px var(--primary);
    position: relative;
    padding-bottom: 30px;
    .process-name {
      font-size: 16px;
      font-weight: 700;
      line-height: 1;
      color: #333333;
      padding-left: 10px;
    }
    &:last-child {
      border-left: 0;
    }
  }
  .index {
    position: absolute;
    width: 28px;
    height: 28px;
    background-color: #eff6ff;
    border: solid 1px #5aa0ff;
    font-size: 18px;
    font-weight: 700;
    line-height: 26px;
    color: #006bfd;
    border-radius: 50%;
    text-align: center;
    left: 0;
    top: -6px;
    transform: translateX(-50%);
  }
  .hanlde-info {
    padding-top: 9px;
    font-size: 14px;
    line-height: 1;
    color: #666;
    display: flex;
    padding-left: 10px;
    .cell {
      display: flex;
      align-items: center;
      margin-right: 30px;
      min-width: 400px;
      &:last-child {
        flex: 1;
        overflow: hidden;
      }
    }
  }
  .desc {
    height: 39px;
    background-color: #f2f8ff;
    border-radius: 4px;
    font-size: 14px;
    line-height: 1.4;
    color: #666666;
    padding: 10px 12px;
    margin-top: 18px;
  }
}
</style>
