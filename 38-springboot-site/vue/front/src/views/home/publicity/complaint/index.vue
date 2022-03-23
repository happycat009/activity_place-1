<template>
  <div class="complaint">
    <div class="container">
      <YhBreadcrumb :renderData="breadcrumbData"></YhBreadcrumb>
      <div class="container__inner">
        <Whistleblower
          :formData="customData.formDataBasicInfo"
          :codeMap="codeMap"
          ref="WhistleblowerRef"
        ></Whistleblower>
        <Reported
          :formData="customData.formDataBasicInfo"
          :isNeedInput="customData.isNeedInput"
          :codeMap="codeMap"
          ref="ReportedRef"
        ></Reported>
        <ComplaintContent
          :formData="customData.formDataBasicInfo"
          :codeMap="codeMap"
          ref="contentRef"
        ></ComplaintContent>
        <VoucherUpload ref="voucherUploadRef"></VoucherUpload>
        <ComplaintSubmit
          :formData="customData.formDataBasicInfo"
          :hadComplaind="customData.hadComplaind"
          ref="complaintSubmitRef"
          @reset="handleReset"
          @submit="handleCheck"
          @showModel="customData.isShowComplaintNotice = true"
        ></ComplaintSubmit>
      </div>
    </div>
    <!-- 申报须知弹窗 -->
    <ComplaintNotice
      v-if="customData.isShowComplaintNotice"
      @closeModel="customData.isShowComplaintNotice = false"
    ></ComplaintNotice>
  </div>
</template>

<script setup>
import YhBreadcrumb from "@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue";
import Whistleblower from "./Whistleblower.vue";
import Reported from "./Reported.vue";
import ComplaintContent from "./ComplaintContent.vue";
import VoucherUpload from "./VoucherUpload.vue";
import ComplaintSubmit from "./ComplaintSubmit.vue";
import ComplaintNotice from "./ComplaintNotice.vue";

