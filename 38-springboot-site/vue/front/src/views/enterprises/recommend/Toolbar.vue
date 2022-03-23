<template>
  <div class="toolbar">
    <el-radio-group v-model="radio" @change="handleChange">
      <template v-for="item in customData.filter">
        <el-radio :label="item.value">{{ item.name }}</el-radio>
      </template>
    </el-radio-group>
  </div>
</template>
<script>
  export default {
    name: 'RecommendToolBar',
    props: {
      radio: {
        type: String,
        required: true,
      },
      recommendNum: {
        type: Number,
        required: true,
        default: () => 0
      },
      overDurNum: {
        type: Number,
        required: true,
        default: () => 0
      }
    },
    data() {
      return {
      }
    },
    methods: {
      handleChange (e) {
        this.$emit('updateRadio', e)
      }
    },
    computed: {
      customData( ) {
        let recommendNum = this.recommendNum
        let overDurNum = this.overDurNum
        return {
          filter: [
            {
              value: 0,
              name: `推荐申报项目（${recommendNum}项）`,
            },
            {
              value: 1,
              name: `已过期申报事项（${overDurNum}项）`,
            },
          ],
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
