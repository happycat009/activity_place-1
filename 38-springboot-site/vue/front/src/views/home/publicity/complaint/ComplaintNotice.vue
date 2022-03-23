<template>
  <div class="complaint-content">
    <div class="box">
      <div class="close" @click="handleCloseModel">
        <img src="~assets/images/home/complaint/close.png" alt="" />
      </div>
      <div class="title">
        <span>投诉</span>
        <span>举报</span>
        <span>须知</span>
      </div>
      <div class="content">
        <ul>
          <li
            v-for="(item, index) in customData.notice"
            :key="index"
            v-html="index + 1 + '、' + item.text"
          ></li>
        </ul>
      </div>
      <div class="know-btn">
        <button @click="handleCloseModel">我已阅读</button>
      </div>
    </div>
  </div>
</template>
<script setup>
import { reactive } from "vue";

const customData = reactive({
  notice: [
    {
      text: "本平台目前主要受理有关企业扶持资金、惠企领域腐败和作风问题等投诉举报。其他信访事项、涉法涉诉等问题，请向有关职能部门反映。",
    },
    {
      text: '投诉举报人需使用真实姓名，并如实填写举报信息栏中的各项内容，标有“<span style="color:red;">*</span>”的为必填项目。',
    },
    {
      text: "投诉举报人应当遵守有关法律法规，不得诬告、陷害他人，不得损害国家、社会、集体的利益和其他公民的合法权利。对借投诉举报诬告陷害他人的，依照有关规定严肃处理。",
    },
    {
      text: "投诉举报的内容应当客观、真实，表述尽可能清晰、具体，不得捏造、歪曲事实。",
    },
    {
      text: "根据投诉举报内容，本平台将按照“属地管理、分级负责”和“谁主管、谁负责”的原则，依纪依规进行办理。",
    },
    {
      text: "对投诉举报的办理结果等信息，本平台将通过手机短信通知您，请注意登录平台查看详情。",
    },
    {
      text: "本平台拥有管理投诉举报的一切权利，包括审核、发布、引用和删除您的留言及其他必要的管理权力。",
    },
  ],
});

const emit = defineEmits(["closeModel"]);
const handleCloseModel = () => {
  emit("closeModel");
};
</script>
<style lang="scss" scoped>
.complaint-content {
  position: fixed;
  width: 100%;
  height: 100%;
  background: rgba(51, 51, 51, 0.3);
  top: 0;
  left: 0;
  z-index: 9999;

  .box {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 1097px;
    padding: 20px;
    background: #fff;
    box-shadow: 1px 1px 8px;
    .close {
      position: absolute;
      top: 20px;
      right: 20px;
      width: 24px;
      height: 24px;
      z-index: 99;
      img {
        width: 24px;
        height: 24px;
      }
    }
    .title {
      position: relative;
      text-align: center;
      margin-bottom: 20px;
      &::after {
        content: "NOTICE";
        position: absolute;
        display: block;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        color: #cccccc36;
        font-size: 56px;
        font-weight: 700;
        z-index: -1;
        // font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
      }
      span {
        font-size: 24px;
        font-weight: 700;
        &:nth-child(1) {
          color: #2583ea;
        }
        &:nth-child(2) {
          color: #ffbe6d;
        }
      }
    }
    .content {
      min-height: 300px;
      max-height: 500px;
      ul {
        box-shadow: 1px 1px 6px #bfdfff;
        li {
          // background: #bfdfff;
          // color: #fff;
          padding: 10px;
          margin-bottom: 5px;
        }
      }
    }
    .know-btn {
      height: 56px;
      background-color: #fff;
      text-align: center;
      display: flex;
      align-items: center;
      justify-content: center;

      button {
        background-color: #5098ff;
        border-radius: 5px;
        width: 140px;
        height: 36px;
        font-size: 13px;
        color: #fff;
      }

      //   .permary {
      //     background: #fff;
      //     border: 1px solid #5098ff;
      //     color: #5098ff;
      //   }
    }
  }
}
</style>