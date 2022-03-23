<template>
  <div class="left-form">
    <div class="form-wrapper basic-form-wrapper">
      <el-form
        ref="ruleFormRef"
        :model="basicForm"
        :rules="rules"
        class="basic-form"
      >
        <el-form-item label="填报企业：" prop="aab012">
          <el-input
            disabled
            v-model="basicForm.aab012"
            placeholder="请输入企业名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="项目：" prop="chi031">
          <el-select
            :disabled="listData.isHadChi031"
            v-model="basicForm.chi031"
            placeholder="请选择项目"
            @change="handleChi031Change"
          >
            <template v-for="(item, index) in codeMap.chi031" :key="index">
              <el-option :label="item.name" :value="item.id"></el-option>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="主管部门：" prop="chi037">
          <el-select
            disabled
            v-model="basicForm.chi037"
            placeholder="请选择主管部门"
          >
            <el-option
              :label="item.aaa103"
              :value="item.aaa102"
              v-for="(item, index) in codeMap.chi037"
              :key="index"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <div class="form-wrapper input-form-wrapper">
      <template v-for="(item, i) in data.optionsArr" :key="item.id">
        <div class="item">
          <div class="title">
            <div class="left">
              {{ item.title }}
            </div>
            <div class="right">
              <el-radio-group v-model="basicForm[item.id]">
                <template
                  v-for="itemRadio in data.radioDataArr"
                  :key="itemRadio.id"
                >
                  <div class="el-radio-wrapper">
                    <el-radio :label="itemRadio.value">{{
                      itemRadio.label
                    }}</el-radio>
                  </div>
                </template>
              </el-radio-group>
            </div>
          </div>
          <template v-if="basicForm[item.id] == '3'">
            <div class="rating-wrapper">
              <div class="name">差评选项</div>
              <el-checkbox-group v-model="listData.ab21List[i].aab212">
                <template
                  v-for="(itemCheckbox, index) in item.checkboxList"
                  :key="index"
                >
                  <div class="el-checkbox-wrapper">
                    <el-checkbox :label="itemCheckbox.aaa102">{{
                      itemCheckbox.aaa103
                    }}</el-checkbox>
                  </div>
                </template>
              </el-checkbox-group>
            </div>
          </template>
        </div>
      </template>
    </div>
    <div class="button-wrapper">
      <button class="primary" :disabled="data.isSubmit" @click="handleSubmit">提交</button>
    </div>
  </div>
</template>

<script setup>
import {
  onMounted,
  reactive,
  computed,
  getCurrentInstance,
  nextTick,
  ref,
} from "vue";
import { useStore } from "vuex";
import { useRouter,useRoute } from "vue-router";
import { CORPORATE_INFO } from "@/store/constants";
import {
  requestGetCollectionData,
  requestB405,
  requestE408,
  requestE412,
  requestE413
} from "@/service";
import { ElMessage } from "element-plus";

const ruleFormRef = ref(null);

const basicForm = reactive({
  aab001: "", //公司编号
  aab012: "", //公司名称
  chi037: "", //业务局
  chi031: "", //补贴项目

  aab201: "1", //服务态度
  aab202: "1", //服务质量
  aab203: "1", //服务效率
  aab204: "1", //服务环境
});

const listData = reactive({
  ab21List: [
    {
      aab211: "1",
      aab212: ["17"],
    },
    {
      aab211: "2",
      aab212: ["17"],
    },
    {
      aab211: "3",
      aab212: ["17"],
    },
    {
      aab211: "4",
      aab212: ["17"],
    },
  ],
  isHadChi031: false,
});

