<template>
  <div class="contact-us">
    <div class="container">
      <div class="content">
        <div class="left">
          <div class="name">联系我们</div>
          <div class="name-en">Contact us</div>
          <img :src="image01" alt="" />
        </div>
        <div class="right">
          <div class="toolbar">
            <div class="item" :style="background.oneItemStyle"
                 @click="changeAab241(1)" >
              <div class="img-wrapper">
                <img :src="image05" alt="" />

              </div>
              <div>媒体联系</div>
            </div>
            <div class="item" :style="background.twoItemStyle"
                 @click="changeAab241(2)">
              <div class="img-wrapper">
                <img :src="image03" alt="" />
              </div>
              <div>客户联系</div>
            </div>
          </div>
          <div class="form-wrapper">
            <el-form
              ref="formRef"
              :model="formData"
              :rules="formRules"
              label-width="54px"
              label-position="left"
            >
              <el-form-item label="姓名：" prop="contactName">
                <el-input
                  type="text"
                  v-model="formData.contactName"
                  maxlength="12"
                  placeholder="请输入您的姓名"
                >
                </el-input>
              </el-form-item>
              <el-form-item label="机构：" prop="contactOrgan">
                <el-input
                  type="text"
                  v-model="formData.contactOrgan"
                  placeholder="请输入机构名称"
                >
                </el-input>
              </el-form-item>
              <el-form-item label="邮箱：" prop="contactEmail">
                <el-input
                  type="text"
                  v-model="formData.contactEmail"
                  placeholder="请输入邮箱"
                >
                </el-input>
              </el-form-item>
              <el-form-item label="电话：" prop="contactPhone">
                <el-input
                  type="text"
                  v-model="formData.contactPhone"
                  placeholder="请输入电话"
                  maxlength="11"
                >
                </el-input>
              </el-form-item>

              <el-form-item label="主题：" prop="contactTheme">
                <el-input
                  type="text"
                  v-model="formData.contactTheme"
                  placeholder="请输入主题"
                >
                </el-input>
              </el-form-item>
              <el-form-item label="内容：" prop="contactContent">
                <el-input
                  type="textarea"
                  v-model="formData.contactContent"
                  placeholder="请输入内容"
                >
                </el-input>
              </el-form-item>
            </el-form>
            <div class="button-wrapper">
              <button class="primary" @click="handleCheck(formRef)">
                提 交
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import image01 from '@/assets/images/contact/01.png'
import image03 from '@/assets/images/contact/03.png'
import image05 from '@/assets/images/contact/05.png'
import {requestB415} from '@/service'

import {reactive, ref, computed, onMounted, watch} from 'vue'
import { requestGetSmsCode, requestCheckSmsCode } from '@/service'
import { ElMessage } from 'element-plus'
import {useRoute} from "vue-router";
import {emailReg ,phoneNumberReg} from "@/utils/regular";

const contacType = ref('')
const formRef = ref(null)

const formData = reactive({
  contactName: '',
  contactOrgan: '',
  contactEmail: '',
  contactPhone: '',
  contactTheme: '',
  contactContent: '',
})
const route = useRoute()

const background= reactive({
  active : {"background-color":"#e8f3f8"},
  oneItemStyle:{"background-color":"#e8f3f8"},
  twoItemStyle:null,
})

let params = reactive([]);
let aab241 = reactive("1");
onMounted(() => {
  let { id } = route.params
   params.push(...id.split("&"))
})
const changeAab241 = (a)=>{
  if(a==1){
    background.oneItemStyle=background.active;background.twoItemStyle=null
    aab241=a
  }else {
    background.twoItemStyle=background.active;background.oneItemStyle=null
    aab241=a
  }
}
// 校验规则
const formRules = reactive({
  contactName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  contactOrgan: [
    { required: true, message: '请输入机构名称', trigger: 'blur' },
  ],
  contactEmail: [{ required: true, message: '请输入邮箱', trigger: 'blur' },
    {pattern: emailReg, message: '请输入正确的电子邮箱', trigger: 'blur',},],
  contactPhone: [{ required: true, message: '请输入电话', trigger: 'blur' },
    {pattern: phoneNumberReg , message:'请输入正确的电话号码' , trigger: 'blur'}],
  contactTheme: [{ required: true, message: '请输入主题', trigger: 'blur' }],
  contactContent: [{ required: true, message: '请输入内容', trigger: 'blur' }],
})

// 提交
const handleSubmit = () => {
  requestB415({
    aab220:params[0],
    aab01w:params[1],
    aab012:params[2],
    aab241:aab241,
    aab242:formData.contactName,
    aab243:formData.contactOrgan,
    aab244:formData.contactEmail,
    aab245:formData.contactPhone,
    aab246:formData.contactTheme,
    aab247:formData.contactContent
  }).then((res) => {
    if(res.code==200){
      ElMessage({
        message: "联系成功",
        type: 'success',
      })
    }else {
      ElMessage({
        message: "联系失败",
        type: 'success',
      })
    }
    console.log("res",res)
  })
  console.log(formData)
}
// 点击下一步 先进行表单校验
const handleCheck = (formEl) => {
  if (!formEl) return
  formEl.validate((valid, formErrObj) => {
    if (valid) {
      handleSubmit()
    } else {
      const formErrPropsArr = []
      for (let item in formErrObj) {
        formErrPropsArr.push(item)
      }
      const firstErrProps = formErrPropsArr[0]
      formEl.scrollToField(firstErrProps)
      return false
    }
  })
}
</script>

<style lang="scss" scoped>
.contact-us {
  width: 100%;
  padding: 30px 0;
  .content {
    background-color: #fff;
    padding: 50px 122px 50px 72px;
    display: flex;
    align-items: center;
    .left {
      width: 338px;
      font-size: 30px;
      font-weight: 700;
      line-height: 1;
      color: #333333;
      text-align: center;
      margin-right: 60px;
      .name-en {
        font-size: 18px;
        font-weight: 300;
        line-height: 1;
        padding-top: 6px;
        color: #999999;
        padding-bottom: 20px;
      }
    }
    .right {
      flex: 1;
      overflow: hidden;
    }
  }
  .toolbar {
    display: flex;
    align-items: center;
    justify-content: center;
    padding-left: 84px;
    .item {
      width: 150px;
      height: 110px;
      background-color: #fff;
      cursor: pointer;
      user-select: none;
      text-align: center;
      font-size: 18px;
      line-height: 1;
      color: #333333;
      padding-top: 16px;
      transition: all 0.3s;
      .img-wrapper {
        height: 56px;
        display: flex;
        img {
          width: 30px;
          margin: auto;
        }
      }
      &.checked,
      &:hover {
        background-color: #eff6ff;
      }
    }
  }
  .form-wrapper {
    padding-top: 30px;

    .button-wrapper {
      display: flex;
      justify-content: center;
      padding-top: 10px;
      button {
        width: 160px;
      }
    }
    .el-form-item {
      margin-bottom: 20px;
    }
  }
}
</style>
