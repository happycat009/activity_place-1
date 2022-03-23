<template>
  <div class="baisc-info">
    <YhTitle title="基本信息" type="03"></YhTitle>
    <div class="hint">
      <i class="iconfont iconfont-hint"></i>
      <span>以下数据来源为内江市市场监督管理局，如有误，请联系数据来源方进行修正。联系电话：0832-2171013</span>
    </div>
    <div class="content">
      <el-form
        ref="formRef"
        :model="formData"
        label-width="132px"
        :disabled="!isEditAble"
        :rules="formRules"
      >
        <div class="el-form-item-cell">
          <el-form-item label="企业名称：">
            <el-input
              v-model="corporateInfo.aab012"
              :disabled="true"
              placeholder="请输入企业名称"
            ></el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="统一社会信用代码：">
            <el-input
              v-model="corporateInfo.aab01w" :disabled="true"
              placeholder="请输入统一社会信用代码"
            ></el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="法定代表人：">
            <el-input v-model="corporateInfo.aac003" :disabled="true" placeholder="请输入法定代表人">
            </el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="注册资本：">
            <el-input v-model="corporateInfo.aab01a" :disabled="true" placeholder="请输入注册资本">
              <template #suffix>
                <i class="input-unit">万元</i>
              </template>
            </el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="成立时间：">
            <el-date-picker
              v-model="corporateInfo.aab018"
              type="date"
              :disabled="true"
              value-format="YYYY-MM-DD"
              placeholder="请选择注册时间"
              style="width: 100%"
            ></el-date-picker>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="注册地址：">
            <el-input v-model="corporateInfo.aab019" :disabled="true" placeholder="请输入企业地址">
            </el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="经营范围：">
            <el-input type="textarea"
                      :rows="4" :input-style="{'height':'100px'}"
                      v-model="corporateInfo.aab01t" :disabled="true" placeholder="请输入主营业务（经营范围）">
            </el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="企业类型：">
            <el-select v-model="corporateInfo.aab014" :disabled="true" placeholder="请选择企业类型">
              <el-option
                      v-for="(val, key) in getCollectionList('aab014')"
                      :label="val"
                      :value="key"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="所属行业：">
            <el-select
                    :span="24"
                    v-model="corporateInfo.aab017"
                    :disabled="true"
                    placeholder="请选择所属行业"
            >
              <el-option
                      v-for="(val, key) in getCollectionList('aab017')"
                      :label="val"
                      :value="key"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="hint notice">
          <i class="iconfont iconfont-hint"></i>
          <span>以下数据由企业自主填报</span>
          <span v-if="isEditAble">
          <button
                  class="primary reverse cancel-button"
                  @click="editAbleChange(false)"
          >
            撤销
          </button>
          <button
                  class="primary reverse save-button"
                  style="background-color: #409eff; color: white"
                  @click="handleSubmit"
          >
            保存
          </button>
        </span>
          <span v-else>
          <button class="primary reverse edit-button" @click="editAbleChange(true)">
            编辑
          </button>
        </span>
        </div>
        <div class="el-form-item-cell" style="margin-bottom: 30px">
          <YhSelectAddress
                  label="生产经营地址："
                  label-width="132px"
                  v-model="corporateInfo.aae006"
          ></YhSelectAddress>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="开户行名称：" prop="aae008">
            <el-select
                    v-model="formData.aae008"
                    placeholder="请选择开户行名称"
            >
              <el-option
                      v-for="(val, key) in getCollectionList('aae008')"
                      :label="val"
                      :value="key"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="开户行户名：">
            <el-input v-model="formData.aae009" maxlength="20" placeholder="企业和产业活动单位填报对公账名">
            </el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="开户行账号：" prop="aae010">
            <el-input v-model="formData.aae010" placeholder="企业和产业活动单位填报对公账号">
            </el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="企业联系人：">
            <el-input v-model="formData.aae004" maxlength="8" placeholder="请输入企业联系人">
            </el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="联系人职务：">
            <el-input v-model="formData.aae007" maxlength="8" placeholder="请输入联系人职务">
            </el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="联系人电话：" prop="aae005">
            <el-input
              v-model="formData.aae005"
              placeholder="请输入联系人电话"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="el-form-item-cell">
          <el-form-item label="联系人邮箱：" prop="aae012">
            <el-input
                    v-model="formData.aae012"
                    placeholder="请输入联系人邮箱"
            >
            </el-input>
          </el-form-item>
        </div>

        <div class="el-form-item-cell el-form-item-cell-button">
          <el-form-item>
            <el-button type="primary" @click="handleSubmit">保存</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>

    <el-dialog v-model="dialogVisible" title="提示" width="30%">
      <b style="font-size: 20px">有内容未保存，确认撤销修改？</b>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="danger" @click="cancelEdit">确认撤销</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { requestA402, requestB301, requestB402, requestB404 } from '@/service'