const codeMap = reactive({
  chi037: [],
  chi031: [],
  aab211: [],
  aab212: [],
});
const { appContext } = getCurrentInstance();
const rules = reactive({
  aab012: [
    {
      required: true,
      message: "请输入企业名称",
      trigger: "blur",
    },
  ],
  chi037: [
    {
      required: true,
      message: "请选择补贴项目",
      trigger: "change",
    },
  ],
  chi031: [
    {
      required: true,
      message: "请选择部门",
      trigger: "change",
    },
  ],
});
const data = reactive({
  isSubmit: false,
  optionsArr: [
    {
      id: "aab201",
      title: "服务态度",
      checkboxValue: [],
      radioValue: "",
      checkboxList: [
        {
          id: "01",
          value: "01",
          label: "不一次性告知；",
        },
        {
          id: "02",
          value: "02",
          label: "工作人员推诿扯皮；",
        },
        {
          id: "03",
          value: "03",
          label: "工作人员服务态度生硬；",
        },
      ],
    },
    {
      id: "aab202",
      title: "服务质量",
      checkboxValue: [],
      radioValue: "",
      checkboxList: [
        {
          id: "01",
          value: "01",
          label: "存在项目申报“吃、拿、卡、要”不正之风；",
        },
        {
          id: "02",
          value: "02",
          label: "多头跑部门；",
        },
        {
          id: "03",
          value: "03",
          label: "申请材料繁琐，同样材料多次提交；",
        },
        {
          id: "04",
          value: "04",
          label: "办事环节繁多、程序复杂；",
        },
        {
          id: "05",
          value: "05",
          label: "申报指南看不懂、有错误、无样本、少清单；",
        },
        {
          id: "06",
          value: "06",
          label: "服务收费；",
        },
        {
          id: "07",
          value: "07",
          label: "承诺网办但无法在线办理；",
        },
      ],
    },
    {
      id: "aab203",
      title: "服务效率",
      checkboxValue: [],
      radioValue: "",
      checkboxList: [
        {
          id: "01",
          value: "01",
          label: "服务导询不到位；",
        },
        {
          id: "02",
          value: "02",
          label: "服务标识不清晰；",
        },
        {
          id: "03",
          value: "03",
          label: "服务设施设备不便民；",
        },
        {
          id: "04",
          value: "04",
          label: "服务网络（系统）不稳定；",
        },
      ],
    },
    {
      id: "aab204",
      title: "服务环境",
      checkboxValue: [],
      radioValue: "",
      checkboxList: [
        {
          id: "01",
          value: "01",
          label: "咨询不方便、回复不及时；",
        },
        {
          id: "02",
          value: "02",
          label: "网上申请不便捷；",
        },
        {
          id: "03",
          value: "03",
          label: "未在承诺时限内办结；",
        },
        {
          id: "04",
          value: "04",
          label: "工作人员业务不熟练；",
        },
      ],
    },
  ],
  radioDataArr: [
    {
      id: "01",
      value: "1",
      checked: false,
      label: "满意",
    },
    {
      id: "02",
      value: "2",
      checked: false,
      label: "基本满意",
    },
    {
      id: "03",
      value: "3",
      checked: false,
      label: "不满意",
    },
  ],
});

const store = useStore();
const router = useRouter();
const route = useRoute()

const corporateInfo = computed(() => {
  return store.getters[CORPORATE_INFO];
});

const handleSubmit = () => {
  if (!basicForm.aab001) {
    ElMessage.warning("该用户不存在企业投诉");
    return;
  }
  if (!handleCheck()) {
    return;
  }
  let arr = ["aab201", "aab202", "aab203", "aab204"];
  let ab21List = [...listData.ab21List];
  console.log(ab21List,'ab21List')
  arr.forEach((item, index) => {
    if (basicForm[item] !== "3") {
      ab21List[index].aab212 = ["0"];
      // console.log()
    }else{     
      if (ab21List[index].aab212 == ''){
        ElMessage.warning("存在不满意但差评选项为空的选项");
        return;
      }
      console.log(ab21List[index].aab212 == '','(ab21List[index].aab212')

    }
  });

  let newAb21List = [];

  ab21List.forEach((item) => {
    item.aab212.forEach((one) => {
      newAb21List.push({ aab211: item.aab211, aab212: one });
    });
  });

  let params = {
    ...basicForm,
    ab21List: JSON.stringify(newAb21List),
  };
  requestB405(params).then((res) => {
    if (res && res.code == "200") {
      ElMessage.success("提交成功");
      data.isSubmit =true
    } else {
      ElMessage.error("提交失败，请稍后再试！");
    }
  });
};
// 补贴项目变化
const handleChi031Change = () => {
  requestE413(basicForm.chi031).then(res => {
    console.log(res,'res')
    console.log(listData.isHadChi031,'listData.isHadChi031listData.isHadChi031')
    basicForm.chi037 = res.data.chi037List[0]? res.data.chi037List[0].id : ''
  })
}

