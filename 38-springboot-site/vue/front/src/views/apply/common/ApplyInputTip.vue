<template>
  <div class="tip-wrapper">
    <YhTip>
      <span class="red">红色</span>
      <span>号代表必填项，系统已自动为你充填部分数据。数据来源可点击录入框查看</span>
    </YhTip>
  </div>
</template>
<script setup>
import YhTip from '@/components/common/YhTip/YhTip.vue'
</script>
<style lang="scss" scoped>
.tip-wrapper {
  padding: 40px 0;
  .red {
    &::after {
      content: '*';
      color: var(--color-hint);
      padding: 0 4px;
    }
  }
  .show-from{
    display: inline-block;
    line-height: 14px;
    border: 1px solid #fff;
    background: #009dd9;
    height: 20px;
    width: 20px;
    font-size: 12px;
    border-radius: 50%;
    text-align: center;
    color: #fff;
    vertical-align: bottom;
  }
}
</style>
