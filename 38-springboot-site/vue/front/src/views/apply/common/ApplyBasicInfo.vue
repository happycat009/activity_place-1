<template>
  <div class="apply-basic-info" ref="applyBasicRef">
    <YhTitle title="企业基本信息" type="04"></YhTitle>
    <div class="hint">
      <i class="iconfont iconfont-hint"></i>
      <span>以下数据来源为内江市市场监督管理局，如有误，请联系数据来源方进行修正。联系电话：0832-2171013</span>
    </div>
    <div class="form-wrapper">
      <el-form ref="formRef" :model="formData" :rules="formRules">
        <el-row>
          <el-col :span="12">
            <el-form-item class="show-from-input" label="企业名称：" prop="aab012">
              <el-input
                v-model="formData.aab012"
                placeholder="请输入企业名称"
                type="text"
                maxlength="30"
                disabled
              />
                <div class="show-from" @click="showFrom" >!</div>
              </el-form-item
          ></el-col>
          <el-col :span="12" class="space">
            <el-form-item label="企业统一社会信用代码：" prop="aab01w">
              <el-input
                v-model="formData.aab01w"
                placeholder="请输入企业统一社会信用代码"
                type="text"
                maxlength="18"
                disabled
              />
              </el-form-item
          ></el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="企业类型：" prop="aab014">
              <el-select
                v-model="formData.aab014"
                disabled
                placeholder="请选择企业类型"
              >
                <el-option
                  :label="item.aaa103"
                  :value="item.aaa102"
                  v-for="item in customData.aab014CodeTableList"
                  :key="item.aaa102"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" class="space">
            <el-form-item label="企业性质：" prop="aab016">
              <el-select
                v-model="formData.aab016"
                disabled
                placeholder="请选择企业性质"
              >
                <el-option
                  :label="item.aaa103"
                  :value="item.aaa102"
                  v-for="item in customData.aab016CodeTableList"
                  :key="item.aaa102"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" >
            <el-form-item label="所属行业：" prop="aab017">
              <el-select
                v-model="formData.aab017"
                disabled
                placeholder="请选择所属行业"
              >
                <el-option
                  :label="item.aaa103"
                  :value="item.aaa102"
                  v-for="item in customData.aab017CodeTableList"
                  :key="item.aaa102"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" class="space">
            <el-form-item label="成立日期：" prop="aab018">
              <el-date-picker
                v-model="formData.aab018"
                placeholder="请选择企业成立日期"
                type="date"
                value-format="YYYY-MM-DD"
                :editable="false"
                :clearable="false"
                disabled
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="法定代表人：" prop="aac003">
              <el-input
                v-model="formData.aac003"
                placeholder="请输入法定代表人"
                type="text"
                maxlength="4"
                disabled
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" class="space">
            <el-form-item label="注册地址：" prop="aab019">
              <el-input
                      v-model="formData.aab019"
                      placeholder="请输入注册地址"
                      type="text"
                      disabled
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="主营业务（经营范围）：" prop="aab01t">
              <el-input
                      v-model="formData.aab01t"
                      placeholder="请输入主营业务（经营范围）"
                      type="textarea"
                      :input-style="{'height':'100px'}"
                      disabled
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="hint notice">
          <i class="iconfont iconfont-hint"></i>
          <span>以下数据由企业自主填报</span>
        </div>
        <el-row>
          <el-col :span="12">
            <YhSelectAddress
                    label="生产经营地址："
                    v-model="formData.aae006"
            ></YhSelectAddress>
          </el-col>
          <el-col :span="12" class="space">
            <el-form-item label="开户行名称：" prop="aae008">
              <el-select
                      v-model="formData.aae008"
                      placeholder="请选择开户行名称"
              >
                <el-option
                        :label="item.aaa103"
                        :value="item.aaa102"
                        v-for="item in customData.aae008CodeTableList"
                        :key="item.aaa102"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户行账号：" prop="aae010">
              <el-input
                      v-model="formData.aae010"
                      placeholder="请输入开户行账号"
                      type="text"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" class="space">
            <el-form-item label="企业联系人：" prop="aae004">
              <el-input
                      v-model="formData.aae004"
                      placeholder="请输入企业联系人"
                      type="text"
                      maxlength="8"
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人职务：" prop="aae007">
              <el-input
                      v-model="formData.aae007"
                      placeholder="请输入联系人职务"
                      type="text"
                      maxlength="8"
              /> </el-form-item
            ></el-col>
          <el-col :span="12" class="space">
            <el-form-item label="联系人电话：" prop="aae005">
              <el-input
                v-model="formData.aae005"
                placeholder="请输入联系人电话"
                type="text"
                maxlength="11"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="联系人邮箱：" prop="aae012">
              <el-input
                      v-model="formData.aae012"
                      placeholder="请输入联系人邮箱"
                      type="text"
                      maxlength="20"
              /> </el-form-item
            ></el-col>
        </el-row>
      </el-form>
    </div>

    <ApplyInfoFrom
      v-if="customData.showApplyInfoFrom"
      :PositionStyle="customData.PositionStyle"
      @closeModel="customData.showApplyInfoFrom=false"
    >
    </ApplyInfoFrom>
  </div>
