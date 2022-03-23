<template>
  <div class="policy-apply">
    <div class="container">
      <!-- <YhBreadcrumb :renderData="breadcrumbData"></YhBreadcrumb> -->

      <div class="form-apply-wrapper">
        <ApplyProjectInfo
          :renderData="customData.subsidyProgramInfo"
        ></ApplyProjectInfo>

        <ApplyProjectProcess
          :renderData="customData.processList"
        ></ApplyProjectProcess>

        <ApplyInputTip></ApplyInputTip>

        <ApplyBasicInfo
          :formData="customData.corporateInfo"
          ref="basicInfoRef"
        ></ApplyBasicInfo>

        <ApplyType
          v-model="aka131"
          :renderData="customData.aka131List"
        ></ApplyType>

        <ApplyInfo v-if="formData"><slot></slot></ApplyInfo>

        <ApplyFilesInfo
          :renderData="customData.fileList"
          @change="handleChangeFileList"
        ></ApplyFilesInfo>

        <ApplySubmit @reset="handleReset" @submit="handleCheck"></ApplySubmit>

        <ApplyCheck
          v-model:show="isShowPopupCheck"
          :chb000="customData.chb000"
          :corporateInfo="customData.corporateInfo"
          :subsidyProgramInfo="customData.subsidyProgramInfo"
          @submit="isShowPopupResult = true"
        ></ApplyCheck>

        <ApplyResult v-model:show="isShowPopupResult"></ApplyResult>

        <!-- 申报须知弹窗 -->
        <ApplyInstructions
          v-show="customData.isShowApplyInstructions"
          @handleCloseModal="customData.isShowApplyInstructions = false"
          @showMe="customData.isShowApplyInstructions = true"
          :projectInfo="customData.subsidyProgramInfo"
        ></ApplyInstructions>
      </div>
    </div>
  </div>
</template>

<script setup>
import YhBreadcrumb from '@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue'

import ApplyProjectInfo from './ApplyProjectInfo.vue'
import ApplyProjectProcess from './ApplyProjectProcess.vue'
import ApplyInputTip from './ApplyInputTip.vue'
import ApplyBasicInfo from './ApplyBasicInfo.vue'
import ApplyType from './ApplyType.vue'
import ApplyInfo from './ApplyInfo.vue'
import ApplyFilesInfo from './ApplyFilesInfo.vue'
import ApplySubmit from './ApplySubmit.vue'
import ApplyCheck from './ApplyCheck.vue'
import ApplyResult from './ApplyResult.vue'
import ApplyInstructions from './ApplyInstructions.vue'

import { ref, onMounted, computed, reactive, watch, inject } from 'vue'

import { useStore } from 'vuex'
import { ElMessage,ElMessageBox } from 'element-plus'

import config from '@/config'
import { requestC408, requestC409, requestC412, requestC416 } from '@/service'

import { USER_INFO, CORPORATE_INFO } from '@/store/constants'
import { useRouter } from 'vue-router'

const formRef = inject('formRef')
const checkForm = inject('checkForm')
const resetForm = inject('resetForm')

const props = defineProps({
  chi031: {
    type: [String],
    required: true,
  },
  formData: {
    type: [Object],
    required: true,
  },
})

const store = useStore()
const router = useRouter()

const userInfo = computed(() => {
  return store.getters[USER_INFO]
})

const corporateInfo = computed(() => {
  return store.getters[CORPORATE_INFO]
})

// 申报类别
const aka131 = ref('')

const customData = reactive({
  fileList: [], // 文件上传列表
  fileRenderList: [], // 文件上传渲染列表
  processList: [], // 流程数据
  subsidyProgramInfo: {}, // 补贴项目信息
  applyRenderInfo: [], // 申报信息
  applyInfo: {}, // 申报信息
  // 企业基本信息
  corporateInfo: {
    ...corporateInfo.value,
    d_address: '', // 申报地址
    chb015: '', // 企业所属区县
    chb017: '', // 企业所属乡镇
    chb018: '', // 企业所属村
  },
  chb000: '', // 申报主键
  aka131List: [], // 人员类别
  isShowApplyInstructions: false, // 展示申报须知弹窗
})
const basicInfoRef = ref(null)

const activeName = ref('')
const isShowPopupCheck = ref(false)
const isShowPopupResult = ref(false)

// 初始化数据
const initData = (chi031) => {
  Promise.allSettled([
    requestC408(chi031),
    requestC412(chi031),
    requestC416(chi031),
  ]).then((res) => {
    console.log(res, 123456788)
    const C408Res = res[0]
    const C412Res = res[1]
    const C416Res = res[2]

    const { status: C408State, value: C408Dta } = C408Res
    const { status: C412State, value: C412Dta } = C412Res
    const { status: C416State, value: C416Dta } = C416Res

    if (C408State == 'fulfilled' && C408Dta.code != '-1' && C408Dta.data) {
      const { resultList } = C408Dta.data
      resultList.map((item, index) => {
        item.id = item.cpb070
        item.name = item.che011
        item.time = item.cpb072
        item.desc = item.cpb07a
        item.state = 0
        if (index == 0) {
          item.state = 1
        }
      })
      customData.processList = [...resultList]
    }

    if (C412State == 'fulfilled' && C412Dta.code != '-1' && C412Dta.data) {
      const { hi03Map, he09List } = C412Dta.data
      he09List.map((item) => {
        item.images = []
        if (item.che09c) {
          item.num = parseInt(item.che09c)
        } else {
          item.num = parseInt(item.che094)
        }
      })
      customData.fileRenderList = [...he09List]
      customData.subsidyProgramInfo = { ...hi03Map }
    }

    if (C416State == 'fulfilled' && C416Dta.code != '-1' && C416Dta.data) {
      const { he14VoList } = C416Dta.data
      customData.aka131List = [...he14VoList]
    }
  })
}

