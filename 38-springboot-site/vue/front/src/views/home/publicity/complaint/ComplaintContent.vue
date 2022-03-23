<template>
  <div class="content">
    <YhTitle title="举报内容" type="03"></YhTitle>
    <div class="form-wrapper">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <div class="el-form-item-cells">
          <div>
            <div class="el-form-item-cell">
              <el-form-item label="投诉主题：" prop="chb091">
                <el-select
                  v-model="formData.chb091"
                  placeholder="请选择投诉主题"
                  style="width: 270px"
                >
                  <template
                    v-for="(item, index) in props.codeMap.chb091"
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
          </div>

          <div class="el-form-item-cell">
            <el-form-item label="投诉标题：" prop="chb092">
              <el-input
                v-model="formData.chb092"
                disabled
                placeholder="请输入投诉标题"
                style="width: 640px"
              />
            </el-form-item>
          </div>

          <div class="el-form-item-cell">
            <el-form-item label="投诉内容：" prop="chb093">
              <el-input
                v-model="formData.chb093"
                type="textarea"
                :autosize="{ minRows: 8 }"
                style="width: 1010px"
              ></el-input>
            </el-form-item>
          </div>

          <div class="el-form-item-cell">
            <el-form-item label="备注：" prop="aae013">
              <el-input
                v-model="formData.aae013"
                type="textarea"
                :autosize="{ minRows: 8 }"
                style="width: 1010px"
              ></el-input>
            </el-form-item>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script setup>
import YhTitle from "@/components/project/YhTitle/YhTitle.vue";
import { ref, reactive,computed,watch } from "vue";

const formRef = ref(null);

const props = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        chb091: "",
        chb092: "",
        chb093: "",
        aae013: "",
      };
    },
  },
  codeMap: {
    type: [Object],
    default: () => {},
  },
});

const formRules = reactive({
  chb091: { required: true, message: "请选择投诉类别", trigger: "change" },
  chb092: {
    required: true,
    message: "请输入投诉标题",
    trigger: "blur",
  },
  chb093: {
    required: true,
    message: "请输入投诉内容",
    trigger: "blur",
  },
  aae013: {
    required: true,
    message: "请选择输入备注",
    trigger: "blur",
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