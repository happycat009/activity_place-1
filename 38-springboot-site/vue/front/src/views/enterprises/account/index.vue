<template>
  <div class="enterprise-account">
    <div class="button-wrapper" v-if="isAdmin">
      <button class="primary" @click="handleAddAccount">
        添加企业经办账号
      </button>
    </div>
    <AccoutTable
      :tableData="customData.accountList"
      @resetPassword="handleResetPassword"
      @delete="handleDelete"
    ></AccoutTable>
    <AccountForm
      v-model:show="isShowPopup"
      :popupTitle="customData.popupTitle"
      :formData="customData.accountInfo"
      @addSuccess="getData"
    ></AccountForm>
  </div>
</template>

<script setup>
import AccoutTable from './AccoutTable.vue'
import AccountForm from './AccountForm.vue'

import { ref, reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { requestB416, requestB202 } from '@/service'
import { CORPORATE_INFO, USER_INFO } from '@/store/constants'
import { useDesensitizedIdCard } from '@/use'
import { ElMessage } from 'element-plus'

const store = useStore()
const isShowPopup = ref(false)

const customData = reactive({
  popupTitle: '添加企业经办账号',
  accountInfo: {
    loginid: '',
    name: '',
    tel: '',
    password: '',
    orgid: '',
    isadmin: '0',
    isEdit: false, // 标记是添加或者修改
  },
  accountList: [], // 账号列表
})

const corporateInfo = computed(() => {
  return store.state[CORPORATE_INFO]
})
const userInfo = computed(() => {
  return store.state[USER_INFO]
})
const isAdmin = computed(() => {
  let result = false
  const { positionInfo } = userInfo.value
  if (positionInfo.isadmin == '1') {
    result = true
  }
  return result
})

// 获取数据
const getData = async () => {
  const { aab010 } = corporateInfo.value
  const res = await requestB416(aab010)
  const { userList } = res.data
  userList.map((item) => {
    item.isEncryptLoginid = true
    item.encryptLoginid = useDesensitizedIdCard(item.loginid)
  })
  customData.accountList = userList
}

// 添加账号
const handleAddAccount = () => {
  customData.accountInfo = {
    loginid: '',
    name: '',
    tel: '',
    password: '',
    orgid: userInfo.value.orgid,
    isadmin: '0',
    isEdit: false, // 标记是添加或者修改
  }
  isShowPopup.value = true
  customData.popupTitle = '添加企业经办账号'
}

// 重置密码
const handleResetPassword = (data) => {
  customData.accountInfo = { ...data, password: '', isEdit: true }
  customData.popupTitle = '修改经办账号'
  isShowPopup.value = true
}

//删除账号
const handleDelete = (data) => {
  const { positionid, userid, orgid } = data
  requestB202(positionid, userid, orgid).then(() => {
    ElMessage.success('删除成功')
    getData()
  })
}

onMounted(() => {
  getData()
})
</script>

<style lang="scss" scoped>
.enterprise-account {
  width: 100%;
  background-color: #ffffff;
  border: solid 2px rgba(216, 232, 255, 0.51);
  padding: 30px;
  .button-wrapper {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    padding-bottom: 30px;
  }
}
</style>