import { mapGetters } from 'vuex'
import {
  CORPORATE_INFO,
  CURRENT_MENU_LIST,
  M_UPDATE_CORPORATE_INFO,
  USER_INFO,
} from '@/store/constants'
import { emailReg, phoneNumberReg } from '@/utils/regular'

export default {
  name: 'EnterprisesInfoBasic',
  data() {
    return {
      formOraData: {},
      formData: {},
      isEditAble: false,
      dialogVisible: false,
      formRules: {
        aae005: [
          {
            required: false,
            message: "请输入正确的电话号码",
            trigger: "change",
          },
          {
            pattern: phoneNumberReg,
            message: "请输入正确的电话号码",
            trigger: "change",
          },
        ],
        aae010: [
          {
            required: false,
            message: "请输入正确的开户行账号",
            trigger: "change",
          },
          {
            pattern: /^([1-9]{1})(\d{14}|\d{18})$/,
            message: "请输入正确的开户行账号",
            trigger: "change",
          },
        ],
        aae012: [
          {
            required: false,
            message: "请输入正确的联系人邮箱",
            trigger: "change",
          },
          {
            pattern: emailReg,
            message: "请输入正确的联系人邮箱",
            trigger: "change",
          },
        ]
      }
    }
  },
  mounted() {
    this.userCodeTableMap('aab014')
    this.userCodeTableMap('aab017')
    this.userCodeTableMap('aae008')

    // 支持首次
    const { edit } = this.$route.query
    if (edit == '1') {
      this.editAbleChange(true)
    }
  },
  methods: {
    /**
     * 修改接口新增可修改字段，不再大幅度改动调用方式与逻辑，添加字段即可
     */
    renderCorpoData() {
      const {
        aae005,
        aae008,
        aae009,
        aae010,
        aae004,
        aae007,
        aae012
      } = this.corporateInfo
      this.formOraData = {
        aae005,
        aae008,
        aae009,
        aae010,
        aae004,
        aae007,
        aae012
      }
      this.formData = {
        aae005,
        aae008,
        aae009,
        aae010,
        aae004,
        aae007,
        aae012
      }
    },
    editAbleChange(editAble) {
      if (editAble) {
        this.$message.success('请认真填写公司信息！')
        this.isEditAble = editAble
      } else {
        let flag = false
        for (let key in this.formData) {
          if (this.formData[key] !== this.formOraData[key]) {
            flag = true
            break
          }
        }
        if (flag) {
          this.dialogVisible = true
        } else {
          this.isEditAble = editAble
          this.$message.success('取消编辑')
        }
      }
    },
    cancelEdit() {
      this.isEditAble = false
      this.dialogVisible = false
    },
    handleSubmit() {
      const {
        aae005,
        aae008,
        aae009,
        aae010,
        aae004,
        aae007,
        aae012
      } = this.formData
      const { aab010 } = this.corporateInfo
      // 提交保存，将信息保存到浏览器
      requestB301('ab01', {
        aab010,
        aae005,
        aae008,
        aae009,
        aae010,
        aae004,
        aae007,
        aae012
      }).then((res) => {
        const { code, data } = res
        if ('200' == code) {
          this.$message.success('企业基础信息更新成功！')
          this.updateCorporateInfo()
        }
      })
    },
    async updateCorporateInfo() {
      const A402Data = await requestA402(this.userInfo.user_id)
      const { positionList } = A402Data.data
      let positionInfo = null
      let corporateInfo = null
      if (positionList.length > 0) {
        positionInfo = positionList[0]
        const B404Data = await requestB404(positionInfo.orgid)
        const { row } = B404Data.data.ab01Map.ab01Domains
        corporateInfo = row
      }
      this.$store.commit(M_UPDATE_CORPORATE_INFO, corporateInfo)
      this.isEditAble = false
    },
  },
  computed: {
    ...mapGetters([USER_INFO, CURRENT_MENU_LIST, CORPORATE_INFO]),
  },
  watch: {
    isEditAble: {
      handler(val) {
        if (!val) {
          // 转化为不可编辑状态后需要刷新公司信息
          this.renderCorpoData()
        }
      },
      immediate: true,
    },
  },
}
</script>

<style lang="scss" scoped>
.baisc-info {
  width: 100%;
  padding: 30px;
  position: relative;
  .content {
    padding-top: 30px;
  }
  .edit-button {
    position: absolute;
    top: 0px;
    right: 22px;
    width: 96px;
  }
  .cancel-button {
    position: absolute;
    top: 0px;
    right: 22px;
    width: 96px;
  }
  .save-button {
    position: absolute;
    top: 0px;
    right: 142px;
    width: 96px;
  }
  .hint{
    position: relative;
    width: auto;
    color: coral;
    line-height: 50px;
    top: 22px;
    background-color: #f6fbff;
    span{
      margin-left: 2px;
    }
  }
  .notice{
    top: -10px;
  }
  .input-unit{
    line-height: 44px;
  }
}
</style>
