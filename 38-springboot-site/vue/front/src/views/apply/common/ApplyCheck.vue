<template>
  <YhPopup
    :modelValue="show"
    popupName="申报结果提示"
    :maskClose="false"
    @update:modelValue="handleCloseModal($event)"
  >
    <div class="apply-check-content">
      <div class="check-result" :style="checkResultStyle">
        <div class="left">
          <img :src="imageLogoSuccess" v-if="checkInfo.state == '1'" alt="" />
          <img :src="imageLogoFail" v-else alt="" />
          <div class="con">
            <el-tooltip :content="subsidyProgramInfo.chi032">
              <div class="project-name">
                {{ subsidyProgramInfo.chi032 }}
              </div>
            </el-tooltip>

            <div class="cells">
              <el-tooltip :content="'企业名称：' + corporateInfo.aab012">
                <div class="cell">企业名称：{{ corporateInfo.aab012 }}</div>
              </el-tooltip>
              <el-tooltip content="统一社会信用代码：9745721964798888576">
                <div class="cell">
                  统一社会信用代码：{{ corporateInfo.aab01w }}
                </div>
              </el-tooltip>
              <el-tooltip :content="'注册地址：' + corporateInfo.aab019">
                <div class="cell">注册地址：{{ corporateInfo.aab019 }}</div>
              </el-tooltip>
            </div>
          </div>
        </div>
        <div class="right">
          <div class="result">
            <img
              :src="imageResultSuccess"
              v-if="checkInfo.state == '1'"
              alt=""
            />
            <img :src="imageResultFail" v-else alt="" />
            <span class="apply-result">{{
              checkInfo.state == '1' ? '申报成功！' : '申报失败！'
            }}</span>
          </div>
          <div class="cells">
            <div class="cell">
              <div class="key">申报时间：</div>
              <div class="value">{{ checkInfo.time }}</div>
            </div>
            <div class="cell">
              <div class="key">本次校验共计完成：</div>
              <div class="value">{{ checkInfo.checkList.length }}项</div>
            </div>
            <div class="cell success">
              <div class="key">校验通过：</div>
              <div class="value">{{ checkInfo.successNum }}项</div>
            </div>
            <div class="cell fail">
              <div class="key">校验失败：</div>
              <div class="value">{{ checkInfo.failNum }}项</div>
            </div>
          </div>
        </div>
      </div>
      <div class="check-list">
        <div class="check-name">
          <div class="left">
            <div>资格验证</div>
            <span
              >（校验通过{{ checkInfo.successNum }}项，检验未通过{{
                checkInfo.failNum
              }}项）</span
            >
          </div>
          <div class="right">
            共<em>{{ checkInfo.checkList.length }}</em
            >项
          </div>
        </div>
        <ul>
          <template v-for="(item, index) in sortCheckList(checkInfo.checkList)" :key="index">
            <li>
              <div class="left">
                <i
                  class="iconfont iconfont-success"
                  v-if="item.chr032 == '1'"
                ></i>
                <i class="iconfont iconfont-hint" v-if="item.chr032 == '3'"></i>
                <i class="iconfont iconfont-fail" v-if="item.chr032 == '2'"></i>
              </div>
              <div class="center">
                {{ item.chr023 }}
              </div>

              <div class="center" v-if="item.chr032 == '3' || item.chr032 == '2' ">
                {{ item.chr037 }}
              </div>

              <div
                class="right"
                :class="[
                  item.chr032 == '1'
                    ? 'success'
                    : item.chr032 == '2'
                    ? 'fail'
                    : 'hint',
                ]"
              >
                <span>{{ item.chr032Desc }}</span>
              </div>
            </li>
          </template>
        </ul>
      </div>
      <!-- 线下提交材料时才显示 -->
      <div class="tip-wrapper" v-if="false">
        <h3>线下材料提交说明：</h3>
        <div>
          在线申报完成后10个工作日内提交到内江市商务局大厅5号窗口（地址：内江市东兴区太白路49号）在线申报完成后10个工作日内提交到内江市商务局大厅5号窗口（地址：内江市东兴区太白路49号）
        </div>
        <span class="btn" @click="isShowPopupMaterial = true">查看材料</span>
      </div>
      <div class="button-wrapper">
        <button
          class="primary reverse"
          v-if="checkInfo.state == '2'"
          @click="emit('update:show', false)"
        >
          返回继续修改
        </button>
        <!-- <button
            class="primary"
            style="width: 160px"
            v-if="checkInfo.state == '1'"
            @click="router.go(0)"
        >
          确认提交
        </button> -->
        <button class="primary reverse"
        style="width: 160px"
        v-if="checkInfo.state == '1'"
         @click="router.push('/home')">
          返回首页
        </button>
        <button
          class="primary"
          style="width: 160px"
          v-if="checkInfo.state == '1'"
          @click="handleJumpRoute"
        >
          查看申报历史
        </button>
      </div>
    </div>
  </YhPopup>
  <YhPopup
    v-model="isShowPopupMaterial"
    popupName="提交材料说明"
    :maskClose="false"
  >
    <div class="apply-material-content">
      <div class="tip-wrapper">
        <h3>要求：</h3>
        <div>
          <p>1.申报书用仿宋体小四号字填写；凡不填内容的栏目，均用“无”表示。</p>
          <p>2.申报单位对所填写内容的真实性、完整性负责。</p>
          <p>
            3.在线申报完成后10个工作日内提交到内江市商务局大厅5号窗口（地址：内江市东兴区太白路49号）
          </p>
        </div>
      </div>
      <div class="table-wrapper">
        <YhTableApplyMaterial
          :table-head="tableHead"
          :table-data="tableData"
          @download="handleDownload"
        ></YhTableApplyMaterial>
      </div>
    </div>
  </YhPopup>
