<template>
  <div class="yh-agreement">
    <label :for="'id_' + time" @click.stop="() => false">
      <i
        class="iconfont"
        :class="[
          modelValue ? 'iconfont-checkbox-checked' : 'iconfont-checkbox',
        ]"
      ></i>
      <input
        class="checkbox"
        :id="'id_' + time"
        type="checkbox"
        :value="modelValue"
        :checked="modelValue ? true : false"
        @change="handleChange"
      />
      <span>我已阅读</span>
      <span class="agreement" @click="showAgreement">{{agreement}}</span>
      <span>并同意</span>
    </label>
    <!-- 服务协议 -->


    <el-dialog v-model="isShowAgreement" width="800px" @close="handleChange" top="8vh">
      <AgreementContent @closeModel="hideAgreement"></AgreementContent>
    </el-dialog>
  </div>
</template>

<script>
  import AgreementContent from '@/components/common/YhAgreement/AgreementContent.vue'
  export default {
    name: "YhAgreement",
    props: {
      agreement: {
        type: [String],
        default: () => '',
      },
      modelValue: {
        type: [Boolean],
        default: () => true,
      },
      showType:{
        type: [String],
        default: ''
      }
    },
    components: {AgreementContent},
    data() {
      return {
        time: new Date(),
        isShowAgreement: false
      }
    },
    mounted() {
    },
    methods: {
      handleChange(e) {
        this.$emit("handlerAgree",true)
      },
      showAgreement() {
        if(this.showType && this.showType == 'complaintNotice'){
          this.$emit("showModel")
          return
        }
        this.isShowAgreement = true
      },
      hideAgreement() {
        this.isShowAgreement = false
      }
    }
  }
</script>

<style lang="scss" scoped>
.yh-agreement {
  display: inline-block;
  label {
    display: block;
    align-items: center;
    font-size: 14px;
    color: #999;
    line-height: 1;
    cursor: pointer;
  }
  .iconfont {
    margin-right: 8px;
    transition: all 0.3s;
    &.iconfont-checkbox-checked {
      color: var(--color-primary);
    }
  }
  input {
    display: none;
  }
  .agreement {
    color: var(--color-primary);
    cursor: pointer;
    user-select: none;
    transition: all 0.3s;
    &:hover {
      text-decoration: underline;
    }
  }
}
</style>
