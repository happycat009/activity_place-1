<template>
  <div class="reported">
    <YhTitle title="举报对象信息" type="03"></YhTitle>
    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <div class="el-form-item-cells">
          <div class="el-form-item-cell">
            <el-form-item label="被举报主体：" prop="chb09h" >
              <el-select
                v-model="formData.chb09h"
                placeholder="请选择被举报主体"
                style="width: 270px"
              >
                <el-option label="项目享受企业" value="1"></el-option>
                <el-option label="项目主管部门" value="2"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="el-form-item-cell">
            <el-form-item label="扶持项目：" prop="chi031">
              <el-select
                :disabled="!props.isNeedInput"
                v-model="formData.chi031"
                placeholder="请选择扶持项目"
                style="width: 270px"
              >
                <template v-for="(item, index) in codeMap.chi031" :key="index">
                  <el-option
                    :label="item.aaa103"
                    :value="item.aaa102"
                  ></el-option>
                </template>
              </el-select>
            </el-form-item>
          </div>
        </div>

        <div class="el-form-item-cells" v-if="formData.chb09h == '1'" >
          <div class="el-form-item-cell">
            <el-form-item label="企业名称：" prop="aab012">
              <el-input
                :disabled="!props.isNeedInput"
                v-model="formData.aab012"
                placeholder="请输入企业名称"
                style="width: 270px"
              />
            </el-form-item>
          </div>

          <div class="el-form-item-cell">
            <el-form-item
              label="社会统一信用代码："
              label-width="150px"
              prop="aab01w"
            >
              <el-input
                :disabled="!props.isNeedInput"
                v-model="formData.aab01w"
                placeholder="请输入企业社会统一信用代码"
                style="width: 220px"
              />
            </el-form-item>
          </div>
        </div>

        <div class="el-form-item-cells" v-else>
          <div class="el-form-item-cell">
            <el-form-item label="主管部门：" required prop="chb09i">
              <el-select
                :disabled="!props.isNeedInput"
                v-model="formData.chb09i"
                placeholder="请选择主管部门"
                style="width: 270px"
              >
                <template
                  v-for="(item, index) in props.codeMap.chi037"
                  :key="index"
                >
                  <el-option
                    :label="item.aaa103"
                    :value="item.aaa102"
                  ></el-option>
                </template>
              </el-select>
            </el-form-item>
          </div>

          <div class="el-form-item-cell">
            <el-form-item label="部门级别：" prop="chb015">
              <el-select
                v-model="formData.chb015"
                placeholder="请选择部门级别"
                style="width: 270px"
              >
                <template
                  v-for="(item, index) in props.codeMap.chb015"
                  :key="index"
                >
                  <el-option
                    :label="item.aaa103"
                    :value="item.aaa102"
                  ></el-option>
                </template>
              </el-select>
            </el-form-item>
          </div>
          <div class="el-form-item-cell">
            <el-form-item label="被举报人：">
              <el-input
                v-model="formData.chb09j"
                placeholder="请输入被举报人"
                style="width: 270px"
              />
            </el-form-item>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script setup>
import YhTitle from "@/components/project/YhTitle/YhTitle.vue";
import { ref, reactive, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { requestE412 } from "@/service";

const route = useRoute();
const router = useRouter();

const formRef = ref(null);

const props = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        chb09h: "",
        aab012: "",
        aab001: "",
        chi037: "",
        aab01w: "",
        chi031: "",
        chb09i: "",
        chb09j: "",
      };
    },
  },
  isNeedInput: {
    type: Boolean,
    default: true,
  },
  codeMap: {
    type: [Object],
    default: () => {},
  },
});

const formRules = reactive({
    chb09h: {
    required: true,
    message: "请选择投诉主体",
    trigger: "change",
  },
    chi031: {
    required: true,
    message: "请选择扶持项目",
    trigger: "change",
  },
  aab012: { required: true, message: "请输入被举报企业名称", trigger: "blur" },
  aab01w: {
    required: true,
    message: "请输入统一社会信用代码",
    trigger: "blur",
  },
  chb09i: {
    required: true,
    message: "请选择主管部门",
    trigger: "change",
  },
    chb015: {
    required: true,
    message: "请选择部门级别",
    trigger: "change",
  },
});

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

defineExpose({
  handleSubmit,
});
</script>
<style lang="scss" scoped>
.form-wrapper {
  margin-top: 10px;
  .el-form-item-cells {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
  }
}
</style>