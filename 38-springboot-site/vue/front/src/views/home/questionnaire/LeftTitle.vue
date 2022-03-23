<template>
  <div class="left-title">
    <div class="title">
      <!-- <img src="~assets/images/home/questionnaire/bg-left-title.png" alt="" /> -->
      <div class="con">
        {{ props.tabInfo.tabTitle }}
        <button class="tab" @click="handleChangeTab">
          {{ props.tabInfo.btnText }}
        </button>
      </div>
    </div>
    <div class="desc" v-if="props.tab == '1'">
      感谢您抽出时间参与本次问卷调查，本次调查是为检验“甜成办”平台的实施效果，请您根据自己观察到的实际情况做出评价。您的意见对我们进一步完善工作细节，改善工作服务非常重要，感谢您的大力支持！
    </div>
  </div>
</template>

<script setup>
import { reactive, computed } from "vue";
import { useRouter } from "vue-router";
import { USER_INFO, CORPORATE_INFO } from '@/store/constants'
import { useStore } from 'vuex'

const props = defineProps({
  tabInfo: {
    type: [Object],
    default: () => {
      return {
        tab: "2",
        btnText: "我要评价",
        tabTitle: "企业评价结果统计",
      }
    },
  },
});
const router = useRouter();
const emit = defineEmits(["update:tabInfo"]);
const store = useStore()

const userInfo = computed(() => {
  return store.getters[USER_INFO]
})
const handleChangeTab = () => {
  if (props.tabInfo.tab == "1") {
    let value = {
      tab: "2",
      btnText: "我要评价",
      tabTitle: "企业评价结果统计",
    }
    emit("update:tabInfo", value);
    return;
  }
  if (!userInfo.value.user_id) {
    router.push("/login?redirect=/home/questionnaire/1");
    return
  }
  let value = {
    tab: "1",
    btnText: "查看评价统计结果",
    tabTitle: "企业评价",
  }
  emit("update:tabInfo", value);
};
</script>

<style lang="scss" scoped>
.left-title {
  width: 100%;
  .title {
    font-size: 24px;
    line-height: 80px;
    height: 80px;
    text-align: center;
    color: #fff;
    position: relative;
    .con {
      left: 0;
      top: 0;
      width: 100%;
      position: absolute;
      background-color: #0066ff;
      .tab {
        position: absolute;
        top: 50%;
        right: 20px;
        font-size: 16px;
        transform: translate(0, -50%);
        background: #fff;
        border-radius: 5px;
        color: #0066ff;
        width: 150px;
        height: 40px;
        line-height: 40px;
      }
    }
  }
  .desc {
    color: #333;
    padding: 20px;
    line-height: 1.4;
  }
}
</style>