const getCodeMap = async (value) => {
  let res = await requestGetCollectionData(value);
  if (res) {
    return res;
  }
  ElMessage(`码表${value}加载失败`);
  return [];
};

// 点击下一步 先进行表单校验
const handleCheck = (formEl) => {
  if (!formEl) {
    formEl = ruleFormRef.value;
  }
  let tag = false;
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      tag = true;
    } else {
      const formErrPropsArr = [];
      for (let item in formErrObj) {
        formErrPropsArr.push(item);
      }
      const firstErrProps = formErrPropsArr[0];
      formEl.scrollToField(firstErrProps);
    }
  });
  return tag;
};

const getChi031 = () => {
  requestE412(basicForm.aab001).then((res) => {
    codeMap.chi031 = res.data.chi031List? res.data.chi031List : []
  });
};

onMounted(async () => {
  let { aab012, aab001 } = corporateInfo.value;
  basicForm.aab012 = aab012;
  basicForm.aab001 = aab001;
  codeMap.chi037 = await getCodeMap("chi037");
  // codeMap.chi031 = await getCodeMap("chi031");
  codeMap.aab211 = await getCodeMap("aab211");
  codeMap.aab212 = await getCodeMap("aab212");
  let arr = [3, 6, 3, 4];
  arr.reduce((pre, cur, index) => {
    let list = codeMap.aab212.filter(
      (item) => item.aaa102 >= pre && item.aaa102 < pre + cur
    );
    list.push(codeMap.aab212.find((item) => item.aaa102 == 17));
    data.optionsArr[index].checkboxList = [...list];
    return pre + cur;
  }, 1);
  console.log(router,'router1111')
  let {id} = route.params
  let [,chi031] = id.split('&')
  console.log(chi031,route,'chi031')
  if(chi031) {
    basicForm.chi031 = chi031
    listData.isHadChi031 = true
    handleChi031Change()
  }
  getChi031();
  console.log(data.optionsArr, "codeMap");
});
</script>

<style lang="scss" scoped>
.left-form {
  width: 100%;
  padding: 0 24px;
}

.basic-form-wrapper {
  padding: 10px 0;
}
.basic-form {
  display: flex;
  align-items: center;
  .el-form-item {
    flex: 1;
    overflow: hidden;
    .el-form-item__content {
      flex: 1;
      overflow: hidden;
    }
  }
}
.rating-wrapper {
  padding-top: 22px;
  padding-left: 12px;
  font-size: 14px;
  line-height: 1;
  color: #333;

  .name {
    font-weight: 700;
    margin-bottom: 10px;
    &::before {
      content: "*";
      color: var(--color-hint);
      padding-right: 6px;
      padding-left: 2px;
    }
  }
  .el-checkbox-wrapper {
    margin-bottom: 18px;
  }
  .el-checkbox {
    height: 16px;
    color: #333;
  }
}
.input-form-wrapper {
  .item {
    margin-bottom: 40px;
  }
  .title {
    font-size: 18px;
    font-weight: 700;
    line-height: 1;
    color: #333333;
    padding-bottom: 15px;
    position: relative;
    display: flex;
    &::after {
      content: "";
      position: absolute;
      left: 0;
      bottom: 0;
      width: 810px;
      height: 8px;
      background-image: linear-gradient(to right, #edf5ff, #fff);
    }
    .left {
      width: 146px;
      &::before {
        content: "*";
        padding-right: 4px;
        color: var(--color-hint);
      }
    }
    .el-radio-wrapper {
      margin-right: 90px;
      .el-radio {
        height: 18px;
        color: #333;
        font-size: 16px;
      }
    }
  }
}
.button-wrapper {
  padding: 20px;
  border-top: 1px solid #c9e0ff;
  display: flex;
  justify-content: center;
  button {
    width: 96px;
  }
}
</style>
