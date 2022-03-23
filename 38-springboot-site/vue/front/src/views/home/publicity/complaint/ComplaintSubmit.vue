<template>
  <div class="complaint-submit">
    <div class="verification-code">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <div class="el-form-item-cells">
          <div class="el-form-item-cell">
            <el-form-item label="验证码：" prop="input">
              <el-input
                class="my-input"
                maxlength="4"
                v-model="formData.input"
                placeholder="请输入验证码"
                style="height: 40px"
              >
                <template #append>
                  <canvas
                    id="canvas"
                    width="100"
                    @click="draw()"
                    height="32"
                  ></canvas>
                </template>
              </el-input>
            </el-form-item>
          </div>
        </div>
      </el-form>
    </div>
    <div class="agreement-wrapper">
      <YhAgreement
        agreement="《投诉举报须知》"
        showType="complaintNotice"
        :modelValue="formData.isRead"
        @showModel="emit('showModel')"
        @handlerAgree="handlerAgree"
      ></YhAgreement>
    </div>
    <div class="button-wrapper">
      <!-- <el-button type="primary" disabled>测试</el-button> -->
      <button class="primary reverse" @click="emit('reset')">重置</button>
      <button class="primary" :disabled="props.hadComplaind" @click="emit('submit')">提交</button>
    </div>
  </div>
</template>

<script setup>
import YhAgreement from "@/components/common/YhAgreement/YhAgreement.vue";

import { ref, reactive, onMounted } from "vue";

const arggen = ref(false);
const formRef = ref(null);
const emit = defineEmits(["reset", "submit","showModel"]);

const submitData = reactive({
  randomNum: [],
});

const props = defineProps({
  formData: {
    type: Object,
    default: () => {
      return {
        input: "",
        isRead: false,
      };
    },
  },
  hadComplaind: {
    type: Boolean,
    default: () => {
      return false
    },
  },
});

const handleCheckRandomInput = (rule, value, callback) => {
  if (!value) {
    callback(new Error("请输入验证码"));
  } else if (value.toLowerCase() !== submitData.randomNum.join("")) {
    draw();
    callback(new Error("请验证码错误请重试"));
  } else {
    callback();
  }
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

const formRules = reactive({
  input: { validator: handleCheckRandomInput, trigger: "blur" },
});

//生成并渲染出验证码图形
const draw = () => {
  let show_num = [];
  var canvas_width = document.getElementById("canvas").width;
  var canvas_height = document.getElementById("canvas").height;
  var canvas = document.getElementById("canvas"); //获取到canvas的对象，演员
  var context = canvas.getContext("2d"); //获取到canvas画图的环境，演员表演的舞台
  canvas.width = canvas_width;
  canvas.height = canvas_height;
  var sCode =
    "a,b,c,d,e,f,g,h,i,j,k,m,n,p,q,r,s,t,u,v,w,x,y,z,A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
  var aCode = sCode.split(",");
  var aLength = aCode.length; //获取到数组的长度
  submitData.randomNum = [];
  for (var i = 0; i < 4; i++) {
    //这里的for循环可以控制验证码位数（如果想显示6位数，4改成6即可）
    var j = Math.floor(Math.random() * aLength); //获取到随机的索引值
    // var deg = Math.random() * 30 * Math.PI / 180;//产生0~30之间的随机弧度
    var deg = Math.random() - 0.5; //产生一个随机弧度
    var txt = aCode[j]; //得到随机的一个内容
    show_num[i] = txt.toLowerCase();
    var x = 10 + i * 20; //文字在canvas上的x坐标
    var y = 20 + Math.random() * 8; //文字在canvas上的y坐标
    context.font = "bold 23px 微软雅黑";

    context.translate(x, y);
    context.rotate(deg);

    context.fillStyle = randomColor();
    context.fillText(txt, 0, 0);

    context.rotate(-deg);
    context.translate(-x, -y);
  }
  submitData.randomNum = [...show_num];
  for (var i = 0; i <= 5; i++) {
    //验证码上显示线条
    context.strokeStyle = randomColor();
    context.beginPath();
    context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
    context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
    context.stroke();
  }
  for (var i = 0; i <= 30; i++) {
    //验证码上显示小点
    context.strokeStyle = randomColor();
    context.beginPath();
    var x = Math.random() * canvas_width;
    var y = Math.random() * canvas_height;
    context.moveTo(x, y);
    context.lineTo(x + 1, y + 1);
    context.stroke();
  }
};

//得到随机的颜色值
const randomColor = () => {
  var r = Math.floor(Math.random() * 256);
  var g = Math.floor(Math.random() * 256);
  var b = Math.floor(Math.random() * 256);
  return "rgb(" + r + "," + g + "," + b + ")";
};

const handleCheckRandom = () => {
  return submitData.input == randomNum.join("");
};

const handlerAgree = () => {
  props.formData.isRead = true
}

onMounted(() => {
  draw();
});
defineExpose({
  handleSubmit,
});
</script>

<style lang="scss" scoped>
.complaint-submit {
  width: 100%;
  padding-top: 34px;
  margin-top: 16px;
  //   border-top: solid 1px #c9e0ff;

  user-select: none;

  .verification-code {
    width: 400px;
    position: relative;
    margin-left: 20px;
    .code-label {
      display: flex;
      align-items: center;
      .label {
        font-size: 18px;
        color: #666;
        width: 85px;
      }
    }
    // canvas {
    //   position: absolute;
    //   top: 0;
    //   right: 0;
    //   display: block;
    //   object-fit: cover;
    //   background-clip: padding-box;
    //   box-sizing: border-box;
    //   width: 100px;
    //   height: 40px;
    // }
  }

  .agreement-wrapper {
    text-align: center;
    margin-top: 30px;
  }

  .button-wrapper {
    display: flex;
    justify-content: center;
    padding-top: 30px;
    button {
      width: 144px;
      line-height: 40px;
      font-weight: 700;
    }
    .reverse {
      margin-right: 30px;
    }
  }
}
</style>
