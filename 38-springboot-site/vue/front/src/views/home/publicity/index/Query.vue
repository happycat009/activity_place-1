<template>
  <div class="publicity-query">
    <div class="container">
      <div class="query-wrapper">
        <div class="bg">
          <img src="~assets/images/publicity/index/query/bg.png" alt="" />
        </div>
        <div class="content">
          <YhTitle title="阳光公示" type="03"></YhTitle>
          <div class="form">
            <el-form ref="formRef" :model="form" label-width="74px">
              <div class="el-form-item-cell">
                <el-form-item label="所属区域：" class="el-form-item-area">
                  <div class="el-select-group">
                    <el-select
                      v-model="aab301Info.orgid"
                      placeholder="地级市"
                      class="el-select-district"
                      style="width: 256px"
                      disabled
                    >
                      <template
                      >
                        <el-option
                          :label="aab301Info.orgname"
                          :value="aab301Info.orgid"
                        ></el-option>
                      </template>
                    </el-select>
                    <el-select
                      v-model="form.chb015"
                      placeholder="请选择区县"
                      class="el-select-district"
                      style="width: 256px"
                    >
                      <template
                        v-for="(item, key) in codeMap.chb015"
                        :key="key"
                      >
                        <el-option
                          :label="item.aaa103"
                          :value="item.aaa102"
                        ></el-option>
                      </template>
                      <!--<el-option label="选项2" value="511000"></el-option>-->
                    </el-select>
                    <!--<el-select-->
                    <!--  v-model="form.chb017"-->
                    <!--  placeholder="请选择乡镇"-->
                    <!--  style="width: 256px"-->
                    <!--&gt;-->
                    <!--  <template v-for="(val, key) in chb017List" :key="key">-->
                    <!--    <el-option-->
                    <!--      :label="val.orgname"-->
                    <!--      :value="val.orgid"-->
                    <!--    ></el-option>-->
                    <!--  </template>-->
                    <!--</el-select>-->
                  </div>
                </el-form-item>
                <el-form-item label="项目名称：">
                  <el-select
                    v-model="form.chi031"
                    placeholder="请选择项目名称"
                    style="width: 402px"
                  >
                    <template v-for="(item, key) in codeMap.chi031" :key="key">
                      <el-option
                        :label="item.aaa103"
                        :value="item.aaa102"
                      ></el-option>
                    </template>
                  </el-select>
                </el-form-item>
              </div>

              <div class="el-form-item-cell">
                <el-form-item label="主管部门：" class="el-form-item-issued">
                  <el-select
                    v-model="form.chi037"
                    placeholder="请选择主管部门"
                    style="width: 528px"
                  >
                    <template v-for="(item, key) in codeMap.chi037" :key="key">
                      <el-option
                        :label="item.aaa103"
                        :value="item.aaa102"
                      ></el-option>
                    </template>

                    <!--<el-option label="选项1" value="1"></el-option>-->
                    <!--<el-option label="选项2" value="2"></el-option>-->
                  </el-select>
                </el-form-item>
                <el-form-item label="企业名称：">
                  <el-input
                    style="width: 202px"
                    v-model="form.aab012"
                    placeholder="请输入企业名称"
                  ></el-input>
                </el-form-item>

                <el-form-item>
                  <el-button type="primary" @click="handleSubmit"
                  >查询</el-button
                  >
                  <el-button @click="handleReset">重置</el-button>
                </el-form-item>

              </div>
              <!--<div class="el-form-item-cell el-form-item-cell-button">-->
              <!--  -->
              <!--</div>-->
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import {
  requestGetData,
  requestE407,
  requestGetCollectionData,
} from '@/service'
import { reactive } from 'vue'
export default {
  name: 'PublicityQuery',
  props: {
    form: {
      type: Object,
      default: () => {
        return {
          chb015: '',
          // chb017: '',
          chi031: '',
          aab012: '',
          chi037: '',
        }
      },
    },
  },
  data() {
    return {
      // chb017List: [],
      aab301Info: {
        orgid: "510100",
        orgname: "内江市"
      },
      codeMap: {
        chb015: [],
        chi031: [],
        chi037: [],
      },
    }
  },
  components: {
    YhTitle,
  },
  async mounted() {
    this.codeMap.chb015 = await this.getCodeMap('chb015')
    this.codeMap.chi031 = await this.getCodeMap('chi031')
    this.codeMap.chi037 = await this.getCodeMap('chi037')
  },
  computed: {},
  methods: {
    handleSubmit() {
      this.$emit('handleSubmit')
    },
    handleReset() {
      this.$emit('handleReset')
    },
    async getCodeMap(value) {
      let res = await requestGetCollectionData(value)
      if (res) {
        return res
      }
      this.$message(`码表${value}加载失败`)
      return []
    },
  },
  watch: {
    // 'form.chb015': {
    //   handler(val) {
    //     if(val) {
    //       // 根据chb015加载子组织机构
    //       requestE407(val).then((res) => {
    //         console.log('----------999-------',res)
    //       })
    //       this.chb017List = [{ orgid: '111', orgname: val + '西林街道' }]
    //     }
    //   },
    //   immediate: true,
    //   deep: true,
    // },
  },
}
</script>

<style lang="scss" scoped>
.publicity-query {
  width: 100%;
  .query-wrapper {
    background-color: #fff;
    position: relative;
    .content {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      padding: 20px 24px;
    }
  }
  .form {
    padding-top: 24px;
    padding-left: 12px;
  }

  .el-form-item-cell {
    display: flex;
    align-items: center;
    .el-form-item {
      margin-bottom: 20px;
    }
    .el-select-group {
      display: flex;
      align-items: center;
      .el-select-district {
        margin-right: 16px;
      }
    }
    .el-form-item-issued {
      margin-right: 42px;
    }
    .el-form-item-area {
      margin-right: 24px;
    }
  }
  .el-form-item-cell-button {
    width: 100%;
  }
}
</style>
