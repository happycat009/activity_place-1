<template>
  <footer class="yh-footer">
    <div class="footer">
      <div class="cell">主办单位：内江市纪委监委、内江市财政局</div>
      <div class="cell">
        <div class="key">备案号：</div>
        <div class="value primary" @click="handleJumpRoute">
          蜀ICP1231312313号 - 22
        </div>
      </div>
      <div class="cell">
        <div class="key">技术支持：</div>
        <div class="value">四川久远银海软件股份有限公司</div>
      </div>
    </div>
  </footer>
</template>

<script setup>
const handleJumpRoute = () => {
  window.open('https://beian.miit.gov.cn/#/Integrated/index', '_blank')
}
</script>

<style lang="scss" scoped>
.yh-footer {
  width: 100%;
  position: absolute;
  left: 0;
  bottom: 0;
  height: $footer-height;
  background-color: rgba($primary, 0.2);
  line-height: 1.4;
  text-align: center;
  color: #666;

  .footer {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    user-select: none;
    .cell {
      display: flex;
      align-items: center;
      margin-left: 30px;
      .value {
        &.primary {
          color: var(--color-primary);
          text-decoration: underline;
          cursor: pointer;
        }
      }
      &:last-child {
        margin-right: 0;
      }
    }
  }
}
</style>
