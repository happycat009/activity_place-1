<template>
  <div class="yh-tag" :style="tagStyle" :class="[border ? 'border' : '']">
    <span>{{ label }}</span>
  </div>
</template>

<script>
/**
 * tag 组件
 * @param {String} label 文字
 * @param {String} color 颜色 取值rgb 例如：255,0,0
 * @param {String} borderRadius 圆角 取值px
 * @param {Boolean} border 是否带有边框
 */
export default {
  name: 'YhTag',
  props: {
    label: {
      type: [String],
      default: '',
    },
    color: {
      type: [String],
      default: '',
    },
    borderRadius: {
      type: [String],
      default: '',
    },
    width: {
      type: [String],
      default: '',
    },
    height: {
      type: [String],
      default: '',
    },
    border: {
      type: [Boolean],
      default: false,
    },
  },
  setup() {
    return {}
  },
  data() {
    return {}
  },
  created() {},
  mounted() {},
  components: {},
  computed: {
    tagStyle() {
      let str = ''
      const color = this.color
      const borderRadius = this.borderRadius
      const border = this.border
      const width = this.width
      const height = this.height

      if (color) {
        str += `color:rgb(${color}); `
        str += `background-color: rgba(${color}, 0.1);`
      }
      if (width) {
        str += `width:${width}; `
        str += `padding-left: 0; `
        str += `padding-right: 0; `
      }
      if (height) {
        str += `height:${height}; `
        str += `padding-top: 0; `
        str += `padding-bottom: 0; `
      }
      if (border) {
        str += `border-color:rgb(${color}); `
      }
      if (borderRadius) {
        str += `border-radius:${borderRadius}; `
      }

      return str
    },
  },
  methods: {},
}
</script>

<style lang="scss" scoped>
.yh-tag {
  display: inline-block;
  width: auto;
  font-size: 14px;
  padding: 5px 12px;
  border-radius: 50px;
  color: $primary;
  background-color: rgba($primary, 0.1);
  margin-right: 12px;
  user-select: none;
  line-height: 1;
  display: inline-flex;
  span {
    margin: auto;
  }
  &.border {
    border: 1px solid $primary;
  }
  &:last-child {
    margin-right: 0;
  }
}
</style>