import { ref, onMounted, computed, reactive, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { USER_LIST, USER_INFO } from "@/store/constants";
import { useStore } from "vuex";
import {
  requestE101,
  requestE102,
  requestGetCollectionData,
  requestB404,
  requestE412
} from "@/service";

import { ElMessage } from "element-plus";

let breadcrumbData = reactive([
  {
    id: "01",
    path: "/home",
    name: "首页",
  },
  {
    id: "/home/complaint/complaint",
    path: "",
    name: "投诉举报",
  },
]);

let customData = reactive({
  fileList: [], // 文件上传列表
  // 企业基本信息
  formDataBasicInfo: {
    chb091: "1", //投诉主题
    chb092: "", //投诉标题
    chb093: "", //投诉内容
    aae013: "", //备注
    aab012: "", //公司名称
    aab001: "", //公司编号
    chi037: "", //公司业务局
    aac002: "", //身份证号
    aac003: "", //姓名
    aae005: "", //电话
    chb015: "", //业务局区县
    input: "",  //验证码
    aae006: "", //投诉人地址 （暂不需要）
    aab01w: "", //企业编码
    isRead: false, // 是否阅读投诉须知
    chi031: "", //补贴项目
    chb09i: '', //被投诉业务局
    chb09h: '1', //投诉主体
    chb09j: '', //被投诉人 (仅业务局)
  },
  isNeedInput: true,
  companyData: {},
  formCopyData: {},
  hadComplaind: false,
  isShowComplaintNotice: true
});

const codeMap = reactive({
  chb017: [],
  chi037: [],
  chb091: [],
  chi031: [],
  chb015: [],
});


var changeChb092 =  computed(()=>{
  let {chb091,aab012,chb09h,chb09i} = customData.formDataBasicInfo
  let mapChb091 = codeMap.chb091
  let mapChb09i = codeMap.chi037
  console.log(chb09h,'ccc')
  if(!mapChb091 || mapChb091.length == 0){
    return ''
  }
    if(!mapChb09i || mapChb09i.length == 0){
    return ''
  }
  if(chb09h == '1'){
    if(chb091 && aab012) {
      let res = mapChb091.find((item) => item.aaa102 == chb091);
      return aab012 + "存在" + res.aaa103
    }
  } else if (chb09h == '2') {
    if(chb091 && chb09i) {
      let res = mapChb091.find((item) => item.aaa102 == chb091);
      let result = mapChb09i.find((item) => item.aaa102 == chb09i);
      return result.aaa103 + "存在" + res.aaa103
    }
  }
  return ''
})
watch(changeChb092,(val)=>{
  customData.formDataBasicInfo.chb092 = val
})
// watch(
//   () => customData.formDataBasicInfo.chb091,
//   (val) => {
//     //通过一个函数返回要监听的属性
//     if (val && customData.formDataBasicInfo.aab012) {
//       let res = codeMap.chb091.find((item) => item.aaa102 == val);
//       customData.formDataBasicInfo.chb092 =
//         customData.formDataBasicInfo.aab012 + "存在" + res.aaa103;
//     }
//   }
// );

// watch(
//   () => customData.formDataBasicInfo.aab012,
//   (val) => {
//     //通过一个函数返回要监听的属性
//     if (val && customData.formDataBasicInfo.chb091) {
//       let res = codeMap.chb091.find(
//         (item) => item.aaa102 == customData.formDataBasicInfo.chb091
//       );
//       customData.formDataBasicInfo.chb092 = val + "存在" + res.aaa103;
//     }
//   }
// );

const route = useRoute();
const store = useStore();

const userList = computed(() => {
  return store.getters[USER_LIST];
});
const userInfo = computed(() => {
  return store.getters[USER_INFO];
});

const WhistleblowerRef = ref(null);
const ReportedRef = ref(null);
const contentRef = ref(null);
const voucherUploadRef = ref(null);
const complaintSubmitRef = ref(null);

const getCodeMap = async (value) => {
  let res = await requestGetCollectionData(value);
  if (res) {
    return res;
  }
  ElMessage(`码表${value}加载失败`);
  return [];
};

// 重置
const handleReset = () => {
  customData.formDataBasicInfo = { ...customData.formCopyData };
  voucherUploadRef.value.removeAllFile();
};

// 提交
const handleSubmit = () => {
  const arr = [];
  customData.fileList.map((item) => {
    let [, type] = item.file.raw.type.split("/");
    let [name] = item.file.name.split("." + type);
    arr.push({
      chi212: name, // 文件名
      chi214: "." + type, // 文件类别
      chi217: "", // 文件备注
      che110: "", // 材料id
      chi215: item.hadUpfile.data.objectid, // 统一存储id
    });
  });
  let {chb09h} = customData.formDataBasicInfo
  if (!customData.isNeedInput && chb09h=="1") {
    let {chb000,cpb100,aab001,chb015,chb570} = customData.companyData
    requestE102({
      ...customData.formDataBasicInfo,
      chb000,
      cpb100,
      aab001,
      chb015,
      chb570,
      chb09i: '',
      chb09j: '',
      hi21List: JSON.stringify(arr),
      userList: JSON.stringify([userList.value]),
    }).then((res) => {
      ElMessage.success("投诉成功");
      customData.hadComplaind = true;
    });
    return;
  }
  let params ={}
  if(chb09h == "2") {
    params = {
      ...customData.formDataBasicInfo,
      aab001: '',
      aab01w: '',
      chi037: '13',
      aab012: '',
    }
  }
  requestE101({
    ...params,
    hi21List: JSON.stringify(arr),
    userList: JSON.stringify([userList.value]),
  }).then((res) => {
    ElMessage.success("投诉成功");
    customData.hadComplaind = true;
  });
};
// 校验
const handleCheck = async () => {
  const WhistleblowerCheckResult = WhistleblowerRef.value.handleSubmit();
  const ReportedCheckResult = ReportedRef.value.handleSubmit();
  const contentheckResult = contentRef.value.handleSubmit();
  const complaintSubmitResult = complaintSubmitRef.value.handleSubmit();
  if (!WhistleblowerCheckResult) {
    return;
  }
  if (!ReportedCheckResult) {
    return;
  }
  if (!contentheckResult) {
    return;
  }
  if (!complaintSubmitResult) {
    return;
  }
  const { arr, err } = await voucherUploadRef.value.handleUpLoad();
  customData.fileList = [...arr];
  if (err) {
    ElMessage.error(`图片上传失败，请重试！`);
    return;
  }
  if (!customData.formDataBasicInfo.isRead) {
    ElMessage.warning(`请先阅读并勾选《投诉举报须知》`);
    return;
  }
  handleSubmit();
};

onMounted(async () => {
  codeMap.chb015 = await getCodeMap("chb015");
  codeMap.chi037 = await getCodeMap("chi037");
  codeMap.chb091 = await getCodeMap("chb091");
  codeMap.chi031 = await getCodeMap("chi031");
  let { companyData } = route.query;
  if (companyData) {
    customData.companyData = JSON.parse(companyData);
    if (customData.companyData.aab012) {
      let { aab012, aab001, chi037, aab01w,chi031 } = customData.companyData;
      customData.formDataBasicInfo = {
        ...customData.formDataBasicInfo,
        aab012,
        aab001,
        chi037,
        aab01w,
        chi031,
        chb09i:chi037
      };
      customData.isNeedInput = false;
    }
  }
  customData.formCopyData = { ...customData.formDataBasicInfo };
});
</script>

<style lang="scss" scoped>
.complaint {
  width: 100%;
  background-color: #f7faff;
  padding-bottom: 30px;
  min-height: 500px;
  .container {
    .container__inner {
      background-color: #fff;
      padding: 30px;
    }
  }
}
</style>
