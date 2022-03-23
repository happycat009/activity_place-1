<template>
  <div class="select-type">
    <div class="tip">请选择以下一种方式验证身份</div>
    <div class="items">
      <div
        class="item"
        :class="[modelValue == '01' ? 'checked' : '']"
        @click="hanldeClick('01')"
      >
        <i class="iconfont iconfont-phone"></i>
        <div class="name">手机号码验证</div>
        <div class="desc">通过您原账号绑定的手机号码进行短信验证</div>
      </div>
      <div
        class="item"
        :class="[modelValue == '02' ? 'checked' : '']"
        @click="hanldeClick('02')"
      >
        <i class="iconfont iconfont-user"></i>
        <div class="name">账号密码验证</div>
        <div class="desc">通过您原账号和密码进行验证</div>
      </div>
    </div>
  </div>
</template>

<script setup>
const emit = defineEmits(['update:modelValue', 'submit'])
const props = defineProps({
  modelValue: {
    type: [String],
    default: () => '',
  },
})

const hanldeClick = (value) => {
  emit('update:modelValue', value)
  emit('submit')
}
</script>

<style lang="scss" scoped>
.select-type {
  width: 100%;
  padding: 60px 0;
  user-select: none;
  .tip {
    text-align: center;
    font-size: 24px;
    font-weight: 500;
    line-height: 1;
    color: #444;
  }
  .items {
    padding: 60px 300px 0;
    .item {
      background-color: #ffffff;
      border: solid 2px #c6ddff;
      margin-bottom: 20px;
      cursor: pointer;
      position: relative;
      padding: 30px;
      padding-left: 100px;
      border-radius: 4px;
      transition: all 0.3s;
      .iconfont {
        position: absolute;
        top: 50%;
        left: 30px;
        font-size: 40px;
        font-weight: 500;
        transform: translateY(-50%);
        color: var(--color-primary);
      }
      .name {
        font-size: 18px;
        color: #333;
        line-height: 1;
        font-weight: 500;
      }
      .desc {
        font-size: 14px;
        color: #999;
        line-height: 1;
        padding-top: 12px;
      }
      &:last-child {
        margin-bottom: 0;
      }
      &.checked,
      &:hover {
        background-color: rgba(#c6ddff, 0.2);
        border-color: var(--color-primary);
      }
    }
  }
}
</style>
