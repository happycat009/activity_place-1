<template>
  <div
    class="company-info"
    :style="wrapperStyle"
    v-if="renderData && renderData.aab220"
  >
    <div class="logo">
<!--      <img :src="imageBgLogo" alt="" />-->
      <img :src="'data:image/png;base64,'+props.renderData.aab225" alt="" />
    </div>
    <div class="info">
      <div class="name">{{ renderData.aab012 }}</div>
      <div class="tags">
        <template v-for="(item, index) in renderData.tags" :key="index">
          <div class="tag" :style="{'color': customData.color[index%customData.color.length],'border-color':customData.color[index%customData.color.length], 'background-color':customData.color[index%customData.color.length]+'24'}" >{{ item }}</div>
        </template>
      </div>
      <div class="cells">
        <div class="cell">
          <div class="icon">
            <img src="~/assets/images/propaganda/info-01.png" alt="">
          </div>
          <div class="key">企业地址：</div>
          <div class="value">{{ renderData.aab223 }}</div>
          <div
            class="link"
            v-if="renderData.aab223"
            @click="handleOpenBaiduMap(renderData.aab223)"
          >
            在地图上查看>>
          </div>
        </div>
        <div class="cell">
          <div class="icon">
            <img src="~/assets/images/propaganda/info-02.png" alt="">
          </div>
          <div class="key">企业网址：</div>
          <div class="value link" @click="handleOpenUrl">
            {{ renderData.aab224 }}
          </div>
        </div>
      </div>
    </div>
    <button class="primary" @click="handleJumpRoute">联系我们</button>
  </div>
</template>

<script setup>
import imageBgCompany from '@/assets/images/propaganda/bg-company.png'
import imageBgLogo from '@/assets/images/propaganda/logo.png'

import {ref, reactive, computed, onMounted} from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const props = defineProps({
  renderData: {
    type: Object,
    required: true,
  },
})

const customData = reactive({
  // 动态绑定tag颜色
  color: ["#da0000","#006cff","#00b260"]
})

const wrapperStyle = computed(() => {
  let str = ''
  str += `background-image: url(${imageBgCompany}); `
  return str
})
const handleJumpRoute = () => {
  console.log('props.renderData', props.renderData)
  const { aab220, aab01w, aab012 } = props.renderData
  if (aab220) {
    router.push('/contact/' + aab220 + '&' + aab01w + '&' + aab012)
  }
}
const handleOpenBaiduMap = (address) => {
  window.open(
    `http://api.map.baidu.com/geocoder?address=${address}&output=html&src=webapp.baidu.openAPIdemo`,
    '_blank'
  )
}

const mounted = onMounted(() => {
  console.log("111111111111111111111111")
  console.log("props",props.renderData)
})
// 打开网页
const handleOpenUrl = () => {
  const { aab224 } = props.renderData
  if (aab224) {
    window.open(aab224, '_blank')
  }
}
</script>

<style lang="scss" scoped>
.company-info {
  width: 100%;
  background-color: #fff;
  display: flex;
  align-items: center;
  padding: 38px 44px;
  background-repeat: no-repeat;
  background-position: left bottom;
  background-size: cover;
  .logo {
    width: 136px;
    margin-right: 50px;
  }
  .info {
    flex: 1;
    overflow: hidden;
    padding-right: 40px;
    .name {
      font-size: 36px;
      font-weight: 400;
      line-height: 1;
      color: #333333;
      padding-bottom: 8px;
    }
    .tags {
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      .tag {
        padding: 6px 12px;
        font-size: 14px;
        line-height: 1;
        color: #666;
        background-color: #f6f6f6;
        border: solid 1px #ededed;
        margin-right: 16px;
        margin-bottom: 8px;
      }
    }
    .cells {
      padding-top: 20px;
      .cell {
        display: flex;
        align-items: center;
        font-size: 14px;
        color: #666;
        line-height: 1;
        margin-bottom: 8px;
        .link {
          color: var(--color-primary);
          cursor: pointer;
          margin-left: 16px;
          &:hover {
            text-decoration: underline;
          }
        }
        .icon{
          margin-right: 7px;
        }
        .value {
          margin-left: 0;
        }

        &:last-child {
          margin-bottom: 0;
        }
      }
    }
  }
  button {
    width: 118px;
    height: 44px;
    line-height: 42px;
    border-radius: 22px;
  }
}
</style>