</template>

<script setup>
import imageBgCheckSuccess from '@/assets/images/apply/bg-check-success.png'
import imageBgCheckFail from '@/assets/images/apply/bg-check-fail.png'

import imageLogoSuccess from '@/assets/images/apply/logo-success.png'
import imageLogoFail from '@/assets/images/apply/logo-faill.png'

import imageResultSuccess from '@/assets/images/apply/result-success.png'
import imageResultFail from '@/assets/images/apply/result-fail.png'

import YhPopup from '@/components/common/YhPopup/YhPopup.vue'
import YhTableApplyMaterial from '@/components/elementPlusExtend/YhTableApplyMaterial/YhTableApplyMaterial.vue'

import { ref, computed, watch, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

import { requestD403 } from '@/service'
import { useMoment } from '@/use'

const router = useRouter()

const isShowPopupMaterial = ref(false)

// 校验信息
const checkInfo = reactive({
  time: useMoment(new Date(), 'YYYY年MM月DD日 hh:mm:ss', false),
  state: '', // 1 校验成功 2 校验失败
  successNum: 0,
  checkList: [],
  failNum: 0,
})


const emit = defineEmits(['update:show', 'submit'])
const props = defineProps({
  show: {
    type: Boolean,
    required: true,
  },
  chb000: {
    type: String,
    required: true,
  },
  // 企业信息
  corporateInfo: {
    type: Object,
    required: true,
  },
  // 补贴项目信息
  subsidyProgramInfo: {
    type: Object,
    required: true,
    default: () => {},
  },
})

const handleCloseModal = (e) => {
  console.log(11111)
  if(checkInfo.state == '1'){
    router.go(0)
  }
  if(checkInfo.state == '2'){
    emit('update:show', e)
  }
}

const sortCheckList = (items)=>{
  items = items.sort((x,y) => x.chr032 - y.chr032)
  return items
}

const tableHead = [
  {
    prop: 'index',
    label: '序号',
  },
  {
    prop: 'che091',
    label: '材料名称',
  },
  {
    prop: 'che09b',
    label: '材料说明',
  },
  {
    prop: 'type',
    label: '提交方式',
  },
  {
    prop: 'che09a',
    label: '是否必要',
  },
  {
    prop: 'download',
    label: '材料下载',
  },
]
const tableData = [
  {
    che091: '材料1',
    che09b: '材料说明1',
    type: '线下提交',
    che09a: '1',
    download: '下载',
  },
]



const checkResultStyle = computed(() => {
  const { state } = checkInfo
  let str = ''
  if (state == '1') {
    str += `background-image:url(${imageBgCheckSuccess}); `
  } else {
    str += `background-image:url(${imageBgCheckFail}); `
  }
  return str
})

// 跳转路由
const handleJumpRoute = () => {
  router.push('/enterprises/record')
}

// 获取数据
const getData = async (chb000) => {
  let successList = []
  let failList = []
  let checkList = []
  const res = await requestD403(chb000)
  const { code, data } = res
  if (code != '-1' && data) {
    const { matchReportItemVo } = data
    if (matchReportItemVo) {
      const { reportItemDetailVoList, chr082 } = matchReportItemVo
      if (reportItemDetailVoList && reportItemDetailVoList.length > 0) {
        reportItemDetailVoList.map((item) => {
          checkList.push(item)
          if (item.chr032 == '1') {
            successList.push(item)
          } else {
            failList.push(item)
          }
        })
      }
      checkInfo.state = chr082
    }
    checkInfo.failNum = failList.length
    checkInfo.checkList = checkList
    checkInfo.successNum = successList.length

    checkInfo.time = useMoment(new Date(), 'YYYY年MM月DD日 hh:mm:ss', false)
  } else {
    ElMessage.error('校验接口调用失败！')
  }
}

watch(
  props,
  (val) => {
    const { chb000 } = val
    if (chb000) {
      getData(chb000)
    }
  },
  { immediate: true }
)
</script>

<style lang="scss" scoped>
.apply-check-content {
  width: 100%;
  max-height: 600px;
  overflow: auto;
  padding: 24px;
  .check-result {
    display: flex;
    background-repeat: no-repeat;
    background-size: cover;
    padding: 16px;
    color: #fff;
    .left {
      flex: 1;
      overflow: hidden;
      display: flex;
      align-items: center;
      img {
        width: 144px;
        margin-right: 30px;
      }
      .con {
        flex: 1;
        overflow: hidden;
        padding-right: 16px;
      }
      .project-name {
        font-size: 20px;
        @include textOverflow();
        margin: 16px 0 26px;
      }
      .cells {
        .cell {
          font-size: 14px;
          line-height: 1.4;
          @include textOverflow();
          margin-bottom: 18px;
        }
      }
    }
    .right {
      display: flex;
      align-items: center;
      position: relative;
      .result {
        position: relative;
        img {
          width: 100%;
        }
        span {
          width: 100%;
          font-weight: 700;
          text-align: center;
          position: absolute;
          text-align: center;
          left: 0;
          bottom: 20px;
        }
      }
      .cells {
        background-color: rgba(0, 0, 0, 0.08);
        border-radius: 4px;
        font-size: 12px;
        line-height: 1;
        padding: 22px 16px;
        .cell {
          white-space: nowrap;
          display: flex;
          align-items: center;
          margin-bottom: 24px;
          &:last-child {
            margin-bottom: 0;
          }
          &.success {
            .value {
              color: var(--color-success);
            }
          }
          &.fail {
            .value {
              color: var(--color-fail);
            }
          }
        }
      }
      &::before {
        content: '';
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        left: 0;
        width: 3px;
        height: 160px;
        background-color: rgba(#fff, 0.1);
      }
    }
  }
  .check-list {
    border: solid 1px #d6e7ff;
    font-size: 16px;
    color: #333333;
    margin-top: 32px;
    .check-name {
      display: flex;
      align-items: center;
      justify-content: space-between;
      height: 56px;
      background-color: #eff6ff;
      padding: 0 16px;
      border-bottom: 1px solid #d6e7ff;
      .left {
        font-weight: 700;
        display: flex;
        align-items: center;
        span {
          font-size: 16px;
          color: var(--color-primary);
          padding-left: 2px;
        }
      }
      .right {
        font-size: 14px;
        em {
          color: var(--color-primary);
        }
      }
    }
    ul {
      li {
        border-bottom: 1px solid #d6e7ff;
        display: flex;
        align-items: center;
        min-height: 56px;
        font-size: 14px;
        line-height: 1.4;
        padding: 16px;
        .left {
          width: 10%;
          font-size: 20px;
          .iconfont-success {
            color: var(--color-success);
          }
          .iconfont-fail {
            color: var(--color-fail);
          }
          .iconfont-hint {
            color: var(--color-warn);
          }
        }
        .center {
          flex: 1;
          overflow: hidden;
        }
        .right {
          width: 10%;
          text-align: right;
          &.success {
            color: var(--color-success);
          }
          &.fail {
            color: var(--color-fail);
          }
          &.hint {
            color: var(--color-warn);
          }
        }
        &:last-child {
          border-bottom: 0;
        }
      }
    }
  }
  .tip-wrapper {
    background-color: #eff6ff;
    border: solid 1px #d6e7ff;
    padding: 20px 16px;
    font-size: 14px;
    color: #333;
    margin-top: 40px;
    position: relative;
    h3 {
      font-weight: 700;
      line-height: 1;
    }
    div {
      line-height: 20px;
      margin-top: 10px;
      padding-right: 120px;
    }
    span {
      display: inline-block;
      font-size: 14px;
      color: var(--color-primary);
      position: absolute;
      right: 16px;
      top: 50%;
      transform: translateY(-50%);
      transition: all 0.3s;
      cursor: pointer;
      user-select: none;
      &:hover {
        text-decoration: underline;
      }
    }
  }
  .button-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px 102px;
    button {
      width: 98px;
      margin-right: 20px;
      &:last-child {
        margin-right: 0;
      }
    }
  }
}
.apply-material-content {
  width: 100%;
  max-height: 400px;
  overflow: auto;
  padding: 24px;
  .tip-wrapper {
    font-size: 14px;
    color: #333;
    line-height: 1.2;
    padding-bottom: 10px;
    border-bottom: 1px solid #dedede;
    h3 {
      font-weight: 700;
      margin-bottom: 12px;
    }
    p {
      margin-bottom: 12px;
    }
  }
  .table-wrapper {
    padding-top: 30px;
  }
  .apply-result{
    font-size: 20px;
  }
}
</style>