// 文件上传列表变化
const handleChangeFileList = (data) => {
  const { fileList } = customData
  const { images } = data
  fileList.map((item) => {
    if (item.che092 == data.che092) {
      item.images = [...images]
    }
  })
}

// 根据申报类别获取材料列表
const getFileList = (aka131) => {
  const { fileRenderList } = customData
  const arr = []
  fileRenderList.map((item) => {
    if (item.aka131 === aka131) {
      arr.push(item)
    }
  })
  customData.fileList = arr
}

// 重置数据
const handleReset = () => {
  // 清除基本信息
  // 清除人员类别
  aka131.value = ''

  // 清除受理信息
  resetForm(formRef)

  // 清除上传的文件信息
  customData.fileList = []
  customData.fileRenderList = customData.fileRenderList.map(item => {
    return {...item,images:[]}
  })
  ElMessage.success('重置数据成功！')
}

// 提交数据
const handleSubmit = () => {
  const { chi031, formData } = props
  const { fileList, corporateInfo } = customData
  const arr = []
  console.log(1111111,formData)
  fileList.map((item) => {
    if (item.images.length > 0) {
      item.images.map((ele) => {
        if (ele && ele.path) {
          arr.push({
            chi212: item.che091, // 文件名
            chi214: ele.chi214, // 文件类别
            chi217: '', // 文件备注
            che110: item.che110, // 材料id
            chi215: ele.objectid, // 统一存储id
            che092: item.che092, //材料编码（当政策私有时对应附件id）
          })
        }
      })
    }
  })
  const basicInfo = {
    aab001: corporateInfo.aab001, // 证件号码/企业ID
    aab012: corporateInfo.aab012, // 姓名/企业名称
    chb00l: corporateInfo.aab018, // 成立时间
    chb00u: corporateInfo.aab014, // 企业类型
    chb00z: corporateInfo.aae006, // 注册地址
    chb00p: corporateInfo.aac003, // 企业法人姓名
    chb00y: corporateInfo.aab017, // 所属行业
    chb00s: corporateInfo.aae004, // 联系人姓名
    aae005: corporateInfo.aae005, // 联系电话
    chb00t: corporateInfo.aae007, // 联系人职务
    chb015: corporateInfo.d_address, // 区县
    chb017: corporateInfo.chb017, // 乡镇
    chb018: corporateInfo.chb018, // 村
    chb00j: corporateInfo.aae012,
    chb00w: corporateInfo.aab019
  }

  const params = {
    ...basicInfo,
    ...formData,
    aka131: aka131.value, // 申报类别
    chi031, // 补贴项目编码
    aab301: config.areaCode, // 申报城市
    aac058: '02', // 线上申报 02
    loginid: userInfo.value.loginid, // 登录账号id
    hi21List: JSON.stringify(arr), // 上传的材料列表
  }

  requestC409(params).then((res) => {
    const { data, code } = res
    if (code != '-1' && data) {
      customData.chb000 = data.chb000
      isShowPopupCheck.value = true
    } else {
      ElMessage.error('申报错误！！！！')
    }
  })
}

// 检验文件必传
const hanldeCheckFileList = () => {
  const fileList = customData.fileList
  // 错误对象
  let errorObj = null
  let num = 0
  let tag = true

  for (let i = 0, len = fileList.length; i < len; i++) {
    const item = fileList[i]
    // 必传
    if (item.che09a == '1') {
      num = 0
      for (let j = 0, jlen = item.images.length; j < jlen; j++) {
        const jitem = item.images[j]
        if (jitem && jitem.path) {
          num++
        }
      }
      // 必传项数目不等于已上传数量
      if (num != item.num) {
        errorObj = { ...item }
        break
      }
    }
  }
  // 未完全成功上传
  if (errorObj) {
    ElMessage.error(`${errorObj.che091}未完全上传成功，请检查！`)
    tag = false
  }

  return tag
}

// 校验所有必填项是否填写完成
const handleCheck = () => {
  // 校验基本信息必填
  const basicInfoCheckResult = basicInfoRef.value.handleSubmit()
  if (!basicInfoCheckResult) {
    return
  }
  if (!aka131.value) {
    ElMessage.error('请选择企业申报类别！')
    return
  }

  const applyInfoCheckResult = checkForm(formRef)
  if (!applyInfoCheckResult) {
    return
  }

  // 检验文件必传
  const fileCheckResult = hanldeCheckFileList()
  if (!fileCheckResult) {
    return
  }
  handleSubmit()
}

// 监听申报类别
watch(
  aka131,
  (val) => {
    if (val) {
      getFileList(val)
    }
  },
  { immediate: true }
)

onMounted(() => {
  const { chi031 } = props
  let arr = ['aab001','aab012','aab018','aab014','aae006','aac003','aab017','aae004','aae005',
  // 'chb015','chb017','chb018'
  ]
  let isFullInfo = arr.find(item => !customData.corporateInfo[item])
  console.log(isFullInfo,'isFullInfo')
  if(isFullInfo){
    ElMessageBox.confirm(
      '请先完善企业基本信息，再进行申报！',
      '提示',
      {
        confirmButtonText: '前往完善',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
      }
    )
    .then((res) => {
      router.push('/enterprises/info/basic')
    })
    .catch((e) => {
      router.go(-1)
    })
  } else {
    customData.isShowApplyInstructions = true
  }
  initData(chi031)
})
</script>

<style lang="scss" scoped>
.policy-apply {
  width: 100%;
  padding: 30px 0;
  .form-apply-wrapper {
    background-color: #fff;
    padding: 30px;
  }
}
</style>
