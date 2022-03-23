<template>
  <div class="questionnaire">
    <div class="container">
      <YhBreadcrumb :renderData="data.breadcrumbData"></YhBreadcrumb>
      <div class="content">
        <div class="left">
          <LeftTitle
            :tabInfo="data.tabInfo"
            @update:tabInfo="data.tabInfo = $event"
          ></LeftTitle>
          <template v-if="data.tabInfo.tab == '1'">
            <LeftForm></LeftForm>
          </template>
          <template v-else>
            <RightTotal
              :renderData="data.totalData"
              @getTotal="handleGetData"
            ></RightTotal>
          </template>
        </div>
        <div class="right">
          <RightTitle></RightTitle>
          <!-- <RightTotal :renderData="data.totalData"></RightTotal> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import YhBreadcrumb from "@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue";
import LeftTitle from "./LeftTitle.vue";
import LeftForm from "./LeftForm.vue";
import RightTitle from "./RightTitle.vue";
import RightTotal from "./RightTotal.vue";

import { reactive, unref, ref, onMounted } from "vue";
import { requestB406 } from "@/service";
import { useRoute } from "vue-router";

import { ElMessage } from "element-plus";

const data = reactive({
  breadcrumbData: [
    {
      id: "01",
      path: "/home",
      name: "首页",
    },
    {
      id: "02",
      path: "",
      name: "企业评价",
    },
  ],
  totalData: [
    {
      id: "01",
      name: "服务态度",
      list: [
        {
          id: "02",
          name: "满意",
          value: 15,
        },
        {
          id: "03",
          name: "基本满意",
          value: 15,
        },
        {
          id: "04",
          name: "不满意",
          value: 20,
        },
      ],
    },
    {
      id: "02",
      name: "服务质量",
      list: [
        {
          id: "02",
          name: "满意",
          value: 15,
        },
        {
          id: "03",
          name: "基本满意",
          value: 15,
        },
        {
          id: "04",
          name: "不满意",
          value: 20,
        },
      ],
    },
    {
      id: "03",
      name: "服务效率",
      list: [
        {
          id: "02",
          name: "满意",
          value: 15,
        },
        {
          id: "03",
          name: "基本满意",
          value: 15,
        },
        {
          id: "04",
          name: "不满意",
          value: 20,
        },
      ],
    },
    {
      id: "04",
      name: "服务环境",
      list: [
        {
          id: "02",
          name: "满意",
          value: 15,
        },
        {
          id: "03",
          name: "基本满意",
          value: 15,
        },
        {
          id: "04",
          name: "不满意",
          value: 20,
        },
      ],
    },
  ],
  tabInfo: {
    tab: "2",
    btnText: "我要评价",
    tabTitle: "企业评价结果统计",
  },
});

const ruleFormsss = ref(null);
const submitForm = async () => {
  const form = unref(ruleFormsss);
  if (!form) return;
  try {
    await form.validate();
    const { username, password, region } = ruleForm;
    console.log(username, password, region);
  } catch (error) {}
};

const route = useRoute();

onMounted(() => {
  handleGetData();
  console.log(route,'route')
  let {id} = route.params
  let [tab,] = id.split('&')
  if (tab == "2") {
    data.tabInfo = {
      tab: "2",
      btnText: "我要评价",
      tabTitle: "企业评价结果统计",
    };
  }
  if(tab == "1") {
    data.tabInfo = {
      tab: "1",
      btnText: "查看评价统计结果",
      tabTitle: "企业评价",
    };
  }
});

const handleGetData = async (val) => {
  let res = "";
  if (val) {
    let { chi031, chi037 } = val;
    res = await requestB406(chi031, chi037);
  } else {
    res = await requestB406();
  }
  if (res.code == "200") {
    data.totalData[0].list[0].value = res.data.aab201SatisfactionRate;
    data.totalData[0].list[1].value = res.data.aab201QuiteSatisfactionRate;
    data.totalData[0].list[2].value = res.data.aab201DissatisfactionRate;
    data.totalData[1].list[0].value = res.data.aab202SatisfactionRate;
    data.totalData[1].list[1].value = res.data.aab202QuiteSatisfactionRate;
    data.totalData[1].list[2].value = res.data.aab202DissatisfactionRate;
    data.totalData[2].list[0].value = res.data.aab203SatisfactionRate;
    data.totalData[2].list[1].value = res.data.aab203QuiteSatisfactionRate;
    data.totalData[2].list[2].value = res.data.aab203DissatisfactionRate;
    data.totalData[3].list[0].value = res.data.aab204SatisfactionRate;
    data.totalData[3].list[1].value = res.data.aab204QuiteSatisfactionRate;
    data.totalData[3].list[2].value = res.data.aab204DissatisfactionRate;
  } else {
    ElMessage.error("数据获取失败，请刷新重试");
  }
};
</script>

<style lang="scss" scoped>
.questionnaire {
  width: 100%;
  padding-bottom: 30px;
}

.content {
  display: flex;
  .left {
    flex: 1;
    overflow: hidden;
    background-color: #fff;
  }
  .right {
    width: 327px;
    margin-left: 16px;
    background-color: #fff;
    padding: 24px;
  }
}
</style>
