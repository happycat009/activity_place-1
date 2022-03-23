<template>
  <div class="apply-project-process" v-if="renderData && renderData.length > 0">
    <div class="bg">
      <img src="~assets/images/policy/bg-apply-process.png" alt="" />
    </div>
    <ul>
      <template v-for="(item, index) in renderData">
        <li :class="['item-state-' + item.state]" :style="liStyle">
          <div class="index">
            <i
              class="iconfont iconfont-success-filling"
              v-if="item.state === 2"
            ></i>
            <span v-else>{{ index + 1 }}</span>
            <div class="text-info">
              <div class="name">{{ item.name }}</div>
              <div class="desc">{{ item.time }}</div>
              <!-- <div class="time">{{ item.desc }}</div> -->
            </div>
          </div>
          <div class="tip-text" v-if="index == 0">
            <p>线上申报</p>
            <p>
              {{ item.bhi035 == "0" ? "线上提交材料" : "线上线下都提交材料" }}
            </p>
          </div>
        </li>
      </template>
    </ul>
  </div>
</template>

<script setup>
import { computed } from "vue";

const props = defineProps({
  renderData: {
    type: Array,
    default: () => [],
  },
});

const liStyle = computed(() => {
  const list = props.renderData;
  const len = list.length;
  let num = (1080 / len - 94) / 2;
  let str = "";
  str += `left: ${num}px;`;
  return str;
});
</script>

<style lang="scss" scoped>
.apply-project-process {
  width: 100%;
  position: relative;
  ul {
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    white-space: nowrap;
    padding: 35px 30px;
    position: absolute;
    width: 100%;
    left: 0;
    top: 0;
    li {
      flex: 1;
      position: relative;
      left: 20px;
      opacity: 0.2;
      transition: all 0.3s;
      user-select: none;

      .tip-text {
        position: absolute;
        top: -10px;
        left: calc( 50% + 47px );
        transform: translate(-50%, 0);
        p {
          text-align: center;
          margin-bottom: 20px;
          font-size: 12px;
        }
      }

      &::after {
        content: "";
        width: calc(100% - 94px);
        height: 3px;
        background-color: var(--color-primary);
        position: absolute;
        top: 15px;
        right: 0;
      }
      &:last-child {
        &::after {
          width: 0;
        }
      }

      &.item-state-1 {
        opacity: 1;
      }
      &.item-state-2 {
        opacity: 1;
      }
    }
    .index {
      width: 94px;
      padding-bottom: 45px;
      position: relative;
      .iconfont {
        display: block;
        width: 100%;
        font-size: 34px;
        line-height: 1;
        text-align: center;
        color: var(--color-primary);
      }
      span {
        display: block;
        width: 34px;
        height: 34px;
        border-radius: 50%;
        line-height: 34px;
        text-align: center;
        color: #fff;
        font-weight: 500;
        background-color: var(--color-primary);
        margin: 0 auto;
      }
    }
    .text-info {
      position: absolute;
      left: 50%;
      bottom: 0;
      transform: translateX(-50%);
      text-align: center;
      line-height: 1;
      height: 35px;
      .name {
        font-size: 16px;
        font-weight: 700;
        color: var(--color-primary);
      }

      .desc {
        position: absolute;
        left: 50%;
        bottom: 0;
        transform: translateX(-50%);
        color: #666;
        font-size: 12px;
      }
      .time {
        position: absolute;
        left: 50%;
        bottom: -18px;
        font-size: 12px;
        transform: translateX(-50%);
        color: var(--color-primary);
      }
    }
  }
}
</style>
