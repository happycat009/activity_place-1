<template>
  <div class="toolbar">
    <el-checkbox
      v-model="unSeeChecked"
      :label="`仅展示未读消息（${unSeeNum}项）`"
      size="large"
    ></el-checkbox>
  </div>
</template>

<script>
  export default {
    name: "MessageToolbar",
    props: {
      unSeeNum: {
        type: [Number],
        default: () => 0
      }
    },
    data() {
      return {
        unSeeChecked: false
      }
    },
    watch: {
      unSeeChecked: {
        handler(val) {
          this.$emit("updateParam",{issee: (val ? '0' : '')})
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
.toolbar {
  width: 100%;
  padding: 30px 0;
}
</style>
