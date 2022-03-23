<template>
  <footer class="footer">
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
  </footer>
</template>

<script setup>
const handleJumpRoute = () => {
  window.open('https://beian.miit.gov.cn/#/Integrated/index', '_blank')
}
</script>

<style lang="scss" scoped>
.footer {
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  text-align: center;
  font-size: 14px;
  line-height: 1;
  color: #ffffff;
  padding: 35px 0 35px 0;
  user-select: none;
  display: flex;
  align-items: center;
  justify-content: center;
  .cell {
    display: flex;
    align-items: center;
    margin-left: 30px;
    .value {
      &.primary {
        cursor: pointer;
        &:hover {
          color: var(--color-primary);
          text-decoration: underline;
        }
      }
    }
    &:last-child {
      margin-right: 0;
    }
  }
}
</style>