</template>

<script setup>
import ApplyInfoFrom from "./ApplyInfoFrom.vue"

import YhTitle from "@/components/project/YhTitle/YhTitle.vue";
import { requestGetCollectionData } from "@/service";

import { ref, reactive, watch, onMounted } from "vue";
import { phoneNumberReg, emailReg } from "@/utils/regular";


const formRef = ref(null);
const applyBasicRef = ref(null)

const props = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        aab001: "", // 企业ID
        aab01w: "", // 统一社会信用代码
        aab012: "", // 企业名称

        aac002: "", // 法定代表人身份证号码
        aac003: "", // 法定代表人姓名

        aab014: "", // 企业类型
        aab016: "", // 企业性质
        aab017: "", // 所属行业/所属领域

        aab018: "", // 企业成立时间
        aae006: "", // 注册企业地址

        aae004: "", // 联系人姓名
        aae005: "", // 联系电话

        chb015: "", // 企业所属区县
        chb015Desc: "",
      };
    },
  },
});
const customData = reactive({
  aab014CodeTableList: [],
  aab016CodeTableList: [],
  aab017CodeTableList: [],
  aae008CodeTableList: [],
  PositionStyle:{},
  showApplyInfoFrom: false
});

const formRules = reactive({
  aae004: [
    {
      required: true,
      message: "请输入联系人姓名",
      trigger: "blur",
    },
    {
      min: 2,
      message: "请输入正确的联系人姓名，至少2个字符",
      trigger: "blur",
    },
  ],

  aae005: [
    {
      required: true,
      message: "请输入联系人电话",
      trigger: "blur",
    },
    {
      pattern: phoneNumberReg,
      message: "请输入正确的电话号码",
      trigger: "blur",
    },
  ],
  aae008: [
    {
      required: true,
      message: "请输入开户行名称",
      trigger: "blur",
    }
  ],
  aae010: [
    {
      required: true,
      message: "请输入开户行账号",
      trigger: "blur",
    },
    {
      pattern: /^([1-9]{1})(\d{14}|\d{18})$/,
      message: "请输入正确的开户行账号",
      trigger: "blur",
    },
  ],
  aae007: [
    {
      required: true,
      message: "请输入联系人职务",
      trigger: "blur",
    }
  ],
  aae012: [
    {
      required: true,
      message: "请输入正确的联系人邮箱",
      trigger: "blur",
    },
    {
      pattern: emailReg,
      message: "请输入正确的联系人邮箱",
      trigger: "blur",
    },
  ]
});
var timer
const showFrom = (e) =>{
  customData.showApplyInfoFrom =true
  if(timer){
    clearTimeout(timer)
  }
  setTimeout(() => {
    customData.showApplyInfoFrom =false
  },2000)
  console.log(applyBasicRef.value.offsetLeft,applyBasicRef.value.scrollTop,'1111')
  let top = applyBasicRef.value.offsetTop - window.document.querySelector('#app').scrollTop
  let left = applyBasicRef.value.offsetLeft
  let res = {top:(e.y-top-20) +'px',left:(e.x-left+10)+'px'}
  customData.PositionStyle = {...res}
}

// 获取码表
const getCodeTableData = async () => {
  const aab014List = await requestGetCollectionData("aab014");
  const aab016List = await requestGetCollectionData("aab016");
  const aab017List = await requestGetCollectionData("aab017");
  const aae008List = await requestGetCollectionData('aae008');

  customData.aab014CodeTableList = aab014List;
  customData.aab016CodeTableList = aab016List;
  customData.aab017CodeTableList = aab017List;
  customData.aae008CodeTableList = aae008List;
};

// 点击下一步 先进行表单校验
const handleSubmit = (formEl) => {
  if (!formEl) {
    formEl = formRef.value;
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
getCodeTableData();
defineExpose({
  handleSubmit
});
</script>

<style lang="scss" scoped>
.apply-basic-info {
  width: 100%;
  position: relative;
  .form-wrapper {
    padding-top: 35px;
  }
}
.form-wrapper {
  .el-form-item-cells {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
  }
}
.show-from-input{
  position: relative;
  .show-from{
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translate(0,-50%);
    line-height: 20px;
    border: 1px solid #009dd9;
    height: 20px;
    width: 20px;
    font-size: 12px;
    border-radius: 50%;
    text-align: center;
    color: #009dd9;
  }
}

.hint{
  position: relative;
  width: fit-content;
  color: coral;
  line-height: 30px;
  top: 22px;
  span{
    margin-left: 2px;
  }
}
.notice{
  top: -10px;
}
</style>
